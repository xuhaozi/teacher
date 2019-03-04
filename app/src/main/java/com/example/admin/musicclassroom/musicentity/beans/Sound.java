package com.example.admin.musicclassroom.musicentity.beans;

/**
 * Created by Administrator on 2017/7/31.
 */

public class Sound {
    private String dynamics;
    private String fine;
    private String segno;
    private String coda;
    private String dacapo;
    private String tocoda;
    private String dalsegno;


    public String getSegno() {
        return segno;
    }

    public void setSegno(String segno) {
        this.segno = segno;
    }

    public String getCoda() {
        return coda;
    }

    public void setCoda(String coda) {
        this.coda = coda;
    }

    public String getDacapo() {
        return dacapo;
    }

    public void setDacapo(String dacapo) {
        this.dacapo = dacapo;
    }

    public String getTocoda() {
        return tocoda;
    }

    public void setTocoda(String tocoda) {
        this.tocoda = tocoda;
    }

    public String getDalsegno() {
        return dalsegno;
    }

    public void setDalsegno(String dalsegno) {
        this.dalsegno = dalsegno;
    }

    public String getFine() {
        return fine;
    }

    public void setFine(String fine) {
        this.fine = fine;
    }

    public String getDynamics() {
        return dynamics;
    }

    public void setDynamics(String dynamics) {
        this.dynamics = dynamics;
    }

    @Override
    public String toString() {
        return "Sound{" +
                "dynamics='" + dynamics + '\'' +
                ", fine='" + fine + '\'' +
                ", segno='" + segno + '\'' +
                ", coda='" + coda + '\'' +
                ", dacapo='" + dacapo + '\'' +
                ", tocoda='" + tocoda + '\'' +
                ", dalsegno='" + dalsegno + '\'' +
                '}';
    }
}
