package com.example.ktragiuaky.model;

import java.io.Serializable;

public class Book implements Serializable {
    private int id;
    private String code;
    private String title;
    private String author;
    private String genre;
    private String status; // "Có sẵn" or "Đang được mượn"

    public Book(int id, String code, String title, String author, String genre, String status) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
