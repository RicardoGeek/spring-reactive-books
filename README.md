# Spring WebFlux And Reactive Streams Example

Sample project that handles an in-memory library of books related to reactive programming in java.

## Endpoints

### [GET] /libro

Gets all the collection of books stored in memory

### [GET] /libro/{id}

Gets a specific book with the id in the url

### [POST] /libro

Saves a new book to the collection

## Highlights

_BookHandlerImpl_

This class holds all the handling methods for the reactive routes defined in _Server_

_BookRepositoryImpl_

The actual collection of books. The _guardarLibro_ method returns a Mono<Book> which can be streamed to get the save result.

_Server_

Defines an embedded tomcat that defines the app routes and consumes the handler methods. 
 
 _Client_
 A small class that consumes the reactive endpoints of the library.

# Resources

This is a repository to complement the information in this post (in spanish): 

[Programación Reactiva Con Spring 5]



[Programación Reactiva Con Spring 5]: https://ricardogeek.com/programacion-reactiva-con-spring-5
