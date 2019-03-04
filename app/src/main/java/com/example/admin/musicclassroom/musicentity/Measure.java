package com.example.admin.musicclassroom.musicentity;

import java.util.List;

/**
 * 小节实例，存储小节相关的属性，包括所有的音符，节拍，宽高，反复记号的形式（向前反复或者向后反复）
 */

public class Measure {

	public static final int MODE_BACK=0;
	public static final int MODE_FORWARD=1;
	public static final int MODE_FROM_START=3;

	private List<Note> noteList;
	private Note startNote;
	private Note endNote;
	private int key;
	private float measureWidth;
	private float measureHeight;
	private float defaultX;
	private float defaultY;
	private int beats;
	private int beatsType;
	private Note notePlayedNow;
	private int backMode=MODE_FROM_START;

	public Measure() {
	}

	public Measure(List<Note> noteList, float measureWidth, float measureHeight, float defaultX, float defaultY, int beats, int beatsType) {
		this.noteList = noteList;
		this.measureWidth = measureWidth;
		this.measureHeight = measureHeight;
		this.defaultX = defaultX;
		this.defaultY = defaultY;
		this.beats = beats;
		this.beatsType = beatsType;
	}

	public float getDefaultY() {
		return defaultY;
	}

	public void setDefaultY(float defaultY) {
		this.defaultY = defaultY;
	}

	public int getBeats() {
		return beats;
	}

	public void setBeats(int beats) {
		this.beats = beats;
	}

	public int getBeatsType() {
		return beatsType;
	}

	public void setBeatsType(int beatsType) {
		this.beatsType = beatsType;
	}

	public Note getNotePlayedNow() {
		return notePlayedNow;
	}

	public void setNotePlayedNow(Note notePlayedNow) {
		this.notePlayedNow = notePlayedNow;
	}

	public int getBackMode() {
		return backMode;
	}

	public void setBackMode(int backMode) {
		this.backMode = backMode;
	}

	public List<Note> getNoteList() {
		return noteList;
	}

	public void setNoteList(List<Note> noteList) {
		this.noteList = noteList;
	}

	public Note getStartNote() {
		return startNote;
	}

	public void setStartNote(Note startNote) {
		this.startNote = startNote;
	}

	public Note getEndNote() {
		return endNote;
	}

	public void setEndNote(Note endNote) {
		this.endNote = endNote;
	}

	public float getMeasureWidth() {
		return measureWidth;
	}

	public void setMeasureWidth(float measureWidth) {
		this.measureWidth = measureWidth;
	}

	public float getMeasureHeight() {
		return measureHeight;
	}

	public void setMeasureHeight(float measureHeight) {
		this.measureHeight = measureHeight;
	}

	public float getDefaultX() {
		return defaultX;
	}

	public void setDefaultX(float defaultX) {
		this.defaultX = defaultX;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}
}
