package com.transition.scorekeeper.domain.exception;

public interface ErrorBundle {
    Exception getException();

    String getErrorMessage();
}
