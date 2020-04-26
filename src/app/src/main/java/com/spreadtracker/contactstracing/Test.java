package com.spreadtracker.contactstracing;

import java.util.Date;

public class Test {
    private long personId;
    private String disease;
    private boolean positive;
    private long date;

    public Test(long personId, String disease, boolean positive, long date){
        this.personId = personId;
        this.disease = disease;
        this.positive = positive;
        this.date = date;
    }

    public long getPersonId() {
        return personId;
    }

    public void setPerson(long personId) {
        this.personId = personId;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public boolean isPositive() {
        return positive;
    }

    public void setResult(boolean result) {
        this.positive = result;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
