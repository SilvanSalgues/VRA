package com.example.darren.new_design;

import java.util.ArrayList;
import java.util.List;

public class ExpandableListDataPump {
    public static ArrayList<ExerciseList>  getData() {
        ArrayList<ExerciseList> fetch = new ArrayList<>();

        List<String> Morn1 = new ArrayList<>();
        Morn1.add("1. Head Rotation");
        Morn1.add("2. efficitur aliquam");

        List<String> Morn2 = new ArrayList<>();
        Morn2.add("1. Head Rotation");
        Morn2.add("2. efficitur aliquam");
        Morn2.add("3. Lorem ipsum dol");

        List<String> Even1 = new ArrayList<>();
        Even1.add("2. efficitur aliquam");
        Even1.add("3. Lorem ipsum dol");

        List<String> Even2 = new ArrayList<>();
        Even2.add("1. Head Rotation");
        Even2.add("2. efficitur aliquam");
        Even2.add("3. Lorem ipsum dol");
        Even2.add("4. Etiam est dui");
        Even2.add("5. amet magna");
        Even2.add("6. Nunc viverra");
        Even2.add("7. hendrerit sapien");
        Even2.add("8. vitae placerat");

        List<String> Afternoon = new ArrayList<>();
        Afternoon.add("1. Head Rotation");
        Afternoon.add("2. efficitur aliquam");

        fetch.add(new ExerciseList("Morning Exercise #1", Morn1));
        fetch.add(new ExerciseList("Morning Exercise #2", Morn2));
        fetch.add(new ExerciseList("Afternoon Exercise #1", Even1));
        fetch.add(new ExerciseList("Afternoon Exercise #2", Even2));
        fetch.add(new ExerciseList("Evening Exercises", Afternoon));

        return fetch;
    }
}
