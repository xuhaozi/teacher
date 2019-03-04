package com.example.admin.musicclassroom.musicentity.beans;

/**
 * Created by Administrator on 2017/6/28.
 */

public class Defaults {
   private Scaling scaling;
    private PageLayout pageLayout;
    private WorkFont workFont;
    private LyricFont lyricFont;

    public Scaling getScaling() {
        return scaling;
    }

    public void setScaling(Scaling scaling) {
        this.scaling = scaling;
    }

    public PageLayout getPageLayout() {
        return pageLayout;
    }

    public void setPageLayout(PageLayout pageLayout) {
        this.pageLayout = pageLayout;
    }

    public WorkFont getWorkFont() {
        return workFont;
    }

    public void setWorkFont(WorkFont workFont) {
        this.workFont = workFont;
    }

    public LyricFont getLyricFont() {
        return lyricFont;
    }

    public void setLyricFont(LyricFont lyricFont) {
        this.lyricFont = lyricFont;
    }


    @Override
    public String toString() {
        return "Defaults{" +
                "scaling=" + scaling +
                ", pageLayout=" + pageLayout +
                ", workFont=" + workFont +
                ", lyricFont=" + lyricFont +
                '}';
    }
}
