package org.sumdu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

/**
 * Клас для роботи з книгами в PostgreSQL.
 */
public class BookRepository {

    /**
     * URL бази даних.
     */
    private final String url;

    /**
     * Користувач бази даних.
     */
    private final String user;

    /**
     * Пароль бази даних.
     */
    private final String password;

    /**
     * Створює репозиторій для роботи з базою даних.
     */
    public BookRepository(Properties properties) {
        this.url = properties.getProperty("db.url");
        this.user = properties.getProperty("db.user");
        this.password = properties.getProperty("db.password");
    }

    /**
     * Додає книгу до бази даних.
     */
    public void insertBook(Book book, int quantity) {
        String sql = """
                INSERT INTO books
                (uuid, type, title, author, year, pages, genre, quantity, file_format, file_size,
                 cover_type, print_run, narrator, duration_minutes, field_of_science, peer_reviewed)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setObject(1, book.getUuid());
            statement.setString(2, book.getClass().getSimpleName());
            statement.setString(3, book.getTitle());
            statement.setString(4, book.getAuthor());
            statement.setInt(5, book.getYear());
            statement.setInt(6, book.getPages());
            statement.setString(7, book.getGenre().name());
            statement.setInt(8, quantity);

            if (book instanceof EBook eBook) {
                statement.setString(9, eBook.getFileFormat());
                statement.setDouble(10, eBook.getFileSize());
            } else {
                statement.setNull(9, java.sql.Types.VARCHAR);
                statement.setNull(10, java.sql.Types.DOUBLE);
            }

            if (book instanceof PrintedBook printedBook) {
                statement.setString(11, printedBook.getCoverType());
                statement.setInt(12, printedBook.getPrintRun());
            } else {
                statement.setNull(11, java.sql.Types.VARCHAR);
                statement.setNull(12, java.sql.Types.INTEGER);
            }

            if (book instanceof AudioBook audioBook) {
                statement.setString(13, audioBook.getNarrator());
                statement.setInt(14, audioBook.getDurationMinutes());
            } else {
                statement.setNull(13, java.sql.Types.VARCHAR);
                statement.setNull(14, java.sql.Types.INTEGER);
            }

            if (book instanceof ScientificBook scientificBook) {
                statement.setString(15, scientificBook.getFieldOfScience());
                statement.setBoolean(16, scientificBook.isPeerReviewed());
            } else {
                statement.setNull(15, java.sql.Types.VARCHAR);
                statement.setNull(16, java.sql.Types.BOOLEAN);
            }

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Помилка під час додавання книги до бази даних: " + e.getMessage());
        }
    }

    /**
     * Повертає всі книги з бази даних.
     */
    public List<BookRow> getAllBooks() {
        List<BookRow> books = new ArrayList<>();

        String sql = """
                SELECT uuid, type, title, author, year, pages, genre, quantity,
                       file_format, file_size, cover_type, print_run,
                       narrator, duration_minutes, field_of_science, peer_reviewed
                FROM books
                """;

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                UUID uuid = UUID.fromString(resultSet.getString("uuid"));
                String type = resultSet.getString("type");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                int year = resultSet.getInt("year");
                int pages = resultSet.getInt("pages");
                Genre genre = Genre.valueOf(resultSet.getString("genre"));
                int quantity = resultSet.getInt("quantity");

                Book book;

                switch (type) {
                    case "EBook":
                        book = new EBook(
                                title,
                                author,
                                year,
                                pages,
                                genre,
                                resultSet.getString("file_format"),
                                resultSet.getDouble("file_size")
                        );
                        break;

                    case "PrintedBook":
                        book = new PrintedBook(
                                title,
                                author,
                                year,
                                pages,
                                genre,
                                resultSet.getString("cover_type"),
                                resultSet.getInt("print_run")
                        );
                        break;

                    case "AudioBook":
                        book = new AudioBook(
                                title,
                                author,
                                year,
                                pages,
                                genre,
                                resultSet.getString("narrator"),
                                resultSet.getInt("duration_minutes")
                        );
                        break;

                    case "ScientificBook":
                        book = new ScientificBook(
                                title,
                                author,
                                year,
                                pages,
                                genre,
                                resultSet.getString("field_of_science"),
                                resultSet.getBoolean("peer_reviewed")
                        );
                        break;

                    default:
                        System.out.println("Невідомий тип книги в БД: " + type);
                        continue;
                }

                book.setUuid(uuid);
                books.add(new BookRow(book, quantity));
            }

        } catch (SQLException e) {
            System.out.println("Помилка під час зчитування книг з бази даних: " + e.getMessage());
        }

        return books;
    }

    /**
     * Шукає книгу за UUID.
     */
    public BookRow findByUuid(UUID uuid) {
        String sql = """
                SELECT uuid, type, title, author, year, pages, genre, quantity,
                       file_format, file_size, cover_type, print_run,
                       narrator, duration_minutes, field_of_science, peer_reviewed
                FROM books
                WHERE uuid = ?
                """;

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setObject(1, uuid);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String type = resultSet.getString("type");
                    String title = resultSet.getString("title");
                    String author = resultSet.getString("author");
                    int year = resultSet.getInt("year");
                    int pages = resultSet.getInt("pages");
                    Genre genre = Genre.valueOf(resultSet.getString("genre"));
                    int quantity = resultSet.getInt("quantity");

                    Book book;

                    switch (type) {
                        case "EBook":
                            book = new EBook(
                                    title,
                                    author,
                                    year,
                                    pages,
                                    genre,
                                    resultSet.getString("file_format"),
                                    resultSet.getDouble("file_size")
                            );
                            break;

                        case "PrintedBook":
                            book = new PrintedBook(
                                    title,
                                    author,
                                    year,
                                    pages,
                                    genre,
                                    resultSet.getString("cover_type"),
                                    resultSet.getInt("print_run")
                            );
                            break;

                        case "AudioBook":
                            book = new AudioBook(
                                    title,
                                    author,
                                    year,
                                    pages,
                                    genre,
                                    resultSet.getString("narrator"),
                                    resultSet.getInt("duration_minutes")
                            );
                            break;

                        case "ScientificBook":
                            book = new ScientificBook(
                                    title,
                                    author,
                                    year,
                                    pages,
                                    genre,
                                    resultSet.getString("field_of_science"),
                                    resultSet.getBoolean("peer_reviewed")
                            );
                            break;

                        default:
                            System.out.println("Невідомий тип книги в БД: " + type);
                            return null;
                    }

                    book.setUuid(UUID.fromString(resultSet.getString("uuid")));
                    return new BookRow(book, quantity);
                }
            }

        } catch (SQLException e) {
            System.out.println("Помилка під час пошуку книги в базі даних: " + e.getMessage());
        }

        return null;
    }

    /**
     * Рядок результату з книгою та її кількістю.
     */
    public record BookRow(Book book, int quantity) {
    }
}