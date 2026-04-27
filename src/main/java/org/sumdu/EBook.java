package org.sumdu;

import java.util.Objects;

/**
 * Похідний клас, що представляє електронну книгу.
 */
public class EBook extends Book {

    /**
     * Формат електронної книги.
     */
    private String fileFormat;

    /**
     * Розмір файлу в мегабайтах.
     */
    private double fileSize;

    /**
     * Створює новий об'єкт електронної книги.
     */
    public EBook(String title, String author, int year, int pages, Genre genre,
                 String fileFormat, double fileSize) {
        super(title, author, year, pages, genre);
        setFileFormat(fileFormat);
        setFileSize(fileSize);
    }

    /**
     * Повертає формат файлу.
     */
    public String getFileFormat() {
        return fileFormat;
    }

    /**
     * Встановлює формат файлу.
     */
    public void setFileFormat(String fileFormat) {
        if (fileFormat == null || fileFormat.trim().isEmpty()) {
            throw new IllegalArgumentException("Формат файлу не може бути порожнім.");
        }
        this.fileFormat = fileFormat;
    }

    /**
     * Повертає розмір файлу.
     */
    public double getFileSize() {
        return fileSize;
    }

    /**
     * Встановлює розмір файлу.
     */
    public void setFileSize(double fileSize) {
        if (fileSize <= 0) {
            throw new IllegalArgumentException("Розмір файлу повинен бути більше 0.");
        }
        this.fileSize = fileSize;
    }

    /**
     * Повертає текстове представлення електронної книги.
     */
    @Override
    public String toString() {
        return "EBook{" +
                "uuid=" + getUuid() +
                ", title='" + getTitle() + '\'' +
                ", author='" + getAuthor() + '\'' +
                ", year=" + getYear() +
                ", pages=" + getPages() +
                ", genre=" + getGenre() +
                ", fileFormat='" + fileFormat + '\'' +
                ", fileSize=" + fileSize +
                '}';
    }

    /**
     * Порівнює електронні книги.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EBook eBook)) return false;
        if (!super.equals(o)) return false;
        return Double.compare(eBook.fileSize, fileSize) == 0 &&
                Objects.equals(fileFormat, eBook.fileFormat);
    }

    /**
     * Повертає хеш-код електронної книги.
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), fileFormat, fileSize);
    }
}