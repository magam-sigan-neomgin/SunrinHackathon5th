package com.jeongwoochang.sunrinhackathon5th.API;

import com.jeongwoochang.sunrinhackathon5th.data.Board;
import com.jeongwoochang.sunrinhackathon5th.data.BoardRes;
import com.jeongwoochang.sunrinhackathon5th.data.ResBody;
import com.jeongwoochang.sunrinhackathon5th.data.User;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.HashMap;

public interface APIInterface {
    @POST("/register")
    Call<ResBody> register(@Body User registerParm); //@PartMap HashMap<String, RequestBody> registerParm

    @POST("/login")
    Call<ResBody> login(@Query(value = "id", encoded = true) String id, @Query(value = "pw", encoded = true) String pw);

    @GET("/status")
    Call<ResBody> status();

    @GET("/board")
    Call<BoardRes> getBoard();

    @POST("/broad/add")
    Call<ResBody> addBoard(@Body Board board);
}
