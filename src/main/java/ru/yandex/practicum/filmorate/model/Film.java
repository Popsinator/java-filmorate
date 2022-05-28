package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film {
    private int id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Integer duration;
    private Set<Integer> listLikes = new HashSet<>();
    private ArrayList<Integer> listLikesAfterSet = new ArrayList<>();

    public static final Comparator<Film> COMPARE_BY_COUNT = new Comparator<Film>() {
        @Override
        public int compare(Film lhs, Film rhs) {
            return lhs.getListLikes().size() - rhs.getListLikes().size();
        }
    };
}
