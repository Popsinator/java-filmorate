package ru.yandex.practicum.filmorate.exception;

public class ValidationExceptionExistId extends RuntimeException {
    public ValidationExceptionExistId(String s) {
        super(s);
    }
}
