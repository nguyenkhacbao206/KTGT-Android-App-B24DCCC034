package com.example.ktragiuaky;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ktragiuaky.database.DatabaseHelper;
import com.example.ktragiuaky.model.Book;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class AddEditBookActivity extends AppCompatActivity {
    private TextInputEditText etBookCode, etBookTitle, etBookAuthor, etBookGenre;
    private TextView tvTitle;
    private DatabaseHelper databaseHelper;
    private Book editBook = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_book);

        databaseHelper = new DatabaseHelper(this);
        etBookCode = findViewById(R.id.etBookCode);
        etBookTitle = findViewById(R.id.etBookTitle);
        etBookAuthor = findViewById(R.id.etBookAuthor);
        etBookGenre = findViewById(R.id.etBookGenre);
        Button btnSaveBook = findViewById(R.id.btnSaveBook);
        Button btnBack = findViewById(R.id.btnBack);
        tvTitle = findViewById(R.id.tvTitle);

        btnBack.setOnClickListener(v -> finish());

        if (getIntent().hasExtra("BOOK")) {
            editBook = (Book) getIntent().getSerializableExtra("BOOK");
            if (editBook != null) {
                tvTitle.setText("Sửa thông tin sách");
                etBookCode.setText(editBook.getCode());
                etBookTitle.setText(editBook.getTitle());
                etBookAuthor.setText(editBook.getAuthor());
                etBookGenre.setText(editBook.getGenre());
                etBookCode.setEnabled(false);
            }
        }

        btnSaveBook.setOnClickListener(v -> saveBook());
    }

    private void saveBook() {
        String code = etBookCode.getText().toString().trim();
        String title = etBookTitle.getText().toString().trim();
        String author = etBookAuthor.getText().toString().trim();
        String genre = etBookGenre.getText().toString().trim();

        if (TextUtils.isEmpty(code) || TextUtils.isEmpty(title) || TextUtils.isEmpty(author) || TextUtils.isEmpty(genre)) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        if (editBook == null) {
            List<Book> allBooks = databaseHelper.getAllBooks();
            for (Book b : allBooks) {
                if (b.getCode().equalsIgnoreCase(code)) {
                    Toast.makeText(this, "Mã sách đã tồn tại, vui lòng nhập mã khác", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            boolean success = databaseHelper.addBook(code, title, author, genre);
            if (success) {
                Toast.makeText(this, "Thêm sách thành công", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Thêm sách thất bại", Toast.LENGTH_SHORT).show();
            }
        } else {
            boolean success = databaseHelper.updateBook(editBook.getId(), code, title, author, genre);
            if (success) {
                Toast.makeText(this, "Cập nhật sách thành công", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Cập nhật sách thất bại", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
