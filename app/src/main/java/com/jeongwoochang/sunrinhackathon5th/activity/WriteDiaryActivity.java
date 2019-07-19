package com.jeongwoochang.sunrinhackathon5th.activity;

import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import com.jeongwoochang.sunrinhackathon5th.R;

public class WriteDiaryActivity extends AppCompatActivity {

    EditText contentEdit, titleEdit;
    LinearLayout emotionLayout;
    TextView emotionText;
    CardView confirmBtn;

    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_diary);

        contentEdit = findViewById(R.id.write_content);
        confirmBtn = findViewById(R.id.write_confirm);
        emotionLayout = findViewById(R.id.write_emotion);
        emotionText = findViewById(R.id.write_emotion_text);

        emotionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(WriteDiaryActivity.this);
                builder.setTitle("현재 기분 선택");

                builder.setSingleChoiceItems(R.array.emotion, index, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        index = i;
                    }
                });

                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String[] datas = getResources().getStringArray(R.array.emotion);
                        String s = datas[index];
                        emotionText.setText(s);
                    }
                });

                builder.setNegativeButton("취소", null);

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }
}
