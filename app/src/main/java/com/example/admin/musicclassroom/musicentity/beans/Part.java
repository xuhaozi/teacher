package com.example.admin.musicclassroom.musicentity.beans;


import java.util.List;

/**
 * Created by Administrator on 2017/6/28.
 */

public class Part {
    private List<Measure> measureList;

    public List<Measure> getMeasureList() {
        return measureList;
    }

    public void setMeasureList(List<Measure> measureList) {
        this.measureList = measureList;
    }

    @Override
    public String toString() {
        return "Part{" +
                "measureList=" + measureList +
                '}';
    }
}
