
package com.jeongwoochang.sunrinhackathon5th.data;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class DiaryRes {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("diary")
    @Expose
    private List<Diary> diary = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<Diary> getDiary() {
        return diary;
    }

    public void setDiary(List<Diary> diary) {
        this.diary = diary;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("status", status).append("diary", diary).toString();
    }

}
