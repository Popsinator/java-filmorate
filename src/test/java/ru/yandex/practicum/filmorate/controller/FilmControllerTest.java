package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import java.time.Duration;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {

    private FilmController test;
    private Film filmTest;


    @BeforeEach
    public void beforeEach() {
        filmTest = new Film();
        test = new FilmController();
        filmTest.setName("Film");
        filmTest.setDescription("Качественный");
        filmTest.setReleaseDate(LocalDate.of(2022, 05, 1));
        filmTest.setDuration(Duration.ofMinutes(125));
        test.create(filmTest);
    }

    @Test
    void findAll() {
        assertEquals(test.findAll(), test.getFilms().values());
    }

    @Test
    void createInCorrectB() {//При создании не введено название фильма
        Film filmTest2 = new Film();
        filmTest2.setName("");
        filmTest2.setDescription("Качественный");
        filmTest2.setReleaseDate(LocalDate.of(2022, 05, 1));
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
        filmTest2.setReleaseDate(LocalDate.of(2022, 05, 1));
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
        filmTest2.setReleaseDate(LocalDate.of(1, 1, 1));
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
        filmTest2.setReleaseDate(LocalDate.of(2022, 5, 1));
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
        filmTest2.setReleaseDate(LocalDate.of(2021, 5, 1));
        filmTest2.setDuration(Duration.ofMinutes(127));
        test.put(filmTest2);
        assertEquals(filmTest2.getName(), test.getFilms().get(filmTest2.getId()).getName());
        assertEquals(filmTest2.getDescription(),  test.getFilms().get(filmTest2.getId()).getDescription());
        assertEquals(filmTest2.getReleaseDate(),  test.getFilms().get(filmTest2.getId()).getReleaseDate());
        assertEquals(filmTest2.getDuration(),  test.getFilms().get(filmTest2.getId()).getDuration());
    }

    @Test
    void putInCorrect() {//Некорректное обновление
        Film filmTest2 = new Film();
        filmTest2.setName("Films");
        filmTest2.setDescription("Очень Качественный");
        filmTest2.setReleaseDate(LocalDate.of(2021, 5, 1));
        filmTest2.setDuration(Duration.ofMinutes(-127));
        final ValidationException exception = assertThrows(
                ValidationException.class, () -> test.put(filmTest2));
        assertEquals("При создании фильма произошла ошибка.", exception.getMessage());
    }
}