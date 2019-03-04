package com.example.admin.musicclassroom.musicentity.beans;

/**
 * Created by Administrator on 2017/6/28.
 */

public class MidiInstrument {
    private String id;
    private int midiProgram;
    private int midiChannel;
    private double volume;
    private int pan;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMidiProgram() {
        return midiProgram;
    }

    public void setMidiProgram(int midiProgram) {
        this.midiProgram = midiProgram;
    }

    public int getMidiChannel() {
        return midiChannel;
    }

    public void setMidiChannel(int midiChannel) {
        this.midiChannel = midiChannel;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public int getPan() {
        return pan;
    }

    public void setPan(int pan) {
        this.pan = pan;
    }


    @Override
    public String toString() {
        return "MidiInstrument{" +
                "id='" + id + '\'' +
                ", midiProgram=" + midiProgram +
                ", midiChannel=" + midiChannel +
                ", volume=" + volume +
                ", pan=" + pan +
                '}';
    }
}
