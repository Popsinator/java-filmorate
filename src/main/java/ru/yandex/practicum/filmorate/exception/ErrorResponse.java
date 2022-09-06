package ru.yandex.practicum.filmorate.exception;

import lombok.Data;

@Data
public class ErrorResponse {

    String Error;

    public ErrorResponse(String error) {
        this.Error = error;
    }
}
