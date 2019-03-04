package com.example.admin.musicclassroom.musicentity.beans;

/**
 * Created by Administrator on 2017/6/28.
 */

public class ScoreInstrument {
    private String id;
    private String InstrumenyName;

    public String getInstrumenyName() {
        return InstrumenyName;
    }

    public void setInstrumenyName(String instrumenyName) {
        InstrumenyName = instrumenyName;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ScoreInstrument{" +
                "id='" + id + '\'' +
                ", InstrumenyName='" + InstrumenyName + '\'' +
                '}';
    }
}
