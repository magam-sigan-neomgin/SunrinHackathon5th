package com.jeongwoochang.sunrinhackathon5th.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class UsernameRes {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("username")
    @Expose
    private Username username;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Username getUsername() {
        return username;
    }

    public void setUsername(Username username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("status", status).append("username", username).toString();
    }

}
