package pro.sky.animalizer.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.animalizer.exceptions.ShelterNotFoundException;
import pro.sky.animalizer.model.ButtonType;
import pro.sky.animalizer.model.Shelter;
import pro.sky.animalizer.model.ShelterType;
import pro.sky.animalizer.model.User;
import pro.sky.animalizer.service.ShelterService;
import pro.sky.animalizer.service.UserService;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pro.sky.animalizer.util.MenuUtil;

/**
 * Класс, уведомляемый о событии. <br>
 * Он должен быть зарегистрирован источником событий
 * и реализовывать методы для получения и обработки уведомлений.
 */
@Service
public class TelegramBotUpdatesListener implements UpdatesListener {
    private final Pattern pattern = Pattern.compile("(^[А-я]+)\\s+(\\d{11})");
    private final TelegramBot telegramBot;
    private final UserService userService;
    private final ShelterService shelterService;
    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    private final MenuUtil menuUtil;


    public TelegramBotUpdatesListener(TelegramBot telegramBot, UserService userService, ShelterService shelterService, MenuUtil menuUtil) {
        this.telegramBot = telegramBot;
        this.userService = userService;
        this.shelterService = shelterService;
        this.menuUtil = menuUtil;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        try {
            updates.forEach(this::processUpdate);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private void processUpdate(Update update) {
        logger.info("Processing update: {}", update);
        if (update.message() != null) {
            sendStartMessage(update);
        }
        createButtonClick(update);
    }

    private void sendStartMessage(Update update) {
        Message message = update.message();
        Long chatId = message.from().id();
        String text = message.text();
        String firstName = update.message().from().firstName();
        String userName = update.message().from().username();
        long telegramId = update.message().from().id();
        if ("/start".equalsIgnoreCase(text)) {
            telegramBot.execute(new SendMessage(chatId, "Привет, " + firstName + "! Добро пожаловать в меню бота приюта для животных!"));
            getMenuWithShelterPicking(chatId);
        } else {
            updateUser(update);
        }
    }

    /**
     * Метод, обрабатывающий резултаты нажатия на кнопки меню.
     */
    private void createButtonClick(Update update) {
        CallbackQuery callbackQuery = update.callbackQuery();
        if (callbackQuery != null) {
            long chatId = callbackQuery.message().chat().id();
            String data = callbackQuery.data();
            Shelter catsShelter = shelterService.getAllShelters().stream()
                    .filter(shelter -> shelter.getShelterType().equals(ShelterType.CAT))
                    .findFirst()
                    .orElseThrow(ShelterNotFoundException::new);
            Shelter dogsShelter = shelterService.getAllShelters().stream()
                    .filter(shelter -> shelter.getShelterType().equals(ShelterType.DOG))
                    .findFirst()
                    .orElseThrow(ShelterNotFoundException::new);
            switch (buttonType) {
                case CAT_SHELTER:
                    getMenuAfterCatsShelterPicking(chatId);
                    break;
                case DOG_SHELTER:
                    getMenuAfterDogsShelterPicking(chatId);
                    break;
                case CAT_SHELTER_INFO:
                    getMenuWithCatsShelterOptions(chatId);
                    break;
                case DOG_SHELTER_INFO:
                    getMenuWithDogsShelterOptions(chatId);
                    break;
                case CAT_SHELTER_INFORMATION:
                    telegramBot.execute(new SendMessage(update.callbackQuery().from().id(), "Some cat's info"));
                    break;
                case DOG_SHELTER_INFORMATION:
                    telegramBot.execute(new SendMessage(update.callbackQuery().from().id(), "Some dog's info"));
                    break;
                case CAT_SHELTER_SCHEDULE:
                    telegramBot.execute(new SendMessage(update.callbackQuery().from().id(), catsShelter.getSchedule()));
                    break;
                case DOG_SHELTER_SCHEDULE:
                    telegramBot.execute(new SendMessage(update.callbackQuery().from().id(), dogsShelter.getSchedule()));
                    break;
                case CAT_SHELTER_ADDRESS:
                    telegramBot.execute(new SendMessage(update.callbackQuery().from().id(), catsShelter.getAddress()));
                    break;
                case DOG_SHELTER_ADDRESS:
                    telegramBot.execute(new SendMessage(update.callbackQuery().from().id(), dogsShelter.getAddress()));
                    break;
                case CAT_DIRECTION_PATH:
                    telegramBot.execute(new SendMessage(update.callbackQuery().from().id(), catsShelter.getDirectionPathFile()));
                    break;
                case DOG_DIRECTION_PATH:
                    telegramBot.execute(new SendMessage(update.callbackQuery().from().id(), dogsShelter.getDirectionPathFile()));
                    break;
                case CAT_SECURITY_CONTACT:
                    telegramBot.execute(new SendMessage(update.callbackQuery().from().id(), catsShelter.getSecurityPhoneNumber()));
                    break;
                case DOG_SECURITY_CONTACT:
                    telegramBot.execute(new SendMessage(update.callbackQuery().from().id(), dogsShelter.getSecurityPhoneNumber()));
                    break;
                case CAT_SAFETY_MEASURES:
                    telegramBot.execute(new SendMessage(update.callbackQuery().from().id(), catsShelter.getSafetyMeasures()));
                    break;
                case DOG_SAFETY_MEASURES:
                    telegramBot.execute(new SendMessage(update.callbackQuery().from().id(), dogsShelter.getSafetyMeasures()));
                    break;
                case GET_PERSONAL_INFO:
                    telegramBot.execute(new SendMessage(chatId, "Send your name and phone number with country code (without plus), please"));
                    break;
                case CAT_ADOPTION_INFO:
                    telegramBot.execute(new SendMessage(update.callbackQuery().from().id(), "CatAdoptionInfo"));
                    break;
                case DOG_ADOPTION_INFO:
                    telegramBot.execute(new SendMessage(update.callbackQuery().from().id(), "DogAdoptionInfo"));
                    break;
                case REPORT_SENDING:
                    telegramBot.execute(new SendMessage(update.callbackQuery().from().id(), "Report taker"));
                    break;
                case VOLUNTEER_CALLING:
                    telegramBot.execute(new SendMessage(update.callbackQuery().from().id(), "Volunteer caller"));
                    break;
            }
        }
    }

    private void updateUser(Update update) {
        Message message = update.message();
        long chatId = message.chat().id();
        String text = message.text();
        long telegramId = message.from().id();
        String telegramNick = message.from().username();
        String fullName;
        String phoneNumber;
        if (text != null) {
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                fullName = matcher.group(1);
                phoneNumber = matcher.group(2);
                User user = new User(telegramId, telegramNick, fullName, phoneNumber);
                telegramBot.execute(new SendMessage(chatId, user.toString()));
            } else {
                telegramBot.execute(new SendMessage(chatId, "Incorrect output"));
            }
        }
    }

    /**
     * Метод для отправки меню c выбором приюта для нового пользователя.
     * Использует {@link MenuUtil#createMenuWithShelterPicking()} для формирования меню. <br>
     * Результат отправляется через бота с использованием {@link TelegramBot#execute(BaseRequest)}.
     *
     * @param chatId идентификатор чата, для которого генерируется меню.
     */
    private void getMenuWithShelterPicking(Long chatId) {
        SendMessage sendMessage =
                new SendMessage(chatId, "Выберите, пожалуйста, приют!");
        sendMessage.replyMarkup(MenuUtil.createMenuWithShelterPicking());
        SendResponse sendResponse = telegramBot.execute(sendMessage);
        if (!sendResponse.isOk()) {
            logger.error("Error during sending message: {}", sendResponse.description());
        }
    }

    /**
     * Метод для отправки пользователю меню с выбором действий в приюте для кошек.
     * Использует {@link MenuUtil#createMenuWithCatsShelterOption()} для формирования меню. <br>
     * Результат отправляется через бота с использованием {@link TelegramBot#execute(BaseRequest)}.
     *
     * @param chatId идентификатор чата, для которого генерируется меню.
     */
    private void getMenuWithCatsShelterOptions(Long chatId) {
        SendMessage sendMessage =
                new SendMessage(chatId, "Pick the cat's shelter option, please: ");
        sendMessage.replyMarkup(MenuUtil.createMenuWithCatsShelterOption());
        SendResponse sendResponse = telegramBot.execute(sendMessage);
        if (!sendResponse.isOk()) {
            logger.error("Error during sending message: {}", sendResponse.description());
        }
    }

    /**
     * Метод для отправки пользователю меню с выбором действий в приюте для собак.
     * Использует {@link MenuUtil#createMenuWithDogsShelterOptions()} для формирования меню. <br>
     * Результат отправляется через бота с использованием {@link TelegramBot#execute(BaseRequest)}.
     *
     * @param chatId идентификатор чата, для которого генерируется меню.
     */
    private void getMenuWithDogsShelterOptions(Long chatId) {
        SendMessage sendMessage =
                new SendMessage(chatId, "Pick the dog's shelter option, please: ");
        sendMessage.replyMarkup(MenuUtil.createMenuWithDogsShelterOptions());
        SendResponse sendResponse = telegramBot.execute(sendMessage);
        if (!sendResponse.isOk()) {
            logger.error("Error during sending message: {}", sendResponse.description());
        }
    }

    /**
     * Метод для отправки пользователю меню с выбором действий после выбора приюта для собак.
     * Использует {@link MenuUtil#createMenuAfterDogsShelterPick()} для формирования меню. <br>
     * Результат отправляется через бота с использованием {@link TelegramBot#execute(BaseRequest)}.
     *
     * @param chatId идентификатор чата, для которого генерируется меню.
     */
    private void getMenuAfterDogsShelterPicking(Long chatId) {
        SendMessage sendMessage =
                new SendMessage(chatId, "You've picked dog's shelter. Pick the action, please:");
        sendMessage.replyMarkup(MenuUtil.createMenuAfterDogsShelterPick());
        SendResponse sendResponse = telegramBot.execute(sendMessage);
        if (!sendResponse.isOk()) {
            logger.error("Error during sending message: {}", sendResponse.description());
        }
    }

    /**
     * Метод для отправки пользователю меню с выбором действий после выбора приюта для кошек.
     * Использует {@link MenuUtil#createMenuAfterCatsShelterPick()} для формирования меню. <br>
     * Результат отправляется через бота с использованием {@link TelegramBot#execute(BaseRequest)}.
     *
     * @param chatId идентификатор чата, для которого генерируется меню.
     */
    private void getMenuAfterCatsShelterPicking(Long chatId) {
        SendMessage sendMessage =
                new SendMessage(chatId, "You've picked cat's shelter. Pick the action, please:");
        sendMessage.replyMarkup(MenuUtil.createMenuAfterCatsShelterPick());
        SendResponse sendResponse = telegramBot.execute(sendMessage);
        if (!sendResponse.isOk()) {
            logger.error("Error during sending message: {}", sendResponse.description());
        }
    }
}