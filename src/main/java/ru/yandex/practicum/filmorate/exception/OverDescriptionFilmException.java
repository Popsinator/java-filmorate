package ru.yandex.practicum.filmorate.exception;

import lombok.Data;

@Data
public class OverDescriptionFilmException extends RuntimeException {

    String parameter;
    public OverDescriptionFilmException(String s) {
        super(s);
        this.parameter = s;
    }
}
