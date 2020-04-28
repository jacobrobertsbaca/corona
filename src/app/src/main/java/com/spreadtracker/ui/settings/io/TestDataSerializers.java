package com.spreadtracker.ui.settings.io;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.spreadtracker.R;
import com.spreadtracker.contactstracing.Test;
import com.spreadtracker.ui.settings.value.ValueSetting;
import com.spreadtracker.ui.util.ToastError;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestDataSerializers {

    private static final DateFormat CALENDAR_FORMAT = SimpleDateFormat.getDateInstance();

    public final String POSITIVE;
    public final String NEGATIVE;

    private Test mTest;
    private boolean mEdit;
    private Context mCtx;

    public TestDataSerializers (@NonNull Test test, boolean editting, @NonNull Context context) {
        mTest = test;
        mEdit = editting;
        mCtx = context;

        POSITIVE = context.getString(R.string.settings_testdata_result_positive);
        NEGATIVE = context.getString(R.string.settings_testdata_result_negative);
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

    public ValueSetting.ValueSerializer<String> testResult() {
        return new ValueSetting.ValueSerializer<String>() {
            @Override
            public String readValue() {
                return mTest.isPositive() ?  POSITIVE : NEGATIVE;
            }

            @Override
            public void writeValue(String value) {
                if (value == null || value.isEmpty()) {
                    mTest.setResult(false);
                    return;
                }

                if (value.equals(POSITIVE)) mTest.setResult(true);
                else if (value.equals(NEGATIVE)) mTest.setResult(false);
                else mTest.setResult(false);
            }
        };
    }

    public ValueSetting.ValueValidator<String> testResultValidator () {
        return value -> {
            boolean valid = value == null || value.isEmpty();
            if (!valid) ToastError.error(mCtx, R.string.settings_testdata_result_error, Toast.LENGTH_LONG);
            return valid;
        };
    }
}

