package org.sumdu;

import java.util.Objects;

class Book {

    private String title;
    private String author;
    private int year;

    // Конструктор
    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Book " +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book book)) return false;

        return year == book.year &&
                Objects.equals(title, book.title) &&
                Objects.equals(author, book.author);
    }
}

public class Main {

    public static void main(String[] args) {

        Book[] library = new Book[5];

        library[0] = new Book("Clean Code", "Robert Martin", 2008);
        library[1] = new Book("Effective Java", "Joshua Bloch", 2018);
        library[2] = new Book("Design Patterns", "Erich Gamma", 1994);
        library[3] = new Book("Refactoring", "Martin Fowler", 1999);
        library[4] = new Book("The Pragmatic Programmer", "Andrew Hunt", 1999);

        for (Book book : library) {
            System.out.println(book);
        }
    }
}



