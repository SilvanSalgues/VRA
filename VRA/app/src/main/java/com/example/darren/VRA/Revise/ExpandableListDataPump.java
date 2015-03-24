// Copyright 2015 Darren McNeely. All Rights Reserved.

package com.example.darren.VRA.Revise;

import android.content.Context;
import android.database.Cursor;

import com.example.darren.VRA.Database.Database_Manager;
import com.example.darren.VRA.Exercise.Exercise_Type;
import com.example.darren.VRA.Exercise.Exercise_Type1;
import com.example.darren.VRA.Exercise.Exercise_Type2;
import com.example.darren.VRA.Exercise.Exercise_description;
import com.example.darren.VRA.Exercise.Exercise_properties;

import java.util.ArrayList;
import java.util.List;


public class ExpandableListDataPump {

    static List<Exercise_properties> exerc;
    static List<Exercise_description> exdesc;
    static Database_Manager db;
    static Cursor cur_desc, cur_list, cur_Results;;


    public ExpandableListDataPump(Context context){
        db = new Database_Manager(context);
    }

    public ArrayList<ExerciseList>  getData(int day) {

        exerc = new ArrayList<>();    // Holds a list of exercises
        exdesc = new ArrayList<>();    // Holds a list of exercises descriptions
        db.open();

        cur_desc = db.getExerciseDescriptions();
        cur_desc.moveToPosition(0);
        do{
            exdesc.add(new Exercise_description(cur_desc.getString(1), cur_desc.getString(2), cur_desc.getString(3)));
        }while (cur_desc.moveToNext());

        cur_list = db.getExerciseListforDay(day);
        cur_list.moveToPosition(0);
        do{
            Exercise_Type Type;
            if (cur_list.getString(5).equals("Exercise_Type1")){
                Type = new Exercise_Type2();
            }
            else{
                Type = new Exercise_Type1();
            }
            // Week, Day, TimeOfDay, exerciseNum, Type, Duration, Gifposition, Speed;
            exerc.add(new Exercise_properties(cur_list.getInt(1), cur_list.getInt(2), cur_list.getString(3), cur_list.getInt(4), Type, cur_list.getInt(6), cur_list.getInt(7), cur_list.getInt(8)));
        }while (cur_list.moveToNext());



        ArrayList<ExerciseList> fetch = new ArrayList<>();

        List<String> Morn1 = new ArrayList<>();
        List<String> Morn2 = new ArrayList<>();
        List<String> After1 = new ArrayList<>();
        List<String> After2 = new ArrayList<>();
        List<String> Evening = new ArrayList<>();

        List<Integer> Morn1Complete = new ArrayList<>();
        List<Integer> Morn2Complete = new ArrayList<>();
        List<Integer> After1Complete = new ArrayList<>();
        List<Integer> After2Complete = new ArrayList<>();
        List<Integer> EveningComplete = new ArrayList<>();


        for (Exercise_properties ex : exerc )
        {
            if (ex.getTimeOfDay().equals("Morning Exercise#1"))
            {
                Morn1.add((ex.getexerciseNum() + 1) +". " + exdesc.get(ex.getexerciseNum()).getName());
            }

            if (ex.getTimeOfDay().equals("Morning Exercise#2"))
            {
                Morn2.add((ex.getexerciseNum() + 1) +". " + exdesc.get(ex.getexerciseNum()).getName()) ;
            }

            if (ex.getTimeOfDay().equals("Afternoon Exercise#1"))
            {
                After1.add((ex.getexerciseNum() + 1) +". " + exdesc.get(ex.getexerciseNum()).getName()) ;
            }

            if (ex.getTimeOfDay().equals("Afternoon Exercise#2"))
            {
                After2.add((ex.getexerciseNum() + 1) +". " + exdesc.get(ex.getexerciseNum()).getName()) ;
            }

            if (ex.getTimeOfDay().equals("Evening Exercise"))
            {
                Evening.add((ex.getexerciseNum() + 1) +". " + exdesc.get(ex.getexerciseNum()).getName()) ;
            }
        }


        cur_Results = db.getExericseResultsforDay(day);
        cur_Results.moveToFirst();
        //Log.d("Got this far", "" + cur_Results.getString(3));

        do{
            if (cur_Results.getString(3).equals("Morning Exercise#1"))
            {
                Morn1Complete.add(cur_Results.getInt(6));

            }
            if (cur_Results.getString(3).equals("Morning Exercise#2"))
            {
                Morn2Complete.add(cur_Results.getInt(6)) ;
            }

            if (cur_Results.getString(3).equals("Afternoon Exercise#1"))
            {
                After1Complete.add(cur_Results.getInt(6)) ;
            }

            if (cur_Results.getString(3).equals("Afternoon Exercise#2"))
            {
                After2Complete.add(cur_Results.getInt(6)) ;
            }

            if (cur_Results.getString(3).equals("Evening Exercise"))
            {
                EveningComplete.add(cur_Results.getInt(6)) ;
            }

        }while (cur_Results.moveToNext());

        db.close();

        fetch.add(new ExerciseList("Morning Exercise#1", Morn1, Morn1Complete));
        fetch.add(new ExerciseList("Morning Exercise#2", Morn2, Morn2Complete));
        fetch.add(new ExerciseList("Afternoon Exercise#1", After1, After1Complete));
        fetch.add(new ExerciseList("Afternoon Exercise#2", After2, After2Complete));
        fetch.add(new ExerciseList("Evening Exercise", Evening, EveningComplete));

        cur_desc.close();
        cur_list.close();
        cur_Results.close();
        return fetch;
    }
}
