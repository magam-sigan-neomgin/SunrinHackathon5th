package com.jeongwoochang.sunrinhackathon5th.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.jeongwoochang.sunrinhackathon5th.API.APIClient;
import com.jeongwoochang.sunrinhackathon5th.API.APIInterface;
import com.jeongwoochang.sunrinhackathon5th.R;
import com.jeongwoochang.sunrinhackathon5th.data.Board;
import com.jeongwoochang.sunrinhackathon5th.data.UsernameRes;
import org.joda.time.Interval;
import org.joda.time.Period;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class YourDiaryListAdapter extends RecyclerView.Adapter<YourDiaryListAdapter.YourDiaryHolder> {

    private ArrayList<Board> items;
    private OnItemClickListener onItemClickListener;
    private OnLikeButtonClickListener onLikeButtonClickListener;
    private OnCommentButtonClickListener onCommentButtonClickListener;
    private Context context;

    public YourDiaryListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public YourDiaryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_diary_your, parent, false);
        return new YourDiaryHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final YourDiaryHolder holder, int position) {
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
        /*APIInterface service = APIClient.getClient(context).create(APIInterface.class);
        service.convertIDToUsername(item.getAuthor()).enqueue(new Callback<UsernameRes>() {
            @Override
            public void onResponse(Call<UsernameRes> call, Response<UsernameRes> response) {
                if(response.code() == 200){
                    if(response.body().getStatus()){
                        holder.author.setText(response.body().getUsername().getUsername());
                    }
                }
            }

            @Override
            public void onFailure(Call<UsernameRes> call, Throwable t) {

            }
        });*/
        holder.author.setText(item.getAuthor());
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
        TextView author;

        YourDiaryHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = itemView.findViewById(R.id.yourTitle);
            date = itemView.findViewById(R.id.yourDate);
            content = itemView.findViewById(R.id.yourContent);
            author = itemView.findViewById(R.id.yourName);
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
        void onItemClick(View v, int position, Board board);
    }

    public interface OnLikeButtonClickListener {
        void onLickClick(Board diaryToShare);
    }

    public interface OnCommentButtonClickListener {
        void onCommentClick(Board diaryToComment);
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
