package com.example.ktragiuaky.model;

import java.io.Serializable;

public class BorrowRecord implements Serializable {
    private int id;
    private int bookId;
    private String bookCode;
    private String bookTitle;
    private String borrowerName;
    private String borrowerPhone;
    private String borrowDate;
    private String dueDate;
    private String status; // "Đang mượn" or "Đã trả"

    public BorrowRecord(int id, int bookId, String bookCode, String bookTitle, String borrowerName, String borrowerPhone, String borrowDate, String dueDate, String status) {
        this.id = id;
        this.bookId = bookId;
        this.bookCode = bookCode;
        this.bookTitle = bookTitle;
        this.borrowerName = borrowerName;
        this.borrowerPhone = borrowerPhone;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public int getBookId() {
        return bookId;
    }

    public String getBookCode() {
        return bookCode;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public String getBorrowerPhone() {
        return borrowerPhone;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
