package ru.yandex.practicum.filmorate.exception;

public class IdUserAlreadyExistException extends RuntimeException {
    public IdUserAlreadyExistException(String s) {
        super(s);
    }
}
