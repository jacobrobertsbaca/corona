package com.spreadtracker.ui.settings.io;

import com.spreadtracker.ui.settings.value.ValueSetting;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Saves calendar objects into date strings.
 */
public class DateSaver implements ValueSetting.ValueSerializer<String> {

    private Calendar mCalendar;
    private DateFormat mFormat;

    public DateSaver (DateFormat format) {
        mCalendar = Calendar.getInstance();
        mFormat = format;
    }

    public DateSaver () {
        this (SimpleDateFormat.getDateInstance());
    }

    public Calendar getCalendar () {return mCalendar;}

    @Override
    public String readValue() {
        return mFormat.format(mCalendar.getTime());
    }

    @Override
    public void writeValue(String value) {
        if (value == null || value.isEmpty())
        {
            // If null or empty string passed in, set current calendar time to the current time
            mCalendar.setTime(new Date());
            return;
        }

        try {
            Date time = mFormat.parse(value);
            if (time == null) return;
            mCalendar.setTime(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
