package com.spreadtracker.ui.settings.value;

import android.app.DatePickerDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.spreadtracker.ui.settings.io.DateSaver;
import com.spreadtracker.ui.util.ToastError;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class DatePickerSetting extends ValueSetting<String> {

    private @StringRes int mErrorText;
    private final DateSaver mDateSaver = new DateSaver();

    public DatePickerSetting(@StringRes int titleRes, @StringRes int errorTextRes, @NonNull String key, String defaultValue) {
        super(titleRes, key, defaultValue);
        mErrorText = errorTextRes;
    }

    public DatePickerSetting(@StringRes int titleRes, @StringRes int errorTextRes, @NonNull String key) {
        this (titleRes, errorTextRes, key, null);
    }

    public DatePickerSetting(@StringRes int titleRes, @NonNull String key) {
        this(titleRes, 0, key);
    }

    public DatePickerSetting (@StringRes int titleRes, @StringRes int errorTextRes, @NonNull ValueSerializer<String> serializer) {
        super(titleRes, serializer);
        mErrorText = errorTextRes;
    }

    public DatePickerSetting (@StringRes int titleRes, @NonNull ValueSerializer<String> serializer) {
        this(titleRes, 0, serializer);
    }

    @NonNull
    @Override
    public View inflateLayout(@NonNull LayoutInflater inflater) {
        View root = super.inflateLayout(inflater);
        iconView.setVisibility(View.GONE);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                mDateSaver.getCalendar().set(Calendar.YEAR, year);
                mDateSaver.getCalendar().set(Calendar.MONTH, monthOfYear);
                mDateSaver.getCalendar().set(Calendar.DAY_OF_MONTH, dayOfMonth);
                setValue(mDateSaver.readValue());
            }
        };

        getRootView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), date,
                        mDateSaver.getCalendar().get(Calendar.YEAR),
                        mDateSaver.getCalendar().get(Calendar.MONTH),
                        mDateSaver.getCalendar().get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        return root;
    }

    @Override
    public boolean canSave() {
        // Disallow dates after the current date
        // Could be made customizable to the caller, as the usage requires it to be
        if (mDateSaver.getCalendar().getTime().after(new Date())) {
            if (mErrorText != 0) ToastError.error(getContext(), mErrorText, Toast.LENGTH_LONG);
            return false;
        }
        return super.canSave();
    }

    @Override
    public void restoreState() {
        super.restoreState();
        mDateSaver.writeValue(readValue());
    }

    @Override
    public void setValue(String value) {
        super.setValue(value);
        textView.setText(value);
    }
}
