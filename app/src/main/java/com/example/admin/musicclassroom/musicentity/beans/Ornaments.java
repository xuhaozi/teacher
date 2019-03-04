package com.example.admin.musicclassroom.musicentity.beans;

/**
 * Created by Administrator on 2017/8/1.
 */

public class Ornaments {
    private boolean InvertedMordent;
    private boolean Mordent;
    private boolean InvertedTurn;
    private boolean TrillMark;
    private boolean turn;

    public boolean isTurn() {
        return turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    public boolean isInvertedMordent() {
        return InvertedMordent;
    }

    public void setInvertedMordent(boolean invertedMordent) {
        InvertedMordent = invertedMordent;
    }

    public boolean isMordent() {
        return Mordent;
    }

    public void setMordent(boolean mordent) {
        Mordent = mordent;
    }

    public boolean isInvertedTurn() {
        return InvertedTurn;
    }

    public void setInvertedTurn(boolean invertedTurn) {
        InvertedTurn = invertedTurn;
    }

    public boolean isTrillMark() {
        return TrillMark;
    }

    public void setTrillMark(boolean trillMark) {
        TrillMark = trillMark;
    }


    @Override
    public String toString() {
        return "Ornaments{" +
                "InvertedMordent=" + InvertedMordent +
                ", Mordent=" + Mordent +
                ", InvertedTurn=" + InvertedTurn +
                ", TrillMark=" + TrillMark +
                ", turn=" + turn +
                '}';
    }
}
