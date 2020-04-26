package com.spreadtracker.contactstracing;

import java.util.Date;

public class DataHandler {
    public final Database database;
    public final Calculator calculator;

    public DataHandler(){
        database = new Database(this, "ContactsTracingDb");
        calculator = new Calculator(this);
    }

    public void userCreated(){
        Person person = new Person();
        database.createPerson(person);
    }

    public void eventDetected(double latitude, double longitude, Date date, double duration, double avgDist){
        Event event = new Event(latitude, longitude, date, Calculator.getWeight(duration, avgDist));
        database.createEvent(event);
    }



}
