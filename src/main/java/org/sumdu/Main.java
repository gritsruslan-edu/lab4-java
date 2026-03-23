package org.sumdu;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Драйвер програми для роботи з книгами.
 */
public class Main {

    /**
     * Шлях до файлу збереження.
     */
    private static final String FILE_NAME = "input.txt";

    /**
     * Точка входу в програму.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Book> books = loadBooksFromFile();

        boolean running = true;

        while (running) {
            System.out.println("\nОберіть дію:");
            System.out.println("1 - Пошук об'єкта");
            System.out.println("2 - Створити новий об'єкт");
            System.out.println("3 - Вивести всі книги");
            System.out.println("4 - Завершити програму");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    searchMenu(scanner, books);
                    break;
                case "2":
                    createBookByType(scanner, books);
                    break;
                case "3":
                    printBooks(books);
                    break;
                case "4":
                    saveBooksToFile(books);
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
     * Виводить підменю пошуку.
     */
    private static void searchMenu(Scanner scanner, List<Book> books) {
        boolean searching = true;

        while (searching) {
            System.out.println("\nПідменю пошуку:");
            System.out.println("1 - Пошук за назвою");
            System.out.println("2 - Пошук за автором");
            System.out.println("3 - Пошук за жанром");
            System.out.println("4 - Повернутися до головного меню");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    searchByTitle(scanner, books);
                    break;
                case "2":
                    searchByAuthor(scanner, books);
                    break;
                case "3":
                    searchByGenre(scanner, books);
                    break;
                case "4":
                    searching = false;
                    break;
                default:
                    System.out.println("Невірний вибір. Спробуйте ще раз.");
            }
        }
    }

    /**
     * Виконує пошук книг за назвою.
     */
    private static void searchByTitle(Scanner scanner, List<Book> books) {
        System.out.print("Введіть назву для пошуку: ");
        String title = scanner.nextLine().trim();

        List<Book> foundBooks = findBooksByTitle(books, title);
        printSearchResults(foundBooks);
    }

    /**
     * Виконує пошук книг за автором.
     */
    private static void searchByAuthor(Scanner scanner, List<Book> books) {
        System.out.print("Введіть автора для пошуку: ");
        String author = scanner.nextLine().trim();

        List<Book> foundBooks = findBooksByAuthor(books, author);
        printSearchResults(foundBooks);
    }

    /**
     * Виконує пошук книг за жанром.
     */
    private static void searchByGenre(Scanner scanner, List<Book> books) {
        System.out.println("Оберіть жанр із переліку:");
        for (Genre genre : Genre.values()) {
            System.out.println("- " + genre);
        }

        System.out.print("Введіть жанр для пошуку: ");
        String genreInput = scanner.nextLine().trim().toUpperCase();

        try {
            Genre genre = Genre.valueOf(genreInput);
            List<Book> foundBooks = findBooksByGenre(books, genre);
            printSearchResults(foundBooks);
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: такого жанру не існує.");
        }
    }

    /**
     * Повертає всі книги, що відповідають назві.
     */
    private static List<Book> findBooksByTitle(List<Book> books, String title) {
        List<Book> foundBooks = new ArrayList<>();

        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                foundBooks.add(book);
            }
        }

        return foundBooks;
    }

    /**
     * Повертає всі книги, що відповідають автору.
     */
    private static List<Book> findBooksByAuthor(List<Book> books, String author) {
        List<Book> foundBooks = new ArrayList<>();

        for (Book book : books) {
            if (book.getAuthor().equalsIgnoreCase(author)) {
                foundBooks.add(book);
            }
        }

        return foundBooks;
    }

    /**
     * Повертає всі книги, що відповідають жанру.
     */
    private static List<Book> findBooksByGenre(List<Book> books, Genre genre) {
        List<Book> foundBooks = new ArrayList<>();

        for (Book book : books) {
            if (book.getGenre() == genre) {
                foundBooks.add(book);
            }
        }

        return foundBooks;
    }

    /**
     * Виводить результати пошуку.
     */
    private static void printSearchResults(List<Book> foundBooks) {
        if (foundBooks.isEmpty()) {
            System.out.println("Жоден об'єкт не відповідає умовам пошуку.");
            return;
        }

        System.out.println("Знайдені об'єкти:");
        for (Book book : foundBooks) {
            System.out.println(book);
        }
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

    /**
     * Завантажує книги з файлу.
     */
    private static List<Book> loadBooksFromFile() {
        List<Book> books = new ArrayList<>();
        Path path = Path.of(FILE_NAME);

        if (!Files.exists(path)) {
            return books;
        }

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                books.add(parseBook(line));
            }

            System.out.println("Книги успішно завантажено з файлу.");
            return books;

        } catch (Exception e) {
            System.out.println("Помилка зчитування файлу input.txt. Програму буде запущено з порожнім списком книг.");
            return new ArrayList<>();
        }
    }

    /**
     * Зберігає книги у файл.
     */
    private static void saveBooksToFile(List<Book> books) {
        Path path = Path.of(FILE_NAME);

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (Book book : books) {
                writer.write(serializeBook(book));
                writer.newLine();
            }

            System.out.println("Книги успішно збережено у файл.");

        } catch (IOException e) {
            System.out.println("Не вдалося зберегти книги у файл.");
        }
    }

    /**
     * Перетворює книгу у рядок для збереження.
     */
    private static String serializeBook(Book book) {
        if (book instanceof EBook eBook) {
            return "EBook;" +
                    eBook.getTitle() + ";" +
                    eBook.getAuthor() + ";" +
                    eBook.getYear() + ";" +
                    eBook.getPages() + ";" +
                    eBook.getGenre() + ";" +
                    eBook.getFileFormat() + ";" +
                    eBook.getFileSize();
        }

        if (book instanceof PrintedBook printedBook) {
            return "PrintedBook;" +
                    printedBook.getTitle() + ";" +
                    printedBook.getAuthor() + ";" +
                    printedBook.getYear() + ";" +
                    printedBook.getPages() + ";" +
                    printedBook.getGenre() + ";" +
                    printedBook.getCoverType() + ";" +
                    printedBook.getPrintRun();
        }

        if (book instanceof AudioBook audioBook) {
            return "AudioBook;" +
                    audioBook.getTitle() + ";" +
                    audioBook.getAuthor() + ";" +
                    audioBook.getYear() + ";" +
                    audioBook.getPages() + ";" +
                    audioBook.getGenre() + ";" +
                    audioBook.getNarrator() + ";" +
                    audioBook.getDurationMinutes();
        }

        if (book instanceof ScientificBook scientificBook) {
            return "ScientificBook;" +
                    scientificBook.getTitle() + ";" +
                    scientificBook.getAuthor() + ";" +
                    scientificBook.getYear() + ";" +
                    scientificBook.getPages() + ";" +
                    scientificBook.getGenre() + ";" +
                    scientificBook.getFieldOfScience() + ";" +
                    scientificBook.isPeerReviewed();
        }

        return "Book;" +
                book.getTitle() + ";" +
                book.getAuthor() + ";" +
                book.getYear() + ";" +
                book.getPages() + ";" +
                book.getGenre();
    }

    /**
     * Створює об'єкт книги з рядка файлу.
     */
    private static Book parseBook(String line) {
        String[] parts = line.split(";");

        if (parts.length == 0) {
            throw new IllegalArgumentException("Порожній рядок.");
        }

        String type = parts[0];

        switch (type) {
            case "Book":
                requirePartsCount(parts, 6);
                return new Book(
                        parts[1],
                        parts[2],
                        Integer.parseInt(parts[3]),
                        Integer.parseInt(parts[4]),
                        Genre.valueOf(parts[5])
                );

            case "EBook":
                requirePartsCount(parts, 8);
                return new EBook(
                        parts[1],
                        parts[2],
                        Integer.parseInt(parts[3]),
                        Integer.parseInt(parts[4]),
                        Genre.valueOf(parts[5]),
                        parts[6],
                        Double.parseDouble(parts[7])
                );

            case "PrintedBook":
                requirePartsCount(parts, 8);
                return new PrintedBook(
                        parts[1],
                        parts[2],
                        Integer.parseInt(parts[3]),
                        Integer.parseInt(parts[4]),
                        Genre.valueOf(parts[5]),
                        parts[6],
                        Integer.parseInt(parts[7])
                );

            case "AudioBook":
                requirePartsCount(parts, 8);
                return new AudioBook(
                        parts[1],
                        parts[2],
                        Integer.parseInt(parts[3]),
                        Integer.parseInt(parts[4]),
                        Genre.valueOf(parts[5]),
                        parts[6],
                        Integer.parseInt(parts[7])
                );

            case "ScientificBook":
                requirePartsCount(parts, 8);
                return new ScientificBook(
                        parts[1],
                        parts[2],
                        Integer.parseInt(parts[3]),
                        Integer.parseInt(parts[4]),
                        Genre.valueOf(parts[5]),
                        parts[6],
                        Boolean.parseBoolean(parts[7])
                );

            default:
                throw new IllegalArgumentException("Невідомий тип книги.");
        }
    }

    /**
     * Перевіряє кількість частин у рядку.
     */
    private static void requirePartsCount(String[] parts, int expected) {
        if (parts.length != expected) {
            throw new IllegalArgumentException("Невірний формат рядка.");
        }
    }
}