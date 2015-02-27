package com.example.darren.new_design;

public class Exercise_properties
{

    int id;
    String Name;
    String Description;
    int Intro_Video;
    Exercise_Type Type;
    int Duration;
    int Gif;
    float Speed;

    public Exercise_properties( String name, String Description, int Intro_Video, Exercise_Type type, int duration, int gif, float speed)
    {
        this.Name = name;
        this.Description = Description;
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
    public String getDescription()
    {
        return Description;
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
