package ru.yandex.practicum.filmorate.exception;

import lombok.Data;

@Data
public class InvalidBirthdayUserException extends RuntimeException {

    String parameter;
    public InvalidBirthdayUserException(String s) {
        super(s);
        this.parameter = s;
    }
}
