package com.example.darren.new_design;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;


public class ExpandableListDataPump {

    static List<Exercise_properties> exerc;
    static List<Exercise_description> exdesc;
    static Database_Manager db;
    static Cursor cur_desc, cur_list;


    public ExpandableListDataPump(Context context){
        db = new Database_Manager(context);
    }

    public static ArrayList<ExerciseList>  getData(int day) {

        exerc = new ArrayList<>();    // Holds a list of exercises
        exdesc = new ArrayList<>();    // Holds a list of exercises descriptions
        db.open();

        cur_desc = db.getExerciseDescriptions();
        cur_desc.moveToPosition(0);
        do{
            exdesc.add(new Exercise_description(cur_desc.getString(1), cur_desc.getString(2), cur_desc.getString(3)));
        }while (cur_desc.moveToNext());

        cur_list = db.getExerciseList(day);
        cur_list.moveToPosition(0);
        do{
            Exercise_Type Type;
            if (cur_list.getString(5).equals("Exercise_Type1")){
                Type = new Exercise_Type2();
            }
            else{
                Type = new Exercise_Type1();
            }
            exerc.add(new Exercise_properties(cur_list.getInt(1), cur_list.getInt(2), cur_list.getString(3), cur_list.getInt(4), Type, cur_list.getInt(6), cur_list.getInt(7), cur_list.getInt(8)));
        }while (cur_list.moveToNext());

        db.close();

        ArrayList<ExerciseList> fetch = new ArrayList<>();

        List<String> Morn1 = new ArrayList<>();
        List<String> Morn2 = new ArrayList<>();
        List<String> After1 = new ArrayList<>();
        List<String> After2 = new ArrayList<>();
        List<String> Evening = new ArrayList<>();

        for (Exercise_properties ex : exerc )
        {
            if (ex.getTimeOfDay().equals("Morning Exercise#1"))
            {
                Morn1.add((ex.getexerciseNum() + 1) +". " + exdesc.get(ex.getexerciseNum()).getName()) ;
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

        fetch.add(new ExerciseList("Morning Exercise #1", Morn1));
        fetch.add(new ExerciseList("Morning Exercise #2", Morn2));
        fetch.add(new ExerciseList("Afternoon Exercise #1", After1));
        fetch.add(new ExerciseList("Afternoon Exercise #2", After2));
        fetch.add(new ExerciseList("Evening Exercises", Evening));

        cur_desc.close();
        cur_list.close();
        return fetch;
    }
}
