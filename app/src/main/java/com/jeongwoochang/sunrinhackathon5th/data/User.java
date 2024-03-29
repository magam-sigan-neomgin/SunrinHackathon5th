package com.jeongwoochang.sunrinhackathon5th.data;

import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class User {
    @SerializedName("id")
    private String id;

    @SerializedName("pw")
    private String pw;

    @SerializedName("username")
    private String username;

    public User(String id, String pw, String username) {
        this.id = id;
        this.pw = pw;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("pw", pw).append("username", username).toString();
    }
}
