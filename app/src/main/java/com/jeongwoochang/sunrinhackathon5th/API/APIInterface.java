package com.jeongwoochang.sunrinhackathon5th.API;

import com.jeongwoochang.sunrinhackathon5th.data.DiaryRes;
import com.jeongwoochang.sunrinhackathon5th.data.ResBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.HashMap;

public interface APIInterface {
    @Multipart
    @POST("/register")
    Call<ResBody> register(@PartMap HashMap<String, RequestBody> registerParm);

    @POST("/login")
    Call<ResBody> login(@Query(value = "id", encoded = true) String id, @Query(value = "pw", encoded = true) String pw);

    @GET("/status")
    Call<ResBody> status();

    @GET("/board")
    Call<DiaryRes> getBoard();

    @Multipart
    @POST("/board/add")
    Call<ResBody> addBoard(@PartMap HashMap<String, RequestBody> board);
}
