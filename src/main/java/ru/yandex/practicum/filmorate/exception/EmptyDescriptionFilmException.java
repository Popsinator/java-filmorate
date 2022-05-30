package ru.yandex.practicum.filmorate.exception;

public class EmptyDescriptionFilmException extends RuntimeException {
    public EmptyDescriptionFilmException(String s) {
        super(s);
    }
}
