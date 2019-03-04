package com.example.admin.musicclassroom.musicentity.beans;

/**
 * Created by Administrator on 2017/6/28.
 */

public class CreditWords {
    private double defaultX;
    private double defaultY;
    private String justify;
    private String valign;
    private int FontSize;
    private String Context;


    public double getDefaultX() {
        return defaultX;
    }

    public void setDefaultX(double defaultX) {
        this.defaultX = defaultX;
    }

    public double getDefaultY() {
        return defaultY;
    }

    public void setDefaultY(double defaultY) {
        this.defaultY = defaultY;
    }

    public String getJustify() {
        return justify;
    }

    public void setJustify(String justify) {
        this.justify = justify;
    }

    public String getValign() {
        return valign;
    }

    public void setValign(String valign) {
        this.valign = valign;
    }

    public int getFontSize() {
        return FontSize;
    }

    public void setFontSize(int fontSize) {
        FontSize = fontSize;
    }

    public String getContext() {
        return Context;
    }

    public void setContext(String context) {
        Context = context;
    }

    @Override
    public String toString() {
        return "CreditWords{" +
                "defaultX=" + defaultX +
                ", defaultY=" + defaultY +
                ", justify='" + justify + '\'' +
                ", valign='" + valign + '\'' +
                ", FontSize=" + FontSize +
                ", Context='" + Context + '\'' +
                '}';
    }
}
