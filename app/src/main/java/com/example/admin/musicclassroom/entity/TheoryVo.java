package com.example.admin.musicclassroom.entity;

import java.util.List;

public class TheoryVo {
    private Long theoryId;
    private Long parentId;
    private String theoryChapter;
    private String theoryName;
    private String theoryImage;
    private List<VideoVo> videoVoList;
    private List<TheoryVo> theoryVoList;

    @Override
    public String toString() {
        return "TheoryVo{" +
                "theoryId=" + theoryId +
                ", parentId=" + parentId +
                ", theoryChapter='" + theoryChapter + '\'' +
                ", theoryName='" + theoryName + '\'' +
                ", theoryImage='" + theoryImage + '\'' +
                ", videoVoList=" + videoVoList +
                ", theoryVoList=" + theoryVoList +
                '}';
    }

    public Long getTheoryId() {
        return theoryId;
    }

    public void setTheoryId(Long theoryId) {
        this.theoryId = theoryId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getTheoryChapter() {
        return theoryChapter;
    }

    public void setTheoryChapter(String theoryChapter) {
        this.theoryChapter = theoryChapter;
    }

    public String getTheoryName() {
        return theoryName;
    }

    public void setTheoryName(String theoryName) {
        this.theoryName = theoryName;
    }

    public String getTheoryImage() {
        return theoryImage;
    }

    public void setTheoryImage(String theoryImage) {
        this.theoryImage = theoryImage;
    }

    public List<VideoVo> getVideoVoList() {
        return videoVoList;
    }

    public void setVideoVoList(List<VideoVo> videoVoList) {
        this.videoVoList = videoVoList;
    }

    public List<TheoryVo> getTheoryVoList() {
        return theoryVoList;
    }

    public void setTheoryVoList(List<TheoryVo> theoryVoList) {
        this.theoryVoList = theoryVoList;
    }

    public TheoryVo(Long theoryId, Long parentId, String theoryChapter, String theoryName, String theoryImage, List<VideoVo> videoVoList, List<TheoryVo> theoryVoList) {
        this.theoryId = theoryId;
        this.parentId = parentId;
        this.theoryChapter = theoryChapter;
        this.theoryName = theoryName;
        this.theoryImage = theoryImage;
        this.videoVoList = videoVoList;
        this.theoryVoList = theoryVoList;
    }
}
