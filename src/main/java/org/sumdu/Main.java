package org.sumdu;

import java.util.Scanner;

/**
 * Драйвер програми для роботи з бібліотекою та книгами.
 */
public class Main {

    /**
     * Точка входу в програму.
     */
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Library library = new Library("Моя бібліотека");

        boolean running = true;

        while (running) {

            System.out.println("\nОберіть дію:");
            System.out.println("1 - Створити нову книгу");
            System.out.println("2 - Вивести всі книги");
            System.out.println("3 - Вивести кількість створених об'єктів Book");
            System.out.println("4 - Завершити програму");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    createBook(scanner, library);
                    break;
                case "2":
                    printBooks(library);
                    break;
                case "3":
                    System.out.println("Кількість створених об'єктів Book: " + Book.getBookCount());
                    break;
                case "4":
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
     * Створює нову книгу та додає її до бібліотеки.
     */
    private static void createBook(Scanner scanner, Library library) {

        try {
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

            Book book = new Book(title, author, year, pages, genre);
            library.addBook(book);

            System.out.println("Книгу успішно додано!");

        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    /**
     * Виводить усі книги з бібліотеки.
     */
    private static void printBooks(Library library) {

        if (library.getBooks().isEmpty()) {
            System.out.println("Бібліотека порожня.");
            return;
        }

        System.out.println("\nКниги у бібліотеці \"" + library.getName() + "\":");
        for (Book book : library.getBooks()) {
            System.out.println(book);
        }
    }
}