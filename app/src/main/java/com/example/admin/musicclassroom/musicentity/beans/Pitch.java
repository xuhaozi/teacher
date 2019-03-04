package com.example.admin.musicclassroom.musicentity.beans;

/**
 * Created by Administrator on 2017/6/28.
 */

public class Pitch {
    private String step;
    private int octave;
    private int alter;

    public int getAlter() {
        return alter;
    }

    public void setAlter(int alter) {
        this.alter = alter;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public int getOctave() {
        return octave;
    }

    public void setOctave(int octave) {
        this.octave = octave;
    }

    @Override
    public String toString() {
        return "Pitch{" +
                "step='" + step + '\'' +
                ", octave=" + octave +
                ", alter=" + alter +
                '}';
    }
}
