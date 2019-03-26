package com.example.admin.musicclassroom.entity;

import java.util.List;

public class MusicianVo {
    private Long musicianId;
    private String musicianName;
    private String musicianIntroduce;
    private String musicianImage;
    private String musicianMusic;
    private List<MusicVo> musicVoList;


    @Override
    public String toString() {
        return "MusicianVo{" +
                "musicianId=" + musicianId +
                ", musicianName='" + musicianName + '\'' +
                ", musicianIntroduce='" + musicianIntroduce + '\'' +
                ", musicianImage='" + musicianImage + '\'' +
                ", musicianMusic='" + musicianMusic + '\'' +
                ", musicVoList=" + musicVoList +
                '}';
    }

    public Long getMusicianId() {
        return musicianId;
    }

    public void setMusicianId(Long musicianId) {
        this.musicianId = musicianId;
    }

    public String getMusicianName() {
        return musicianName;
    }

    public void setMusicianName(String musicianName) {
        this.musicianName = musicianName;
    }

    public String getMusicianIntroduce() {
        return musicianIntroduce;
    }

    public void setMusicianIntroduce(String musicianIntroduce) {
        this.musicianIntroduce = musicianIntroduce;
    }

    public String getMusicianImage() {
        return musicianImage;
    }

    public void setMusicianImage(String musicianImage) {
        this.musicianImage = musicianImage;
    }

    public String getMusicianMusic() {
        return musicianMusic;
    }

    public void setMusicianMusic(String musicianMusic) {
        this.musicianMusic = musicianMusic;
    }

    public List<MusicVo> getMusicVoList() {
        return musicVoList;
    }

    public void setMusicVoList(List<MusicVo> musicVoList) {
        this.musicVoList = musicVoList;
    }

    public MusicianVo(Long musicianId, String musicianName, String musicianIntroduce, String musicianImage, String musicianMusic, List<MusicVo> musicVoList) {
        this.musicianId = musicianId;
        this.musicianName = musicianName;
        this.musicianIntroduce = musicianIntroduce;
        this.musicianImage = musicianImage;
        this.musicianMusic = musicianMusic;
        this.musicVoList = musicVoList;
    }
    public MusicianVo(String musicianName, String musicianImage){
        this.musicianName = musicianName;
        this.musicianImage = musicianImage;
    }
    public MusicianVo(String musicianName, String musicianIntroduce, String musicianImage, List<MusicVo> musicVoList){
        this.musicianName = musicianName;
        this.musicianIntroduce = musicianIntroduce;
        this.musicianImage = musicianImage;
        this.musicVoList = musicVoList;
    }
}
