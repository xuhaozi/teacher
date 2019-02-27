package com.example.admin.musicclassroom.entity;

public class CourseVo {
    /**
     * 课程信息
     */
    private Long courseId;
    private String grade;
    private String term;
    private String unit;
    private String unitName;
    private String courseName;
    private String courseImage;
    private String courseIntroduce;
    /**
     * 词作者信息
     */
    private String wordAuthorName;
    private String wordAuthorIntroduce;
    private String wordAuthorImage;
    /**
     * 曲作者信息
     */
    private String anAuthorName;
    private String anAuthorIntroduce;
    private String anAuthorImage;
    /**
     * 曲谱信息
     */
    private String staffName;
    private String stave;
    private String notation;
    private String midi;
    private String xml;
    private String mp3;
    private String accompany;

    @Override
    public String toString() {
        return "CourseVo{" +
                "courseId=" + courseId +
                ", grade='" + grade + '\'' +
                ", term='" + term + '\'' +
                ", unit='" + unit + '\'' +
                ", unitName='" + unitName + '\'' +
                ", courseName='" + courseName + '\'' +
                ", courseImage='" + courseImage + '\'' +
                ", courseIntroduce='" + courseIntroduce + '\'' +
                ", wordAuthorName='" + wordAuthorName + '\'' +
                ", wordAuthorIntroduce='" + wordAuthorIntroduce + '\'' +
                ", wordAuthorImage='" + wordAuthorImage + '\'' +
                ", anAuthorName='" + anAuthorName + '\'' +
                ", anAuthorIntroduce='" + anAuthorIntroduce + '\'' +
                ", anAuthorImage='" + anAuthorImage + '\'' +
                ", staffName='" + staffName + '\'' +
                ", stave='" + stave + '\'' +
                ", notation='" + notation + '\'' +
                ", midi='" + midi + '\'' +
                ", xml='" + xml + '\'' +
                ", mp3='" + mp3 + '\'' +
                ", accompany='" + accompany + '\'' +
                '}';
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseImage() {
        return courseImage;
    }

    public void setCourseImage(String courseImage) {
        this.courseImage = courseImage;
    }

    public String getCourseIntroduce() {
        return courseIntroduce;
    }

    public void setCourseIntroduce(String courseIntroduce) {
        this.courseIntroduce = courseIntroduce;
    }

    public String getWordAuthorName() {
        return wordAuthorName;
    }

    public void setWordAuthorName(String wordAuthorName) {
        this.wordAuthorName = wordAuthorName;
    }

    public String getWordAuthorIntroduce() {
        return wordAuthorIntroduce;
    }

    public void setWordAuthorIntroduce(String wordAuthorIntroduce) {
        this.wordAuthorIntroduce = wordAuthorIntroduce;
    }

    public String getWordAuthorImage() {
        return wordAuthorImage;
    }

    public void setWordAuthorImage(String wordAuthorImage) {
        this.wordAuthorImage = wordAuthorImage;
    }

    public String getAnAuthorName() {
        return anAuthorName;
    }

    public void setAnAuthorName(String anAuthorName) {
        this.anAuthorName = anAuthorName;
    }

    public String getAnAuthorIntroduce() {
        return anAuthorIntroduce;
    }

    public void setAnAuthorIntroduce(String anAuthorIntroduce) {
        this.anAuthorIntroduce = anAuthorIntroduce;
    }

    public String getAnAuthorImage() {
        return anAuthorImage;
    }

    public void setAnAuthorImage(String anAuthorImage) {
        this.anAuthorImage = anAuthorImage;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
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

    public CourseVo(Long courseId, String grade, String term, String unit, String unitName, String courseName, String courseImage, String courseIntroduce, String wordAuthorName, String wordAuthorIntroduce, String wordAuthorImage, String anAuthorName, String anAuthorIntroduce, String anAuthorImage, String staffName, String stave, String notation, String midi, String xml, String mp3, String accompany) {
        this.courseId = courseId;
        this.grade = grade;
        this.term = term;
        this.unit = unit;
        this.unitName = unitName;
        this.courseName = courseName;
        this.courseImage = courseImage;
        this.courseIntroduce = courseIntroduce;
        this.wordAuthorName = wordAuthorName;
        this.wordAuthorIntroduce = wordAuthorIntroduce;
        this.wordAuthorImage = wordAuthorImage;
        this.anAuthorName = anAuthorName;
        this.anAuthorIntroduce = anAuthorIntroduce;
        this.anAuthorImage = anAuthorImage;
        this.staffName = staffName;
        this.stave = stave;
        this.notation = notation;
        this.midi = midi;
        this.xml = xml;
        this.mp3 = mp3;
        this.accompany = accompany;
    }
}
