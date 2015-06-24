package com.rehabilitation.VRA.Revise;

import android.content.Context;
import android.database.Cursor;

import com.rehabilitation.VRA.Database.Database_Manager;
import com.rehabilitation.VRA.Exercise.Exercise_Type;
import com.rehabilitation.VRA.Exercise.Exercise_Type1;
import com.rehabilitation.VRA.Exercise.Exercise_Type2;
import com.rehabilitation.VRA.Exercise.Exercise_description;
import com.rehabilitation.VRA.Exercise.Exercise_properties;

import java.util.ArrayList;
import java.util.List;


public class ExpandableListDataPump {

    static List<Exercise_properties> exerc;
    static List<Exercise_description> exdesc;
    static Database_Manager db;
    static Cursor cur_desc, cur_list, cur_Results, cur_times;


    public ExpandableListDataPump(Context context){
        db = new Database_Manager(context);
    }

    public ArrayList<ExerciseList>  getData(int week, int day) {

        exerc = new ArrayList<>();    // Holds a list of exercises
        exdesc = new ArrayList<>();    // Holds a list of exercises descriptions
        db.open();

        cur_desc = db.getExerciseDescriptions();
        cur_desc.moveToPosition(0);
        do{
            exdesc.add(new Exercise_description(cur_desc.getString(0), cur_desc.getString(1), cur_desc.getString(2)));
        }while (cur_desc.moveToNext());

        cur_list = db.getExerciseListforDay(week, day);
        cur_list.moveToPosition(0);
        do{
            Exercise_Type Type;
            if (cur_list.getString(4).equals("Exercise_Type1")){
                Type = new Exercise_Type2();
            }
            else{
                Type = new Exercise_Type1();
            }
            // Week, Day, TimeOfDay, exerciseNum, Type, Duration, Gifposition, Speed;
            exerc.add(new Exercise_properties(cur_list.getInt(0), cur_list.getInt(1), cur_list.getString(2), cur_list.getInt(3), Type, cur_list.getInt(5), cur_list.getInt(6), cur_list.getInt(7)));
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

        List<String> ExerciseTimes = new ArrayList<>();


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


        cur_Results = db.getExericseResultsforDay(db.isUserLoggedIn(), week, day);
        cur_Results.moveToFirst();
        //Log.d("Got this far", "" + cur_Results.getString(3));

        do{
            if (cur_Results.getString(2).equals("Morning Exercise#1"))
            {
                Morn1Complete.add(cur_Results.getInt(5));

            }
            if (cur_Results.getString(2).equals("Morning Exercise#2"))
            {
                Morn2Complete.add(cur_Results.getInt(5)) ;
            }

            if (cur_Results.getString(2).equals("Afternoon Exercise#1"))
            {
                After1Complete.add(cur_Results.getInt(5)) ;
            }

            if (cur_Results.getString(2).equals("Afternoon Exercise#2"))
            {
                After2Complete.add(cur_Results.getInt(5)) ;
            }

            if (cur_Results.getString(2).equals("Evening Exercise"))
            {
                EveningComplete.add(cur_Results.getInt(5)) ;
            }

        }while (cur_Results.moveToNext());



        cur_times = db.getExerciseTimes();
        cur_times.moveToFirst();

        do{
            if (cur_times.getString(0).equals("Morning Exercise#1")) {
                ExerciseTimes.add(calculate_time(cur_times.getInt(1), cur_times.getInt(2)));
            }

            else if (cur_times.getString(0).equals("Morning Exercise#2")){
                ExerciseTimes.add(calculate_time(cur_times.getInt(1), cur_times.getInt(2)));
            }

            else if (cur_times.getString(0).equals("Afternoon Exercise#1")){
                ExerciseTimes.add(calculate_time(cur_times.getInt(1), cur_times.getInt(2)));
            }

            else if (cur_times.getString(0).equals("Afternoon Exercise#2")){
                ExerciseTimes.add(calculate_time(cur_times.getInt(1), cur_times.getInt(2)));
            }

            else if (cur_times.getString(0).equals("Evening Exercise")){
                ExerciseTimes.add(calculate_time(cur_times.getInt(1), cur_times.getInt(2)));
            }
        }while (cur_times.moveToNext());

        fetch.add(new ExerciseList("Morning Exercise#1", ExerciseTimes.get(0) , Morn1, Morn1Complete));
        fetch.add(new ExerciseList("Morning Exercise#2", ExerciseTimes.get(1) ,Morn2, Morn2Complete));
        fetch.add(new ExerciseList("Afternoon Exercise#1", ExerciseTimes.get(2) , After1, After1Complete));
        fetch.add(new ExerciseList("Afternoon Exercise#2", ExerciseTimes.get(3) ,After2, After2Complete));
        fetch.add(new ExerciseList("Evening Exercise", ExerciseTimes.get(4) ,Evening, EveningComplete));

        db.close();

        cur_desc.close();
        cur_list.close();
        cur_Results.close();
        cur_times.close();
        return fetch;
    }

    private String calculate_time(int hours, int minutes){

        int finish_hour = hours + 2;
        if (finish_hour > 12)
        {
            finish_hour -= 12;
        }

        return hours + ":" + String.format("%02d", minutes)  + " - " + finish_hour + ":" + String.format("%02d", minutes);
    }
}
