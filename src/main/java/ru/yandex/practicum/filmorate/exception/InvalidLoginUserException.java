package ru.yandex.practicum.filmorate.exception;

import lombok.Data;

@Data
public class InvalidLoginUserException extends RuntimeException {

    String parameter;
    public InvalidLoginUserException(String s) {
        this.parameter = s;
    }
}
