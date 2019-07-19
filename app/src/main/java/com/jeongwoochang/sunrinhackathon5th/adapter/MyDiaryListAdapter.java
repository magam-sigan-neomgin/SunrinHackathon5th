package com.jeongwoochang.sunrinhackathon5th.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.jeongwoochang.sunrinhackathon5th.R;
import com.jeongwoochang.sunrinhackathon5th.data.Board;

import java.util.ArrayList;

public class MyDiaryListAdapter extends RecyclerView.Adapter<MyDiaryListAdapter.MyDiaryHolder> {
    private ArrayList<Board> items;
    private OnItemClickListener onItemClickListener;

    @NonNull
    @Override
    public MyDiaryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_diary_my, parent, false);
        return new MyDiaryHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyDiaryHolder holder, int position) {
        final Board item = items.get(position);
        holder.title.setText(item.getTitle());
        holder.date.setText(item.getDate());
        holder.content.setText(item.getContent());
    }

    @Override
    public int getItemCount() {
        if (items == null) return 0;
        return items.size();
    }

    public class MyDiaryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;
        TextView date;
        TextView content;

        MyDiaryHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = itemView.findViewById(R.id.myTitle);
            date = itemView.findViewById(R.id.myDate);
            content = itemView.findViewById(R.id.myContent);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null)
                onItemClickListener.onItemClick(v, getAdapterPosition(), items.get(getAdapterPosition()));
        }
    }

    public ArrayList<Board> getItems() {
        return items;
    }

    public void setItems(ArrayList<Board> items) {
        this.items = items;
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position, Board board);
    }
}
