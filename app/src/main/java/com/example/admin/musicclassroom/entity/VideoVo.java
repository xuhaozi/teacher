package com.example.admin.musicclassroom.entity;


public class VideoVo {
    private String videoName;
    private String video;

    @Override
    public String toString() {
        return "VideoVo{" +
                "videoName='" + videoName + '\'' +
                ", video='" + video + '\'' +
                '}';
    }

    public VideoVo(String videoName, String video) {
        this.videoName = videoName;
        this.video = video;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}
