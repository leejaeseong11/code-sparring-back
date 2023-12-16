package com.trianglechoke.codesparring.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNSUPPORTED_MEDIA_TYPE;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /* 400 BAD_REQUEST */

    /** Valid 유효성 검사 실패 */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        log.error("MethodArgumentNotValidException :: ", ex);

        List<String> errors = generateErrors(ex);
        ErrorResponse response = new ErrorResponse(BAD_REQUEST, ex.getLocalizedMessage(), errors);

        return ResponseEntity.status(response.getStatus()).headers(headers).body(response);
    }

    /** 잘못된 유형으로 Bean 속성 설정 */
    @Override
    protected ResponseEntity<Object> handleTypeMismatch(
            TypeMismatchException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        log.error("TypeMismatchException :: ", ex);

        String error =
                ex.getValue()
                        + " value for "
                        + ex.getPropertyName()
                        + " should be of type "
                        + ex.getRequiredType();
        ErrorResponse response = new ErrorResponse(BAD_REQUEST, ex.getLocalizedMessage(), error);
        return ResponseEntity.status(response.getStatus()).headers(headers).body(response);
    }

    /** multipart/form-data 요청 실패 */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(
            MissingServletRequestPartException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        log.error("MissingServletRequestPartException :: ", ex);

        String error = ex.getRequestPartName() + " part is missing";
        ErrorResponse response = new ErrorResponse(BAD_REQUEST, ex.getLocalizedMessage(), error);
        return ResponseEntity.status(response.getStatus()).headers(headers).body(response);
    }

    /** 필수 인수 누락 */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        log.error("MissingServletRequestParameterException :: ", ex);

        String error = ex.getParameterName() + " parameter is missing";
        ErrorResponse response = new ErrorResponse(BAD_REQUEST, ex.getLocalizedMessage(), error);
        return ResponseEntity.status(response.getStatus()).headers(headers).body(response);
    }

    /** 인수가 예상한 형식이 아닐 시 */
    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex) {
        log.error("MethodArgumentTypeMismatchException :: ", ex);

        String error =
                ex.getName()
                        + " should be of type "
                        + Objects.requireNonNull(ex.getRequiredType()).getName();
        ErrorResponse response = new ErrorResponse(BAD_REQUEST, ex.getLocalizedMessage(), error);
        return ResponseEntity.status(response.getStatus())
                .headers(new HttpHeaders())
                .body(response);
    }

    /** 404 Not Found */
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        log.error("NoHandlerFoundException :: ", ex);

        String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();
        ErrorResponse response = new ErrorResponse(NOT_FOUND, ex.getLocalizedMessage(), error);
        return ResponseEntity.status(response.getStatus()).headers(headers).body(response);
    }

    /** 405 지원하지 않는 HTTP 메서드로 요청 */
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        log.error("HttpRequestMethodNotSupportedException :: ", ex);

        StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(" method is not supported for this request. Supported methods are ");

        Set<HttpMethod> supportedHttpMethods = ex.getSupportedHttpMethods();
        if (supportedHttpMethods != null) {
            supportedHttpMethods.forEach(t -> builder.append(t).append(" "));
        }

        ErrorResponse response =
                new ErrorResponse(METHOD_NOT_ALLOWED, ex.getLocalizedMessage(), builder.toString());
        return ResponseEntity.status(response.getStatus())
                .headers(new HttpHeaders())
                .body(response);
    }

    /** 415 지원되지 않는 미디어 유형으로 요청 */
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        log.error("HttpMediaTypeNotSupportedException :: ", ex);

        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(" "));

        ErrorResponse response =
                new ErrorResponse(
                        UNSUPPORTED_MEDIA_TYPE,
                        ex.getLocalizedMessage(),
                        builder.substring(0, builder.length() - 2));
        return ResponseEntity.status(response.getStatus())
                .headers(new HttpHeaders())
                .body(response);
    }

    /** 500 특정 핸들러 없는 모든 예외 */
    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<Object> handleAll(Exception ex) {
        log.error("Exception :: ", ex);

        ErrorResponse response =
                new ErrorResponse(INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), "오류가 발생했습니다.");
        return ResponseEntity.status(response.getStatus())
                .headers(new HttpHeaders())
                .body(response);
    }

    /** 커스텀 예외 발생 시 */
    @ExceptionHandler(value = MyException.class)
    protected ResponseEntity<ErrorResponse> myException(MyException ex) {
        log.error("MyException :: ", ex);

        ErrorCode errorCode = ex.getErrorCode();
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(ErrorResponse.toErrorResponse(errorCode));
    }

    private List<String> generateErrors(BindException ex) {
        List<String> errors = new ArrayList<>();
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();

        for (ObjectError error : allErrors) {
            errors.add(error.getDefaultMessage());
        }
        return errors;
    }

    /** 접근 권한 예외 발생 시 */
    @ExceptionHandler(value = AccessDeniedException.class)
    protected ResponseEntity<Object> handleAccessDeniedException(
            AccessDeniedException ex,
            WebRequest request) {
        log.error("Access Denied: {}", ex.getMessage());
        ErrorResponse response = new ErrorResponse(HttpStatus.FORBIDDEN, ex.getLocalizedMessage(), "Access Denied");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }
}
