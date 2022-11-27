package com.o4.open.commons.exceptions;

/**
 * This custom exception will be thrown when no record is found
 *
 * @author M. Mazhar Hassan
 * @version 1.0
 * @since 25.08.2022
 *
 */
public class ResourceNotFoundException extends ApplicationException {

    public ResourceNotFoundException(String message) {
        this(ERRORS.RECORD_NOT_FOUND, message);
    }

    public ResourceNotFoundException(int code, String message) {
        super(code, message);
    }
}
