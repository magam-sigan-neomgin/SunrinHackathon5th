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
import com.jeongwoochang.sunrinhackathon5th.data.ResBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.HashMap;

public class YourDiaryActivity extends AppCompatActivity {

    private TextView title, date, content, author;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_diary);

        title = findViewById(R.id.yourTitle);
        date = findViewById(R.id.yourDate);
        content = findViewById(R.id.yourContent);
        author = findViewById(R.id.yourWriter);

        Board data = (Board) getIntent().getSerializableExtra("diary_data");

        if (data == null) {
            Toast.makeText(this, "일기 정보를 불러오는데 문제가 생겼습니다.", Toast.LENGTH_SHORT).show();
            finish();
        }

        title.setText(data.getTitle());
        content.setText(data.getContent());
        date.setText(data.getDate());

        final APIInterface service = APIClient.getClient(this).create(APIInterface.class);



    }
}
