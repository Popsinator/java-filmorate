package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.exception.ValidationExceptionExistId;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.Duration;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {

    private FilmController test;
    private Film filmTest;


    @BeforeEach
    public void beforeEach() throws Exception {
        filmTest = new Film();
        test = new FilmController();
        filmTest.setName("Film");
        filmTest.setDescription("Качественный");
        filmTest.setId(1);
        filmTest.setDateRelease(LocalDate.of(2022, 05, 1));
        filmTest.setDuration(Duration.ofMinutes(125));
        test.create(filmTest);
    }

    @Test
    void findAll() {
        assertEquals(test.findAll(), test.getFilms().values());
    }

    @Test
    void createInCorrectA() {//При создании введён существующий id
        Film filmTest2 = new Film();
        filmTest2.setName("Film");
        filmTest2.setDescription("Качественный");
        filmTest2.setId(1);
        filmTest2.setDateRelease(LocalDate.of(2022, 05, 1));
        filmTest2.setDuration(Duration.ofMinutes(125));
        final ValidationExceptionExistId exception = assertThrows(
                ValidationExceptionExistId.class, () -> test.create(filmTest2));
        assertEquals("При добавлении нового film указан уже существующий film.id "
                + filmTest2.getId(), exception.getMessage());
    }

    @Test
    void createInCorrectB() {//При создании не введено название фильма
        Film filmTest2 = new Film();
        filmTest2.setName("");
        filmTest2.setDescription("Качественный");
        filmTest2.setId(2);
        filmTest2.setDateRelease(LocalDate.of(2022, 05, 1));
        filmTest2.setDuration(Duration.ofMinutes(125));
        final ValidationException exception = assertThrows(
                ValidationException.class, () -> test.create(filmTest2));
        assertEquals("При создании фильма произошла ошибка.", exception.getMessage());
    }

    @Test
    void createInCorrectC() {//При создании введено слишком большое описание
        Film filmTest2 = new Film();
        filmTest2.setName("Film");
        filmTest2.setDescription("Описание этого фильма запредеееееееееееееееееееееееееееееееееееееееееееееееееееееее" +
                "еееееееееееееееееееееееееееееееееееееееееееееееееееееееееееееееееееееееельно огроооооооооооооооооооо" +
                "оооооооооооооооооооооооооооомное");
        int c = filmTest2.getDescription().length();
        filmTest2.setId(2);
        filmTest2.setDateRelease(LocalDate.of(2022, 05, 1));
        filmTest2.setDuration(Duration.ofMinutes(125));
        final ValidationException exception = assertThrows(
                ValidationException.class, () -> test.create(filmTest2));
        assertEquals("При создании фильма произошла ошибка.", exception.getMessage());
    }

    @Test
    void createInCorrectD() {//Фильм вышел очень давно
        Film filmTest2 = new Film();
        filmTest2.setName("Film");
        filmTest2.setDescription("Качественный");
        filmTest2.setId(2);
        filmTest2.setDateRelease(LocalDate.of(1, 1, 1));
        filmTest2.setDuration(Duration.ofMinutes(125));
        final ValidationException exception = assertThrows(
                ValidationException.class, () -> test.create(filmTest2));
        assertEquals("При создании фильма произошла ошибка.", exception.getMessage());
    }

    @Test
    void createInCorrectE() {//Фильм с отрицательной продолжительностью
        Film filmTest2 = new Film();
        filmTest2.setName("Film");
        filmTest2.setDescription("Качественный");
        filmTest2.setId(2);
        filmTest2.setDateRelease(LocalDate.of(2022, 5, 1));
        filmTest2.setDuration(Duration.ofMinutes(-125));
        final ValidationException exception = assertThrows(
                ValidationException.class, () -> test.create(filmTest2));
        assertEquals("При создании фильма произошла ошибка.", exception.getMessage());
    }

    @Test
    void putCorrect() {//Корректное обновление
        Film filmTest2 = new Film();
        filmTest2.setName("Films");
        filmTest2.setDescription("Очень Качественный");
        filmTest2.setId(1);
        filmTest2.setDateRelease(LocalDate.of(2021, 5, 1));
        filmTest2.setDuration(Duration.ofMinutes(127));
        test.put(filmTest2);
        assertEquals(filmTest2.getName(), test.getFilms().get(1).getName());
        assertEquals(filmTest2.getDescription(),  test.getFilms().get(1).getDescription());
        assertEquals(filmTest2.getDateRelease(),  test.getFilms().get(1).getDateRelease());
        assertEquals(filmTest2.getDuration(),  test.getFilms().get(1).getDuration());
    }

    @Test
    void putInCorrect() {//Некорректное обновление
        Film filmTest2 = new Film();
        filmTest2.setName("Films");
        filmTest2.setDescription("Очень Качественный");
        filmTest2.setId(1);
        filmTest2.setDateRelease(LocalDate.of(2021, 5, 1));
        filmTest2.setDuration(Duration.ofMinutes(-127));
        final ValidationException exception = assertThrows(
                ValidationException.class, () -> test.put(filmTest2));
        assertEquals("При создании фильма произошла ошибка.", exception.getMessage());
    }
}