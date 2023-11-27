package com.trianglechoke.codesparring.member.handler;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.FORBIDDEN;

import com.trianglechoke.codesparring.exception.DuplicateMemberException;
import com.trianglechoke.codesparring.exception.NotFoundMemberException;
import com.trianglechoke.codesparring.member.dto.ErrorDTO;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(CONFLICT)
    @ExceptionHandler(value = { DuplicateMemberException.class })
    @ResponseBody
    protected ErrorDTO conflict(RuntimeException ex, WebRequest request) {
        return new ErrorDTO(CONFLICT.value(), ex.getMessage());
    }

    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler(value = { NotFoundMemberException.class, AccessDeniedException.class })
    @ResponseBody
    protected ErrorDTO forbidden(RuntimeException ex, WebRequest request) {
        return new ErrorDTO(FORBIDDEN.value(), ex.getMessage());
    }
}
