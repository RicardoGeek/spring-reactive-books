package com.ricardogeek;

import com.ricardogeek.handler.BookHandler;
import com.ricardogeek.handler.BookHandlerImpl;
import com.ricardogeek.repository.BookRepository;
import com.ricardogeek.repository.BookrepositoryImpl;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ServletHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;
import static org.springframework.web.reactive.function.server.RequestPredicates.method;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RouterFunctions.toHttpHandler;

public class Server {

    public static void main(final String[] args) throws Exception {
        final Server server = new Server();
        server.startTomcatServer("localhost", 8080);
        System.out.println("Press ENTER to exit.");
        System.in.read();
    }

    private RouterFunction<ServerResponse> routingFunction() {
        final BookRepository repositorio = new BookrepositoryImpl();
        final BookHandler handler = new BookHandlerImpl(repositorio);

        return nest(path("/libro"),
                nest(accept(APPLICATION_JSON),
                        route(GET("/{id}"), handler::getBookFromRepository)
                                .andRoute(method(HttpMethod.GET), handler::getAllBooksFromRepository)
                ).andRoute(POST("/")
                        .and(contentType(APPLICATION_JSON)), handler::saveBookToRepository));
    }


    private void startTomcatServer(final String host, final int port) throws LifecycleException {
        final RouterFunction<?> route = routingFunction();
        final HttpHandler httpHandler = toHttpHandler(route);
        final Tomcat tomcatServer = new Tomcat();
        final Context rootContext = tomcatServer.addContext("", System.getProperty("java.io.tmpdir"));
        final ServletHttpHandlerAdapter servlet = new ServletHttpHandlerAdapter(httpHandler);

        tomcatServer.setHostname(host);
        tomcatServer.setPort(port);

        Tomcat.addServlet(rootContext, "httpHandlerServlet", servlet);
        rootContext.addServletMapping("/", "httpHandlerServlet");
        tomcatServer.start();
    }
}
