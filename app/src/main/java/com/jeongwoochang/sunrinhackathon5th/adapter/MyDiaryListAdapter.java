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
import org.joda.time.Interval;
import org.joda.time.Period;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

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
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            holder.date.setText(getSummaryPeriod(sdf.parse(item.getDate())));
        } catch (ParseException e) {
            e.printStackTrace();
            holder.date.setText("날짜 형식 오류");
        }
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

    private String getSummaryPeriod(Date date) {
        String resultPeriod = "";
        Interval interval = new Interval(date.getTime(), new Date().getTime());
        Period period = interval.toPeriod();
        if (period.getYears() > 0) {
            resultPeriod = period.getYears() + "년 전";
        } else if (period.getMonths() > 0) {
            resultPeriod = period.getMonths() + "개월 전";
        } else if (period.getWeeks() > 0) {
            resultPeriod = period.getWeeks() + "주 전";
        } else if (period.getHours() > 0) {
            resultPeriod = period.getDays() + "일 전";
        } else if (period.getWeeks() > 0) {
            resultPeriod = period.getHours() + "시간 전";
        } else if (period.getMinutes() > 0) {
            resultPeriod = period.getMinutes() + "분 전";
        } else if (period.getSeconds() > 0) {
            resultPeriod = period.getSeconds() + "초 전";
        }
        return resultPeriod;
    }
}
