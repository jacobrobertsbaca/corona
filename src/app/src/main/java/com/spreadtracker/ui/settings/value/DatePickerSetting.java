package com.spreadtracker.ui.settings.value;

import android.app.DatePickerDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.spreadtracker.ui.util.ToastError;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DatePickerSetting extends ValueSetting<String> {

    private final Calendar mCalendar = Calendar.getInstance();
    private final DateFormat mDateFormat = SimpleDateFormat.getDateInstance();

    private @StringRes int mErrorText;

    public DatePickerSetting(@NonNull String storageKey, int titleRes) {
        super(storageKey, titleRes, null);
    }

    public DatePickerSetting(@NonNull String storageKey, int titleRes, @StringRes int errorTextRes) {
        this(storageKey, titleRes);
        mErrorText = errorTextRes;
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
                // TODO Auto-generated method stub
                mCalendar.set(Calendar.YEAR, year);
                mCalendar.set(Calendar.MONTH, monthOfYear);
                mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                notifyDirty(!getStringFromDate(mCalendar).equals(readValue()));
                setState();
            }
        };

        getRootView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), date, mCalendar
                        .get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
                        mCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        return root;
    }

    @Override
    public boolean canSave() {
        // Disallow dates after the current date
        if (mCalendar.getTime().after(new Date())) {
            if (mErrorText != 0) ToastError.error(getContext(), mErrorText, Toast.LENGTH_LONG);
            return false;
        }
        return true;
    }

    @Override
    public void saveState() {
        writeValue(getStringFromDate(mCalendar));
    }

    @Override
    public void restoreState() {
        setState(readValue());
    }

    private void setState (String date) {
        setDateFromString(mCalendar, date);
        setState();
    }

    private void setState () {
        textView.setText(getStringFromDate(mCalendar));
    }

    private void setDateFromString (Calendar cal, String date) {
        if (date == null || date.isEmpty())
        {
            cal.setTime(new Date());
            return;
        }

        try {
            cal.setTime(mDateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private String getStringFromDate (Calendar cal) {
        return mDateFormat.format(cal.getTime());
    }
}
