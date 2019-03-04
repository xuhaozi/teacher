package com.example.admin.musicclassroom.musicentity.beans;

/**
 * Created by Administrator on 2017/6/28.
 */

public class Identification {
    private Encoding encoding;

    public Encoding getEncoding() {
        return encoding;
    }

    public void setEncoding(Encoding encoding) {
        this.encoding = encoding;
    }

    @Override
    public String toString() {
        return "Identification{" +
                "encoding=" + encoding +
                '}';
    }
}
