package org.sumdu;

import java.util.Objects;

/**
 * Клас, що представляє книгу в бібліотеці.
 */
public class Book {

    private String title;
    private String author;
    private int year;
    private int pages;
    private String genre;

    /**
     * Створює новий об'єкт книги з базовою валідацією.
     */
    public Book(String title, String author, int year, int pages, String genre) {
        setTitle(title);
        setAuthor(author);
        setYear(year);
        setPages(pages);
        setGenre(genre);
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
        if (year < 1450 || year > 2025) {
            throw new IllegalArgumentException("Рік публікації має бути між 1450 та 2025.");
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
    public String getGenre() {
        return genre;
    }

    /**
     * Встановлює жанр книги.
     */
    public void setGenre(String genre) {
        if (genre == null || genre.trim().isEmpty()) {
            throw new IllegalArgumentException("Жанр не може бути порожнім.");
        }
        this.genre = genre;
    }

    /**
     * Повертає текстове представлення книги.
     */
    @Override
    public String toString() {
        return "Book " +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                ", pages=" + pages +
                ", genre='" + genre + '\'';
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
                Objects.equals(genre, book.genre);
    }
}
