package org.sumdu;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

/**
 * Драйвер програми для роботи з книгами.
 */
public class Main {

    /**
     * Точка входу в програму.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Library library = new Library();

        boolean running = true;

        while (running) {
            System.out.println("\nОберіть дію:");
            System.out.println("1 - Створити новий об'єкт");
            System.out.println("2 - Завершити програму");
            System.out.println("3 - Вивести всі об'єкти у відсортованому режимі");
            System.out.println("4 - Пошук за ідентифікатором");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    createBookByType(scanner, library);
                    break;
                case "2":
                    running = false;
                    System.out.println("Програму завершено.");
                    break;
                case "3":
                    sortMenu(scanner, library);
                    break;
                case "4":
                    findBookByUuid(scanner, library);
                    break;
                default:
                    System.out.println("Невірний вибір. Спробуйте ще раз.");
            }
        }

        scanner.close();
    }

    /**
     * Шукає книгу за UUID.
     */
    private static void findBookByUuid(Scanner scanner, Library library) {
        System.out.print("Введіть UUID книги: ");
        String uuidText = scanner.nextLine().trim();

        UUID uuid;
        try {
            uuid = UUID.fromString(uuidText);
        } catch (IllegalArgumentException e) {
            System.out.println("Невірний формат UUID.");
            return;
        }

        Book book = library.findByUuid(uuid);

        if (book == null) {
            System.out.println("Книгу не знайдено.");
            return;
        }

        System.out.println(book + ", quantity=" + library.getQuantityForBook(book));
    }

    /**
     * Виводить підменю сортування.
     */
    private static void sortMenu(Scanner scanner, Library library) {
        boolean sorting = true;

        while (sorting) {
            System.out.println("\nОберіть критерій сортування:");
            System.out.println("1 - Сортувати за назвою");
            System.out.println("2 - Сортувати за роком видання");
            System.out.println("3 - Сортувати за кількістю сторінок");
            System.out.println("4 - Повернутися до головного меню");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    printSortedBooksByTitle(library);
                    break;
                case "2":
                    printSortedBooksByYear(library);
                    break;
                case "3":
                    printSortedBooksByPages(library);
                    break;
                case "4":
                    sorting = false;
                    break;
                default:
                    System.out.println("Невірний вибір. Спробуйте ще раз.");
            }
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
     * Виводить усі книги, відсортовані за назвою.
     */
    private static void printSortedBooksByTitle(Library library) {
        List<Book> books = new ArrayList<>(library.getBooks());

        if (books.isEmpty()) {
            System.out.println("Список книг порожній.");
            return;
        }

        Comparator<Book> cmp = new Comparator<Book>() {
            @Override
            public int compare(Book o1, Book o2) {
                return o1.getTitle().compareToIgnoreCase(o2.getTitle());
            }
        };

        books.sort(cmp);

        for (Book book : books) {
            System.out.println(book + ", quantity=" + library.getQuantityForBook(book));
        }
    }

    /**
     * Виводить усі книги, відсортовані за роком видання.
     */
    private static void printSortedBooksByYear(Library library) {
        List<Book> books = new ArrayList<>(library.getBooks());

        if (books.isEmpty()) {
            System.out.println("Список книг порожній.");
            return;
        }

        Comparator<Book> cmp = new Comparator<Book>() {
            @Override
            public int compare(Book o1, Book o2) {
                return Integer.compare(o1.getYear(), o2.getYear());
            }
        };

        books.sort(cmp);

        for (Book book : books) {
            System.out.println(book + ", quantity=" + library.getQuantityForBook(book));
        }
    }

    /**
     * Виводить усі книги, відсортовані за кількістю сторінок.
     */
    private static void printSortedBooksByPages(Library library) {
        List<Book> books = new ArrayList<>(library.getBooks());

        if (books.isEmpty()) {
            System.out.println("Список книг порожній.");
            return;
        }

        Comparator<Book> cmp = new Comparator<Book>() {
            @Override
            public int compare(Book o1, Book o2) {
                return Integer.compare(o1.getPages(), o2.getPages());
            }
        };

        books.sort(cmp);

        for (Book book : books) {
            System.out.println(book + ", quantity=" + library.getQuantityForBook(book));
        }
    }
}