import org.junit.jupiter.api.Test;
import org.sumdu.Book;
import org.sumdu.Genre;

import static org.junit.jupiter.api.Assertions.*;

class BookTests {

    @Test
    void shouldThrowExceptionWhenInvalidYearInSetter() {
        Book book = new Book("title", "author", 2000, 1000, Genre.DETECTIVE);

        assertThrows(IllegalArgumentException.class, () -> {
            book.setYear(3000);
        });
    }

    @Test
    void shouldThrowExceptionWhenInvalidConstructorData() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Book("", "", 2000, 100000, Genre.DETECTIVE);
        });
    }
}