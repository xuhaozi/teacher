package com.example.admin.musicclassroom.musicentity.beans;

/**
 * Created by Administrator on 2017/6/28.
 */

public class Work {
    private String work_title;

    public String getWork_title() {
        return work_title;
    }

    public void setWork_title(String work_title) {
        this.work_title = work_title;
    }

    @Override
    public String toString() {
        return "Work{" +
                "work_title='" + work_title + '\'' +
                '}';
    }
}
