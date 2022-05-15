package ru.yandex.practicum.filmorate.controller;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Data
@RestController
public class FilmController {

    private final Map<Integer, Film> films = new HashMap<>();

    private int id = 0;

    //Граница для даты релиза
    private final static LocalDate limitData = LocalDate.of(1895, 12, 28);

    @GetMapping("/films")//Получение всех фильмов
    public Collection<Film> findAll() {
        log.debug("Текущее количество фильмов: {}", films.size());
        return films.values();
    }

    @PostMapping("/films")//Добавление нового фильма
    public Film create(@RequestBody Film film) {
        id++;
        film.setId(id);
        if (!(checkValidationFilm(film))) {//Проверяю добавляемый объект - film на соответствие условиям ТЗ
            throw new ValidationException("При создании фильма произошла ошибка.");
        }
        return film;
    }

    @PutMapping("/films")//Обновление существующего фильма
    public Film put(@RequestBody Film film) {
        if (!(checkValidationFilm(film))) {//Проверяю добавляемый объект - user на соответствие условиям ТЗ
            throw new ValidationException("При создании фильма произошла ошибка.");
        }
        return film;
    }

    //Проверка условиям ТЗ
    private boolean checkValidationFilm(Film film) {
        if ((film.getName() != null && !(film.getName().isBlank())) //1. Название не может быть пустым;
                && film.getDescription().length() < 201 //2. Максимальная длина описания — 200 символов
                && !(film.getDescription().isBlank()) //Проверяю, что описание не пустое, для теста на Гите
                && film.getReleaseDate().isAfter(limitData) //3. Дата релиза — не раньше 28 декабря 1895 года
                && !(film.getDuration().isNegative())) {//4. Продолжительность фильма должна быть положительной
            films.put(film.getId(), film);
            log.trace(String.valueOf(film));
            return true;
        } else
            return false;
    }
}
