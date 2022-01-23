package com.axonactive.bookstore.rest;

import com.axonactive.bookstore.model.Book;
import com.axonactive.bookstore.repository.BookRepository;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

import static javax.ws.rs.core.MediaType.*;


@Path("/books")
public class BookEndpoint {
    @Inject
    private BookRepository bookRepository;

    public Book getBook(Long id) {
        return bookRepository.find(id);
    }

    @GET
    @Produces(APPLICATION_JSON)
    public Response getBooks() {
        List<Book> bookList = bookRepository.findAll();
        if(bookList.size()==0)
            return Response.noContent().build();
        return Response.ok(bookList).build();
    }

    @GET
    @Path("/count")
    public Response countBook() {
        Long nbOfBooks = bookRepository.countAll();
        if (nbOfBooks==0)
            return Response.noContent().build();
        return Response.ok(nbOfBooks).build();
    }

    @POST
    @Consumes(APPLICATION_JSON)
    public Response createBook(Book book, @Context UriInfo uriInfo) {
        book = bookRepository.create(book);
        URI uri = uriInfo.getBaseUriBuilder().path(book.getId().toString()).build();
        return Response.created(uri).build();
    }

    public void deleteBook(Long id) {
        bookRepository.delete(id);
    }
}
