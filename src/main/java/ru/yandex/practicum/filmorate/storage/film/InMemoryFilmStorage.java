package ru.yandex.practicum.filmorate.storage.film;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Data
@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Integer, Film> films = new HashMap<>();

    private Integer id = 0;

    private final static LocalDate limitData = LocalDate.of(1895, 12, 28);

    FilmService filmService;

    @Autowired
    public InMemoryFilmStorage(FilmService filmService) {
        this.filmService = filmService;
    }

    @Override
    public Film createFilm(Film film) {
        checkFilm(film);
        id++;
        film.setId(id);
        if (films.containsKey(film.getId())) {
            throw new IdFilmAlreadyExistException(String.format(
                    "Фильм с данным идентификатором %s уже зарегистрирован.", film.getId()));
        }
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        checkFilm(film);
        if (!(films.containsKey(film.getId()))) {
            throw new IdFilmNotExistException(String.format(
                    "Фильм с данным идентификатором %s не зарегистрирован.", film.getId()));
        }
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Collection<Film> findAllFilm() {
        return films.values();
    }

    @Override
    public Film findFilmById(Integer filmId) {
        if (filmId == null) {
            throw new NullPointerException("Передан некорректный или нулевой id фильма.");
        }
        if (!(films.containsKey(filmId))) {
            throw new IdFilmNotExistException(String.format(
                    "Фильм с данным идентификатором %s не зарегистрирован.", filmId));
        }
        return films.get(filmId);
    }

    @Override
    public void checkFilm(Film film) {
        if (film.getName() == null || film.getName().isBlank()) {
            throw new InvalidNameFilmException("name");
        }
        if (film.getDescription().length() > 201) {
            throw new OverDescriptionFilmException("description");
        }
        if (film.getDescription().isBlank()) {
            throw new EmptyDescriptionFilmException("Пустое описание фильма");
        }
        if (film.getReleaseDate().isBefore(limitData)) {
            throw new InvalidReleaseDateException("releaseDate");
        }
        if (film.getDuration() < 0) {
            throw new InvalidDurationFilmException("duration");
        }
    }
}
