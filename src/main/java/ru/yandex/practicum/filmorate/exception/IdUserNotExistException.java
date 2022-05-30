package ru.yandex.practicum.filmorate.exception;

public class IdUserNotExistException extends RuntimeException {
    public IdUserNotExistException(String s) {
        super(s);
    }
}
