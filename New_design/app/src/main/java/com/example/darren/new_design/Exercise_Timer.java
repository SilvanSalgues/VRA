package com.example.darren.new_design;

import android.os.Handler;

public class Exercise_Timer {
    private long time_remaining = 0;
    private long countDownInterval;
    private boolean status;

    public Exercise_Timer(){
        this.countDownInterval = 1000;
        status = false;
        Initialize();
    }

    public void Stop() {
        status = false;
    }

    public void setDuration(long duration)
    {
        this.time_remaining = duration;
    }

    public int getCurrentTime() {
        return (int) time_remaining / 1000;
    }

    public void Start() {
        status = true;
    }
    public void Initialize()
    {
        final Handler handler = new Handler();
        final Runnable counter = new Runnable(){

            public void run(){
                if(status) {
                    if (time_remaining > 0)
                    {
                        time_remaining -= countDownInterval;
                        handler.postDelayed(this, countDownInterval);
                    }
                }
                else
                {
                    handler.postDelayed(this, countDownInterval);
                }
            }
        };
        handler.postDelayed(counter, countDownInterval);
    }
}


