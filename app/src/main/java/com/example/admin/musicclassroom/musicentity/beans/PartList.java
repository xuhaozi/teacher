package com.example.admin.musicclassroom.musicentity.beans;

import java.util.List;

/**
 * Created by Administrator on 2017/6/28.
 */

public class PartList {
    private List<ScorePart> scorePartList;

    public List<ScorePart> getScorePartList() {
        return scorePartList;
    }

    public void setScorePartList(List<ScorePart> scorePartList) {
        this.scorePartList = scorePartList;
    }

    @Override
    public String toString() {
        return "PartList{" +
                "scorePartList=" + scorePartList +
                '}';
    }
}
