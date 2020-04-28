package com.spreadtracker.ui.settings.io;

import androidx.annotation.NonNull;

import com.spreadtracker.contactstracing.Test;
import com.spreadtracker.ui.settings.value.ValueSetting;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestDataSerializers {

    private static final DateFormat CALENDAR_FORMAT = SimpleDateFormat.getDateInstance();

    private Test mTest;
    private boolean mEdit;

    public TestDataSerializers (@NonNull Test test, boolean editting) {
        mTest = test;
        mEdit = editting;
    }

    public ValueSetting.ValueSerializer<String> testDate () {
        return new ValueSetting.ValueSerializer<String>() {
            @Override
            public String readValue() {
                if (mTest.getDate() == 0) return null; // No such date
                else return CALENDAR_FORMAT.format(new Date(mTest.getDate()));
            }

            @Override
            public void writeValue(String value) {
                Date time;
                try {
                    time = CALENDAR_FORMAT.parse(value);
                } catch (ParseException e) {
                    time = null;
                }

                if (time != null)
                    mTest.setDate(time.getTime());
                else mTest.setDate(0);
            }
        };
    }
}
