package com.ricardogeek.repository;

import com.ricardogeek.models.Book;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookRepository {

    Mono<Book> getLibro(int id);

    Flux<Book> getLibros();

    Mono<Void> guardarLibro(Mono<Book> libro);

}
