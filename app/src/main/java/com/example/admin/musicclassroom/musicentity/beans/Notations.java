package com.example.admin.musicclassroom.musicentity.beans;

/**
 * Created by CY on 2017/7/23.
 */

public class Notations {
    private Fermata fermata;
    private Articulations articulations;
    private Ornaments ornaments;


    public Articulations getArticulations() {
        return articulations;
    }

    public void setArticulations(Articulations articulations) {
        this.articulations = articulations;
    }

    public Ornaments getOrnaments() {
        return ornaments;
    }

    public void setOrnaments(Ornaments ornaments) {
        this.ornaments = ornaments;
    }

    public Fermata getFermata() {
        return fermata;
    }

    public void setFermata(Fermata fermata) {
        this.fermata = fermata;
    }

    @Override
    public String toString() {
        return "Notations{" +
                "fermata=" + fermata +
                ", articulations=" + articulations +
                ", ornaments=" + ornaments +
                '}';
    }
}
