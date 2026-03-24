package org.sumdu;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
        Library library = loadLibraryFromFile();

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
                    searchMenu(scanner, library);
                    break;
                case "2":
                    createBookByType(scanner, library);
                    break;
                case "3":
                    printBooks(library);
                    break;
                case "4":
                    saveLibraryToFile(library);
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
    private static void searchMenu(Scanner scanner, Library library) {
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
                    searchByTitle(scanner, library);
                    break;
                case "2":
                    searchByAuthor(scanner, library);
                    break;
                case "3":
                    searchByGenre(scanner, library);
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
    private static void searchByTitle(Scanner scanner, Library library) {
        System.out.print("Введіть назву для пошуку: ");
        String title = scanner.nextLine().trim();

        List<Book> foundBooks = library.findBooksByTitle(title);
        printSearchResults(foundBooks, library);
    }

    /**
     * Виконує пошук книг за автором.
     */
    private static void searchByAuthor(Scanner scanner, Library library) {
        System.out.print("Введіть автора для пошуку: ");
        String author = scanner.nextLine().trim();

        List<Book> foundBooks = library.findBooksByAuthor(author);
        printSearchResults(foundBooks, library);
    }

    /**
     * Виконує пошук книг за жанром.
     */
    private static void searchByGenre(Scanner scanner, Library library) {
        System.out.println("Оберіть жанр із переліку:");
        for (Genre genre : Genre.values()) {
            System.out.println("- " + genre);
        }

        System.out.print("Введіть жанр для пошуку: ");
        String genreInput = scanner.nextLine().trim().toUpperCase();

        try {
            Genre genre = Genre.valueOf(genreInput);
            List<Book> foundBooks = library.findBooksByGenre(genre);
            printSearchResults(foundBooks, library);
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: такого жанру не існує.");
        }
    }

    /**
     * Виводить результати пошуку.
     */
    private static void printSearchResults(List<Book> foundBooks, Library library) {
        if (foundBooks.isEmpty()) {
            System.out.println("Жоден об'єкт не відповідає умовам пошуку.");
            return;
        }

        System.out.println("Знайдені об'єкти:");
        for (Book book : foundBooks) {
            System.out.println(book + ", quantity=" + library.getQuantityForBook(book));
        }
    }

    /**
     * Створює новий об'єкт вибраного типу та додає його до бібліотеки.
     */
    private static void createBookByType(Scanner scanner, Library library) {
        try {
            System.out.println("\nОберіть тип об'єкта:");
            System.out.println("1 - EBook");
            System.out.println("2 - PrintedBook");
            System.out.println("3 - AudioBook");
            System.out.println("4 - ScientificBook");

            String typeChoice = scanner.nextLine();

            switch (typeChoice) {
                case "1":
                    createEBook(scanner, library);
                    break;
                case "2":
                    createPrintedBook(scanner, library);
                    break;
                case "3":
                    createAudioBook(scanner, library);
                    break;
                case "4":
                    createScientificBook(scanner, library);
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
    private static void createEBook(Scanner scanner, Library library) {
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
        library.addNewBook(book, quantity);

        System.out.println("Електронну книгу успішно додано!");
    }

    /**
     * Створює об'єкт похідного класу PrintedBook.
     */
    private static void createPrintedBook(Scanner scanner, Library library) {
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
        library.addNewBook(book, quantity);

        System.out.println("Друковану книгу успішно додано!");
    }

    /**
     * Створює об'єкт похідного класу AudioBook.
     */
    private static void createAudioBook(Scanner scanner, Library library) {
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
        library.addNewBook(book, quantity);

        System.out.println("Аудіокнигу успішно додано!");
    }

    /**
     * Створює об'єкт похідного класу ScientificBook.
     */
    private static void createScientificBook(Scanner scanner, Library library) {
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
        library.addNewBook(book, quantity);

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

    /**
     * Виводить усі книги з бібліотеки.
     */
    private static void printBooks(Library library) {
        if (library.isEmpty()) {
            System.out.println("Список книг порожній.");
            return;
        }

        for (int i = 0; i < library.getBooks().size(); i++) {
            System.out.println(library.getBooks().get(i) + ", quantity=" + library.getQuantityByIndex(i));
        }
    }

    /**
     * Завантажує бібліотеку з файлу.
     */
    private static Library loadLibraryFromFile() {
        Library library = new Library();
        Path path = Path.of(FILE_NAME);

        if (!Files.exists(path)) {
            return library;
        }

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                ParsedBookData parsedBookData = parseBookLine(line);
                library.addNewBook(parsedBookData.book(), parsedBookData.quantity());
            }

            System.out.println("Книги успішно завантажено з файлу.");
            return library;

        } catch (Exception e) {
            System.out.println("Помилка зчитування файлу input.txt. Програму буде запущено з порожньою бібліотекою.");
            return new Library();
        }
    }

    /**
     * Зберігає бібліотеку у файл.
     */
    private static void saveLibraryToFile(Library library) {
        Path path = Path.of(FILE_NAME);

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (int i = 0; i < library.getBooks().size(); i++) {
                Book book = library.getBooks().get(i);
                int quantity = library.getQuantityByIndex(i);

                writer.write(serializeBook(book, quantity));
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
    private static String serializeBook(Book book, int quantity) {
        if (book instanceof EBook eBook) {
            return "EBook;" +
                    eBook.getTitle() + ";" +
                    eBook.getAuthor() + ";" +
                    eBook.getYear() + ";" +
                    eBook.getPages() + ";" +
                    eBook.getGenre() + ";" +
                    eBook.getFileFormat() + ";" +
                    eBook.getFileSize() + ";" +
                    quantity;
        }

        if (book instanceof PrintedBook printedBook) {
            return "PrintedBook;" +
                    printedBook.getTitle() + ";" +
                    printedBook.getAuthor() + ";" +
                    printedBook.getYear() + ";" +
                    printedBook.getPages() + ";" +
                    printedBook.getGenre() + ";" +
                    printedBook.getCoverType() + ";" +
                    printedBook.getPrintRun() + ";" +
                    quantity;
        }

        if (book instanceof AudioBook audioBook) {
            return "AudioBook;" +
                    audioBook.getTitle() + ";" +
                    audioBook.getAuthor() + ";" +
                    audioBook.getYear() + ";" +
                    audioBook.getPages() + ";" +
                    audioBook.getGenre() + ";" +
                    audioBook.getNarrator() + ";" +
                    audioBook.getDurationMinutes() + ";" +
                    quantity;
        }

        if (book instanceof ScientificBook scientificBook) {
            return "ScientificBook;" +
                    scientificBook.getTitle() + ";" +
                    scientificBook.getAuthor() + ";" +
                    scientificBook.getYear() + ";" +
                    scientificBook.getPages() + ";" +
                    scientificBook.getGenre() + ";" +
                    scientificBook.getFieldOfScience() + ";" +
                    scientificBook.isPeerReviewed() + ";" +
                    quantity;
        }

        throw new IllegalArgumentException("Невідомий тип книги.");
    }

    /**
     * Зчитує книгу та її кількість з рядка файлу.
     */
    private static ParsedBookData parseBookLine(String line) {
        String[] parts = line.split(";");

        if (parts.length == 0) {
            throw new IllegalArgumentException("Порожній рядок.");
        }

        String type = parts[0];

        switch (type) {
            case "EBook":
                requirePartsCount(parts, 9);
                return new ParsedBookData(
                        new EBook(
                                parts[1],
                                parts[2],
                                Integer.parseInt(parts[3]),
                                Integer.parseInt(parts[4]),
                                Genre.valueOf(parts[5]),
                                parts[6],
                                Double.parseDouble(parts[7])
                        ),
                        Integer.parseInt(parts[8])
                );

            case "PrintedBook":
                requirePartsCount(parts, 9);
                return new ParsedBookData(
                        new PrintedBook(
                                parts[1],
                                parts[2],
                                Integer.parseInt(parts[3]),
                                Integer.parseInt(parts[4]),
                                Genre.valueOf(parts[5]),
                                parts[6],
                                Integer.parseInt(parts[7])
                        ),
                        Integer.parseInt(parts[8])
                );

            case "AudioBook":
                requirePartsCount(parts, 9);
                return new ParsedBookData(
                        new AudioBook(
                                parts[1],
                                parts[2],
                                Integer.parseInt(parts[3]),
                                Integer.parseInt(parts[4]),
                                Genre.valueOf(parts[5]),
                                parts[6],
                                Integer.parseInt(parts[7])
                        ),
                        Integer.parseInt(parts[8])
                );

            case "ScientificBook":
                requirePartsCount(parts, 9);
                return new ParsedBookData(
                        new ScientificBook(
                                parts[1],
                                parts[2],
                                Integer.parseInt(parts[3]),
                                Integer.parseInt(parts[4]),
                                Genre.valueOf(parts[5]),
                                parts[6],
                                Boolean.parseBoolean(parts[7])
                        ),
                        Integer.parseInt(parts[8])
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

    /**
     * Допоміжний record для зчитування книги та кількості.
     */
    private record ParsedBookData(Book book, int quantity) {
    }
}