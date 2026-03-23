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
            System.out.println("1 - Створити звичайну книгу");
            System.out.println("2 - Створити електронну книгу");
            System.out.println("3 - Створити друковану книгу");
            System.out.println("4 - Вивести всі книги");
            System.out.println("5 - Завершити програму");

            String choice = scanner.nextLine();

            switch (choice) {
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
                    printBooks(books);
                    break;
                case "5":
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
     * Створює об'єкт базового класу Book та додає його до колекції.
     */
    private static void createBook(Scanner scanner, List<Book> books) {
        try {
            BookData data = readCommonBookData(scanner);
            Book book = new Book(data.title(), data.author(), data.year(), data.pages(), data.genre());
            books.add(book);
            System.out.println("Звичайну книгу успішно додано!");
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    /**
     * Створює об'єкт похідного класу EBook та додає його до колекції.
     */
    private static void createEBook(Scanner scanner, List<Book> books) {
        try {
            BookData data = readCommonBookData(scanner);

            System.out.print("Формат файлу: ");
            String fileFormat = scanner.nextLine();

            System.out.print("Розмір файлу (MB): ");
            double fileSize = Double.parseDouble(scanner.nextLine());

            Book book = new EBook(
                    data.title(),
                    data.author(),
                    data.year(),
                    data.pages(),
                    data.genre(),
                    fileFormat,
                    fileSize
            );

            books.add(book);
            System.out.println("Електронну книгу успішно додано!");
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    /**
     * Створює об'єкт похідного класу PrintedBook та додає його до колекції.
     */
    private static void createPrintedBook(Scanner scanner, List<Book> books) {
        try {
            BookData data = readCommonBookData(scanner);

            System.out.print("Тип палітурки: ");
            String coverType = scanner.nextLine();

            System.out.print("Тираж: ");
            int printRun = Integer.parseInt(scanner.nextLine());

            Book book = new PrintedBook(
                    data.title(),
                    data.author(),
                    data.year(),
                    data.pages(),
                    data.genre(),
                    coverType,
                    printRun
            );

            books.add(book);
            System.out.println("Друковану книгу успішно додано!");
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    /**
     * Зчитує спільні для всіх типів книг дані.
     */
    private static BookData readCommonBookData(Scanner scanner) {
        System.out.print("Назва: ");
        String title = scanner.nextLine();

        System.out.print("Автор: ");
        String author = scanner.nextLine();

        System.out.print("Рік: ");
        int year = Integer.parseInt(scanner.nextLine());

        System.out.print("Кількість сторінок: ");
        int pages = Integer.parseInt(scanner.nextLine());

        System.out.println("Оберіть жанр із переліку:");
        for (Genre genre : Genre.values()) {
            System.out.println("- " + genre);
        }

        System.out.print("Жанр: ");
        String genreInput = scanner.nextLine().trim().toUpperCase();
        Genre genre = Genre.valueOf(genreInput);

        return new BookData(title, author, year, pages, genre);
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

    /**
     * Допоміжний record для зберігання спільних даних книги.
     */
    private record BookData(String title, String author, int year, int pages, Genre genre) {
    }
}