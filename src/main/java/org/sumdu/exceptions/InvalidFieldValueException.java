package org.sumdu.exceptions;

/**
 * Виняток для некоректних значень полів.
 */
public class InvalidFieldValueException extends RuntimeException {

    public InvalidFieldValueException(String message) {
        super(message);
    }
}