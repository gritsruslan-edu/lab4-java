package org.sumdu;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Драйвер програми для роботи з книгами.
 */
public class Main {

    /**
     * Точка входу в програму.
     */
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        List<Book> books = new ArrayList<>();

        boolean running = true;

        while (running) {

            System.out.println("\nОберіть дію:");
            System.out.println("1 - Створити новий об'єкт");
            System.out.println("2 - Вивести всі книги");
            System.out.println("3 - Завершити програму");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    createBookByType(scanner, books);
                    break;
                case "2":
                    printBooks(books);
                    break;
                case "3":
                    running = false;
                    System.out.println("Програму завершено.");
                    break;
                default:
                    System.out.println("Невірний вибір. Спробуйте ще раз.");
            }
        }

        scanner.close();
    }

    /**
     * Створює новий об'єкт вибраного типу та додає його до колекції.
     */
    private static void createBookByType(Scanner scanner, List<Book> books) {
        try {
            System.out.println("\nОберіть тип об'єкта:");
            System.out.println("1 - Book");
            System.out.println("2 - EBook");
            System.out.println("3 - PrintedBook");
            System.out.println("4 - AudioBook");
            System.out.println("5 - ScientificBook");

            String typeChoice = scanner.nextLine();

            switch (typeChoice) {
                case "1":
                    createBook(scanner, books);
                    break;
                case "2":
                    createEBook(scanner, books);
                    break;
                case "3":
                    createPrintedBook(scanner, books);
                    break;
                case "4":
                    createAudioBook(scanner, books);
                    break;
                case "5":
                    createScientificBook(scanner, books);
                    break;
                default:
                    System.out.println("Невірний тип об'єкта.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    /**
     * Створює об'єкт базового класу Book.
     */
    private static void createBook(Scanner scanner, List<Book> books) {
        String title = readTitle(scanner);
        String author = readAuthor(scanner);
        int year = readYear(scanner);
        int pages = readPages(scanner);
        Genre genre = readGenre(scanner);

        Book book = new Book(title, author, year, pages, genre);
        books.add(book);

        System.out.println("Звичайну книгу успішно додано!");
    }

    /**
     * Створює об'єкт похідного класу EBook.
     */
    private static void createEBook(Scanner scanner, List<Book> books) {
        String title = readTitle(scanner);
        String author = readAuthor(scanner);
        int year = readYear(scanner);
        int pages = readPages(scanner);
        Genre genre = readGenre(scanner);

        System.out.print("Формат файлу: ");
        String fileFormat = scanner.nextLine();

        System.out.print("Розмір файлу (MB): ");
        double fileSize = Double.parseDouble(scanner.nextLine());

        Book book = new EBook(title, author, year, pages, genre, fileFormat, fileSize);
        books.add(book);

        System.out.println("Електронну книгу успішно додано!");
    }

    /**
     * Створює об'єкт похідного класу PrintedBook.
     */
    private static void createPrintedBook(Scanner scanner, List<Book> books) {
        String title = readTitle(scanner);
        String author = readAuthor(scanner);
        int year = readYear(scanner);
        int pages = readPages(scanner);
        Genre genre = readGenre(scanner);

        System.out.print("Тип палітурки: ");
        String coverType = scanner.nextLine();

        System.out.print("Тираж: ");
        int printRun = Integer.parseInt(scanner.nextLine());

        Book book = new PrintedBook(title, author, year, pages, genre, coverType, printRun);
        books.add(book);

        System.out.println("Друковану книгу успішно додано!");
    }

    /**
     * Створює об'єкт похідного класу AudioBook.
     */
    private static void createAudioBook(Scanner scanner, List<Book> books) {
        String title = readTitle(scanner);
        String author = readAuthor(scanner);
        int year = readYear(scanner);
        int pages = readPages(scanner);
        Genre genre = readGenre(scanner);

        System.out.print("Диктор: ");
        String narrator = scanner.nextLine();

        System.out.print("Тривалість у хвилинах: ");
        int durationMinutes = Integer.parseInt(scanner.nextLine());

        Book book = new AudioBook(title, author, year, pages, genre, narrator, durationMinutes);
        books.add(book);

        System.out.println("Аудіокнигу успішно додано!");
    }

    /**
     * Створює об'єкт похідного класу ScientificBook.
     */
    private static void createScientificBook(Scanner scanner, List<Book> books) {
        String title = readTitle(scanner);
        String author = readAuthor(scanner);
        int year = readYear(scanner);
        int pages = readPages(scanner);
        Genre genre = readGenre(scanner);

        System.out.print("Галузь науки: ");
        String fieldOfScience = scanner.nextLine();

        System.out.print("Чи є книга рецензованою? (true/false): ");
        boolean peerReviewed = Boolean.parseBoolean(scanner.nextLine());

        Book book = new ScientificBook(title, author, year, pages, genre, fieldOfScience, peerReviewed);
        books.add(book);

        System.out.println("Наукову книгу успішно додано!");
    }

    /**
     * Зчитує назву книги.
     */
    private static String readTitle(Scanner scanner) {
        System.out.print("Назва: ");
        return scanner.nextLine();
    }

    /**
     * Зчитує автора книги.
     */
    private static String readAuthor(Scanner scanner) {
        System.out.print("Автор: ");
        return scanner.nextLine();
    }

    /**
     * Зчитує рік видання.
     */
    private static int readYear(Scanner scanner) {
        System.out.print("Рік: ");
        return Integer.parseInt(scanner.nextLine());
    }

    /**
     * Зчитує кількість сторінок.
     */
    private static int readPages(Scanner scanner) {
        System.out.print("Кількість сторінок: ");
        return Integer.parseInt(scanner.nextLine());
    }

    /**
     * Зчитує жанр книги.
     */
    private static Genre readGenre(Scanner scanner) {
        System.out.println("Оберіть жанр із переліку:");
        for (Genre genre : Genre.values()) {
            System.out.println("- " + genre);
        }

        System.out.print("Жанр: ");
        String genreInput = scanner.nextLine().trim().toUpperCase();
        return Genre.valueOf(genreInput);
    }

    /**
     * Виводить усі книги з колекції.
     */
    private static void printBooks(List<Book> books) {
        if (books.isEmpty()) {
            System.out.println("Список книг порожній.");
            return;
        }

        for (Book book : books) {
            System.out.println(book);
        }
    }
}