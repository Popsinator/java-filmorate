package ru.yandex.practicum.filmorate.storage.user;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Data
@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Integer, User> users = new HashMap<>();

    private Integer id = 0;

    private final static LocalDate limitData = LocalDate.of(1895, 12, 28);

    UserService userService;

    @Autowired
    public InMemoryUserStorage(UserService userService) {
        this.userService = userService;
    }

    @Override
    public User createUser(User user) {
        checkUser(user);
        id++;
        user.setId(id);
        if (users.containsKey(user.getId())) {
            throw new IdUserAlreadyExistException(String.format(
                    "Пользователь с данным идентификатором %s уже зарегистрирован.", user.getId()));
        }
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        checkUser(user);
        if (!(users.containsKey(user.getId()))) {
            throw new IdUserNotExistException(String.format(
                    "Пользователь с данным идентификатором %s не зарегистрирован.", user.getId()));
        }
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public Collection<User> findAllUser() {
        return users.values();
    }

    @Override
    public User findUserById(Integer userId) {
        if (!(users.containsKey(userId))) {
            throw new IdUserNotExistException(String.format(
                    "Пользователь с данным идентификатором %s не зарегистрирован.", userId));
        }
        if (userId == null) {
            throw new NullPointerException("Передан некорректный или нулевой id пользователя.");
        }
        return users.get(userId);
    }

    @Override
    public void checkUser(User user) {
        if (user.getEmail() == null || user.getEmail().isBlank() || !(user.getEmail().contains("@"))) {
            throw new InvalidMaleUserException("email");
        }
        if (user.getLogin() == null || user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            throw new InvalidLoginUserException("login");
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new InvalidBirthdayUserException("birthday");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());//Записываю логин в имя
        }
    }
}