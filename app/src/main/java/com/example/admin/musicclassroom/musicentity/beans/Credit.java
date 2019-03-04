package com.example.admin.musicclassroom.musicentity.beans;

/**
 * Created by Administrator on 2017/6/28.
 */

public class Credit {
    private CreditWords creditWords;

    public CreditWords getCreditWords() {
        return creditWords;
    }

    public void setCreditWords(CreditWords creditWords) {
        this.creditWords = creditWords;
    }

    @Override
    public String toString() {
        return "Credit{" +
                "creditWords=" + creditWords +
                '}';
    }
}
