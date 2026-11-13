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
import com.example.ktragiuaky.model.BorrowRecord;

import java.util.List;

public class BorrowAdapter extends RecyclerView.Adapter<BorrowAdapter.BorrowViewHolder> {
    private List<BorrowRecord> borrowList;
    private OnBorrowListener listener;

    public interface OnBorrowListener {
        void onReturn(BorrowRecord record);
    }

    public BorrowAdapter(List<BorrowRecord> borrowList, OnBorrowListener listener) {
        this.borrowList = borrowList;
        this.listener = listener;
    }

    public void updateList(List<BorrowRecord> newList) {
        this.borrowList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BorrowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_borrow, parent, false);
        return new BorrowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BorrowViewHolder holder, int position) {
        BorrowRecord record = borrowList.get(position);
        holder.tvBookTitle.setText(record.getBookTitle());
        holder.tvBookCode.setText("Mã sách: " + record.getBookCode());
        holder.tvBorrowerName.setText("Người mượn: " + record.getBorrowerName());
        holder.tvBorrowerPhone.setText("SĐT: " + record.getBorrowerPhone());
        holder.tvBorrowDates.setText("Mượn: " + record.getBorrowDate() + " - Hạn: " + record.getDueDate());
        holder.tvStatus.setText(record.getStatus());

        if ("Đang mượn".equals(record.getStatus())) {
            holder.tvStatus.setTextColor(Color.parseColor("#FF9800")); // Orange
            holder.btnReturn.setVisibility(View.VISIBLE);
        } else {
            holder.tvStatus.setTextColor(Color.parseColor("#4CAF50")); // Green
            holder.btnReturn.setVisibility(View.GONE);
        }

        holder.btnReturn.setOnClickListener(v -> {
            if (listener != null) listener.onReturn(record);
        });
    }

    @Override
    public int getItemCount() {
        return borrowList.size();
    }

    public static class BorrowViewHolder extends RecyclerView.ViewHolder {
        TextView tvBookTitle, tvBookCode, tvBorrowerName, tvBorrowerPhone, tvBorrowDates, tvStatus;
        Button btnReturn;

        public BorrowViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBookTitle = itemView.findViewById(R.id.tvBorrowBookTitle);
            tvBookCode = itemView.findViewById(R.id.tvBorrowBookCode);
            tvBorrowerName = itemView.findViewById(R.id.tvBorrowerName);
            tvBorrowerPhone = itemView.findViewById(R.id.tvBorrowerPhone);
            tvBorrowDates = itemView.findViewById(R.id.tvBorrowDates);
            tvStatus = itemView.findViewById(R.id.tvBorrowStatus);
            btnReturn = itemView.findViewById(R.id.btnReturnBook);
        }
    }
}
