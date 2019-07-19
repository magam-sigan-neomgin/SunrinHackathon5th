package com.jeongwoochang.sunrinhackathon5th.activity;

import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import com.jeongwoochang.sunrinhackathon5th.R;

public class WriteDiaryActivity extends AppCompatActivity {

    EditText contentEdit;
    CardView confirmBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_diary);

        contentEdit = findViewById(R.id.write_content);
        confirmBtn = findViewById(R.id.write_confirm);
    }
}
