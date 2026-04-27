package org.sumdu;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

/**
 * GUI-стартер JavaFX застосунку.
 */
public class MainApp extends Application {

    /**
     * Репозиторій для роботи з базою даних.
     */
    private BookRepository repository;

    /**
     * Список для короткого виводу книг.
     */
    private final ObservableList<String> items = FXCollections.observableArrayList();

    /**
     * Запускає головне вікно JavaFX.
     */
    @Override
    public void start(Stage stage) {
        Properties properties = loadProperties("db.properties");

        if (properties == null) {
            showError("Не вдалося зчитати файл db.properties.");
            return;
        }

        repository = new BookRepository(properties);

        ComboBox<String> typeBox = new ComboBox<>(FXCollections.observableArrayList(
                "EBook",
                "PrintedBook",
                "AudioBook",
                "ScientificBook"
        ));
        typeBox.setValue("EBook");

        ComboBox<Genre> genreBox = new ComboBox<>(FXCollections.observableArrayList(Genre.values()));
        genreBox.setValue(Genre.FANTASY);

        TextField titleField = new TextField();
        titleField.setPromptText("Назва");

        TextField authorField = new TextField();
        authorField.setPromptText("Автор");

        TextField yearField = new TextField();
        yearField.setPromptText("Рік видання");

        TextField pagesField = new TextField();
        pagesField.setPromptText("Кількість сторінок");

        TextField quantityField = new TextField();
        quantityField.setPromptText("Кількість");

        Label extraLabel1 = new Label("Додаткове поле 1");
        TextField extraField1 = new TextField();

        Label extraLabel2 = new Label("Додаткове поле 2");
        TextField extraField2 = new TextField();

        TextField uuidField = new TextField();
        uuidField.setPromptText("Введіть UUID");

        Button findButton = new Button("Знайти");

        TextArea detailsArea = new TextArea();
        detailsArea.setEditable(false);
        detailsArea.setWrapText(true);
        detailsArea.setPrefHeight(220);

        updateExtraFields(typeBox.getValue(), extraLabel1, extraField1, extraLabel2, extraField2);

        typeBox.setOnAction(event -> updateExtraFields(
                typeBox.getValue(),
                extraLabel1,
                extraField1,
                extraLabel2,
                extraField2
        ));

        Button addButton = new Button("Додати");

        ListView<String> listView = new ListView<>(items);
        listView.setPrefHeight(200);

        addButton.setOnAction(event -> {
            try {
                String title = titleField.getText().trim();
                String author = authorField.getText().trim();
                int year = Integer.parseInt(yearField.getText().trim());
                int pages = Integer.parseInt(pagesField.getText().trim());
                int quantity = Integer.parseInt(quantityField.getText().trim());
                Genre genre = genreBox.getValue();

                Book book;

                if ("EBook".equals(typeBox.getValue())) {
                    String fileFormat = extraField1.getText().trim();
                    double fileSize = Double.parseDouble(extraField2.getText().trim());
                    book = new EBook(title, author, year, pages, genre, fileFormat, fileSize);
                } else if ("PrintedBook".equals(typeBox.getValue())) {
                    String coverType = extraField1.getText().trim();
                    int printRun = Integer.parseInt(extraField2.getText().trim());
                    book = new PrintedBook(title, author, year, pages, genre, coverType, printRun);
                } else if ("AudioBook".equals(typeBox.getValue())) {
                    String narrator = extraField1.getText().trim();
                    int durationMinutes = Integer.parseInt(extraField2.getText().trim());
                    book = new AudioBook(title, author, year, pages, genre, narrator, durationMinutes);
                } else {
                    String fieldOfScience = extraField1.getText().trim();
                    boolean peerReviewed = Boolean.parseBoolean(extraField2.getText().trim());
                    book = new ScientificBook(title, author, year, pages, genre, fieldOfScience, peerReviewed);
                }

                repository.insertBook(book, quantity);
                items.add(book.getClass().getSimpleName() + ": " + book.getTitle() + " | UUID: " + book.getUuid());

                clearFields(titleField, authorField, yearField, pagesField, quantityField, extraField1, extraField2);

                showInfo("Об'єкт успішно додано.");
            } catch (NumberFormatException e) {
                showError("Числові поля заповнені некоректно.");
            } catch (IllegalArgumentException e) {
                showError(e.getMessage());
            }
        });

        findButton.setOnAction(event -> {
            String uuidText = uuidField.getText().trim();

            UUID uuid;
            try {
                uuid = UUID.fromString(uuidText);
            } catch (IllegalArgumentException e) {
                showError("Невірний формат UUID.");
                return;
            }

            BookRepository.BookRow row = repository.findByUuid(uuid);

            if (row == null) {
                detailsArea.setText("Не знайдено");
                return;
            }

            detailsArea.setText(buildFullBookInfo(row));
        });

        VBox root = new VBox(10,
                new Label("Тип об'єкта"),
                typeBox,
                new Label("Жанр"),
                genreBox,
                new Label("Назва"),
                titleField,
                new Label("Автор"),
                authorField,
                new Label("Рік видання"),
                yearField,
                new Label("Кількість сторінок"),
                pagesField,
                new Label("Кількість"),
                quantityField,
                extraLabel1,
                extraField1,
                extraLabel2,
                extraField2,
                addButton,
                new Label("Додані об'єкти"),
                listView,
                new Label("Пошук за UUID"),
                uuidField,
                findButton,
                new Label("Повна інформація про знайдений об'єкт"),
                detailsArea
        );

        root.setPadding(new Insets(15));

        Scene scene = new Scene(root, 520, 980);
        stage.setTitle("Books GUI");
        stage.setScene(scene);
        stage.show();

        loadExistingBooks();
    }

    /**
     * Оновлює підписи та підказки додаткових полів.
     */
    private void updateExtraFields(String type, Label extraLabel1, TextField extraField1,
                                   Label extraLabel2, TextField extraField2) {
        if ("EBook".equals(type)) {
            extraLabel1.setText("Формат файлу");
            extraField1.setPromptText("Наприклад: pdf");
            extraLabel2.setText("Розмір файлу");
            extraField2.setPromptText("Наприклад: 15.5");
        } else if ("PrintedBook".equals(type)) {
            extraLabel1.setText("Тип палітурки");
            extraField1.setPromptText("Наприклад: тверда");
            extraLabel2.setText("Тираж");
            extraField2.setPromptText("Наприклад: 1000");
        } else if ("AudioBook".equals(type)) {
            extraLabel1.setText("Диктор");
            extraField1.setPromptText("Ім'я диктора");
            extraLabel2.setText("Тривалість у хвилинах");
            extraField2.setPromptText("Наприклад: 320");
        } else {
            extraLabel1.setText("Галузь науки");
            extraField1.setPromptText("Наприклад: Physics");
            extraLabel2.setText("Рецензована");
            extraField2.setPromptText("true / false");
        }
    }

    /**
     * Завантажує дані підключення до бази даних.
     */
    private Properties loadProperties(String path) {
        Properties properties = new Properties();

        try (Reader reader = new FileReader(path)) {
            properties.load(reader);
            return properties;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Завантажує вже існуючі книги з бази даних.
     */
    private void loadExistingBooks() {
        List<BookRepository.BookRow> rows = repository.getAllBooks();

        for (BookRepository.BookRow row : rows) {
            Book book = row.book();
            items.add(book.getClass().getSimpleName() + ": " + book.getTitle() + " | UUID: " + book.getUuid());
        }
    }

    /**
     * Формує повну інформацію про книгу.
     */
    private String buildFullBookInfo(BookRepository.BookRow row) {
        Book book = row.book();
        StringBuilder builder = new StringBuilder();

        builder.append("UUID: ").append(book.getUuid()).append("\n");
        builder.append("Type: ").append(book.getClass().getSimpleName()).append("\n");
        builder.append("Title: ").append(book.getTitle()).append("\n");
        builder.append("Author: ").append(book.getAuthor()).append("\n");
        builder.append("Year: ").append(book.getYear()).append("\n");
        builder.append("Pages: ").append(book.getPages()).append("\n");
        builder.append("Genre: ").append(book.getGenre()).append("\n");
        builder.append("Quantity: ").append(row.quantity()).append("\n");

        if (book instanceof EBook eBook) {
            builder.append("File format: ").append(eBook.getFileFormat()).append("\n");
            builder.append("File size: ").append(eBook.getFileSize()).append("\n");
        } else if (book instanceof PrintedBook printedBook) {
            builder.append("Cover type: ").append(printedBook.getCoverType()).append("\n");
            builder.append("Print run: ").append(printedBook.getPrintRun()).append("\n");
        } else if (book instanceof AudioBook audioBook) {
            builder.append("Narrator: ").append(audioBook.getNarrator()).append("\n");
            builder.append("Duration minutes: ").append(audioBook.getDurationMinutes()).append("\n");
        } else if (book instanceof ScientificBook scientificBook) {
            builder.append("Field of science: ").append(scientificBook.getFieldOfScience()).append("\n");
            builder.append("Peer reviewed: ").append(scientificBook.isPeerReviewed()).append("\n");
        }

        return builder.toString();
    }

    /**
     * Очищує поля форми.
     */
    private void clearFields(TextField titleField, TextField authorField, TextField yearField,
                             TextField pagesField, TextField quantityField,
                             TextField extraField1, TextField extraField2) {
        titleField.clear();
        authorField.clear();
        yearField.clear();
        pagesField.clear();
        quantityField.clear();
        extraField1.clear();
        extraField2.clear();
    }

    /**
     * Показує повідомлення про помилку.
     */
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Помилка");
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Показує інформаційне повідомлення.
     */
    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Інформація");
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Точка входу для запуску JavaFX.
     */
    public static void main(String[] args) {
        launch(args);
    }
}