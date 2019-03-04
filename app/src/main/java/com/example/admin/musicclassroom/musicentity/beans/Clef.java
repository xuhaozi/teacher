package com.example.admin.musicclassroom.musicentity.beans;

/**
 * Created by Administrator on 2017/6/30.
 */

public class Clef {
    private String Sign;
    private int line;

    public String getSign() {
        return Sign;
    }

    public void setSign(String sign) {
        Sign = sign;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }


    @Override
    public String toString() {
        return "Clef{" +
                "Sign='" + Sign + '\'' +
                ", line=" + line +
                '}';
    }
}
