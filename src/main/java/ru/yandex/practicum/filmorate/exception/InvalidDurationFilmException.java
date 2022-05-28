package ru.yandex.practicum.filmorate.exception;

import lombok.Data;

import java.time.Duration;

@Data
public class InvalidDurationFilmException extends RuntimeException {

    String parameter;
    public InvalidDurationFilmException(String s) {
        this.parameter = s;
    }
}
