package com.example.admin.musicclassroom.entity;

import java.util.List;

public class MusicalVo {
    private Long musicalId;
    private String musicalName;
    private String musicalImage;
    private String musicalMp3;
    private String musicalIntroduce;
    private List<MusicVo> musicVoList;
    private List<VideoVo> videoVoList;


    @Override
    public String toString() {
        return "MusicalVo{" +
                "musicalId=" + musicalId +
                ", musicalName='" + musicalName + '\'' +
                ", musicalImage='" + musicalImage + '\'' +
                ", musicalMp3='" + musicalMp3 + '\'' +
                ", musicalIntroduce='" + musicalIntroduce + '\'' +
                ", musicVoList=" + musicVoList +
                ", videoVoList=" + videoVoList +
                '}';
    }

    public Long getMusicalId() {
        return musicalId;
    }

    public void setMusicalId(Long musicalId) {
        this.musicalId = musicalId;
    }

    public String getMusicalName() {
        return musicalName;
    }

    public void setMusicalName(String musicalName) {
        this.musicalName = musicalName;
    }

    public String getMusicalImage() {
        return musicalImage;
    }

    public void setMusicalImage(String musicalImage) {
        this.musicalImage = musicalImage;
    }

    public String getMusicalMp3() {
        return musicalMp3;
    }

    public void setMusicalMp3(String musicalMp3) {
        this.musicalMp3 = musicalMp3;
    }

    public String getMusicalIntroduce() {
        return musicalIntroduce;
    }

    public void setMusicalIntroduce(String musicalIntroduce) {
        this.musicalIntroduce = musicalIntroduce;
    }

    public List<MusicVo> getMusicVoList() {
        return musicVoList;
    }

    public void setMusicVoList(List<MusicVo> musicVoList) {
        this.musicVoList = musicVoList;
    }

    public List<VideoVo> getVideoVoList() {
        return videoVoList;
    }

    public void setVideoVoList(List<VideoVo> videoVoList) {
        this.videoVoList = videoVoList;
    }

    public MusicalVo(Long musicalId, String musicalName, String musicalImage, String musicalMp3, String musicalIntroduce, List<MusicVo> musicVoList, List<VideoVo> videoVoList) {
        this.musicalId = musicalId;
        this.musicalName = musicalName;
        this.musicalImage = musicalImage;
        this.musicalMp3 = musicalMp3;
        this.musicalIntroduce = musicalIntroduce;
        this.musicVoList = musicVoList;
        this.videoVoList = videoVoList;
    }
}
