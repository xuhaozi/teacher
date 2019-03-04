package com.example.admin.musicclassroom.musicentity.beans;

/**
 * Created by Administrator on 2017/6/30.
 */

public class Print {
    private SystemLayout systemLayout;

    public SystemLayout getSystemLayout() {
        return systemLayout;
    }

    public void setSystemLayout(SystemLayout systemLayout) {
        this.systemLayout = systemLayout;
    }

    @Override
    public String toString() {
        return "Print{" +
                "systemLayout=" + systemLayout +
                '}';
    }
}
