package com.jeongwoochang.sunrinhackathon5th.API;

import com.jeongwoochang.sunrinhackathon5th.data.*;
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

    @GET("/board/my")
    Call<DiaryRes> getMyBoard();

    @GET("/board")
    Call<DiaryRes> getBoard();

    @Multipart
    @POST("/board/add")
    Call<ResBody> addBoard(@PartMap HashMap<String, RequestBody> board);

    @POST("/board/share")
    Call<ResBody> setBoardShare(@Body BoardIDReq id);

    @POST("/board/comment")
    Call<ResBody> addComment(@Body CommentReq comment);

    @GET("/profile")
    Call<ProfileRes> getProfile();

    @POST("/board/unlike")
    Call<ResBody> unlike(@Body BoardIDReq id);

    @POST("/board/like")
    Call<ResBody> like(@Body BoardIDReq id);

    @GET("/id/to/username")
    Call<UsernameRes> convertIDToUsername(@Query("id") String id);
}
