package ru.yandex.practicum.filmorate.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exception.*;

@RestControllerAdvice
public class ErrorHandler {//Централизовано организовал выбрасывание исключений согласно условиям ТЗ

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleEmptyDescriptionFilmException (final EmptyDescriptionFilmException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleIdFilmAlreadyExistException (final IdFilmAlreadyExistException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleIdFilmNotExistException (final IdFilmNotExistException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleIdUserAlreadyExistException (final IdUserAlreadyExistException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleIdUserNotExistException (final IdUserNotExistException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidBirthdayUserException (final InvalidBirthdayUserException exception) {
        return new ErrorResponse(String.format("Ошибка с полем \"%s\".", exception.getParameter()));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidDurationFilmException (final InvalidDurationFilmException exception) {
        return new ErrorResponse(String.format("Ошибка с полем \"%s\".", exception.getParameter()));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidLoginUserException (final InvalidLoginUserException exception) {
        return new ErrorResponse(String.format("Ошибка с полем \"%s\".", exception.getParameter()));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidMaleUserException (final InvalidMaleUserException exception) {
        return new ErrorResponse(String.format("Ошибка с полем \"%s\".", exception.getParameter()));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidNameFilmException (final InvalidNameFilmException exception) {
        return new ErrorResponse(String.format("Ошибка с полем \"%s\".", exception.getParameter()));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidReleaseDateException (final InvalidReleaseDateException exception) {
        return new ErrorResponse(String.format("Ошибка с полем \"%s\".", exception.getParameter()));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleOverDescriptionFilmException (final OverDescriptionFilmException exception) {
        return new ErrorResponse(String.format("Ошибка с полем \"%s\".", exception.getParameter()));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleThrowable(final Throwable e) {
        return new ErrorResponse(
                "Произошла непредвиденная ошибка."
        );
    }
}
