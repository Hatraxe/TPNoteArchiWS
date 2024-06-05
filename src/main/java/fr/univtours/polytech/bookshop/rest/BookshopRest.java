package fr.univtours.polytech.bookshop.rest;

import java.util.List;
import java.util.stream.Collectors;

import fr.univtours.polytech.bookshop.business.BookBusiness;
import fr.univtours.polytech.bookshop.model.BookBean;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("v1")
public class BookshopRest {

    @Inject
    private BookBusiness bookBusiness;

    // Endpoint pour récupérer une liste de livres filtrée par auteur et titre
    @Path("books")
    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public List<BookBean> getBooks(@QueryParam("author") String author, @QueryParam("title") String title) {
        List<BookBean> books = this.bookBusiness.getBooks();
        if (author != null && !author.isEmpty()) {
            books = books.stream()
                .filter(book -> book.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .collect(Collectors.toList());
        }
        if (title != null && !title.isEmpty()) {
            books = books.stream()
                .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
        }
        return books;
    }

    // Endpoint pour récupérer un livre par son ID
    @GET
    @Path("books/{id}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response getBook(@PathParam("id") Integer id) {
        BookBean book = this.bookBusiness.getBook(id);
        if (book == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok(book).build();
    }

    // Endpoint pour supprimer un livre par son ID
    @DELETE
    @Path("books/{id}")
    public Response deleteBook(@PathParam("id") Integer id,
                               @HeaderParam(HttpHeaders.AUTHORIZATION) String auth) {
        if (!"42".equals(auth)) {
            return Response.status(Status.UNAUTHORIZED).build();
        } else {
            BookBean book = this.bookBusiness.getBook(id);
            if (book == null) {
                return Response.status(Status.NOT_FOUND).build();
            } else {
                this.bookBusiness.removeBook(id);
                return Response.status(Status.NO_CONTENT).build();
            }
        }
    }

    // Endpoint pour créer un nouveau livre
    @POST
    @Path("books")
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response createBook(BookBean bookBean, @HeaderParam(HttpHeaders.AUTHORIZATION) String auth) {
        if (!"42".equals(auth)) {
            return Response.status(Status.UNAUTHORIZED).build();
        }
        this.bookBusiness.createBook(bookBean);
        return Response.status(Status.CREATED).entity(bookBean).build();
    }

    // Endpoint pour mettre à jour complètement un livre existant
    @PUT
    @Path("books/{id}")
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response fullUpdateBook(@PathParam("id") Integer id, BookBean bookBean, @HeaderParam(HttpHeaders.AUTHORIZATION) String auth) {
        if (!"42".equals(auth)) {
            return Response.status(Status.UNAUTHORIZED).build();
        }
        BookBean oldBookBean = this.bookBusiness.getBook(id);
        if (oldBookBean == null) {
            return Response.status(Status.NOT_FOUND).build();
        }

        oldBookBean.setTitle(bookBean.getTitle());
        oldBookBean.setAuthor(bookBean.getAuthor());
        oldBookBean.setPrice(bookBean.getPrice());
        oldBookBean.setCurrency(bookBean.getCurrency());

        this.bookBusiness.updateBook(oldBookBean);
        return Response.status(Status.ACCEPTED).build();
    }

    // Endpoint pour mettre à jour partiellement un livre existant
    @PATCH
    @Path("books/{id}")
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response partialUpdateBook(@PathParam("id") Integer id, BookBean bookBean, @HeaderParam(HttpHeaders.AUTHORIZATION) String auth) {
        if (!"42".equals(auth)) {
            return Response.status(Status.UNAUTHORIZED).build();
        }
        BookBean oldBookBean = this.bookBusiness.getBook(id);
        if (oldBookBean == null) {
            return Response.status(Status.NOT_FOUND).build();
        }

        if (bookBean.getTitle() != null && !bookBean.getTitle().isEmpty()) {
            oldBookBean.setTitle(bookBean.getTitle());
        }
        if (bookBean.getAuthor() != null && !bookBean.getAuthor().isEmpty()) {
            oldBookBean.setAuthor(bookBean.getAuthor());
        }
        if (bookBean.getPrice() != null) {
            oldBookBean.setPrice(bookBean.getPrice());
        }
        if (bookBean.getCurrency() != null && !bookBean.getCurrency().isEmpty()) {
            oldBookBean.setCurrency(bookBean.getCurrency());
        }

        this.bookBusiness.updateBook(oldBookBean);
        return Response.status(Status.ACCEPTED).build();
    }
}
