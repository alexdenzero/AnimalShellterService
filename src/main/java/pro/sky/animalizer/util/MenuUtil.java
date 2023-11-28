package pro.sky.animalizer.util;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import org.springframework.stereotype.Component;

@Component
public class MenuUtil {

    /**
     * Метод для создания меню выбора приюта, содержащего опции "Cat's shelter" и "Dog's shelter".
     * Возвращает объект {@link InlineKeyboardMarkup}, представляющий собой клавиатуру с этими опциями.
     *
     * @return InlineKeyboardMarkup с опциями выбора приюта.
     */

    public static InlineKeyboardMarkup createMenuWithShelterPicking() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Cat's shelter").callbackData("cat's shelter"),
                new InlineKeyboardButton("Dog's shelter").callbackData("dog's shelter"));
        return inlineKeyboardMarkup;
    }

    /**
     * Метод, генерирующий клавиатуру для выбора действия внутри меню приюта для кошек.<br>
     * #{@link InlineKeyboardMarkup#addRow(InlineKeyboardButton...)} <br>
     *
     * @return InlineKeyboardMarkup
     */
    public static InlineKeyboardMarkup createMenuAfterCatsShelterPick() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Get cat's shelter info").callbackData("cat's shelter info"),
                new InlineKeyboardButton("How to adopt a cat").callbackData("cat adoption info"));
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Send a report").callbackData("report sending"),
                new InlineKeyboardButton("Call the volunteer").callbackData("volunteer calling"));
        return inlineKeyboardMarkup;
    }

    /**
     * Метод, генерирующий клавиатуру для выбора действия внутри меню приюта для собак.<br>
     * #{@link InlineKeyboardMarkup#addRow(InlineKeyboardButton...)} <br>
     *
     * @return InlineKeyboardMarkup
     */
    public static InlineKeyboardMarkup createMenuAfterDogsShelterPick() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Get dog's shelter info").callbackData("dog's shelter info"),
                new InlineKeyboardButton("How to adopt a dog").callbackData("dog adoption info"));
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Send a report").callbackData("report sending"),
                new InlineKeyboardButton("Call the volunteer").callbackData("volunteer calling"));
        return inlineKeyboardMarkup;
    }

    public static InlineKeyboardMarkup createMenuWithCatsShelterOption() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Get general cat's shelter information").callbackData("cat's shelter information"),
                new InlineKeyboardButton("Get cat's shelter schedule").callbackData("cat's shelter schedule"));
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Get cat's shelter address").callbackData("cat's shelter address"),
                new InlineKeyboardButton("Get cat's shelter direction path").callbackData("cat's direction path"));
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Get cat's security contact").callbackData("cat's security contact"),
                new InlineKeyboardButton("Get cat's safety measures").callbackData("cat's safety measures"));
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Give personal info").callbackData("get personal info"),
                new InlineKeyboardButton("Call the volunteer").callbackData("volunteer calling"));
        return inlineKeyboardMarkup;
    }

    public static InlineKeyboardMarkup createMenuWithDogsShelterOptions() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Get general dog's shelter information").callbackData("dog's shelter information"),
                new InlineKeyboardButton("Get dog's shelter schedule").callbackData("dog's shelter schedule"));
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Get dog's shelter address").callbackData("dog's shelter address"),
                new InlineKeyboardButton("Get dog's shelter direction path").callbackData("dog's direction path"));
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Get dog's security contact").callbackData("dog's security contact"),
                new InlineKeyboardButton("Get dog's safety measures").callbackData("dog's safety measures"));
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Give personal info").callbackData("get personal info"),
                new InlineKeyboardButton("Call the volunteer").callbackData("volunteer calling"));
        return inlineKeyboardMarkup;
    }
}
