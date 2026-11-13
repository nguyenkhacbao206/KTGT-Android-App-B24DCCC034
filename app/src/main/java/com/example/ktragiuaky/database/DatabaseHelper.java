package com.example.ktragiuaky.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ktragiuaky.model.Book;
import com.example.ktragiuaky.model.BorrowRecord;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "library.db";
    private static final int DATABASE_VERSION = 1;

    // Table Books
    public static final String TABLE_BOOKS = "books";
    public static final String COLUMN_BOOK_ID = "id";
    public static final String COLUMN_BOOK_CODE = "code";
    public static final String COLUMN_BOOK_TITLE = "title";
    public static final String COLUMN_BOOK_AUTHOR = "author";
    public static final String COLUMN_BOOK_GENRE = "genre";
    public static final String COLUMN_BOOK_STATUS = "status";

    // Table Borrows
    public static final String TABLE_BORROWS = "borrows";
    public static final String COLUMN_BORROW_ID = "id";
    public static final String COLUMN_BORROW_BOOK_ID = "book_id";
    public static final String COLUMN_BORROWER_NAME = "borrower_name";
    public static final String COLUMN_BORROWER_PHONE = "borrower_phone";
    public static final String COLUMN_BORROW_DATE = "borrow_date";
    public static final String COLUMN_BORROW_DUE_DATE = "due_date";
    public static final String COLUMN_BORROW_STATUS = "status";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_BOOKS_TABLE = "CREATE TABLE " + TABLE_BOOKS + "("
                + COLUMN_BOOK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_BOOK_CODE + " TEXT UNIQUE,"
                + COLUMN_BOOK_TITLE + " TEXT,"
                + COLUMN_BOOK_AUTHOR + " TEXT,"
                + COLUMN_BOOK_GENRE + " TEXT,"
                + COLUMN_BOOK_STATUS + " TEXT" + ")";
        db.execSQL(CREATE_BOOKS_TABLE);

        String CREATE_BORROWS_TABLE = "CREATE TABLE " + TABLE_BORROWS + "("
                + COLUMN_BORROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_BORROW_BOOK_ID + " INTEGER,"
                + COLUMN_BORROWER_NAME + " TEXT,"
                + COLUMN_BORROWER_PHONE + " TEXT,"
                + COLUMN_BORROW_DATE + " TEXT,"
                + COLUMN_BORROW_DUE_DATE + " TEXT,"
                + COLUMN_BORROW_STATUS + " TEXT,"
                + "FOREIGN KEY(" + COLUMN_BORROW_BOOK_ID + ") REFERENCES " + TABLE_BOOKS + "(" + COLUMN_BOOK_ID + ")" + ")";
        db.execSQL(CREATE_BORROWS_TABLE);

        // Insert some sample data
        insertSampleData(db);
    }

    private void insertSampleData(SQLiteDatabase db) {
        ContentValues cv1 = new ContentValues();
        cv1.put(COLUMN_BOOK_CODE, "S001");
        cv1.put(COLUMN_BOOK_TITLE, "Lập trình Android cơ bản");
        cv1.put(COLUMN_BOOK_AUTHOR, "Nguyễn Văn A");
        cv1.put(COLUMN_BOOK_GENRE, "Công nghệ thông tin");
        cv1.put(COLUMN_BOOK_STATUS, "Có sẵn");
        db.insert(TABLE_BOOKS, null, cv1);

        ContentValues cv2 = new ContentValues();
        cv2.put(COLUMN_BOOK_CODE, "S002");
        cv2.put(COLUMN_BOOK_TITLE, "Cấu trúc dữ liệu và giải thuật");
        cv2.put(COLUMN_BOOK_AUTHOR, "Trần Văn B");
        cv2.put(COLUMN_BOOK_GENRE, "Khoa học máy tính");
        cv2.put(COLUMN_BOOK_STATUS, "Có sẵn");
        db.insert(TABLE_BOOKS, null, cv2);

        ContentValues cv3 = new ContentValues();
        cv3.put(COLUMN_BOOK_CODE, "S003");
        cv3.put(COLUMN_BOOK_TITLE, "Toán cao cấp A1");
        cv3.put(COLUMN_BOOK_AUTHOR, "Lê Văn C");
        cv3.put(COLUMN_BOOK_GENRE, "Toán học");
        cv3.put(COLUMN_BOOK_STATUS, "Có sẵn");
        db.insert(TABLE_BOOKS, null, cv3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BORROWS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKS);
        onCreate(db);
    }

    // --- Book CRUD Operations ---
    public boolean addBook(String code, String title, String author, String genre) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BOOK_CODE, code);
        values.put(COLUMN_BOOK_TITLE, title);
        values.put(COLUMN_BOOK_AUTHOR, author);
        values.put(COLUMN_BOOK_GENRE, genre);
        values.put(COLUMN_BOOK_STATUS, "Có sẵn");

        long result = db.insert(TABLE_BOOKS, null, values);
        return result != -1;
    }

    public boolean updateBook(int id, String code, String title, String author, String genre) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BOOK_CODE, code);
        values.put(COLUMN_BOOK_TITLE, title);
        values.put(COLUMN_BOOK_AUTHOR, author);
        values.put(COLUMN_BOOK_GENRE, genre);

        int rows = db.update(TABLE_BOOKS, values, COLUMN_BOOK_ID + " = ?", new String[]{String.valueOf(id)});
        return rows > 0;
    }

    public boolean deleteBook(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete(TABLE_BOOKS, COLUMN_BOOK_ID + " = ?", new String[]{String.valueOf(id)});
        return rows > 0;
    }

    public List<Book> getAllBooks() {
        List<Book> bookList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_BOOKS, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BOOK_ID));
                String code = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOK_CODE));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOK_TITLE));
                String author = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOK_AUTHOR));
                String genre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOK_GENRE));
                String status = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOK_STATUS));
                bookList.add(new Book(id, code, title, author, genre, status));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return bookList;
    }

    public List<Book> searchBooks(String keyword) {
        List<Book> bookList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_BOOKS + " WHERE " + COLUMN_BOOK_TITLE + " LIKE ? OR " + COLUMN_BOOK_AUTHOR + " LIKE ?";
        Cursor cursor = db.rawQuery(query, new String[]{"%" + keyword + "%", "%" + keyword + "%"});
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BOOK_ID));
                String code = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOK_CODE));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOK_TITLE));
                String author = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOK_AUTHOR));
                String genre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOK_GENRE));
                String status = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOK_STATUS));
                bookList.add(new Book(id, code, title, author, genre, status));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return bookList;
    }

    public List<Book> getAvailableBooks() {
        List<Book> bookList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_BOOKS + " WHERE " + COLUMN_BOOK_STATUS + " = 'Có sẵn'", null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BOOK_ID));
                String code = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOK_CODE));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOK_TITLE));
                String author = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOK_AUTHOR));
                String genre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOK_GENRE));
                String status = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOK_STATUS));
                bookList.add(new Book(id, code, title, author, genre, status));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return bookList;
    }

    // --- Borrow Operations ---
    public boolean borrowBook(int bookId, String borrowerName, String borrowerPhone, String borrowDate, String dueDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            // Insert borrow record
            ContentValues values = new ContentValues();
            values.put(COLUMN_BORROW_BOOK_ID, bookId);
            values.put(COLUMN_BORROWER_NAME, borrowerName);
            values.put(COLUMN_BORROWER_PHONE, borrowerPhone);
            values.put(COLUMN_BORROW_DATE, borrowDate);
            values.put(COLUMN_BORROW_DUE_DATE, dueDate);
            values.put(COLUMN_BORROW_STATUS, "Đang mượn");
            long borrowResult = db.insert(TABLE_BORROWS, null, values);

            if (borrowResult == -1) {
                return false;
            }

            // Update book status to "Đang được mượn"
            ContentValues bookValues = new ContentValues();
            bookValues.put(COLUMN_BOOK_STATUS, "Đang được mượn");
            int bookResult = db.update(TABLE_BOOKS, bookValues, COLUMN_BOOK_ID + " = ?", new String[]{String.valueOf(bookId)});

            if (bookResult <= 0) {
                return false;
            }

            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            db.endTransaction();
        }
    }

    public boolean returnBook(int borrowId, int bookId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            // Update borrow record status to "Đã trả"
            ContentValues borrowValues = new ContentValues();
            borrowValues.put(COLUMN_BORROW_STATUS, "Đã trả");
            int borrowResult = db.update(TABLE_BORROWS, borrowValues, COLUMN_BORROW_ID + " = ?", new String[]{String.valueOf(borrowId)});

            if (borrowResult <= 0) {
                return false;
            }

            // Update book status to "Có sẵn"
            ContentValues bookValues = new ContentValues();
            bookValues.put(COLUMN_BOOK_STATUS, "Có sẵn");
            int bookResult = db.update(TABLE_BOOKS, bookValues, COLUMN_BOOK_ID + " = ?", new String[]{String.valueOf(bookId)});

            if (bookResult <= 0) {
                return false;
            }

            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            db.endTransaction();
        }
    }

    public List<BorrowRecord> getAllBorrowRecords() {
        List<BorrowRecord> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT b.*, bk." + COLUMN_BOOK_CODE + ", bk." + COLUMN_BOOK_TITLE + " FROM " + TABLE_BORROWS + " b INNER JOIN " + TABLE_BOOKS + " bk ON b." + COLUMN_BORROW_BOOK_ID + " = bk." + COLUMN_BOOK_ID;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BORROW_ID));
                int bookId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BORROW_BOOK_ID));
                String borrowerName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BORROWER_NAME));
                String borrowerPhone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BORROWER_PHONE));
                String borrowDate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BORROW_DATE));
                String dueDate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BORROW_DUE_DATE));
                String status = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BORROW_STATUS));
                String bookCode = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOK_CODE));
                String bookTitle = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOK_TITLE));

                list.add(new BorrowRecord(id, bookId, bookCode, bookTitle, borrowerName, borrowerPhone, borrowDate, dueDate, status));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
}
