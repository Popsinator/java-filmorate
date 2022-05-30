package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {

    Film createFilm(Film film);

    Film updateFilm(Film film);

    Collection<Film> findAllFilm();

    Film findFilmById(Integer postId);

    void checkFilm(Film film);
}
