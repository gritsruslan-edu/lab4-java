package org.sumdu;

import org.sumdu.exceptions.BookNotFoundException;
import org.sumdu.exceptions.InvalidFieldValueException;

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
            System.out.println("3 - Модифікувати об'єкт");
            System.out.println("4 - Видалити об'єкт");
            System.out.println("5 - Вивести всі об'єкти у відсортованому режимі");
            System.out.println("6 - Пошук за ідентифікатором");

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
                    modifyBook(scanner, library);
                    break;
                case "4":
                    deleteBook(scanner, library);
                    break;
                case "5":
                    sortMenu(scanner, library);
                    break;
                case "6":
                    findBookByUuid(scanner, library);
                    break;
                default:
                    System.out.println("Невірний вибір. Спробуйте ще раз.");
            }
        }

        scanner.close();
    }

    /**
     * Видаляє книгу з бібліотеки.
     */
    private static void deleteBook(Scanner scanner, Library library) {
        if (library.isEmpty()) {
            System.out.println("Список книг порожній.");
            return;
        }

        System.out.print("Введіть UUID книги для видалення: ");
        String uuidText = scanner.nextLine().trim();

        try {
            UUID uuid = UUID.fromString(uuidText);
            Book existingBook = library.findByUuid(uuid);
            boolean deleted = library.delete(existingBook);

            if (deleted) {
                System.out.println("Об'єкт успішно видалено.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Невірний формат UUID.");
        } catch (BookNotFoundException | InvalidFieldValueException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Модифікує книгу в бібліотеці.
     */
    private static void modifyBook(Scanner scanner, Library library) {
        if (library.isEmpty()) {
            System.out.println("Список книг порожній.");
            return;
        }

        System.out.print("Введіть UUID книги для модифікації: ");
        String uuidText = scanner.nextLine().trim();

        try {
            UUID uuid = UUID.fromString(uuidText);
            Book existingBook = library.findByUuid(uuid);
            Book updatedBook = buildUpdatedBook(scanner, existingBook);

            if (updatedBook == null) {
                System.out.println("Модифікацію скасовано.");
                return;
            }

            boolean updated = library.update(existingBook, updatedBook);

            if (updated) {
                System.out.println("Об'єкт успішно оновлено.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Невірний формат UUID.");
        } catch (BookNotFoundException | InvalidFieldValueException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Оновлює електронну книгу.
     */
    private static Book buildUpdatedEBook(Scanner scanner, EBook existingBook) {
        System.out.println("Оберіть атрибут для зміни:");
        System.out.println("1 - Назва");
        System.out.println("2 - Автор");
        System.out.println("3 - Рік видання");
        System.out.println("4 - Кількість сторінок");
        System.out.println("5 - Жанр");
        System.out.println("6 - Формат файлу");
        System.out.println("7 - Розмір файлу");

        String choice = scanner.nextLine();

        String title = existingBook.getTitle();
        String author = existingBook.getAuthor();
        int year = existingBook.getYear();
        int pages = existingBook.getPages();
        Genre genre = existingBook.getGenre();
        String fileFormat = existingBook.getFileFormat();
        double fileSize = existingBook.getFileSize();

        try {
            switch (choice) {
                case "1":
                    System.out.print("Нова назва: ");
                    title = scanner.nextLine();
                    break;
                case "2":
                    System.out.print("Новий автор: ");
                    author = scanner.nextLine();
                    break;
                case "3":
                    System.out.print("Новий рік видання: ");
                    year = Integer.parseInt(scanner.nextLine());
                    break;
                case "4":
                    System.out.print("Нова кількість сторінок: ");
                    pages = Integer.parseInt(scanner.nextLine());
                    break;
                case "5":
                    genre = readGenre(scanner);
                    break;
                case "6":
                    System.out.print("Новий формат файлу: ");
                    fileFormat = scanner.nextLine();
                    break;
                case "7":
                    System.out.print("Новий розмір файлу: ");
                    fileSize = Double.parseDouble(scanner.nextLine());
                    break;
                default:
                    return null;
            }
        } catch (NumberFormatException e) {
            System.out.println("Невірний формат числа.");
            return null;
        }

        EBook updatedBook = new EBook(title, author, year, pages, genre, fileFormat, fileSize);
        updatedBook.setUuid(existingBook.getUuid());
        return updatedBook;
    }

    /**
     * Оновлює друковану книгу.
     */
    private static Book buildUpdatedPrintedBook(Scanner scanner, PrintedBook existingBook) {
        System.out.println("Оберіть атрибут для зміни:");
        System.out.println("1 - Назва");
        System.out.println("2 - Автор");
        System.out.println("3 - Рік видання");
        System.out.println("4 - Кількість сторінок");
        System.out.println("5 - Жанр");
        System.out.println("6 - Тип палітурки");
        System.out.println("7 - Тираж");

        String choice = scanner.nextLine();

        String title = existingBook.getTitle();
        String author = existingBook.getAuthor();
        int year = existingBook.getYear();
        int pages = existingBook.getPages();
        Genre genre = existingBook.getGenre();
        String coverType = existingBook.getCoverType();
        int printRun = existingBook.getPrintRun();

        try {
            switch (choice) {
                case "1":
                    System.out.print("Нова назва: ");
                    title = scanner.nextLine();
                    break;
                case "2":
                    System.out.print("Новий автор: ");
                    author = scanner.nextLine();
                    break;
                case "3":
                    System.out.print("Новий рік видання: ");
                    year = Integer.parseInt(scanner.nextLine());
                    break;
                case "4":
                    System.out.print("Нова кількість сторінок: ");
                    pages = Integer.parseInt(scanner.nextLine());
                    break;
                case "5":
                    genre = readGenre(scanner);
                    break;
                case "6":
                    System.out.print("Новий тип палітурки: ");
                    coverType = scanner.nextLine();
                    break;
                case "7":
                    System.out.print("Новий тираж: ");
                    printRun = Integer.parseInt(scanner.nextLine());
                    break;
                default:
                    return null;
            }
        } catch (NumberFormatException e) {
            System.out.println("Невірний формат числа.");
            return null;
        }

        PrintedBook updatedBook = new PrintedBook(title, author, year, pages, genre, coverType, printRun);
        updatedBook.setUuid(existingBook.getUuid());
        return updatedBook;
    }


    /**
     * Оновлює аудіокнигу.
     */
    private static Book buildUpdatedAudioBook(Scanner scanner, AudioBook existingBook) {
        System.out.println("Оберіть атрибут для зміни:");
        System.out.println("1 - Назва");
        System.out.println("2 - Автор");
        System.out.println("3 - Рік видання");
        System.out.println("4 - Кількість сторінок");
        System.out.println("5 - Жанр");
        System.out.println("6 - Диктор");
        System.out.println("7 - Тривалість у хвилинах");

        String choice = scanner.nextLine();

        String title = existingBook.getTitle();
        String author = existingBook.getAuthor();
        int year = existingBook.getYear();
        int pages = existingBook.getPages();
        Genre genre = existingBook.getGenre();
        String narrator = existingBook.getNarrator();
        int durationMinutes = existingBook.getDurationMinutes();

        try {
            switch (choice) {
                case "1":
                    System.out.print("Нова назва: ");
                    title = scanner.nextLine();
                    break;
                case "2":
                    System.out.print("Новий автор: ");
                    author = scanner.nextLine();
                    break;
                case "3":
                    System.out.print("Новий рік видання: ");
                    year = Integer.parseInt(scanner.nextLine());
                    break;
                case "4":
                    System.out.print("Нова кількість сторінок: ");
                    pages = Integer.parseInt(scanner.nextLine());
                    break;
                case "5":
                    genre = readGenre(scanner);
                    break;
                case "6":
                    System.out.print("Новий диктор: ");
                    narrator = scanner.nextLine();
                    break;
                case "7":
                    System.out.print("Нова тривалість у хвилинах: ");
                    durationMinutes = Integer.parseInt(scanner.nextLine());
                    break;
                default:
                    return null;
            }
        } catch (NumberFormatException e) {
            System.out.println("Невірний формат числа.");
            return null;
        }

        AudioBook updatedBook = new AudioBook(title, author, year, pages, genre, narrator, durationMinutes);
        updatedBook.setUuid(existingBook.getUuid());
        return updatedBook;
    }

    /**
     * Оновлює наукову книгу.
     */
    private static Book buildUpdatedScientificBook(Scanner scanner, ScientificBook existingBook) {
        System.out.println("Оберіть атрибут для зміни:");
        System.out.println("1 - Назва");
        System.out.println("2 - Автор");
        System.out.println("3 - Рік видання");
        System.out.println("4 - Кількість сторінок");
        System.out.println("5 - Жанр");
        System.out.println("6 - Галузь науки");
        System.out.println("7 - Рецензована");

        String choice = scanner.nextLine();

        String title = existingBook.getTitle();
        String author = existingBook.getAuthor();
        int year = existingBook.getYear();
        int pages = existingBook.getPages();
        Genre genre = existingBook.getGenre();
        String fieldOfScience = existingBook.getFieldOfScience();
        boolean peerReviewed = existingBook.isPeerReviewed();

        try {
            switch (choice) {
                case "1":
                    System.out.print("Нова назва: ");
                    title = scanner.nextLine();
                    break;
                case "2":
                    System.out.print("Новий автор: ");
                    author = scanner.nextLine();
                    break;
                case "3":
                    System.out.print("Новий рік видання: ");
                    year = Integer.parseInt(scanner.nextLine());
                    break;
                case "4":
                    System.out.print("Нова кількість сторінок: ");
                    pages = Integer.parseInt(scanner.nextLine());
                    break;
                case "5":
                    genre = readGenre(scanner);
                    break;
                case "6":
                    System.out.print("Нова галузь науки: ");
                    fieldOfScience = scanner.nextLine();
                    break;
                case "7":
                    System.out.print("Нове значення рецензування (true/false): ");
                    peerReviewed = Boolean.parseBoolean(scanner.nextLine());
                    break;
                default:
                    return null;
            }
        } catch (NumberFormatException e) {
            System.out.println("Невірний формат числа.");
            return null;
        }

        ScientificBook updatedBook = new ScientificBook(title, author, year, pages, genre, fieldOfScience, peerReviewed);
        updatedBook.setUuid(existingBook.getUuid());
        return updatedBook;
    }

    /**
     * Створює новий об'єкт книги на основі старого зі зміненим атрибутом.
     */
    private static Book buildUpdatedBook(Scanner scanner, Book existingBook) {
        if (existingBook instanceof EBook eBook) {
            return buildUpdatedEBook(scanner, eBook);
        }

        if (existingBook instanceof PrintedBook printedBook) {
            return buildUpdatedPrintedBook(scanner, printedBook);
        }

        if (existingBook instanceof AudioBook audioBook) {
            return buildUpdatedAudioBook(scanner, audioBook);
        }

        if (existingBook instanceof ScientificBook scientificBook) {
            return buildUpdatedScientificBook(scanner, scientificBook);
        }

        return null;
    }

    /**
     * Шукає книгу за UUID.
     */
    private static void findBookByUuid(Scanner scanner, Library library) {
        System.out.print("Введіть UUID книги: ");
        String uuidText = scanner.nextLine().trim();

        try {
            UUID uuid = UUID.fromString(uuidText);
            Book book = library.findByUuid(uuid);
            System.out.println(book + ", quantity=" + library.getQuantityForBook(book));
        } catch (IllegalArgumentException e) {
            System.out.println("Невірний формат UUID.");
        } catch (BookNotFoundException | InvalidFieldValueException e) {
            System.out.println(e.getMessage());
        }
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
        } catch (InvalidFieldValueException e) {
            System.out.println(e.getMessage());
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