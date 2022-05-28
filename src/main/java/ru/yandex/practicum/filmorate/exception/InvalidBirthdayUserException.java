package ru.yandex.practicum.filmorate.exception;

import lombok.Data;

import java.time.LocalDate;

@Data
public class InvalidBirthdayUserException extends RuntimeException {

    String parameter;
    public InvalidBirthdayUserException(String s) {
        this.parameter = s;
    }
}
