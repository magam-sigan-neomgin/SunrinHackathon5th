package com.jeongwoochang.sunrinhackathon5th.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.jeongwoochang.sunrinhackathon5th.R;
import com.jeongwoochang.sunrinhackathon5th.data.Diary;

import java.util.ArrayList;

public class YourDiaryListAdapter extends RecyclerView.Adapter<YourDiaryListAdapter.YourDiaryHolder> {

    private ArrayList<Diary> items;
    private OnItemClickListener onItemClickListener;
    private OnLikeButtonClickListener onLikeButtonClickListener;
    private OnCommentButtonClickListener onCommentButtonClickListener;

    @NonNull
    @Override
    public YourDiaryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_diary_your, parent, false);
        return new YourDiaryHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull YourDiaryHolder holder, int position) {
        final Diary item = items.get(position);
        holder.title.setText(item.getTitle());
        holder.date.setText(item.getDate());
        holder.content.setText(item.getContent());
        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onLikeButtonClickListener != null)
                    onLikeButtonClickListener.onLickClick(item);
            }
        });
        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onCommentButtonClickListener != null)
                    onCommentButtonClickListener.onCommentClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (items == null) return 0;
        return items.size();
    }

    public class YourDiaryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;
        TextView date;
        TextView content;
        Button like;
        Button comment;

        YourDiaryHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = itemView.findViewById(R.id.yourTitle);
            date = itemView.findViewById(R.id.yourDate);
            content = itemView.findViewById(R.id.yourContent);
            like = itemView.findViewById(R.id.yourLike);
            comment = itemView.findViewById(R.id.yourComment);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null)
                onItemClickListener.onItemClick(v, getAdapterPosition());
        }
    }

    public ArrayList<Diary> getItems() {
        return items;
    }

    public void setItems(ArrayList<Diary> items) {
        this.items = items;
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public OnLikeButtonClickListener getOnLikeButtonClickListener() {
        return onLikeButtonClickListener;
    }

    public void setOnLikeButtonClickListener(OnLikeButtonClickListener onLikeButtonClickListener) {
        this.onLikeButtonClickListener = onLikeButtonClickListener;
    }

    public OnCommentButtonClickListener getOnCommentButtonClickListener() {
        return onCommentButtonClickListener;
    }

    public void setOnCommentButtonClickListener(OnCommentButtonClickListener onCommentButtonClickListener) {
        this.onCommentButtonClickListener = onCommentButtonClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public interface OnLikeButtonClickListener {
        void onLickClick(Diary diaryToShare);
    }

    public interface OnCommentButtonClickListener{
        void onCommentClick(Diary diaryToComment);
    }
}
