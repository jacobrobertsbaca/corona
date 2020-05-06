package com.spreadtracker.ui.fragment.tutorial;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.spreadtracker.App;
import com.spreadtracker.R;
import com.spreadtracker.contactstracing.Person;

public class RandomUserTutorialPage extends TutorialPage {
    public RandomUserTutorialPage(int layoutRes) {
        super(layoutRes);
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View inflateLayout(@NonNull LayoutInflater inflater) {
        View root = super.inflateLayout(inflater);

        TextView personName, personPercentage;
        personName = root.findViewById(R.id.tutorial_c_randomname);
        personPercentage = root.findViewById(R.id.tutorial_c_randompercentage);

        App.getInstance().getContactTracer().addOnPersonChangedListener(person -> {
            personName.setText(person.getFirstName() + " " + person.getLastName());
            personPercentage.setText(getContext().getString(R.string.tutorial_d_randompercentage_format,
                    (int) (App.getInstance().getContactTracer().getRandomPersonPercentage() * 100)));
        }, true);

        return root;
    }
}
