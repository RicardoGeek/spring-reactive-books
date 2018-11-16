package com.ricardogeek.handler;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


public interface BookHandler {
    public Mono<ServerResponse> getBookFromRepository(ServerRequest request);

    public Mono<ServerResponse> saveBookToRepository(ServerRequest request);

    public Mono<ServerResponse> getAllBooksFromRepository(ServerRequest request);
}
