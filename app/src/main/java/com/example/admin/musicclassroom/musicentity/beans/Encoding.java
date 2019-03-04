package com.example.admin.musicclassroom.musicentity.beans;

import java.util.List;

/**
 * Created by Administrator on 2017/6/28.
 */

public class Encoding {
    private String software;
    private String encoding_date;
    private List<Supports> supportsList;

    public String getSoftware() {
        return software;
    }

    public void setSoftware(String software) {
        this.software = software;
    }

    public String getEncoding_date() {
        return encoding_date;
    }

    public void setEncoding_date(String encoding_date) {
        this.encoding_date = encoding_date;
    }

    public List<Supports> getSupportsList() {
        return supportsList;
    }

    public void setSupportsList(List<Supports> supportsList) {
        this.supportsList = supportsList;
    }

    @Override
    public String toString() {
        return "Encoding{" +
                "software='" + software + '\'' +
                ", encoding_date='" + encoding_date + '\'' +
                ", supportsList=" + supportsList +
                '}';
    }
}
