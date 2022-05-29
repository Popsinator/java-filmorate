package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.IdUserNotExistException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FilmService {

    public void addLike(Film film, Integer id) {
        film.getListLikes().add(id);//Добавляю лайк
    }

    public void deleteLike(Film film, Integer id) {
        if (id > 0) {//Проверяю корректность id
            film.getListLikes().remove(id);//Удаляю лайк
        } else {
            throw new IdUserNotExistException(String.format
                    ("Пользователь с данным идентификатором %s не зарегистрирован.", id));
        }
    }

    public List<Film> outListPopularFilm(Collection<Film> films, Integer count) {//Сортирую фильмы по количеству лайков
        ArrayList<Film> sorted = new ArrayList<>(films);//Передаю массив фильмов для сортировки
            return sorted.stream().sorted(Collections.reverseOrder(Film.COMPARE_BY_LIKES))
                    .limit(count).collect(Collectors.toList());//Сортирую применяя компаратор и обратный порядок
    }
}
