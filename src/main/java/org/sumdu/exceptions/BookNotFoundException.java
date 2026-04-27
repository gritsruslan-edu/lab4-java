package org.sumdu.exceptions;

/**
 * Виняток для ситуації, коли книгу не знайдено.
 */
public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(String message) {
        super(message);
    }
}