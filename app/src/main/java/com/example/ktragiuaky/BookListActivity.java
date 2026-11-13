package com.example.ktragiuaky;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ktragiuaky.adapter.BookAdapter;
import com.example.ktragiuaky.database.DatabaseHelper;
import com.example.ktragiuaky.model.Book;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class BookListActivity extends AppCompatActivity implements BookAdapter.OnBookListener {
    private DatabaseHelper databaseHelper;
    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private EditText etSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        databaseHelper = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.recyclerViewBooks);
        etSearch = findViewById(R.id.etSearch);
        Button btnSearch = findViewById(R.id.btnSearch);
        Button btnBack = findViewById(R.id.btnBack);
        FloatingActionButton fabAddBook = findViewById(R.id.fabAddBook);

        btnBack.setOnClickListener(v -> finish());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadBooks();

        fabAddBook.setOnClickListener(v -> {
            Intent intent = new Intent(BookListActivity.this, AddEditBookActivity.class);
            startActivity(intent);
        });

        btnSearch.setOnClickListener(v -> {
            String keyword = etSearch.getText().toString().trim();
            List<Book> searchResult = databaseHelper.searchBooks(keyword);
            bookAdapter.updateList(searchResult);
        });

        if (getIntent().getBooleanExtra("FOCUS_SEARCH", false)) {
            etSearch.requestFocus();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadBooks();
    }

    private void loadBooks() {
        List<Book> bookList = databaseHelper.getAllBooks();
        if (bookAdapter == null) {
            bookAdapter = new BookAdapter(bookList, this);
            recyclerView.setAdapter(bookAdapter);
        } else {
            bookAdapter.updateList(bookList);
        }
    }

    @Override
    public void onEdit(Book book) {
        Intent intent = new Intent(BookListActivity.this, AddEditBookActivity.class);
        intent.putExtra("BOOK", book);
        startActivity(intent);
    }

    @Override
    public void onDelete(Book book) {
        new AlertDialog.Builder(this)
                .setTitle("Xóa sách")
                .setMessage("Bạn có chắc chắn muốn xóa sách \"" + book.getTitle() + "\" không?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    boolean success = databaseHelper.deleteBook(book.getId());
                    if (success) {
                        Toast.makeText(this, "Xóa sách thành công", Toast.LENGTH_SHORT).show();
                        loadBooks();
                    } else {
                        Toast.makeText(this, "Xóa sách thất bại", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
}
