package com.politechnika.shootingrange.exceptions;

/**
 * Created by Jacek on 15.12.2017.
 */

public class UnknownFragmentException  extends Exception{
    public UnknownFragmentException() {
    }

    public UnknownFragmentException(String message) {
        super(message);
    }

    public UnknownFragmentException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownFragmentException(Throwable cause) {
        super(cause);
    }
}
