package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
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
        test.create(userTest);
    }

    @Test
    void findAll() {
        assertEquals(test.findAll(), test.getUsers().values());
    }

    @Test
    void createInCorrectB() throws Exception {//При создании не введено имя
        User userTest2 = new User();
        userTest2.setName("");
        userTest2.setEmail("@test");
        userTest2.setLogin("Testing");
        userTest2.setBirthday(LocalDate.of(1, 1, 1));
        test.create(userTest2);
        assertEquals("Testing", userTest2.getName());
    }

    @Test
    void createInCorrectC() throws Exception {//При создании email введён некорректно
        User userTest2 = new User();
        userTest2.setName("");
        userTest2.setEmail("test");//Нет @
        userTest2.setLogin("Testing");
        userTest2.setBirthday(LocalDate.of(1, 1, 1));
        final ValidationException exception = assertThrows(
                ValidationException.class, () -> test.create(userTest2));
        assertEquals("При создании пользователя произошла ошибка.", exception.getMessage());
    }

    @Test
    void createInCorrectD() throws Exception {//При создании Д.р. в будущем времени
        User userTest2 = new User();
        userTest2.setName("");
        userTest2.setEmail("@test");
        userTest2.setLogin("Testing");
        userTest2.setBirthday(LocalDate.now().plusDays(3));// Д.р. в будущем времени
        final ValidationException exception = assertThrows(
                ValidationException.class, () -> test.create(userTest2));
        assertEquals("При создании пользователя произошла ошибка.", exception.getMessage());
    }

    @Test
    void createInCorrectE() throws Exception {//При создании логин отсутствует
        User userTest2 = new User();
        userTest2.setName("");
        userTest2.setEmail("@test");
        userTest2.setLogin("");
        userTest2.setBirthday(LocalDate.of(1, 1, 1));
        final ValidationException exception = assertThrows(
                ValidationException.class, () -> test.create(userTest2));
        assertEquals("При создании пользователя произошла ошибка.", exception.getMessage());
    }

    @Test
    void createInCorrectF() throws Exception {//При создании отсутствует email
        User userTest2 = new User();
        userTest2.setName("");
        userTest2.setEmail("");
        userTest2.setLogin("Testing");
        userTest2.setBirthday(LocalDate.of(1, 1, 1));
        final ValidationException exception = assertThrows(
                ValidationException.class, () -> test.create(userTest2));
        assertEquals("При создании пользователя произошла ошибка.", exception.getMessage());
    }

    @Test
    void putCorrect() {//Корректное обновление
        User userTest2 = new User();
        userTest2.setName("Alexes");
        userTest2.setEmail("@popsin");
        userTest2.setLogin("Popsinators");
        userTest2.setBirthday(LocalDate.of(1993, 6, 4));
        test.put(userTest2);
        assertEquals(userTest2.getEmail(), test.getUsers().get(userTest2.getId()).getEmail());
        assertEquals(userTest2.getName(), test.getUsers().get(userTest2.getId()).getName());
        assertEquals(userTest2.getLogin(), test.getUsers().get(userTest2.getId()).getLogin());
        assertEquals(userTest2.getBirthday(), test.getUsers().get(userTest2.getId()).getBirthday());
    }

    @Test
    void putInCorrect() {//Некорректное обновление
        User userTest2 = new User();
        test = new UserController();
        userTest2.setName("Alexes");
        userTest2.setEmail("");//Нету email
        userTest2.setLogin("Popsinators");
        userTest2.setBirthday(LocalDate.of(1993, 6, 4));
        final ValidationException exception = assertThrows(
                ValidationException.class, () -> test.put(userTest2));
        assertEquals("При создании пользователя произошла ошибка.", exception.getMessage());
    }
}