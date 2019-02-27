package com.example.admin.musicclassroom.entity;

import java.util.List;

public class MusicStyleVo {
    private Long styleId;
    private Integer parentId;
    private String styleKind;
    private String styleName;
    private String styleIntroduce;
    private List<MusicStyleVo> musicStyleVoList;
    private List<MusicVo> musicVoList;
    private List<VideoVo> videoVoList;


    @Override
    public String toString() {
        return "MusicStyleVo{" +
                "styleId=" + styleId +
                ", parentId=" + parentId +
                ", styleKind='" + styleKind + '\'' +
                ", styleName='" + styleName + '\'' +
                ", styleIntroduce='" + styleIntroduce + '\'' +
                ", musicStyleVoList=" + musicStyleVoList +
                ", musicVoList=" + musicVoList +
                ", videoVoList=" + videoVoList +
                '}';
    }

    public Long getStyleId() {
        return styleId;
    }

    public void setStyleId(Long styleId) {
        this.styleId = styleId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getStyleKind() {
        return styleKind;
    }

    public void setStyleKind(String styleKind) {
        this.styleKind = styleKind;
    }

    public String getStyleName() {
        return styleName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    public String getStyleIntroduce() {
        return styleIntroduce;
    }

    public void setStyleIntroduce(String styleIntroduce) {
        this.styleIntroduce = styleIntroduce;
    }

    public List<MusicStyleVo> getMusicStyleVoList() {
        return musicStyleVoList;
    }

    public void setMusicStyleVoList(List<MusicStyleVo> musicStyleVoList) {
        this.musicStyleVoList = musicStyleVoList;
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

    public MusicStyleVo(Long styleId, Integer parentId, String styleKind, String styleName, String styleIntroduce, List<MusicStyleVo> musicStyleVoList, List<MusicVo> musicVoList, List<VideoVo> videoVoList) {
        this.styleId = styleId;
        this.parentId = parentId;
        this.styleKind = styleKind;
        this.styleName = styleName;
        this.styleIntroduce = styleIntroduce;
        this.musicStyleVoList = musicStyleVoList;
        this.musicVoList = musicVoList;
        this.videoVoList = videoVoList;
    }
}
