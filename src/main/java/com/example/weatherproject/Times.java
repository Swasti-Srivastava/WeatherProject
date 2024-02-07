package com.example.weatherproject;

public class Times {
    private int image;
    private String high;
    private String lo;
    private String date;

    public Times(int image, String high, String lo, String date){
        this.image = image;
        this.high = high;
        this.lo = lo;
        this.date = date;

    }

    public int getImage(){
        return image;
    }

    public String getHigh(){return high;}

    public String getLo(){return lo;}

    public String getDate(){return date;}
}
