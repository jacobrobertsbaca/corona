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

public class DatePickerSetting extends ValueSetting<Long> {

    private final Calendar mCalendar = Calendar.getInstance();
    private final DateFormat mFormat = SimpleDateFormat.getDateInstance();
    private @StringRes int mErrorText;

    public DatePickerSetting(@StringRes int titleRes, @StringRes int errorTextRes, @NonNull String key, Long defaultValue) {
        super(titleRes, key, defaultValue);
        mErrorText = errorTextRes;
    }

    public DatePickerSetting(@StringRes int titleRes, @StringRes int errorTextRes, @NonNull String key) {
        this (titleRes, errorTextRes, key, 0L);
    }

    public DatePickerSetting(@StringRes int titleRes, @NonNull String key) {
        this(titleRes, 0, key);
    }

    public DatePickerSetting (@StringRes int titleRes, @StringRes int errorTextRes, @NonNull ValueSerializer<Long> serializer) {
        super(titleRes, serializer);
        mErrorText = errorTextRes;
    }

    public DatePickerSetting (@StringRes int titleRes, @NonNull ValueSerializer<Long> serializer) {
        this(titleRes, 0, serializer);
    }

    @NonNull
    @Override
    public View inflateLayout(@NonNull LayoutInflater inflater) {
        View root = super.inflateLayout(inflater);
        iconView.setVisibility(View.GONE);

        final DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            mCalendar.set(Calendar.YEAR, year);
            mCalendar.set(Calendar.MONTH, monthOfYear);
            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setValue(mCalendar.getTime().getTime());
        };

        getRootView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), date,
                        mCalendar.get(Calendar.YEAR),
                        mCalendar.get(Calendar.MONTH),
                        mCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        return root;
    }

    @Override
    public boolean canSave() {
        // Disallow dates after the current date
        // Could be made customizable to the caller, as the usage requires it to be
        if (new Date(getValue()).after(new Date())) {
            if (mErrorText != 0) ToastError.error(getContext(), mErrorText, Toast.LENGTH_LONG);
            return false;
        }
        return super.canSave();
    }

    @Override
    public void restoreState() {
        super.restoreState();
        mCalendar.setTime(new Date(getValue()));
    }

    @Override
    public void setValue(Long value) {
        super.setValue(value);
        if (value != 0)
            textView.setText(mFormat.format(new Date(value)));
        else textView.setText(null);
    }

    @Override
    public boolean hasValue() {
        return getValue() != 0;
    }
}
