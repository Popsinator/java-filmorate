package ru.yandex.practicum.filmorate.exception;

public class IdFilmAlreadyExistException extends RuntimeException {
    public IdFilmAlreadyExistException(String s) {
        super(s);
    }
}
