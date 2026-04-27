package org.sumdu;

import java.util.Objects;
import java.util.UUID;

/**
 * Абстрактний базовий клас, що представляє книгу.
 */
public abstract class Book implements Comparable<Book>, Identifiable {

    /**
     * Унікальний ідентифікатор книги.
     */
    private UUID uuid;

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
     * Кількість сторінок.
     */
    private int pages;

    /**
     * Жанр книги.
     */
    private Genre genre;

    /**
     * Створює новий об'єкт книги.
     */
    public Book(String title, String author, int year, int pages, Genre genre) {
        this.uuid = UUID.randomUUID();
        setTitle(title);
        setAuthor(author);
        setYear(year);
        setPages(pages);
        setGenre(genre);
    }

    /**
     * Конструктор копіювання.
     */
    public Book(Book other) {
        if (other == null) {
            throw new IllegalArgumentException("Об'єкт для копіювання не може бути null.");
        }

        this.uuid = UUID.randomUUID();
        this.title = other.title;
        this.author = other.author;
        this.year = other.year;
        this.pages = other.pages;
        this.genre = other.genre;
    }

    /**
     * Встановлює UUID книги.
     */
    protected void setUuid(UUID uuid) {
        if (uuid == null) {
            throw new IllegalArgumentException("UUID не може бути null.");
        }
        this.uuid = uuid;
    }

    /**
     * Повертає UUID книги.
     */
    @Override
    public UUID getUuid() {
        return uuid;
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
     * Порівнює книги за назвою.
     */
    @Override
    public int compareTo(Book other) {
        return this.title.compareToIgnoreCase(other.title);
    }

    /**
     * Порівнює поточний об'єкт з іншим.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book book)) return false;
        return year == book.year
                && pages == book.pages
                && Objects.equals(uuid, book.uuid)
                && Objects.equals(title, book.title)
                && Objects.equals(author, book.author)
                && genre == book.genre;
    }

    /**
     * Повертає хеш-код об'єкта.
     */
    @Override
    public int hashCode() {
        return Objects.hash(uuid, title, author, year, pages, genre);
    }
}