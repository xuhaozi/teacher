package com.example.admin.musicclassroom.musicentity;

import android.graphics.Bitmap;

import java.util.List;

/**
 * 用于进行逻辑操作的乐谱类，保存当前乐谱相关的信息，乐谱的长宽，小节的数量，向后重复的小节，向前重复的小节
 * 重复的次数，当前的播放状态
 */

public class Score {

	public static final int STATE_PLAY = 0;
	public static final int STATE_PAUSE = 1;
	public static final int STATE_STOP = 2;

	private float scoreWidth;
	private float scoreHeight;
	private String scoreName;
	private String authorName;
	private int measureCount;
	private String instrumentType;
	private Measure startMeasure;
	private Measure endMeasure;
	private Measure forwardMeasure;
	private Measure backMeasure;
	private int returnTime;
	private int playState;
	private Bitmap content;
	private List<Measure> allMeasure;

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public List<Measure> getAllMeasure() {
		return allMeasure;
	}

	public void setAllMeasure(List<Measure> allMeasure) {
		this.allMeasure = allMeasure;
	}

	public Score() {
	}

	public Score(float scoreWidth, float scoreHeight, String scoreName, String authorName, Bitmap content) {
		this.scoreWidth = scoreWidth;
		this.scoreHeight = scoreHeight;
		this.scoreName = scoreName;
		this.authorName = authorName;
		this.content = content;
	}

	public float getScoreWidth() {
		return scoreWidth;
	}

	public void setScoreWidth(float scoreWidth) {
		this.scoreWidth = scoreWidth;
	}

	public float getScoreHeight() {
		return scoreHeight;
	}

	public void setScoreHeight(float scoreHeight) {
		this.scoreHeight = scoreHeight;
	}

	public String getScoreName() {
		return scoreName;
	}

	public void setScoreName(String scoreName) {
		this.scoreName = scoreName;
	}

	public String getAutherName() {
		return authorName;
	}

	public void setAutherName(String authorName) {
		this.authorName = authorName;
	}

	public int getMeasureCount() {
		return measureCount;
	}

	public void setMeasureCount(int measureCount) {
		this.measureCount = measureCount;
	}

	public String getInstrumentType() {
		return instrumentType;
	}

	public void setInstrumentType(String instrumentType) {
		this.instrumentType = instrumentType;
	}

	public Measure getStartMeasure() {
		return startMeasure;
	}

	public void setStartMeasure(Measure startMeasure) {
		this.startMeasure = startMeasure;
	}

	public Measure getEndMeasure() {
		return endMeasure;
	}

	public void setEndMeasure(Measure endMeasure) {
		this.endMeasure = endMeasure;
	}

	public Measure getForwardMeasure() {
		return forwardMeasure;
	}

	public void setForwardMeasure(Measure forwardMeasure) {
		this.forwardMeasure = forwardMeasure;
	}

	public Measure getBackMeasure() {
		return backMeasure;
	}

	public void setBackMeasure(Measure backMeasure) {
		this.backMeasure = backMeasure;
	}

	public int getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(int returnTime) {
		this.returnTime = returnTime;
	}

	public int getPlayState() {
		return playState;
	}

	public void setPlayState(int playState) {
		this.playState = playState;
	}

	public Bitmap getContent() {
		return content;
	}

	public void setContent(Bitmap content) {
		this.content = content;
	}
}
