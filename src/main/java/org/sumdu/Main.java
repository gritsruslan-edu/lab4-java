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
        List<Book> library = new ArrayList<>();

        boolean running = true;

        while (running) {

            System.out.println("\nОберіть дію:");
            System.out.println("1 - Створити нову книгу");
            System.out.println("2 - Вивести всі книги");
            System.out.println("3 - Завершити програму");

            String choice = scanner.nextLine();

            switch (choice) {

                case "1":
                    createBook(scanner, library);
                    break;

                case "2":
                    printBooks(library);
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
     * Створює нову книгу та додає її до списку.
     */
    private static void createBook(Scanner scanner, List<Book> library) {

        try {
            System.out.print("Назва: ");
            String title = scanner.nextLine();

            System.out.print("Автор: ");
            String author = scanner.nextLine();

            System.out.print("Рік: ");
            int year = Integer.parseInt(scanner.nextLine());

            System.out.print("Кількість сторінок: ");
            int pages = Integer.parseInt(scanner.nextLine());

            System.out.print("Жанр: ");
            String genre = scanner.nextLine();

            Book book = new Book(title, author, year, pages, genre);
            library.add(book);

            System.out.println("Книгу успішно додано!");

        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    /**
     * Виводить всі книги на екран.
     */
    private static void printBooks(List<Book> library) {

        if (library.isEmpty()) {
            System.out.println("Бібліотека порожня.");
            return;
        }

        for (Book book : library) {
            System.out.println(book);
        }
    }
}
