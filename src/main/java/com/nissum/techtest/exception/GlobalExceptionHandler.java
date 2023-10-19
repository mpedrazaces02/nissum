package com.nissum.techtest.exception;

import com.nissum.techtest.dto.ErrorResponse;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ValidationException;
import java.net.SocketException;
import java.time.LocalDateTime;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    private static final String EXCEPTION_MESSAGE_CAUSE = "Exception Message {} Caused By {}";

    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> httpRequestMethodNotSupportedExceptionHandler(
            HttpRequestMethodNotSupportedException e
    ) {
        logger.info(EXCEPTION_MESSAGE_CAUSE, e.getMessage(), e.getCause());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.builder().dateTime(LocalDateTime.now())
                        .statusCode(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                        .title("Bad Request")
                        .detail(e.getMessage())
                        .build());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class,
            ConstraintViolationException.class,
            ValidationException.class,
            IllegalArgumentException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> exceptionValidateRequest(Throwable e) {
        logger.info(EXCEPTION_MESSAGE_CAUSE, e.getMessage(), e.getCause());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.builder().dateTime(LocalDateTime.now())
                        .statusCode(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                        .title("Bad request")
                        .detail("Invalid input data => " + e.getMessage())
                        .build());
    }


    @ExceptionHandler({Exception.class, SocketException.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> globalExceptionHandler(Exception e) {
        logger.info(EXCEPTION_MESSAGE_CAUSE, e.getMessage(), e.getCause());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.builder().dateTime(LocalDateTime.now())
                        .statusCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                        .title("General Error")
                        .detail(e.getMessage() + " -> CAUSE: " + e.getCause() + " COMPLETE E >>> " + e)
                        .build());
    }

    @ExceptionHandler({PasswordException.class, EmailException.class})
    public ResponseEntity<ErrorResponse> contentGroupContentException(Exception e) {
        logger.info(EXCEPTION_MESSAGE_CAUSE, e.getMessage(), e.getCause());

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ErrorResponse.builder().dateTime(LocalDateTime.now())
                            .statusCode(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                            .title("Bad request")
                            .detail("Message: " + e.getMessage())
                            .build());

    }
}
