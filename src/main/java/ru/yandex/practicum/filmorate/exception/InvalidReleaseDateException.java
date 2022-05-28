package ru.yandex.practicum.filmorate.exception;

import lombok.Data;

@Data
public class InvalidReleaseDateException extends RuntimeException {

    String parameter;
    public InvalidReleaseDateException(String s) {
        this.parameter = s;
    }
}
