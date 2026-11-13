package com.example.ktragiuaky.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ktragiuaky.R;
import com.example.ktragiuaky.model.Book;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private List<Book> bookList;
    private OnBookListener listener;

    public interface OnBookListener {
        void onEdit(Book book);
        void onDelete(Book book);
    }

    public BookAdapter(List<Book> bookList, OnBookListener listener) {
        this.bookList = bookList;
        this.listener = listener;
    }

    public void updateList(List<Book> newList) {
        this.bookList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.tvTitle.setText(book.getTitle());
        holder.tvCode.setText("Mã sách: " + book.getCode());
        holder.tvAuthor.setText("Tác giả: " + book.getAuthor());
        holder.tvGenre.setText("Thể loại: " + book.getGenre());
        holder.tvStatus.setText(book.getStatus());

        if ("Có sẵn".equals(book.getStatus())) {
            holder.tvStatus.setBackgroundColor(Color.parseColor("#4CAF50")); // Green
        } else {
            holder.tvStatus.setBackgroundColor(Color.parseColor("#FF9800")); // Orange
        }

        holder.btnEdit.setOnClickListener(v -> {
            if (listener != null) listener.onEdit(book);
        });

        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) listener.onDelete(book);
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvCode, tvAuthor, tvGenre, tvStatus;
        Button btnEdit, btnDelete;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvBookTitle);
            tvCode = itemView.findViewById(R.id.tvBookCode);
            tvAuthor = itemView.findViewById(R.id.tvBookAuthor);
            tvGenre = itemView.findViewById(R.id.tvBookGenre);
            tvStatus = itemView.findViewById(R.id.tvBookStatus);
            btnEdit = itemView.findViewById(R.id.btnEditBook);
            btnDelete = itemView.findViewById(R.id.btnDeleteBook);
        }
    }
}
