package com.example.ktragiuaky;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ktragiuaky.database.DatabaseHelper;
import com.example.ktragiuaky.model.Book;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddBorrowActivity extends AppCompatActivity {
    private Spinner spinnerBooks;
    private TextInputEditText etBorrowerName, etBorrowerPhone, etBorrowDate, etDueDate;
    private DatabaseHelper databaseHelper;
    private List<Book> availableBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_borrow);

        databaseHelper = new DatabaseHelper(this);
        spinnerBooks = findViewById(R.id.spinnerBooks);
        etBorrowerName = findViewById(R.id.etBorrowerName);
        etBorrowerPhone = findViewById(R.id.etBorrowerPhone);
        etBorrowDate = findViewById(R.id.etBorrowDate);
        etDueDate = findViewById(R.id.etDueDate);
        Button btnSaveBorrow = findViewById(R.id.btnSaveBorrow);
        Button btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> finish());

        // Pre-fill current date
        String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        etBorrowDate.setText(currentDate);

        loadAvailableBooks();

        btnSaveBorrow.setOnClickListener(v -> saveBorrowRecord());
    }

    private void loadAvailableBooks() {
        availableBooks = databaseHelper.getAvailableBooks();
        List<String> bookTitles = new ArrayList<>();
        for (Book b : availableBooks) {
            bookTitles.add(b.getCode() + " - " + b.getTitle());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, bookTitles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBooks.setAdapter(adapter);
    }

    private void saveBorrowRecord() {
        if (availableBooks == null || availableBooks.isEmpty()) {
            Toast.makeText(this, "Không có sách nào đang có sẵn để mượn!", Toast.LENGTH_SHORT).show();
            return;
        }

        int selectedPosition = spinnerBooks.getSelectedItemPosition();
        if (selectedPosition < 0 || selectedPosition >= availableBooks.size()) {
            Toast.makeText(this, "Vui lòng chọn sách", Toast.LENGTH_SHORT).show();
            return;
        }

        Book selectedBook = availableBooks.get(selectedPosition);
        String borrowerName = etBorrowerName.getText().toString().trim();
        String borrowerPhone = etBorrowerPhone.getText().toString().trim();
        String borrowDate = etBorrowDate.getText().toString().trim();
        String dueDate = etDueDate.getText().toString().trim();

        if (TextUtils.isEmpty(borrowerName) || TextUtils.isEmpty(borrowerPhone) || TextUtils.isEmpty(borrowDate) || TextUtils.isEmpty(dueDate)) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin người mượn và ngày mượn/trả", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean success = databaseHelper.borrowBook(selectedBook.getId(), borrowerName, borrowerPhone, borrowDate, dueDate);
        if (success) {
            Toast.makeText(this, "Mượn sách thành công", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Mượn sách thất bại", Toast.LENGTH_SHORT).show();
        }
    }
}
