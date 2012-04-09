package com.ciklum.jtt.utils;

public class Timer implements Runnable {
    private boolean running;
    
    public Timer () {
        
    }

    @Override
    public void run() {
    }
    
    public void destroy() {
        running = false;
    }
}
