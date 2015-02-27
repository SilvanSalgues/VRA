package com.example.darren.new_design;

import java.util.List;

public class ExerciseList {
    private String ExTitle;
    private List<String> ExList;

    public ExerciseList(String st1, List<String> st2){
        ExTitle = st1;
        ExList = st2;
    }

    public String getExTitle(){
        return ExTitle;
    }

    public List<String> getExList(){
        return ExList;
    }
}