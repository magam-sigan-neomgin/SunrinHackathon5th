package com.jeongwoochang.sunrinhackathon5th.activity;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.jeongwoochang.sunrinhackathon5th.API.APIClient;
import com.jeongwoochang.sunrinhackathon5th.API.APIInterface;
import com.jeongwoochang.sunrinhackathon5th.R;
import com.jeongwoochang.sunrinhackathon5th.data.*;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import java.util.HashMap;

public class YourDiaryActivity extends AppCompatActivity {

    private TextView title, date, content, author, likes;
    private View like, comment;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_diary);

        title = findViewById(R.id.yourTitle);
        date = findViewById(R.id.yourDate);
        content = findViewById(R.id.yourContent);
        author = findViewById(R.id.yourWriter);
        like = findViewById(R.id.your_like);
        comment = findViewById(R.id.your_comment);
        likes = findViewById(R.id.likes);

        final Board data = (Board) getIntent().getSerializableExtra("diary_data");

        if (data == null) {
            Toast.makeText(this, "일기 정보를 불러오는데 문제가 생겼습니다.", Toast.LENGTH_SHORT).show();
            finish();
        }

        title.setText(data.getTitle());
        content.setText(data.getContent());
        date.setText(data.getDate());
        author.setText(data.getAuthor());
        likes.setText(String.valueOf(data.getLikes().size()));

        final APIInterface service = APIClient.getClient(this).create(APIInterface.class);
        service.getProfile().enqueue(new Callback<ProfileRes>() {
            @Override
            public void onResponse(Call<ProfileRes> call, final Response<ProfileRes> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus()) {
                        id = response.body().getProfile().getId();
                        like.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
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
}
