package com.example.admin.musicclassroom.musicentity.beans;


import java.util.List;

/**
 * Created by Administrator on 2017/6/28.
 */

public class ScorePartWise {
    private Work work;
    private Identification identification;
    private Defaults defaults;
    private List<Credit> creditList;
    private PartList partList;
    private Part part;


    public List<Credit> getCreditList() {
        return creditList;
    }

    public void setCreditList(List<Credit> creditList) {
        this.creditList = creditList;
    }

    public Work getWork() {
        return work;
    }

    public void setWork(Work work) {
        this.work = work;
    }

    public Identification getIdentification() {
        return identification;
    }

    public void setIdentification(Identification identification) {
        this.identification = identification;
    }

    public Defaults getDefaults() {
        return defaults;
    }

    public void setDefaults(Defaults defaults) {
        this.defaults = defaults;
    }



    public PartList getPartList() {
        return partList;
    }

    public void setPartList(PartList partList) {
        this.partList = partList;
    }

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    @Override
    public String toString() {
        return "ScorePartWise{" +
                "work=" + work +
                ", identification=" + identification +
                ", defaults=" + defaults +
                ", creditList=" + creditList +
                ", partList=" + partList +
                ", part=" + part +
                '}';
    }
}
