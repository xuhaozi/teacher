package com.example.admin.musicclassroom.musicentity.beans;

/**
 * Created by CY on 2017/7/23.
 */

public class Direction {
    private DirectionType directionType;
    private Sound sound;

    private String placement;


    public Sound getSound() {
        return sound;
    }

    public void setSound(Sound sound) {
        this.sound = sound;
    }

    public String getPlacement() {
        return placement;
    }

    public void setPlacement(String placement) {
        this.placement = placement;
    }
    public DirectionType getDirectionType() {
        return directionType;
    }

    public void setDirectionType(DirectionType directionType) {
        this.directionType = directionType;
    }


    @Override
    public String toString() {
        return "Direction{" +
                "directionType=" + directionType +
                ", sound=" + sound +
                ", placement='" + placement + '\'' +
                '}';
    }
}
