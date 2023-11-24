package com.trianglechoke.codesparring.exception;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class ErrorResponse {
    private final HttpStatus status;
    private final String message;
    private final List<String> errors;

    public ErrorResponse(HttpStatus status, String message, String error) {
        this.status = status;
        this.message = message;
        this.errors = List.of(error);
    }

    public static ErrorResponse toErrorResponse(ErrorCode errorCode) {
        return new ErrorResponse(
                errorCode.getHttpStatus(), errorCode.name(), errorCode.getDetail());
    }
}
