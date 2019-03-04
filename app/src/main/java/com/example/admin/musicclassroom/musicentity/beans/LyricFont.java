package com.example.admin.musicclassroom.musicentity.beans;

/**
 * Created by Administrator on 2017/6/28.
 */

public class LyricFont {
    private String FontFamily;
    private int FontSize;

    public String getFontFamily() {
        return FontFamily;
    }

    public void setFontFamily(String fontFamily) {
        FontFamily = fontFamily;
    }

    public int getFontSize() {
        return FontSize;
    }

    public void setFontSize(int fontSize) {
        FontSize = fontSize;
    }

    @Override
    public String toString() {
        return "WorkFont{" +
                "FontFamily='" + FontFamily + '\'' +
                ", FontSize=" + FontSize +
                '}';
    }
}
