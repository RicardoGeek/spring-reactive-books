package com.ricardogeek.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class Book {
    private int bookId;
    private String nombre;
    private String autor;
    private String imagen;
    private BigDecimal precio;

    @JsonCreator
    public Book(@JsonProperty("id") final int bookId, @JsonProperty("nombre") final String nombre, @JsonProperty("autor") final String autor, @JsonProperty("imagen") final String imagen, @JsonProperty("precio") final BigDecimal precio) {
        this.bookId = bookId;
        this.nombre = nombre;
        this.autor = autor;
        this.imagen = imagen;
        this.precio = precio;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

}
