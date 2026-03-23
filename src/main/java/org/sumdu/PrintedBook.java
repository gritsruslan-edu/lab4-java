package org.sumdu;

/**
 * Похідний клас, що представляє друковану книгу.
 */
public class PrintedBook extends Book {

    /**
     * Тип палітурки.
     */
    private String coverType;

    /**
     * Тираж книги.
     */
    private int printRun;

    /**
     * Створює новий об'єкт друкованої книги.
     */
    public PrintedBook(String title, String author, int year, int pages, Genre genre,
                       String coverType, int printRun) {
        super(title, author, year, pages, genre);
        setCoverType(coverType);
        setPrintRun(printRun);
    }

    /**
     * Повертає тип палітурки.
     */
    public String getCoverType() {
        return coverType;
    }

    /**
     * Встановлює тип палітурки.
     */
    public void setCoverType(String coverType) {
        if (coverType == null || coverType.trim().isEmpty()) {
            throw new IllegalArgumentException("Тип палітурки не може бути порожнім.");
        }
        this.coverType = coverType;
    }

    /**
     * Повертає тираж книги.
     */
    public int getPrintRun() {
        return printRun;
    }

    /**
     * Встановлює тираж книги.
     */
    public void setPrintRun(int printRun) {
        if (printRun <= 0) {
            throw new IllegalArgumentException("Тираж повинен бути більше 0.");
        }
        this.printRun = printRun;
    }

    /**
     * Повертає текстове представлення друкованої книги.
     */
    @Override
    public String toString() {
        return "PrintedBook{" +
                "title='" + getTitle() + '\'' +
                ", author='" + getAuthor() + '\'' +
                ", year=" + getYear() +
                ", pages=" + getPages() +
                ", genre=" + getGenre() +
                ", coverType='" + coverType + '\'' +
                ", printRun=" + printRun +
                '}';
    }
}
