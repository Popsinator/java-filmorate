package ru.yandex.practicum.filmorate.exception;

import lombok.Data;

@Data
public class InvalidDurationFilmException extends RuntimeException {

    String parameter;
    public InvalidDurationFilmException(String s) {
        super(s);
        this.parameter = s;
    }
}
