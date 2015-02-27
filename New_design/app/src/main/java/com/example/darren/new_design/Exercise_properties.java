package com.example.darren.new_design;

public class Exercise_properties
{

    Exercise_Type Type;
    String Name;
    int Intro_Video;
    int Duration;
    int Gif;
    float Speed;

    public Exercise_properties( String name, int Intro_Video, Exercise_Type type, int duration, int gif, float speed)
    {
        this.Name = name;
        this.Intro_Video = Intro_Video;
        this.Type = type;
        this.Duration = duration;
        this.Gif = gif;
        this.Speed = speed;
    }

    public String getName()
    {
        return Name;
    }
    public int getIntro_Video(){ return Intro_Video;}
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
