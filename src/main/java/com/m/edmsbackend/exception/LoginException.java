package com.m.edmsbackend.exception;

public class LoginException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    public LoginException() {
    }

    public LoginException(String message) {
        super(message);
    }

    public LoginException(String message, Throwable cause) {
        super(message, cause);
    }
}