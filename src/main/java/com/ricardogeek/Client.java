package com.ricardogeek;

import com.ricardogeek.models.Book;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.ExchangeFunctions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

public class Client {
    private static final String HOST = "localhost";
    private static final int PORT = 8080;
    private final  ExchangeFunction exchange = ExchangeFunctions.create(new ReactorClientHttpConnector());

    public static void main(final String[] args) throws Exception {
        final Client client = new Client();
        client.createProduct();
        client.getAllProduct();
    }

    private void createProduct() {
        final URI uri = URI.create(String.format("http://%s:%d/libro/", HOST, PORT));
        final Book libro = new Book(319, "Learning RxJava", "Thomas Nield ", "03.jpg", new BigDecimal("35.95"));
        final ClientRequest request = ClientRequest.method(HttpMethod.POST, uri)
                .header("Content-Type", "application/json")
                .body(BodyInserters.fromObject(libro)).build();
        final Mono<ClientResponse> response = exchange.exchange(request);
        System.out.println(response.block().statusCode());
    }

    private void getAllProduct() {
        final URI uri = URI.create(String.format("http://%s:%d/libro", HOST, PORT));
        final ClientRequest request = ClientRequest.method(HttpMethod.GET, uri).build();
        final Flux<Book> productList = exchange.exchange(request)
                .flatMapMany(response -> response.bodyToFlux(Book.class));
        final Mono<List<Book>> productListMono = productList.collectList();
        System.out.println(productListMono.block());
    }
}
