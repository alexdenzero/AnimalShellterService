package pro.sky.animalizer.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import pro.sky.animalizer.exceptions.UserNotFoundException;
import pro.sky.animalizer.model.User;
import pro.sky.animalizer.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void testCreateUser() {
        User user = new User(1L, 111L, "old_nick", "Old Name", "111111111", "OLD123");
        user.setTelegramId(123L);
        user.setTelegramNick("test_user");

        // Мокируем метод save и возвращаем тот же объект пользователя с установленным ID
        given(userRepository.save(any(User.class))).willAnswer(invocation -> {
            User argument = invocation.getArgument(0);
            argument.setId(1L);  // Устанавливаем ID, чтобы объект был не null
            return argument;
        });

        User savedUser = userService.createUser(user);
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getTelegramId()).isEqualTo(user.getTelegramId());
        assertThat(savedUser.getTelegramNick()).isEqualTo(user.getTelegramNick());
    }

    @Test
    public void testFindUserById() {
        User user = new User(1L, 111L, "old_nick", "Old Name", "111111111", "OLD123");
        user.setTelegramId(123L);
        user.setTelegramNick("test_user");

        given(userRepository.findById(1L)).willReturn(Optional.of(user));

        User foundUser = userService.findUserById(1L);
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getTelegramId()).isEqualTo(user.getTelegramId());
        assertThat(foundUser.getTelegramNick()).isEqualTo(user.getTelegramNick());
    }

    @Test
    public void testFindUserById_UserNotFound() {
        given(userRepository.findById(999L)).willReturn(Optional.empty());

        assertThatExceptionOfType(UserNotFoundException.class)
                .isThrownBy(() -> userService.findUserById(999L));
    }

    @Test
    public void testFindUserByTelegramId() {
        User user = new User(1L, 111L, "old_nick", "Old Name", "111111111", "OLD123");
        user.setTelegramId(123L);
        user.setTelegramNick("test_user");

        given(userRepository.findByTelegramId(123L)).willReturn(user);

        User foundUser = userService.findUserByTelegramId(123L);
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getTelegramId()).isEqualTo(user.getTelegramId());
        assertThat(foundUser.getTelegramNick()).isEqualTo(user.getTelegramNick());
    }

    @Test
    public void testGetAllUsers() {
        Page<User> allUsers = new PageImpl<>(List.of(
                new User(1L, 111L, "old_nick", "Old Name", "111111111", "OLD123"),
                new User(2L, 222L, "new_nick", "New Name", "222222222", "NEW456"),
                new User(3L, 123L, "john_doe", "John Doe", "123456789", "ABC123")
        ));

        given(userRepository.findAll(any(Pageable.class))).willReturn(allUsers);

        assertThat(allUsers).isNotEmpty().hasSize(3);
    }

    @Test
    public void testEditUser() {
        User user = new User(1L, 111L, "old_nick", "Old Name", "111111111", "OLD123");
        user.setTelegramId(123L);
        user.setTelegramNick("test_user");

        given(userRepository.findById(1L)).willReturn(Optional.of(user));
        given(userRepository.save(user)).willReturn(user);

        User editedUser = userService.editUser(1L, user);
        assertThat(editedUser).isNotNull();
        assertThat(editedUser.getTelegramId()).isEqualTo(user.getTelegramId());
        assertThat(editedUser.getTelegramNick()).isEqualTo(user.getTelegramNick());
    }

    @Test
    public void testDeleteUserById() {
        // Создаём пользователя для теста
        User user = new User(1L, 111L, "old_nick", "Old Name", "111111111", "OLD123");
        user.setTelegramId(123L);
        user.setTelegramNick("test_user");

        // Мокируем метод findById
        given(userRepository.findById(1L)).willReturn(Optional.of(user));

        // Вызываем метод deleteUserById
        userService.deleteUserById(1L);

        // Проверяем, что метод delete был вызван с правильным пользователем
        verify(userRepository, times(1)).delete(user);
    }
}
