package com.nguyen.security.exception;

import org.springframework.security.core.AuthenticationException;

public class CustomAccessDeniedException extends AuthenticationException {

    public CustomAccessDeniedException(String msg) {
        super(msg);
    }
}
