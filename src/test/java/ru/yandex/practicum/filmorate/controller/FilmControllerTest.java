package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.*;
import ru.yandex.practicum.filmorate.model.Film;
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
        filmTest.setDuration(125);
        test.getStorageFilm().createFilm(filmTest);
    }

    @Test
    void findAll() {
        assertEquals(test.getStorageFilm().findAllFilm(), test.getStorageFilm().getFilms().values());
    }

    @Test
    void createInCorrectB() {//При создании не введено название фильма
        Film filmTest2 = new Film();
        filmTest2.setName("");
        filmTest2.setDescription("Качественный");
        filmTest2.setReleaseDate(LocalDate.of(2022, 05, 1));
        filmTest2.setDuration(125);
        final InvalidNameFilmException exception = assertThrows(
                InvalidNameFilmException.class, () -> test.getStorageFilm().createFilm(filmTest2));
        assertEquals("name", exception.getMessage());
    }

    @Test
    void createInCorrectC() {//При создании введено слишком большое описание
        Film filmTest2 = new Film();
        filmTest2.setName("Film");
        filmTest2.setDescription("Описание этого фильма запредеееееееееееееееееееееееееееееееееееееееееееееееееееееее" +
                "еееееееееееееееееееееееееееееееееееееееееееееееееееееееееееееееееееееееельно огроооооооооооооооооооо" +
                "оооооооооооооооооооооооооооомное");
        filmTest2.setReleaseDate(LocalDate.of(2022, 05, 1));
        filmTest2.setDuration(125);
        final OverDescriptionFilmException exception = assertThrows(
                OverDescriptionFilmException.class, () -> test.getStorageFilm().createFilm(filmTest2));
        assertEquals("description", exception.getMessage());
    }

    @Test
    void createInCorrectD() {//Фильм вышел очень давно
        Film filmTest2 = new Film();
        filmTest2.setName("Film");
        filmTest2.setDescription("Качественный");
        filmTest2.setReleaseDate(LocalDate.of(1, 1, 1));
        filmTest2.setDuration(125);
        final InvalidReleaseDateException exception = assertThrows(
                InvalidReleaseDateException.class, () -> test.getStorageFilm().createFilm(filmTest2));
        assertEquals("releaseDate", exception.getMessage());
    }

    @Test
    void createInCorrectE() {//Фильм с отрицательной продолжительностью
        Film filmTest2 = new Film();
        filmTest2.setName("Film");
        filmTest2.setDescription("Качественный");
        filmTest2.setReleaseDate(LocalDate.of(2022, 5, 1));
        filmTest2.setDuration(-125);
        final InvalidDurationFilmException exception = assertThrows(
                InvalidDurationFilmException.class, () -> test.getStorageFilm().createFilm(filmTest2));
        assertEquals("duration", exception.getMessage());
    }

    @Test
    void putCorrect() {//Корректное обновление
        Film filmTest2 = new Film();
        filmTest2.setName("Films");
        filmTest2.setDescription("Очень Качественный");
        filmTest2.setReleaseDate(LocalDate.of(2021, 5, 1));
        filmTest2.setDuration(127);
        test.getStorageFilm().createFilm(filmTest2);
        test.getStorageFilm().updateFilm(filmTest2);
        assertEquals(filmTest2.getName(), test.getStorageFilm().getFilms().get(filmTest2.getId()).getName());
        assertEquals(filmTest2.getDescription(),  test.getStorageFilm().getFilms().get(filmTest2.getId()).getDescription());
        assertEquals(filmTest2.getReleaseDate(),  test.getStorageFilm().getFilms().get(filmTest2.getId()).getReleaseDate());
        assertEquals(filmTest2.getDuration(),  test.getStorageFilm().getFilms().get(filmTest2.getId()).getDuration());
    }

    @Test
    void putInCorrect() {//Некорректное обновление
        Film filmTest2 = new Film();
        filmTest2.setName("Films");
        filmTest2.setDescription("Очень Качественный");
        filmTest2.setReleaseDate(LocalDate.of(2021, 5, 1));
        filmTest2.setDuration(127);
        filmTest2.setId(-5);
        final IdFilmNotExistException exception = assertThrows(
                IdFilmNotExistException.class, () -> test.getStorageFilm().updateFilm(filmTest2));
        assertEquals(String.format("Фильм с данным идентификатором %s не зарегистрирован.", filmTest2.getId()),
                exception.getMessage());
    }
}