package com.spreadtracker.contactstracing;

import android.database.sqlite.SQLiteStatement;

import java.util.Date;

public class Event {

    private long id;

    private double latitude;
    private double longitude;
    private long date;
//    between 0 and 1, correlates to closeness of interaction and duration

    private double weight;
    public Event(long id, double latitude, double longitude, long date, Double weight){
        setId(id);
        setLatitude(latitude);
        setLongitude(longitude);
        setDate(date);
        setWeight(weight);
    }

    public void bindForInsert(SQLiteStatement statement){
        statement.bindLong(1, getId());
        statement.bindLong(2, getDate());
        statement.bindDouble(3, getWeight());
        statement.bindDouble(4, getLatitude());
        statement.bindDouble(5, getLongitude());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
