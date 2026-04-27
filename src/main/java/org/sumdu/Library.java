package org.sumdu;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Клас бібліотеки для зберігання книг та їх кількості.
 */
public class Library {

    /**
     * Список книг.
     */
    private ArrayList<Book> books;

    /**
     * Кількість кожної книги.
     */
    private ArrayList<Integer> quantities;

    /**
     * Створює нову бібліотеку.
     */
    public Library() {
        books = new ArrayList<>();
        quantities = new ArrayList<>();
    }

    /**
     * Шукає книгу за UUID.
     */
    public Book findByUuid(UUID uuid) {
        if (uuid == null) {
            return null;
        }

        for (Book book : books) {
            if (uuid.equals(book.getUuid())) {
                return book;
            }
        }

        return null;
    }

    /**
     * Додає нову книгу або збільшує кількість існуючої.
     */
    public void addNewBook(Book bk, int quantity) {
        if (bk == null) {
            throw new IllegalArgumentException("Книга не може бути null.");
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException("Кількість повинна бути більше 0.");
        }

        int index = books.indexOf(bk);

        if (index >= 0) {
            quantities.set(index, quantities.get(index) + quantity);
        } else {
            books.add(bk);
            quantities.add(quantity);
        }
    }

    /**
     * Повертає всі книги бібліотеки.
     */
    public ArrayList<Book> getBooks() {
        return books;
    }

    /**
     * Повертає всі кількості книг.
     */
    public ArrayList<Integer> getQuantities() {
        return quantities;
    }

    /**
     * Повертає кількість книги за індексом.
     */
    public int getQuantityByIndex(int index) {
        return quantities.get(index);
    }

    /**
     * Шукає книги за назвою.
     */
    public List<Book> findBooksByTitle(String title) {
        List<Book> foundBooks = new ArrayList<>();

        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                foundBooks.add(book);
            }
        }

        return foundBooks;
    }

    /**
     * Шукає книги за автором.
     */
    public List<Book> findBooksByAuthor(String author) {
        List<Book> foundBooks = new ArrayList<>();

        for (Book book : books) {
            if (book.getAuthor().equalsIgnoreCase(author)) {
                foundBooks.add(book);
            }
        }

        return foundBooks;
    }

    /**
     * Шукає книги за жанром.
     */
    public List<Book> findBooksByGenre(Genre genre) {
        List<Book> foundBooks = new ArrayList<>();

        for (Book book : books) {
            if (book.getGenre() == genre) {
                foundBooks.add(book);
            }
        }

        return foundBooks;
    }

    /**
     * Повертає кількість для конкретної книги.
     */
    public int getQuantityForBook(Book targetBook) {
        int index = books.indexOf(targetBook);

        if (index >= 0) {
            return quantities.get(index);
        }

        return 0;
    }

    /**
     * Перевіряє, чи бібліотека порожня.
     */
    public boolean isEmpty() {
        return books.isEmpty();
    }
}