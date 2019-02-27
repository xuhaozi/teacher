package com.example.admin.musicclassroom.entity;


public class ExerciseVo {
    private String exercise;
    private String exerciseMp3;
    private String exerciseImage;

    @Override
    public String toString() {
        return "ExerciseVo{" +
                "exercise='" + exercise + '\'' +
                ", exerciseMp3='" + exerciseMp3 + '\'' +
                ", exerciseImage='" + exerciseImage + '\'' +
                '}';
    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public String getExerciseMp3() {
        return exerciseMp3;
    }

    public void setExerciseMp3(String exerciseMp3) {
        this.exerciseMp3 = exerciseMp3;
    }

    public String getExerciseImage() {
        return exerciseImage;
    }

    public void setExerciseImage(String exerciseImage) {
        this.exerciseImage = exerciseImage;
    }

    public ExerciseVo(String exercise, String exerciseMp3, String exerciseImage) {
        this.exercise = exercise;
        this.exerciseMp3 = exerciseMp3;
        this.exerciseImage = exerciseImage;
    }
}
