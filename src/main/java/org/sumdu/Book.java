package org.sumdu;

import java.util.Objects;

/**
 * Клас, що представляє книгу в бібліотеці.
 */
public class Book {

    /**
     * Лічильник створених об'єктів Book.
     */
    private static int bookCount = 0;

    /**
     * Назва книги.
     */
    private String title;

    /**
     * Автор книги.
     */
    private String author;

    /**
     * Рік публікації книги.
     */
    private int year;

    /**
     * Кількість сторінок у книзі.
     */
    private int pages;

    /**
     * Жанр книги.
     */
    private Genre genre;

    /**
     * Створює новий об'єкт книги з базовою валідацією.
     */
    public Book(String title, String author, int year, int pages, Genre genre) {
        setTitle(title);
        setAuthor(author);
        setYear(year);
        setPages(pages);
        setGenre(genre);
        bookCount++;
    }

    /**
     * Конструктор копіювання.
     */
    public Book(Book other) {
        if (other == null) {
            throw new IllegalArgumentException("Об'єкт для копіювання не може бути null.");
        }

        this.title = other.title;
        this.author = other.author;
        this.year = other.year;
        this.pages = other.pages;
        this.genre = other.genre;
        bookCount++;
    }

    /**
     * Повертає кількість створених об'єктів Book.
     */
    public static int getBookCount() {
        return bookCount;
    }

    /**
     * Повертає назву книги.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Встановлює назву книги.
     */
    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Назва книги не може бути порожньою.");
        }
        this.title = title;
    }

    /**
     * Повертає автора книги.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Встановлює автора книги.
     */
    public void setAuthor(String author) {
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("Автор не може бути порожнім.");
        }
        this.author = author;
    }

    /**
     * Повертає рік публікації.
     */
    public int getYear() {
        return year;
    }

    /**
     * Встановлює рік публікації.
     */
    public void setYear(int year) {
        if (year < 1450 || year > 2026) {
            throw new IllegalArgumentException("Рік публікації має бути між 1450 та 2026.");
        }
        this.year = year;
    }

    /**
     * Повертає кількість сторінок.
     */
    public int getPages() {
        return pages;
    }

    /**
     * Встановлює кількість сторінок.
     */
    public void setPages(int pages) {
        if (pages <= 0) {
            throw new IllegalArgumentException("Кількість сторінок повинна бути більше 0.");
        }
        this.pages = pages;
    }

    /**
     * Повертає жанр книги.
     */
    public Genre getGenre() {
        return genre;
    }

    /**
     * Встановлює жанр книги.
     */
    public void setGenre(Genre genre) {
        if (genre == null) {
            throw new IllegalArgumentException("Жанр не може бути null.");
        }
        this.genre = genre;
    }

    /**
     * Повертає текстове представлення книги.
     */
    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                ", pages=" + pages +
                ", genre=" + genre +
                '}';
    }

    /**
     * Порівнює книги між собою.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book book)) return false;

        return year == book.year &&
                pages == book.pages &&
                Objects.equals(title, book.title) &&
                Objects.equals(author, book.author) &&
                genre == book.genre;
    }

    /**
     * Повертає хеш-код книги.
     */
    @Override
    public int hashCode() {
        return Objects.hash(title, author, year, pages, genre);
    }
}