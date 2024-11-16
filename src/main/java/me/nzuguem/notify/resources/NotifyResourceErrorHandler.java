package me.nzuguem.notify.resources;

import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import me.nzuguem.notify.exceptions.NotifyException;

@RestControllerAdvice
public class NotifyResourceErrorHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotifyResourceErrorHandler.class);

    @ExceptionHandler({NotifyException.class, UnsupportedOperationException.class})
    public ProblemDetail handleNotifyException(Exception ex) {

        LOGGER.error(ex.getMessage(), ex);

        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());

        problemDetail.setTitle(ex.getClass().getSimpleName());
        problemDetail.setProperty("exception", ex.getClass().getName());
        problemDetail.setProperty("timestamp", Instant.now());


        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGlobalException(Exception ex) {

        LOGGER.error(ex.getMessage(), ex);

        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage());

        problemDetail.setTitle(ex.getClass().getSimpleName());
        problemDetail.setProperty("exception", ex.getClass().getName());
        problemDetail.setProperty("timestamp", Instant.now());

        return problemDetail;
    }


    
}
