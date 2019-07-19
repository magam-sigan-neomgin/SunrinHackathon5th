package com.jeongwoochang.sunrinhackathon5th.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ProfileRes {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("profile")
    @Expose
    private Profile profile;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("status", status).append("profile", profile).toString();
    }
}
