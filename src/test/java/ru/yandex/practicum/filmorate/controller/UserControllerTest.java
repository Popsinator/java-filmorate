package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.*;
import ru.yandex.practicum.filmorate.model.User;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    private UserController test;
    private User userTest;


    @BeforeEach
    public void beforeEach() {
        userTest = new User();
        test = new UserController();
        userTest.setName("Alex");
        userTest.setEmail("@pops");
        userTest.setLogin("Popsinator");
        userTest.setBirthday(LocalDate.of(1994, 6, 4));
        test.getUserStorage().createUser(userTest);
    }

    @Test
    void findAll() {
        assertEquals(test.findAll(), test.getUserStorage().getUsers().values());
    }

    @Test
    void createInCorrectB() {//При создании не введено имя
        User userTest2 = new User();
        userTest2.setName("");
        userTest2.setEmail("@test");
        userTest2.setLogin("Testing");
        userTest2.setBirthday(LocalDate.of(1, 1, 1));
        test.getUserStorage().createUser(userTest2);
        assertEquals("Testing", userTest2.getName());
    }

    @Test
    void createInCorrectC() {//При создании email введён некорректно
        User userTest2 = new User();
        userTest2.setName("");
        userTest2.setEmail("test");//Нет @
        userTest2.setLogin("Testing");
        userTest2.setBirthday(LocalDate.of(1, 1, 1));
        final InvalidMaleUserException exception = assertThrows(
                InvalidMaleUserException.class, () -> test.getUserStorage().createUser(userTest2));
        assertEquals("email", exception.getMessage());
    }

    @Test
    void createInCorrectD() {//При создании Д.р. в будущем времени
        User userTest2 = new User();
        userTest2.setName("");
        userTest2.setEmail("@test");
        userTest2.setLogin("Testing");
        userTest2.setBirthday(LocalDate.now().plusDays(3));// Д.р. в будущем времени
        final InvalidBirthdayUserException exception = assertThrows(
                InvalidBirthdayUserException.class, () -> test.getUserStorage().createUser(userTest2));
        assertEquals("birthday", exception.getMessage());
    }

    @Test
    void createInCorrectE() {//При создании логин отсутствует
        User userTest2 = new User();
        userTest2.setName("");
        userTest2.setEmail("@test");
        userTest2.setLogin("");
        userTest2.setBirthday(LocalDate.of(1, 1, 1));
        final InvalidLoginUserException exception = assertThrows(
                InvalidLoginUserException.class, () -> test.getUserStorage().createUser(userTest2));
        assertEquals("login", exception.getMessage());
    }

    @Test
    void createInCorrectF() {//При создании отсутствует email
        User userTest2 = new User();
        userTest2.setName("");
        userTest2.setEmail("");
        userTest2.setLogin("Testing");
        userTest2.setBirthday(LocalDate.of(1, 1, 1));
        final InvalidMaleUserException exception = assertThrows(
                InvalidMaleUserException.class, () -> test.getUserStorage().createUser(userTest2));
        assertEquals("email", exception.getMessage());
    }

    @Test
    void putCorrect() {//Корректное обновление
        User userTest2 = new User();
        userTest2.setName("Alexes");
        userTest2.setEmail("@popsin");
        userTest2.setLogin("Popsinators");
        userTest2.setBirthday(LocalDate.of(1993, 6, 4));
        test.getUserStorage().createUser(userTest2);
        test.getUserStorage().updateUser(userTest2);
        assertEquals(userTest2.getEmail(), test.getUserStorage().getUsers().get(userTest2.getId()).getEmail());
        assertEquals(userTest2.getName(), test.getUserStorage().getUsers().get(userTest2.getId()).getName());
        assertEquals(userTest2.getLogin(), test.getUserStorage().getUsers().get(userTest2.getId()).getLogin());
        assertEquals(userTest2.getBirthday(), test.getUserStorage().getUsers().get(userTest2.getId()).getBirthday());
    }

    @Test
    void putInCorrect() {//Некорректное обновление
        User userTest2 = new User();
        test = new UserController();
        userTest2.setName("Alexes");
        userTest2.setEmail("@email");//Нету email
        userTest2.setLogin("Popsinators");
        userTest2.setBirthday(LocalDate.of(1993, 6, 4));
        test.getUserStorage().createUser(userTest2);
        userTest2.setId(-5);
        final IdUserNotExistException exception = assertThrows(
                IdUserNotExistException.class, () -> test.getUserStorage().updateUser(userTest2));
        assertEquals(String.format("Пользователь с данным идентификатором %s не зарегистрирован.",
                userTest2.getId()), exception.getMessage());
    }
}