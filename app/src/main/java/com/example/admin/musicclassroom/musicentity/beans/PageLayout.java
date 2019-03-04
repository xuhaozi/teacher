package com.example.admin.musicclassroom.musicentity.beans;

import java.util.List;

/**
 * Created by Administrator on 2017/6/28.
 */

public class PageLayout {
    private double pageHeight;
    private double pageWidth;
    private List<PageMargins> pageMarginsList;

    public double getPageHeight() {
        return pageHeight;
    }

    public void setPageHeight(double pageHeight) {
        this.pageHeight = pageHeight;
    }

    public double getPageWidth() {
        return pageWidth;
    }

    public void setPageWidth(double pageWidth) {
        this.pageWidth = pageWidth;
    }

    public List<PageMargins> getPageMarginsList() {
        return pageMarginsList;
    }

    public void setPageMarginsList(List<PageMargins> pageMarginsList) {
        this.pageMarginsList = pageMarginsList;
    }

    @Override
    public String toString() {
        return "PageLayout{" +
                "pageHeight=" + pageHeight +
                ", pageWidth=" + pageWidth +
                ", pageMarginsList=" + pageMarginsList +
                '}';
    }
}
