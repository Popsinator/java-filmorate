package ru.yandex.practicum.filmorate.exception;

import lombok.Data;

@Data
public class InvalidNameFilmException extends RuntimeException {

    String parameter;

    public InvalidNameFilmException(String s) {
        this.parameter = s;
    }
}
