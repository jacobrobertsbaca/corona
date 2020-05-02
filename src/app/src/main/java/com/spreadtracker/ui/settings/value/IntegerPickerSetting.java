package com.spreadtracker.ui.settings.value;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.shawnlin.numberpicker.NumberPicker;
import com.spreadtracker.R;

import java.util.ArrayList;

/**
 * A setting that opens a dialog and allows users to scroll through and select a list of values
 */
public class IntegerPickerSetting extends ValueSetting<Integer> {

    public interface ValueFormatter {
        String formatValue (int value);
    }

    private interface OnValueSelectedListener {
        void onValueSelected (int value);
    }

    private String mDialogTitle;
    private int mMinValue = 48;
    private int mMaxValue = 120;
    private int mIncrement = 1;
    private ValueFormatter mValueFormatter;

    /**
     * A dialog that is displayed when this setting is clicked on
     */
    private class IntegerPickerDialog extends Dialog {

        private final String[] mValues;         // The string values adopted by the NumberPicker
        private final int[] mValuesInches;      // An array containing the integer value in inches for each of the entries in the string values array
        private final int mCurrentValue;        // Current value of the setting, as passed by construction

        private OnValueSelectedListener mListener;
        private TextView mTitleView;
        private NumberPicker mPickerView;
        private String mTitleText;

        private String formatInches (int inches) {
            int inchesComponent = inches % 12;
            int feetComponent = inches / 12;
            return getContext().getString(R.string.settings_general_height_formatter, feetComponent, inchesComponent);
        }

        public IntegerPickerDialog(@NonNull Context context, String title, int currentValue) {
            super(context);

            mTitleText = title;
            mCurrentValue = currentValue;

            // Generate inches values
            ArrayList<String> values = new ArrayList<>();
            ArrayList<Integer> valuesInches = new ArrayList<>();
            int current = mMinValue;
            while (current <= mMaxValue) {
                values.add(mValueFormatter.formatValue(current));
                valuesInches.add(current);
                current += mIncrement;
            }

            mValues = new String[values.size()];
            mValuesInches = new int[values.size()];
            values.toArray(mValues);
            for (int i = 0; i < mValuesInches.length; i++) mValuesInches[i] = valuesInches.get(i);
        }

        public void setOnValueSelectedListener (OnValueSelectedListener listener) {mListener = listener;}

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_picker);
            getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            mTitleView = findViewById(R.id.dialog_picker_title);
            mPickerView = findViewById(R.id.dialog_picker_numberpicker);
            mTitleView.setText(mTitleText);

            // Set values
            mPickerView.setMinValue(1);
            mPickerView.setMaxValue(mValues.length);
            mPickerView.setDisplayedValues(mValues);

            // Find the value that matches the current value
            // This will select the first value if no matching value was found
            mPickerView.setValue(1);
            for (int i = mValuesInches.length - 1; i >= 0; i--)
                if (mCurrentValue >= mValuesInches[i]) {
                    mPickerView.setValue(i + 1);
                    break;
                }

            findViewById(R.id.dialog_picker_done).setOnClickListener(v -> {
                // User is done setting height.
                // Notify the consumer of the new value and close the dialog
                if (mListener != null)
                    mListener.onValueSelected(mValuesInches[mPickerView.getValue() - 1]);
                dismiss();
            });
        }
    }

    public IntegerPickerSetting(@StringRes int titleRes,
                                @NonNull String key,
                                int minValue,
                                int maxValue,
                                int increment,
                                ValueFormatter valueFormatter,
                                String dialogTitle) {
        super(titleRes, key, 0);
        setup(minValue, maxValue, increment, valueFormatter, dialogTitle);
    }

    public IntegerPickerSetting(@StringRes int titleRes,
                                @NonNull String key,
                                int minValue,
                                int maxValue,
                                int increment,
                                ValueFormatter valueFormatter) {
        this(titleRes, key, minValue, maxValue, increment, valueFormatter, null);
    }

    public IntegerPickerSetting(@StringRes int titleRes,
                                @NonNull String key,
                                int minValue,
                                int maxValue,
                                int increment) {
        this(titleRes, key, minValue, maxValue, increment, Integer::toString);
    }

    public IntegerPickerSetting(int titleRes,
                                @NonNull ValueSerializer<Integer> serializer,
                                int minValue,
                                int maxValue,
                                int increment,
                                ValueFormatter valueFormatter,
                                String dialogTitle) {
        super(titleRes, serializer);
        setup(minValue, maxValue, increment, valueFormatter, dialogTitle);
    }

    public IntegerPickerSetting(int titleRes,
                                @NonNull ValueSerializer<Integer> serializer,
                                int minValue,
                                int maxValue,
                                int increment,
                                ValueFormatter valueFormatter) {
        this(titleRes, serializer, minValue, maxValue, increment, valueFormatter, null);
    }

    public IntegerPickerSetting(int titleRes,
                                @NonNull ValueSerializer<Integer> serializer,
                                int minValue,
                                int maxValue,
                                int increment) {
        this(titleRes, serializer, minValue, maxValue, increment, Integer::toString);
    }

    private void setup (int minValue,
                        int maxValue,
                        int increment,
                        ValueFormatter valueFormatter,
                        String dialogTitle) {
        mMinValue = minValue;
        mMaxValue = maxValue;
        mIncrement = increment;
        mValueFormatter = valueFormatter;
        mDialogTitle = dialogTitle;
    }

    @NonNull
    @Override
    public View inflateLayout(@NonNull LayoutInflater inflater) {
        View root = super.inflateLayout(inflater);

        iconView.setVisibility(View.GONE);

        // Create this setting such that a dialog is opened when the user clicks on us
        getRootView().setOnClickListener(v -> {
            final IntegerPickerDialog heightDialog = new IntegerPickerDialog(getContext(),
                    (mDialogTitle != null ? mDialogTitle : getTitle()),
                    getValue());
            heightDialog.show();
            heightDialog.setOnValueSelectedListener(this::setValue); // Sets value to the value returned by the height picker dialog
        });

        return root;
    }

    @Override
    public void setValue(Integer value) {
        super.setValue(value);
        if (value != 0)
            textView.setText(mValueFormatter.formatValue(value));
        else textView.setText(null);
    }

    @Override
    public boolean hasValue() {
        return getValue() != 0;
    }
}
