package com.transition.scorekeeper.data.exception;

public class MatchNotFoundException extends Exception {

    public MatchNotFoundException() {
        super();
    }

    public MatchNotFoundException(final String message) {
        super(message);
    }

    public MatchNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public MatchNotFoundException(final Throwable cause) {
        super(cause);
    }
}
