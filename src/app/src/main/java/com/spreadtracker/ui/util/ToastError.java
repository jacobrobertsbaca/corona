package com.spreadtracker.ui.util;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.spreadtracker.R;

public class ToastError {
    public static void error (Context context, String message, int duration) {
        Toast toast = Toast.makeText(context, message, duration);
        View view = toast.getView();

        //Gets the actual oval background of the Toast then sets the colour filter
        view.getBackground().setColorFilter(ContextCompat.getColor(context, R.color.errorRed), PorterDuff.Mode.SRC_IN);
        view.getBackground().setAlpha(210);

        //Gets the TextView from the Toast so it can be editted
        TextView text = view.findViewById(android.R.id.message);
        text.setTextColor(ContextCompat.getColor(context, R.color.white));

        toast.show();
    }
}

