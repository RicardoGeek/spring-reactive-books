package com.ricardogeek.handler;

import com.ricardogeek.models.Book;
import com.ricardogeek.repository.BookRepository;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

public class BookHandlerImpl implements BookHandler {

    private final BookRepository repositorio;

    public BookHandlerImpl(BookRepository repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public Mono<ServerResponse> getBookFromRepository(final ServerRequest request) {
        final int bookId = Integer.parseInt(request.pathVariable("id"));
        final Mono<ServerResponse> notFound = ServerResponse.notFound().build();
        final Mono<Book> bookMono = this.repositorio.getLibro(bookId);
        return bookMono
                .flatMap(libro -> ServerResponse.ok().contentType(APPLICATION_JSON).body(fromObject(libro)))
                .switchIfEmpty(notFound);
    }

    @Override
    public Mono<ServerResponse> saveBookToRepository(final ServerRequest request) {
        final Mono<Book> libro = request.bodyToMono(Book.class);
        return ServerResponse.ok().build(this.repositorio.guardarLibro(libro));
    }

    @Override
    public Mono<ServerResponse> getAllBooksFromRepository(final ServerRequest request) {
        final Flux<Book> libros = this.repositorio.getLibros();
        return ServerResponse.ok().contentType(APPLICATION_JSON).body(libros, Book.class);
    }
}
