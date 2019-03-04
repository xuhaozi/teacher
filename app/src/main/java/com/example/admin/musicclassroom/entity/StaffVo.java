package com.example.admin.musicclassroom.entity;

public class StaffVo {
    private String name;
    private String stave;
    private String notation;
    private String midi;
    private String xml;
    private String mp3;
    private String accompany;

    @Override
    public String toString() {
        return "StaffVo{" +
                "name='" + name + '\'' +
                ", stave='" + stave + '\'' +
                ", notation='" + notation + '\'' +
                ", midi='" + midi + '\'' +
                ", xml='" + xml + '\'' +
                ", mp3='" + mp3 + '\'' +
                ", accompany='" + accompany + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStave() {
        return stave;
    }

    public void setStave(String stave) {
        this.stave = stave;
    }

    public String getNotation() {
        return notation;
    }

    public void setNotation(String notation) {
        this.notation = notation;
    }

    public String getMidi() {
        return midi;
    }

    public void setMidi(String midi) {
        this.midi = midi;
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public String getMp3() {
        return mp3;
    }

    public void setMp3(String mp3) {
        this.mp3 = mp3;
    }

    public String getAccompany() {
        return accompany;
    }

    public void setAccompany(String accompany) {
        this.accompany = accompany;
    }

    public StaffVo(String name, String stave, String notation, String midi, String xml, String mp3, String accompany) {
        this.name = name;
        this.stave = stave;
        this.notation = notation;
        this.midi = midi;
        this.xml = xml;
        this.mp3 = mp3;
        this.accompany = accompany;
    }
}
