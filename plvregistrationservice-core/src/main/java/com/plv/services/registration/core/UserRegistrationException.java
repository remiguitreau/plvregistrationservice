package com.plv.services.registration.core;

public class UserRegistrationException extends RuntimeException {

    public UserRegistrationException(final String message) {
        super(message);
    }

    public UserRegistrationException(final Throwable cause) {
        super(cause);
    }

    public UserRegistrationException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
