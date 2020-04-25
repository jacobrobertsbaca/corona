package com.spreadtracker.contactstracing;

import android.database.sqlite.SQLiteStatement;

import java.util.Date;

public class Event {

    private double latitude;
    private double longitude;
    private Date date;

//    between 0 and 1, correlates to closeness of interaction and duration
    private double weight;

    public Event(double latitude, double longitude, Date date, Double weight){
        setLatitude(latitude);
        setLongitude(longitude);
        setDate(date);
        setWeight(weight);
    }

    public void bindForInsert(SQLiteStatement statement){
        statement.bindDouble(1, getDate().getTime());
        statement.bindDouble(2, getLatitude());
        statement.bindDouble(3, getLongitude());
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
