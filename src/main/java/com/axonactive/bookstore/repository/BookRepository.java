package com.axonactive.bookstore.repository;

import com.axonactive.bookstore.model.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;

import static javax.transaction.Transactional.TxType.REQUIRED;
import static javax.transaction.Transactional.TxType.SUPPORTS;


@Transactional(SUPPORTS)
public class BookRepository {
    @PersistenceContext(unitName = "bookStorePU")
    private EntityManager em;

    public Book find(@NotNull Long id){
        return em.find(Book.class, id);
    }

    public List<Book> findAll(){
        TypedQuery<Book> query = em.createQuery(
                "SELECT b FROM Book b ORDER BY b.title DESC",Book.class);
        return query.getResultList();
    }

    public Long countAll(){
        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(b) FROM Book b",Long.class
        );
        return query.getSingleResult();
    }

    @Transactional(REQUIRED)
    public Book create(Book book){
        em.persist(book);
        return book;
    }

    @Transactional(REQUIRED)
    public void delete(Long id){
        em.remove(em.getReference(Book.class, id));
    }
}
