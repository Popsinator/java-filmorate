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
            return sorted.stream().sorted(Collections.reverseOrder(Film.COMPARE_BY_COUNT))
                    .limit(count).collect(Collectors.toList());//Сортирую применяя компаратор и обратный порядок
        /*if (films.size() >= 10) {
            return films.stream().sorted((p0, p1) -> {
                int comp = p0.getListLikesAfterSet().get(0).compareTo(p1.getListLikesAfterSet().get(1)); //прямой порядок сортировки
                return comp;
            }).limit(10).collect(Collectors.toList());
        } else {
            return films.stream().sorted((p0, p1) -> {
                int comp = p0.getListLikesAfterSet().get(0).compareTo(p1.getListLikesAfterSet().get(1)); //прямой порядок сортировки
                return comp;
            }).limit(films.size()).collect(Collectors.toList());
        }
        Comparator<Film> comparator = (film1, film2) -> {
            return ((Integer) film1.getListLikes().size()).compareTo((Integer) film2.getListLikes().size());
        };
        TreeSet<Film> popularFilm = new TreeSet<>(comparator);
        popularFilm.addAll(films);
        List<Film> popSortFilm = new ArrayList<>(popularFilm);
        int length = popSortFilm.size();
        ArrayList<Film> result = new ArrayList<Film>(length);

        for (int i = length - 1; i >= 0; i--) {
            result.add(popSortFilm.get(i));
        }
        if (result.size() >= 10) {

            return result.stream()
                    .limit(10)
                    .collect(Collectors.toList());
        } else {
            return result.stream()
                    .limit(count)
                    .collect(Collectors.toList());
        }*/
    }
}
