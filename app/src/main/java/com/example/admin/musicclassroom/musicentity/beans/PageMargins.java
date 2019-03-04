package com.example.admin.musicclassroom.musicentity.beans;

/**
 * Created by Administrator on 2017/6/28.
 */

public class PageMargins {
    private String type;
    private double LeftMargin;
    private double RightMargin;
    private double TopMargin;
    private double BottomMargin;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getLeftMargin() {
        return LeftMargin;
    }

    public void setLeftMargin(double leftMargin) {
        LeftMargin = leftMargin;
    }

    public double getRightMargin() {
        return RightMargin;
    }

    public void setRightMargin(double rightMargin) {
        RightMargin = rightMargin;
    }

    public double getTopMargin() {
        return TopMargin;
    }

    public void setTopMargin(double topMargin) {
        TopMargin = topMargin;
    }

    public double getBottomMargin() {
        return BottomMargin;
    }

    public void setBottomMargin(double bottomMargin) {
        BottomMargin = bottomMargin;
    }

    @Override
    public String toString() {
        return "PageMargins{" +
                "type='" + type + '\'' +
                ", LeftMargin=" + LeftMargin +
                ", RightMargin=" + RightMargin +
                ", TopMargin=" + TopMargin +
                ", BottomMargin=" + BottomMargin +
                '}';
    }
}
