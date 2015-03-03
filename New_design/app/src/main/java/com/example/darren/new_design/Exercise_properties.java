package com.example.darren.new_design;

public class Exercise_properties
{

    int id;
    int Week;
    int Day;
    String TimeOfDay;
    String Name;
    String Description;
    String Intro_Video;
    Exercise_Type Type;
    int Duration;
    int Gif;
    float Speed;

    public Exercise_properties(int Week, int Day, String TimeOfDay, String name, String Description, String Intro_Video, Exercise_Type type, int duration, int gif, float speed)
    {
        this.Week = Week;
        this.Day = Day;
        this.TimeOfDay = TimeOfDay;
        this.Name = name;
        this.Description = Description;
        this.Intro_Video = Intro_Video;
        this.Type = type;
        this.Duration = duration;
        this.Gif = gif;
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
    public String getName()
    {
        return Name;
    }
    public String getDescription()
    {
        return Description;
    }
    public String getIntro_Video(){ return Intro_Video;}
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
        return Gif;
    }
    public float getSpeed()
    {
        return Speed;
    }
}
