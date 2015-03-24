// Copyright 2015 Darren McNeely. All Rights Reserved.

package com.example.darren.VRA.Revise;

import java.util.List;

public class ExerciseList {
    private String ExTitle;
    private List<String> ExList;
    private List<Integer> ExComplete;

    public ExerciseList(String st1, List<String> st2, List<Integer> st3){
        ExTitle = st1;
        ExList = st2;
        ExComplete = st3;
    }

    public String getExTitle(){
        return ExTitle;
    }

    public List<String> getExList(){
        return ExList;
    }

    public List<Integer> getExComplete(){
        return ExComplete;
    }
}