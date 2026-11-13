package com.example.ktragiuaky;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnBookManagement = findViewById(R.id.btnBookManagement);
        Button btnSearchBook = findViewById(R.id.btnSearchBook);
        Button btnBorrowManagement = findViewById(R.id.btnBorrowManagement);

        btnBookManagement.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, BookListActivity.class);
            startActivity(intent);
        });

        btnSearchBook.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, BookListActivity.class);
            intent.putExtra("FOCUS_SEARCH", true);
            startActivity(intent);
        });

        btnBorrowManagement.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, BorrowListActivity.class);
            startActivity(intent);
        });
    }
}
