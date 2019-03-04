package com.example.admin.musicclassroom.musicentity.beans;

import java.util.List;

/**
 * Created by Administrator on 2017/6/28.
 */

public class Note {
    private boolean rest;
    private double defaultX;
    private double defaultY;
    private Pitch pitch;
    private int duration;
    private int voice;
    private String type;
    private String stem;
    private List<Beam> beamList;
    private List<Lyric> lyricList;
    private String accidental;
    private Slur slur;
    private List<Dot> isDot;
    private Notations notations;

    public Notations getNotations() {
        return notations;
    }

    public void setNotations(Notations notations) {
        this.notations = notations;
    }

    public List<Dot> getIsDot() {
        return isDot;
    }

    public void setIsDot(List<Dot> isDot) {
        this.isDot = isDot;
    }

    public Slur getSlur() {
        return slur;
    }

    public void setSlur(Slur slur) {
        this.slur = slur;
    }

    public String getAccidental() {
        return accidental;
    }

    public void setAccidental(String accidental) {
        this.accidental = accidental;
    }

    public List<Beam> getBeamList() {
        return beamList;
    }

    public void setBeamList(List<Beam> beamList) {
        this.beamList = beamList;
    }

    public boolean isRest() {
        return rest;
    }

    public void setRest(boolean rest) {
        this.rest = rest;
    }

    public double getDefaultX() {
        return defaultX;
    }

    public void setDefaultX(double defaultX) {
        this.defaultX = defaultX;
    }

    public double getDefaultY() {
        return defaultY;
    }

    public void setDefaultY(double defaultY) {
        this.defaultY = defaultY;
    }

    public Pitch getPitch() {
        return pitch;
    }

    public void setPitch(Pitch pitch) {
        this.pitch = pitch;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getVoice() {
        return voice;
    }

    public void setVoice(int voice) {
        this.voice = voice;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStem() {
        return stem;
    }

    public void setStem(String stem) {
        this.stem = stem;
    }

    public List<Lyric> getLyricList() {
        return lyricList;
    }

    public void setLyricList(List<Lyric> lyricList) {
        this.lyricList = lyricList;
    }

    @Override
    public String toString() {
        return "Note{" +
                "rest=" + rest +
                ", defaultX=" + defaultX +
                ", defaultY=" + defaultY +
                ", pitch=" + pitch +
                ", duration=" + duration +
                ", voice=" + voice +
                ", type='" + type + '\'' +
                ", stem='" + stem + '\'' +
                ", beamList=" + beamList +
                ", lyricList=" + lyricList +
                ", accidental='" + accidental + '\'' +
                ", slur=" + slur +
                ", isDot=" + isDot +
                ", notations=" + notations +
                '}';
    }
}
