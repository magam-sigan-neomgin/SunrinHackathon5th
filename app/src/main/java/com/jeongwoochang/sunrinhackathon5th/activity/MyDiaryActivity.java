package com.jeongwoochang.sunrinhackathon5th.activity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.jeongwoochang.sunrinhackathon5th.API.APIClient;
import com.jeongwoochang.sunrinhackathon5th.API.APIInterface;
import com.jeongwoochang.sunrinhackathon5th.R;
import com.jeongwoochang.sunrinhackathon5th.data.Board;
import com.jeongwoochang.sunrinhackathon5th.data.BoardIDReq;
import com.jeongwoochang.sunrinhackathon5th.data.DiaryRes;
import com.jeongwoochang.sunrinhackathon5th.data.ResBody;
import com.squareup.picasso.Picasso;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.joda.time.Interval;
import org.joda.time.Period;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

public class MyDiaryActivity extends AppCompatActivity {

    private TextView title, date, content, sharBtn, musicTitle, musicArtist;
    private ImageView thumbnail;
    private ImageView playBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_diary);

        title = findViewById(R.id.myTitle);
        date = findViewById(R.id.myDate);
        content = findViewById(R.id.myContent);
        sharBtn = findViewById(R.id.myShare);

        musicTitle = findViewById(R.id.myMusicTitle);
        musicArtist = findViewById(R.id.myMusicSinger);
        thumbnail = findViewById(R.id.myMusicAlbum);
        playBtn = findViewById(R.id.myMusicPlay);

        final Board data = (Board) getIntent().getSerializableExtra("diary_data");

        if (data == null) {
            Toast.makeText(this, "일기 정보를 불러오는데 문제가 생겼습니다.", Toast.LENGTH_SHORT).show();
            finish();
        }

        title.setText(data.getTitle());
        content.setText(data.getContent());
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            date.setText(getSummaryPeriod(sdf.parse(data.getDate())));
        } catch (ParseException e) {
            e.printStackTrace();
            date.setText("날짜 형식 오류");
        }

        musicTitle.setText(data.getSuggest().getItems().get(0).getSnippet().getTitle());
        musicArtist.setText(data.getSuggest().getItems().get(0).getSnippet().getChannelTitle());
        Picasso.get()
                .load(data.getSuggest().getItems().get(0).getSnippet().getThumbnails().getHigh().getUrl())
                .fit()
                .centerCrop()
                .into(thumbnail);

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                watchYoutubeVideo(MyDiaryActivity.this, data.getSuggest().getItems().get(0).getId().getVideoId());
            }
        });

        final APIInterface service = APIClient.getClient(this).create(APIInterface.class);
        sharBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                service.setBoardShare(new BoardIDReq(data.getId())).enqueue(
                        new Callback<ResBody>() {
                            @Override
                            public void onResponse(Call<ResBody> call, Response<ResBody> response) {
                                if (response.code() == 200) {
                                    if (response.body().isStatus()) {
                                        Toast.makeText(MyDiaryActivity.this, "공유되었습니다.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<ResBody> call, Throwable t) {

                            }
                        });
                /*HashMap<String, RequestBody> map = new HashMap<>();
                map.put("title", RequestBody.create(MediaType.parse("text/plain"), data.getTitle()));
                map.put("content", RequestBody.create(MediaType.parse("text/plain"), data.getContent()));
                map.put("photo\"; filename=\"photo.png\"",
                        RequestBody.create(MediaType.parse("image/png"), data.getPhoto()));
                map.put("emotion", RequestBody.create(MediaType.parse("text/plain"), data.getEmotion()));
                service.addBoard(map).enqueue(new Callback<ResBody>() {
                    @Override
                    public void onResponse(Call<ResBody> call, Response<ResBody> response) {
                        if (response.code() == 200) {
                            if (response.body().isStatus()) {

                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<ResBody> call, Throwable t) {

                    }
                });*/
            }
        });
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

    public static void watchYoutubeVideo(Context context, String id){
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }
    }
}
