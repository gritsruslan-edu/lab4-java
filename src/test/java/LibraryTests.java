import org.junit.jupiter.api.Test;
import org.sumdu.Book;
import org.sumdu.EBook;
import org.sumdu.Genre;
import org.sumdu.Library;
import org.sumdu.exceptions.BookNotFoundException;
import org.sumdu.exceptions.InvalidFieldValueException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

class LibraryTest {

    /**
     * Перевіряє викидання винятку при видаленні неіснуючої книги.
     */
    @Test
    void shouldThrowBookNotFoundExceptionWhenDeletingNonExistingBook() {
        Library library = new Library();
        Book book = new EBook("Test", "Author", 2020, 100, Genre.FANTASY, "pdf", 12.5);

        assertThrows(BookNotFoundException.class, () -> {
            library.delete(book);
        });
    }

    /**
     * Перевіряє викидання винятку при пошуку книги з null UUID.
     */
    @Test
    void shouldThrowInvalidFieldValueExceptionWhenFindingBookByNullUuid() {
        Library library = new Library();

        assertThrows(InvalidFieldValueException.class, () -> {
            library.findByUuid((UUID) null);
        });
    }
}