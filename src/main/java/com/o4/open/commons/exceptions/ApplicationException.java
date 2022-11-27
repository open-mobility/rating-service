package com.o4.open.commons.exceptions;

/**
 * This is an application exception class and must be a parent of the all the custom exceptions
 * Any exception thrown from the application must be an application exception
 * A unique code is needed for each unique error situation
 *
 * @author M. Mazhar Hassan
 * @version 1.0
 * @since 25.08.2022
 *
 */
public class ApplicationException extends RuntimeException {
    private final int code;

    public ApplicationException(String message) {
        this(ERRORS.RUNTIME_EXCEPTION, message);
    }

    public ApplicationException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
