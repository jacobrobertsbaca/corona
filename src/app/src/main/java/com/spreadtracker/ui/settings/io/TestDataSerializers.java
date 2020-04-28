package com.spreadtracker.ui.settings.io;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.spreadtracker.R;
import com.spreadtracker.contactstracing.Test;
import com.spreadtracker.ui.settings.value.ValueSetting;
import com.spreadtracker.ui.util.ToastError;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestDataSerializers {

    private static final DateFormat CALENDAR_FORMAT = SimpleDateFormat.getDateInstance();

    private Test mTest;
    private boolean mEdit;
    private Context mCtx;

    public TestDataSerializers (@NonNull Test test, boolean editting, Context context) {
        mTest = test;
        mEdit = editting;
        mCtx = context;
    }

    public ValueSetting.ValueSerializer<Long> testDate () {
        return new ValueSetting.ValueSerializer<Long>() {
            @Override
            public Long readValue() {
                // I am assuming 0 means no test data set yet.
                // Unless of course they got tested for the coronavirus on December 31st, 1969, at 11:59pm
                // in which case... they are beyond saving.
                if (mTest.getDate() == 0) return new Date().getTime();
                else return mTest.getDate();
            }

            @Override
            public void writeValue(Long value) {
                mTest.setDate(value);
            }
        };
    }

    public ValueSetting.ValueSerializer<String> testFacility() {
        return new ValueSetting.ValueSerializer<String>() {
            @Override
            public String readValue() {
                return mTest.getFacilityName();
            }

            @Override
            public void writeValue(String value) {
                mTest.setFacilityName(value);
            }
        };
    }

    public ValueSetting.ValueValidator<String> testFacilityValidator() {
        return value -> {
            boolean valid = value != null && !value.isEmpty() && value.length() >= 3;
            if (!valid) ToastError.error(mCtx, R.string.settings_testdata_facilityname_error, Toast.LENGTH_LONG);
            return valid;
        };
    }
}

