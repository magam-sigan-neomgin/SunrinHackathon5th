package com.jeongwoochang.sunrinhackathon5th.activity;

import android.app.Application;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import com.jeongwoochang.sunrinhackathon5th.API.APIClient;
import com.jeongwoochang.sunrinhackathon5th.API.APIInterface;
import com.jeongwoochang.sunrinhackathon5th.MyApplication;
import com.jeongwoochang.sunrinhackathon5th.R;
import com.jeongwoochang.sunrinhackathon5th.data.DiaryRes;
import com.jeongwoochang.sunrinhackathon5th.data.ProfileRes;
import com.jeongwoochang.sunrinhackathon5th.util.SharedPreferencesHelper;
import com.squareup.picasso.Picasso;
import net.danlew.android.joda.JodaTimeAndroid;
import org.joda.time.DateTime;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    CardView writeDiaryBtn, myDiaryBtn, yourDiaryBtn;
    TextView mainDate;
    TextView name, email;
    ImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyApplication.getInstance().setOnVisibilityChangeListener(new MyApplication.ValueChangeListener() {
            @Override
            public void onChanged(Boolean value) {
                if (value) {
                    if (!new SharedPreferencesHelper(MainActivity.this).getAutoLogin())
                        new SharedPreferencesHelper(MainActivity.this).removeCookies();
                }
            }
        });

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        profileImage = findViewById(R.id.profileImage);

        APIInterface service = APIClient.getClient(this).create(APIInterface.class);
        service.getProfile().enqueue(new Callback<ProfileRes>() {
            @Override
            public void onResponse(Call<ProfileRes> call, Response<ProfileRes> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus()) {
                        ProfileRes data = response.body();

                        email.setText(data.getProfile().getId());
                        name.setText(data.getProfile().getUsername());
                        Picasso.get().load("https://good-night-image-bucket.s3.ap-northeast-2.amazonaws.com/" + data.getProfile().getImage()).into(profileImage);
                    }
                }
            }

            @Override
            public void onFailure(Call<ProfileRes> call, Throwable t) {

            }
        });
        service.getMyBoard().enqueue(new Callback<DiaryRes>() {
            @Override
            public void onResponse(Call<DiaryRes> call, Response<DiaryRes> response) {
                if (response.code() == 200) {
                    DiaryRes board = response.body();
                    Timber.d(board.toString());
                }
            }

            @Override
            public void onFailure(Call<DiaryRes> call, Throwable t) {

            }
        });

        mainDate = findViewById(R.id.main_date);
        writeDiaryBtn = findViewById(R.id.main_btn_diary_write);
        myDiaryBtn = findViewById(R.id.main_btn_diary_my);
        yourDiaryBtn = findViewById(R.id.main_btn_diary_your);

        DateTime dateTime = new DateTime();
        String today = dateTime.toString("오늘은 yyyy년 MM월 dd일 입니다.");

        mainDate.setText(today);

        writeDiaryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, WriteDiaryActivity.class);
                startActivity(intent);
            }
        });

        myDiaryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MyDiaryListActivity.class);
                startActivity(intent);
            }
        });

        yourDiaryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, YourDiaryListActivity.class);
                startActivity(intent);
            }
        });
    }
}

class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
    }
}