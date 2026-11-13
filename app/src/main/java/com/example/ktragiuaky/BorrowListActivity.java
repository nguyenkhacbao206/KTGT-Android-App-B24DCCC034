package com.example.ktragiuaky;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ktragiuaky.adapter.BorrowAdapter;
import com.example.ktragiuaky.database.DatabaseHelper;
import com.example.ktragiuaky.model.BorrowRecord;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class BorrowListActivity extends AppCompatActivity implements BorrowAdapter.OnBorrowListener {
    private DatabaseHelper databaseHelper;
    private RecyclerView recyclerView;
    private BorrowAdapter borrowAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow_list);

        databaseHelper = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.recyclerViewBorrows);
        FloatingActionButton fabAddBorrow = findViewById(R.id.fabAddBorrow);
        Button btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> finish());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadBorrows();

        fabAddBorrow.setOnClickListener(v -> {
            Intent intent = new Intent(BorrowListActivity.this, AddBorrowActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadBorrows();
    }

    private void loadBorrows() {
        List<BorrowRecord> list = databaseHelper.getAllBorrowRecords();
        if (borrowAdapter == null) {
            borrowAdapter = new BorrowAdapter(list, this);
            recyclerView.setAdapter(borrowAdapter);
        } else {
            borrowAdapter.updateList(list);
        }
    }

    @Override
    public void onReturn(BorrowRecord record) {
        new AlertDialog.Builder(this)
                .setTitle("Trả sách")
                .setMessage("Xác nhận trả sách \"" + record.getBookTitle() + "\"?")
                .setPositiveButton("Xác nhận", (dialog, which) -> {
                    boolean success = databaseHelper.returnBook(record.getId(), record.getBookId());
                    if (success) {
                        Toast.makeText(this, "Trả sách thành công", Toast.LENGTH_SHORT).show();
                        loadBorrows();
                    } else {
                        Toast.makeText(this, "Trả sách thất bại", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
}
