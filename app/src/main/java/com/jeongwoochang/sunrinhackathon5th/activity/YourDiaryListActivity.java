package com.jeongwoochang.sunrinhackathon5th.activity;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.jeongwoochang.sunrinhackathon5th.API.APIClient;
import com.jeongwoochang.sunrinhackathon5th.API.APIInterface;
import com.jeongwoochang.sunrinhackathon5th.R;
import com.jeongwoochang.sunrinhackathon5th.adapter.YourDiaryListAdapter;
import com.jeongwoochang.sunrinhackathon5th.data.Board;
import com.jeongwoochang.sunrinhackathon5th.data.DiaryRes;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.Collections;

public class YourDiaryListActivity extends AppCompatActivity {

    private RecyclerView yourDiaryList;
    private YourDiaryListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_diary_list);

        yourDiaryList = findViewById(R.id.recycler_your);
        adapter = new YourDiaryListAdapter(this);
        adapter.setOnItemClickListener(new YourDiaryListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position, Board board) {
                Intent i = new Intent(YourDiaryListActivity.this, YourDiaryActivity.class);
                i.putExtra("diary_data", board);
                startActivity(i);
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        yourDiaryList.setLayoutManager(layoutManager);
        yourDiaryList.setAdapter(adapter);

        APIInterface service = APIClient.getClient(this).create(APIInterface.class);
        service.getBoard().enqueue(new Callback<DiaryRes>() {
            @Override
            public void onResponse(Call<DiaryRes> call, Response<DiaryRes> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus()) {
                        ArrayList items = response.body().getBoard();
                        Collections.reverse(items);
                        adapter.setItems(items);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<DiaryRes> call, Throwable t) {

            }
        });
    }
}
