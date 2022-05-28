package ru.yandex.practicum.filmorate.controller;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import java.util.Collection;
import java.util.List;

@Slf4j
@Data
@RestController
public class FilmController {

    InMemoryFilmStorage storageFilm = new InMemoryFilmStorage(new FilmService());

    @GetMapping("/films")//Получение всех фильмов
    public Collection<Film> findAll() {
        log.debug("Текущее количество фильмов: {}", storageFilm.getFilms().size());
        return storageFilm.findAllFilm();
    }

    @PostMapping("/films")//Добавление нового фильма
    public Film create(@RequestBody Film film) {
        return storageFilm.createFilm(film);
    }

    @PutMapping("/films")//Обновление существующего фильма
    public Film put(@RequestBody Film film) {
        return storageFilm.updateFilm(film);
    }

    @GetMapping("/films/{filmId}")//Получение фильма по id
    public Film findPost(@PathVariable("filmId") Integer filmId) {
        return storageFilm.findFilmById(filmId);
    }


    @PutMapping("/films/{id}/like/{userId}")//Добавление лайка к фильму
    public Film put(@PathVariable("id") Integer id,
                    @PathVariable("userId") Integer userId) {
        storageFilm.getFilmService().addLike(storageFilm.findFilmById(id), userId);
        return storageFilm.findFilmById(id);
    }

    @DeleteMapping("/films/{id}/like/{userId}")//Удаление лайка у фильма
    public Film deleteFilms(@PathVariable("id") Integer id,
                            @PathVariable("userId") Integer userId) {
        storageFilm.getFilmService().deleteLike(storageFilm.findFilmById(id), userId);
        return storageFilm.findFilmById(id);
    }

    @GetMapping("/films/popular")//Получение списка наиболее популярных фильмов
    public List<Film> findListPopularFilm(@RequestParam(value = "count", defaultValue = "10", required = false) Integer count) {
        return storageFilm.getFilmService().outListPopularFilm(storageFilm.findAllFilm(), count);
    }
}
