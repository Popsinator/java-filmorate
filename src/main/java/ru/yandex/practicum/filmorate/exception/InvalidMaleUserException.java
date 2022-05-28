package ru.yandex.practicum.filmorate.exception;

import lombok.Data;

@Data
public class InvalidMaleUserException extends RuntimeException {

    String parameter;
    public InvalidMaleUserException(String s) {
        this.parameter = s;
    }
}
