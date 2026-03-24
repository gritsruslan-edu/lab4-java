package org.sumdu;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Scanner;
import java.util.Properties;

/**
 * Драйвер програми для роботи з книгами.
 */
public class Main {

    /**
     * Точка входу в програму.
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Не вказано шлях до файлу db.properties.");
            return;
        }

        Properties properties = loadProperties(args[0]);

        if (properties == null) {
            return;
        }

        Scanner scanner = new Scanner(System.in);
        BookRepository repository = new BookRepository(properties);

        boolean running = true;

        while (running) {
            System.out.println("\nОберіть дію:");
            System.out.println("1 - Пошук об'єкта");
            System.out.println("2 - Створити новий об'єкт");
            System.out.println("3 - Завершити програму");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    searchMenu();
                    break;
                case "2":
                    createBookByType(scanner, repository);
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
     * Завантажує властивості підключення до бази даних.
     */
    private static Properties loadProperties(String path) {
        Properties properties = new Properties();

        try (Reader reader = new FileReader(path)) {
            properties.load(reader);
            return properties;
        } catch (IOException e) {
            System.out.println("Не вдалося зчитати файл конфігурації: " + e.getMessage());
            return null;
        }
    }

    /**
     * Виводить повідомлення про нереалізований пошук.
     */
    private static void searchMenu() {
        System.out.println("Пошук ще не реалізовано.");
    }

    /**
     * Створює новий об'єкт вибраного типу та додає його до бази даних.
     */
    private static void createBookByType(Scanner scanner, BookRepository repository) {
        try {
            System.out.println("\nОберіть тип об'єкта:");
            System.out.println("1 - EBook");
            System.out.println("2 - PrintedBook");
            System.out.println("3 - AudioBook");
            System.out.println("4 - ScientificBook");

            String typeChoice = scanner.nextLine();

            switch (typeChoice) {
                case "1":
                    createEBook(scanner, repository);
                    break;
                case "2":
                    createPrintedBook(scanner, repository);
                    break;
                case "3":
                    createAudioBook(scanner, repository);
                    break;
                case "4":
                    createScientificBook(scanner, repository);
                    break;
                default:
                    System.out.println("Невірний тип об'єкта.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    /**
     * Створює об'єкт похідного класу EBook.
     */
    private static void createEBook(Scanner scanner, BookRepository repository) {
        String title = readTitle(scanner);
        String author = readAuthor(scanner);
        int year = readYear(scanner);
        int pages = readPages(scanner);
        Genre genre = readGenre(scanner);

        System.out.print("Формат файлу: ");
        String fileFormat = scanner.nextLine();

        System.out.print("Розмір файлу (MB): ");
        double fileSize = Double.parseDouble(scanner.nextLine());

        int quantity = readQuantity(scanner);

        Book book = new EBook(title, author, year, pages, genre, fileFormat, fileSize);
        repository.insertBook(book, quantity);

        System.out.println("Електронну книгу успішно додано!");
    }

    /**
     * Створює об'єкт похідного класу PrintedBook.
     */
    private static void createPrintedBook(Scanner scanner, BookRepository repository) {
        String title = readTitle(scanner);
        String author = readAuthor(scanner);
        int year = readYear(scanner);
        int pages = readPages(scanner);
        Genre genre = readGenre(scanner);

        System.out.print("Тип палітурки: ");
        String coverType = scanner.nextLine();

        System.out.print("Тираж: ");
        int printRun = Integer.parseInt(scanner.nextLine());

        int quantity = readQuantity(scanner);

        Book book = new PrintedBook(title, author, year, pages, genre, coverType, printRun);
        repository.insertBook(book, quantity);

        System.out.println("Друковану книгу успішно додано!");
    }

    /**
     * Створює об'єкт похідного класу AudioBook.
     */
    private static void createAudioBook(Scanner scanner, BookRepository repository) {
        String title = readTitle(scanner);
        String author = readAuthor(scanner);
        int year = readYear(scanner);
        int pages = readPages(scanner);
        Genre genre = readGenre(scanner);

        System.out.print("Диктор: ");
        String narrator = scanner.nextLine();

        System.out.print("Тривалість у хвилинах: ");
        int durationMinutes = Integer.parseInt(scanner.nextLine());

        int quantity = readQuantity(scanner);

        Book book = new AudioBook(title, author, year, pages, genre, narrator, durationMinutes);
        repository.insertBook(book, quantity);

        System.out.println("Аудіокнигу успішно додано!");
    }

    /**
     * Створює об'єкт похідного класу ScientificBook.
     */
    private static void createScientificBook(Scanner scanner, BookRepository repository) {
        String title = readTitle(scanner);
        String author = readAuthor(scanner);
        int year = readYear(scanner);
        int pages = readPages(scanner);
        Genre genre = readGenre(scanner);

        System.out.print("Галузь науки: ");
        String fieldOfScience = scanner.nextLine();

        System.out.print("Чи є книга рецензованою? (true/false): ");
        boolean peerReviewed = Boolean.parseBoolean(scanner.nextLine());

        int quantity = readQuantity(scanner);

        Book book = new ScientificBook(title, author, year, pages, genre, fieldOfScience, peerReviewed);
        repository.insertBook(book, quantity);

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
     * Зчитує кількість книги.
     */
    private static int readQuantity(Scanner scanner) {
        System.out.print("Кількість: ");
        return Integer.parseInt(scanner.nextLine());
    }
}