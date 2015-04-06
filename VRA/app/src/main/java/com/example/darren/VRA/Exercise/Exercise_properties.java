// Copyright 2015 Darren McNeely. All Rights Reserved.

package com.example.darren.VRA.Exercise;

public class Exercise_properties
{
    private int Week;
    private int Day;
    private String TimeOfDay;
    private int exerciseNum;
    private Exercise_Type Type;
    private int Duration;
    private int Gifposition;
    private float Speed;

    public Exercise_properties(int Week, int Day, String TimeOfDay, int exerciseNum, Exercise_Type type, int duration, int Gifposition, float speed)
    {
        this.Week = Week;
        this.Day = Day;
        this.TimeOfDay = TimeOfDay;
        this.exerciseNum = exerciseNum;
        this.Type = type;
        this.Duration = duration;
        this.Gifposition = Gifposition;
        this.Speed = speed;
    }

    public int getWeek()
    {
        return Week;
    }
    public int getDay()
    {
        return Day;
    }
    public String getTimeOfDay(){ return TimeOfDay;}
    public int getexerciseNum()
    {
        return exerciseNum;
    }
    public Exercise_Type getType()
    {
        return Type;
    }
    public int getDuration()
    {
        return Duration;
    }
    public int getGifposition()
    {
        return Gifposition;
    }
    public float getSpeed()
    {
        return Speed;
    }
}
