package com.example.admin.musicclassroom.entity;

public class MusicVo {
    private String musicName;
    private String musicMp3;

    @Override
    public String toString() {
        return "MusicVo{" +
                "musicName='" + musicName + '\'' +
                ", musicMp3='" + musicMp3 + '\'' +
                '}';
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public String getMusicMp3() {
        return musicMp3;
    }

    public void setMusicMp3(String musicMp3) {
        this.musicMp3 = musicMp3;
    }

    public MusicVo(String musicName, String musicMp3) {
        this.musicName = musicName;
        this.musicMp3 = musicMp3;
    }
}
