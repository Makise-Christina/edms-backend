package com.m.edmsbackend.exception;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@ResponseBody
@Slf4j
public class HttpExceptionHandler {
    private String formater(Exception ex) {
        return Arrays.toString(ex.getStackTrace()).replaceAll("\\[|\\]|\\s", "").replaceAll("\\,", "\r\n");
    }

    // 401
    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<String> request401(Exception ex) {
        // log.info(ex.getMessage() + "\r\n" + formater(ex));
        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    // 404
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> request404(Exception ex) {
        log.info(ex.getMessage() + "\r\n" + formater(ex));
        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // 500
    @ExceptionHandler({ ServerErrorException.class, IOException.class, ExpiredException.class })
    public ResponseEntity<String> request501(Exception ex) {
        log.error(ex.getMessage() + "\r\n" + formater(ex));
        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
