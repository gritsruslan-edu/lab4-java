package org.sumdu;

import java.util.Objects;

/**
 * Похідний клас, що представляє наукову книгу.
 */
public class ScientificBook extends Book {

    /**
     * Галузь науки.
     */
    private String fieldOfScience;

    /**
     * Ознака наявності рецензування.
     */
    private boolean peerReviewed;

    /**
     * Створює новий об'єкт наукової книги.
     */
    public ScientificBook(String title, String author, int year, int pages, Genre genre,
                          String fieldOfScience, boolean peerReviewed) {
        super(title, author, year, pages, genre);
        setFieldOfScience(fieldOfScience);
        setPeerReviewed(peerReviewed);
    }

    /**
     * Повертає галузь науки.
     */
    public String getFieldOfScience() {
        return fieldOfScience;
    }

    /**
     * Встановлює галузь науки.
     */
    public void setFieldOfScience(String fieldOfScience) {
        if (fieldOfScience == null || fieldOfScience.trim().isEmpty()) {
            throw new IllegalArgumentException("Галузь науки не може бути порожньою.");
        }
        this.fieldOfScience = fieldOfScience;
    }

    /**
     * Повертає ознаку наявності рецензування.
     */
    public boolean isPeerReviewed() {
        return peerReviewed;
    }

    /**
     * Встановлює ознаку наявності рецензування.
     */
    public void setPeerReviewed(boolean peerReviewed) {
        this.peerReviewed = peerReviewed;
    }

    /**
     * Повертає текстове представлення наукової книги.
     */
    @Override
    public String toString() {
        return "ScientificBook{" +
                "uuid=" + getUuid() +
                ", title='" + getTitle() + '\'' +
                ", author='" + getAuthor() + '\'' +
                ", year=" + getYear() +
                ", pages=" + getPages() +
                ", genre=" + getGenre() +
                ", fieldOfScience='" + fieldOfScience + '\'' +
                ", peerReviewed=" + peerReviewed +
                '}';
    }

    /**
     * Порівнює наукові книги.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ScientificBook that)) return false;
        if (!super.equals(o)) return false;
        return peerReviewed == that.peerReviewed &&
                Objects.equals(fieldOfScience, that.fieldOfScience);
    }

    /**
     * Повертає хеш-код наукової книги.
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), fieldOfScience, peerReviewed);
    }
}