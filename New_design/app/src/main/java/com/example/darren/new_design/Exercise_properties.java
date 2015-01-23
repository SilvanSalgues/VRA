package com.example.darren.new_design;

public class Exercise_properties
{
        int Duration;
        String colour;
        float Speed;

        public Exercise_properties(int duration, String colour, float speed)
        {
            this.Duration = duration;
            this.colour = colour;
            this.Speed = speed;
        }

        public String getBackground()
        {
        return colour;
        }

        public float getSpeed()
        {
            return Speed;
        }

        public int getDuration()
        {
        return Duration;
        }
}
