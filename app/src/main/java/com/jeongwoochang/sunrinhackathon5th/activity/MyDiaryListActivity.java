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
import com.jeongwoochang.sunrinhackathon5th.adapter.MyDiaryListAdapter;
import com.jeongwoochang.sunrinhackathon5th.data.Board;
import com.jeongwoochang.sunrinhackathon5th.data.DiaryRes;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyDiaryListActivity extends AppCompatActivity {

    private RecyclerView myDiaryList;
    private MyDiaryListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_diary_list);

        myDiaryList = findViewById(R.id.recycler_my);
        adapter = new MyDiaryListAdapter();
        adapter.setOnItemClickListener(new MyDiaryListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position, Board board) {
                Intent i = new Intent(MyDiaryListActivity.this, MyDiaryActivity.class);
                i.putExtra("diary_data", board);
                startActivity(i);
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        myDiaryList.setLayoutManager(layoutManager);
        myDiaryList.setAdapter(adapter);

        APIInterface service = APIClient.getClient(this).create(APIInterface.class);
        service.getMyBoard().enqueue(new Callback<DiaryRes>() {
            @Override
            public void onResponse(Call<DiaryRes> call, Response<DiaryRes> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus()) {
                        adapter.setItems(response.body().getBoard());
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
