package com.spreadtracker.contactstracing;

import android.database.sqlite.SQLiteStatement;

import java.util.Date;

public class Person {
    private long id;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;

    public void bindForInsert(SQLiteStatement statement) {
        statement.bindString(1, getFirstName());
        statement.bindString(2, getLastName());
        statement.bindLong(3, getDateOfBirth().getTime());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
