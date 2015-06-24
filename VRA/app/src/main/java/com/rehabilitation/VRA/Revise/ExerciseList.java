// Copyright 2015 Darren McNeely. All Rights Reserved.

package com.rehabilitation.VRA.Revise;

import java.util.List;

public class ExerciseList {
    private String ExTitle, ExTime;
    private List<String> ExList;
    private List<Integer> ExComplete;

    public ExerciseList(String ExTitle, String ExTime ,List<String> ExList, List<Integer> ExComplete){
        this.ExTitle = ExTitle;
        this.ExTime = ExTime;
        this.ExList = ExList;
        this.ExComplete = ExComplete;
    }

    public String getExTitle(){
        return ExTitle;
    }
    public String getExTime(){
        return ExTime;
    }
    public List<String> getExList(){
        return ExList;
    }
    public List<Integer> getExComplete(){
        return ExComplete;
    }
}