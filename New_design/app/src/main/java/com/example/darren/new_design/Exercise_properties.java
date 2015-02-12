package com.example.darren.new_design;

public class Exercise_properties
{

    Exercise_Type Type;
    String Name;
    int Duration;
    int Colour;
    float Speed;

    public Exercise_properties( String name, Exercise_Type type, int duration, int colour, float speed)
    {
        this.Name = name;
        this.Type = type;
        this.Duration = duration;
        this.Colour = colour;
        this.Speed = speed;
    }

    public String getName()
    {
        return Name;
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
        return Colour;
    }
    public float getSpeed()
    {
        return Speed;
    }
}
