package com.jeongwoochang.sunrinhackathon5th.activity;

import android.app.Application;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.jeongwoochang.sunrinhackathon5th.MyApplication;
import com.jeongwoochang.sunrinhackathon5th.R;
import com.jeongwoochang.sunrinhackathon5th.util.SharedPreferencesHelper;
import net.danlew.android.joda.JodaTimeAndroid;
import org.joda.time.DateTime;

public class MainActivity extends AppCompatActivity {

    TextView mainDate;

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

        mainDate = findViewById(R.id.main_date);

        DateTime dateTime = new DateTime();
        String today = dateTime.toString("오늘은 yyyy년 MM월 dd일 입니다.");

        mainDate.setText(today);
    }
}

class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
    }
}
