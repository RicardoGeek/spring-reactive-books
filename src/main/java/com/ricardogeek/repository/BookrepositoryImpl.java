package com.ricardogeek.repository;

import com.ricardogeek.models.Book;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BookrepositoryImpl implements BookRepository {

    private final Map<Integer, Book> catalogo = new ConcurrentHashMap<>();

    public BookrepositoryImpl() {
        catalogo.put(1, new Book(1,
                "Learning Reactive Programming With Java 8",
                "Nickolay Tsvetinov ",
                "01.jpg",
                new BigDecimal("22.39")));

        catalogo.put(2, new Book(2,
                "Reactive Programming with RxJava: Creating Asynchronous, Event-Based Applications",
                "Tomasz Nurkiewicz &  Ben Christensen ",
                "02.jpg",
                new BigDecimal("36.17")));

    }

    @Override
    public Mono<Book> getLibro(final int id) {
        return Mono.justOrEmpty(this.catalogo.get(id));
    }

    @Override
    public Flux<Book> getLibros() {
        return Flux.fromIterable(this.catalogo.values());
    }

    @Override
    public Mono<Void> guardarLibro(final Mono<Book> libro) {
        final Mono<Book> monoLibro = libro.doOnNext(product -> {
            final int id = catalogo.size() + 1;
            catalogo.put(id, product);
        });
        return monoLibro.thenEmpty(Mono.empty());
    }
}
