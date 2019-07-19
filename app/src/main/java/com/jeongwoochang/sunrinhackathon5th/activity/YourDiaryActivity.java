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
import com.jeongwoochang.sunrinhackathon5th.data.*;
import com.squareup.picasso.Picasso;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.joda.time.Interval;
import org.joda.time.Period;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

public class YourDiaryActivity extends AppCompatActivity {

    private TextView title, date, content, author, likes, musicTitle, musicArtist;
    private ImageView thumbnail;
    private View like, comment;
    private String id;
    private ImageView playBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_diary);

        Timber.plant(new Timber.DebugTree());

        title = findViewById(R.id.yourTitle);
        date = findViewById(R.id.yourDate);
        content = findViewById(R.id.yourContent);
        author = findViewById(R.id.yourWriter);
        like = findViewById(R.id.your_like);
        comment = findViewById(R.id.your_comment);
        likes = findViewById(R.id.likes);

        musicTitle = findViewById(R.id.yourMusicTitle);
        musicArtist = findViewById(R.id.yourMusicSinger);
        thumbnail = findViewById(R.id.yourMusicAlbum);

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
        final APIInterface service = APIClient.getClient(this).create(APIInterface.class);
        /*service.convertIDToUsername(data.getAuthor()).enqueue(new Callback<UsernameRes>() {
            @Override
            public void onResponse(Call<UsernameRes> call, Response<UsernameRes> response) {
                if(response.code() == 200){
                    if(response.body().getStatus()){
                        author.setText(response.body().getUsername().getUsername());
                    }
                }
            }

            @Override
            public void onFailure(Call<UsernameRes> call, Throwable t) {

            }
        });*/
        author.setText(data.getAuthor());
        likes.setText(String.valueOf(data.getLikes().size()));

        musicTitle.setText(data.getSuggest().getItems().get(0).getSnippet().getTitle());
        musicArtist.setText(data.getSuggest().getItems().get(0).getSnippet().getChannelTitle());
        Picasso.get()
                .load(data.getSuggest().getItems().get(0).getSnippet().getThumbnails().getHigh().getUrl())
                .fit()
                .centerCrop()
                .into(thumbnail);
        playBtn = findViewById(R.id.yourMusicPlay);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                watchYoutubeVideo(YourDiaryActivity.this, data.getSuggest().getItems().get(0).getId().getVideoId());
            }
        });

        service.getProfile().enqueue(new Callback<ProfileRes>() {
            @Override
            public void onResponse(Call<ProfileRes> call, final Response<ProfileRes> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus()) {
                        id = response.body().getProfile().getId();
                        like.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Timber.d(""+data.getLikes().contains(new Like(id)));
                                if (data.getLikes().contains(new Like(id))) {
                                    service.unlike(new BoardIDReq(data.getId())).enqueue(new retrofit2.Callback<ResBody>() {
                                        @Override
                                        public void onResponse(Call<ResBody> call, Response<ResBody> response) {
                                            if (response.code() == 200) {
                                                if (response.body().isStatus()) {
                                                    Toast.makeText(YourDiaryActivity.this, "좋아요가 반영되었습니다.", Toast.LENGTH_SHORT).show();
                                                    likes.setText(Integer.valueOf(likes.getText().toString()) - 1);
                                                }
                                            } else {
                                                Toast.makeText(YourDiaryActivity.this, "좋아요가 반영되지 않았습니다.", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<ResBody> call, Throwable t) {

                                        }
                                    });
                                } else {
                                    service.like(new BoardIDReq(data.getId())).enqueue(new retrofit2.Callback<ResBody>() {
                                        @Override
                                        public void onResponse(Call<ResBody> call, Response<ResBody> response) {
                                            if (response.code() == 200) {
                                                if (response.body().isStatus()) {
                                                    Toast.makeText(YourDiaryActivity.this, "좋아요가 반영되었습니다.", Toast.LENGTH_SHORT).show();
                                                    likes.setText(Integer.valueOf(likes.getText().toString()) + 1);
                                                }
                                            } else {
                                                Toast.makeText(YourDiaryActivity.this, "좋아요가 반영되지 않았습니다.", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<ResBody> call, Throwable t) {

                                        }
                                    });
                                }
                            }
                        });
                        if (data.getLikes().contains(new Like(response.body().getProfile().getId()))) {

                        } else {

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ProfileRes> call, Throwable t) {

            }
        });

        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
