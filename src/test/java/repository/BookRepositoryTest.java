package repository;

import com.axonactive.bookstore.model.Book;
import com.axonactive.bookstore.model.Language;
import com.axonactive.bookstore.repository.BookRepository;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import java.util.Date;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class BookRepositoryTest {
    @Inject
    private BookRepository bookRepository;


    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(BookRepository.class)
                .addClass(Book.class)
                .addClass(Language.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsManifestResource("META-INF/test-persistence.xml","persistence.xml");
    }

    @Test
    public void create() {
        assertEquals(Long.valueOf(0),bookRepository.countAll());
        assertEquals(0,bookRepository.findAll().size());

        Book book = new Book(null, "title", "description", 12F, "isbn", new Date(), 123, "http:asd", Language.ENGLISH);
        bookRepository.create(book);
        Long bookId = book.getId();

        assertNotNull(bookId);

        Book bookFound = bookRepository.find(bookId);

        assertEquals("title", bookFound.getTitle());
    }
}
