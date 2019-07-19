package com.jeongwoochang.sunrinhackathon5th.activity;

import android.view.View;
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
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.HashMap;

public class MyDiaryActivity extends AppCompatActivity {

    private TextView title, date, content, sharBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_diary);

        title = findViewById(R.id.myTitle);
        date = findViewById(R.id.myDate);
        content = findViewById(R.id.myContent);
        sharBtn = findViewById(R.id.myShare);

        final Board data = (Board) getIntent().getSerializableExtra("diary_data");

        if (data == null) {
            Toast.makeText(this, "일기 정보를 불러오는데 문제가 생겼습니다.", Toast.LENGTH_SHORT).show();
            finish();
        }

        title.setText(data.getTitle());
        content.setText(data.getContent());
        date.setText(data.getDate());

        final APIInterface service = APIClient.getClient(this).create(APIInterface.class);
        sharBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, RequestBody> map = new HashMap<>();
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
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<ResBody> call, Throwable t) {

                    }
                });
            }
        });
    }
}
