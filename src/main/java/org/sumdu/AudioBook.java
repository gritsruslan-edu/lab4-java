package org.sumdu;

/**
 * Похідний клас, що представляє аудіокнигу.
 */
public class AudioBook extends Book {

    /**
     * Диктор аудіокниги.
     */
    private String narrator;

    /**
     * Тривалість аудіокниги в хвилинах.
     */
    private int durationMinutes;

    /**
     * Створює новий об'єкт аудіокниги.
     */
    public AudioBook(String title, String author, int year, int pages, Genre genre,
                     String narrator, int durationMinutes) {
        super(title, author, year, pages, genre);
        setNarrator(narrator);
        setDurationMinutes(durationMinutes);
    }

    /**
     * Повертає диктора аудіокниги.
     */
    public String getNarrator() {
        return narrator;
    }

    /**
     * Встановлює диктора аудіокниги.
     */
    public void setNarrator(String narrator) {
        if (narrator == null || narrator.trim().isEmpty()) {
            throw new IllegalArgumentException("Ім'я диктора не може бути порожнім.");
        }
        this.narrator = narrator;
    }

    /**
     * Повертає тривалість аудіокниги.
     */
    public int getDurationMinutes() {
        return durationMinutes;
    }

    /**
     * Встановлює тривалість аудіокниги.
     */
    public void setDurationMinutes(int durationMinutes) {
        if (durationMinutes <= 0) {
            throw new IllegalArgumentException("Тривалість повинна бути більше 0.");
        }
        this.durationMinutes = durationMinutes;
    }

    /**
     * Повертає текстове представлення аудіокниги.
     */
    @Override
    public String toString() {
        return "AudioBook{" +
                "title='" + getTitle() + '\'' +
                ", author='" + getAuthor() + '\'' +
                ", year=" + getYear() +
                ", pages=" + getPages() +
                ", genre=" + getGenre() +
                ", narrator='" + narrator + '\'' +
                ", durationMinutes=" + durationMinutes +
                '}';
    }
}