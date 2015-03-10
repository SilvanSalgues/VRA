package com.example.darren.new_design;

public class Exercise_properties
{

    int id;
    int Week;
    int Day;
    String TimeOfDay;
    int exerciseNum;
    Exercise_Type Type;
    int Duration;
    int Gifposition;
    float Speed;

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
    public int getBackground()
    {
        return Gifposition;
    }
    public float getSpeed()
    {
        return Speed;
    }
}
