package com.example.admin.musicclassroom.musicentity.beans;


/**
 * Created by Administrator on 2017/6/28.
 */

public class ScorePart {
    private String partname;
    private String partAbbreviation;
    private ScoreInstrument scoreInstrument;
    private MidiDevice midiDevice;
    private MidiInstrument midiInstrument;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPartname() {
        return partname;
    }

    public void setPartname(String partname) {
        this.partname = partname;
    }

    public String getPartAbbreviation() {
        return partAbbreviation;
    }

    public void setPartAbbreviation(String partAbbreviation) {
        this.partAbbreviation = partAbbreviation;
    }

    public ScoreInstrument getScoreInstrument() {
        return scoreInstrument;
    }

    public void setScoreInstrument(ScoreInstrument scoreInstrument) {
        this.scoreInstrument = scoreInstrument;
    }

    public MidiDevice getMidiDevice() {
        return midiDevice;
    }

    public void setMidiDevice(MidiDevice midiDevice) {
        this.midiDevice = midiDevice;
    }

    public MidiInstrument getMidiInstrument() {
        return midiInstrument;
    }

    public void setMidiInstrument(MidiInstrument midiInstrument) {
        this.midiInstrument = midiInstrument;
    }

    @Override
    public String toString() {
        return "ScorePart{" +
                "partname='" + partname + '\'' +
                ", partAbbreviation='" + partAbbreviation + '\'' +
                ", scoreInstrument=" + scoreInstrument +
                ", midiDevice=" + midiDevice +
                ", midiInstrument=" + midiInstrument +
                ", id='" + id + '\'' +
                '}';
    }
}
