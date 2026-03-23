package org.sumdu;

import java.util.ArrayList;
import java.util.List;

/**
 * Клас, що представляє бібліотеку як агрегатор книг.
 */
public class Library {

    /**
     * Назва бібліотеки.
     */
    private String name;

    /**
     * Список книг у бібліотеці.
     */
    private List<Book> books;

    /**
     * Створює нову бібліотеку.
     */
    public Library(String name) {
        setName(name);
        this.books = new ArrayList<>();
    }

    /**
     * Повертає назву бібліотеки.
     */
    public String getName() {
        return name;
    }

    /**
     * Встановлює назву бібліотеки.
     */
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Назва бібліотеки не може бути порожньою.");
        }
        this.name = name;
    }

    /**
     * Повертає список книг у бібліотеці.
     */
    public List<Book> getBooks() {
        return books;
    }

    /**
     * Додає книгу до бібліотеки.
     */
    public void addBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Книга не може бути null.");
        }
        books.add(book);
    }

    /**
     * Видаляє книгу з бібліотеки.
     */
    public boolean removeBook(Book book) {
        return books.remove(book);
    }
}
