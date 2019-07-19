
package com.jeongwoochang.sunrinhackathon5th.data;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class BoardRes {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("board")
    @Expose
    private List<Board> board = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<Board> getBoard() {
        return board;
    }

    public void setBoard(List<Board> board) {
        this.board = board;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("status", status).append("board", board).toString();
    }

}
