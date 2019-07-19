package com.jeongwoochang.sunrinhackathon5th.activity;

import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import com.jeongwoochang.sunrinhackathon5th.API.APIClient;
import com.jeongwoochang.sunrinhackathon5th.API.APIInterface;
import com.jeongwoochang.sunrinhackathon5th.R;
import com.jeongwoochang.sunrinhackathon5th.data.ResBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.HashMap;

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

        titleEdit = findViewById(R.id.write_title);
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

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!titleEdit.getText().toString().isEmpty() && !contentEdit.getText().toString().isEmpty()) {
                    APIInterface service = APIClient.getClient(WriteDiaryActivity.this).create(APIInterface.class);
                    HashMap<String, RequestBody> map = new HashMap<>();
                    map.put("title", RequestBody.create(MediaType.parse("text/plain"), titleEdit.getText().toString()));
                    map.put("content", RequestBody.create(MediaType.parse("text/plain"), contentEdit.getText().toString()));
                    map.put("photo\"; filename=\"photo.png\"",
                            RequestBody.create(MediaType.parse("image/png"), "/sdcard/DCIM/Camera/IMG_20190621_090539.jpg"));
                    map.put("emotion", RequestBody.create(MediaType.parse("text/plain"), getResources().getStringArray(R.array.key_emotion)[index]));
                    service.addBoard(map).enqueue(new Callback<ResBody>() {
                        @Override
                        public void onResponse(Call<ResBody> call, Response<ResBody> response) {
                            if(response.code() == 200){
                                if(response.body().isStatus()){
                                    Toast.makeText(WriteDiaryActivity.this, "글이 작성되었습니다.", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResBody> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }
}
