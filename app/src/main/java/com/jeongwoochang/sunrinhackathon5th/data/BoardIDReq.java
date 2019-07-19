package com.jeongwoochang.sunrinhackathon5th.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BoardIDReq {
    @SerializedName("id")
    @Expose
    private Integer id;

    public BoardIDReq(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BoardIDReq{" +
                "id=" + id +
                '}';
    }
}
