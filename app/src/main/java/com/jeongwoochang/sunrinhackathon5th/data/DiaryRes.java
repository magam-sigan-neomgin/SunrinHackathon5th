
package com.jeongwoochang.sunrinhackathon5th.data;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class DiaryRes {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("board")
    @Expose
    private ArrayList<Board> board = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public ArrayList<Board> getBoard() {
        return board;
    }

    public void setBoard(ArrayList<Board> board) {
        this.board = board;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("status", status).append("board", board).toString();
    }

}
