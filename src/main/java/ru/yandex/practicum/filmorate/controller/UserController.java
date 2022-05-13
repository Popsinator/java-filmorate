package ru.yandex.practicum.filmorate.controller;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j//Создаю логгер
@Data
@RestController
public class UserController {

    private final Map<Integer, User> users = new HashMap<>();

    private int id = 0;

    @GetMapping("/users")//Получение списка всех пользователей
    public Collection<User> findAll() {
        log.trace("Текущее количество пользователей: {}", users.size());
        return users.values();
    }

    @PostMapping("/users")//Добавление нового пользователя
    public User create(@RequestBody User user) {
        id++;
        user.setId(id);
        if (!(checkValidationUser(user))) {//Проверяю добавляемый объект - user на соответствие условиям ТЗ
            throw new ValidationException("При создании пользователя произошла ошибка.");
        }
        return user;
    }

    @PutMapping("/users")//Обновление пользователя
    public User put(@RequestBody User user) throws ValidationException {
        if (!(checkValidationUser(user))) {//Проверяю добавляемый объект - user на соответствие условиям ТЗ
            throw new ValidationException("При создании пользователя произошла ошибка.");
        }
        return user;
    }

    //Проверка условиям ТЗ
    public boolean checkValidationUser(@RequestBody User user) {
        //Электронная почта не может быть пустой и должна содержать символ @
        if (user.getEmail() != null && !(user.getEmail().isBlank()) && user.getEmail().contains("@")
                //Логин не может быть пустым и содержать пробелы
                && user.getLogin() != null && !(user.getLogin().isBlank()) && !(user.getLogin().contains(" ")) &&
                user.getBirthday().isBefore(LocalDate.now())) {//Дата рождения не может быть в будущем
            //Имя для отображения может быть пустым — в таком случае будет использован логин
            if (user.getName() == null || user.getName().isBlank()) {
                user.setName(user.getLogin());//Записываю логин в имя
            }
            users.put(user.getId(), user);
            log.trace(String.valueOf(user));
            return true;
        } else
            return false;
    }
}
