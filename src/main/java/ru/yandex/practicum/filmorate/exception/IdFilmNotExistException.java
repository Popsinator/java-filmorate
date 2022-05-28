package ru.yandex.practicum.filmorate.exception;

public class IdFilmNotExistException extends RuntimeException {
    public IdFilmNotExistException(String s) {
        super(s);
    }
}
