package com.spreadtracker.ui.fragment.info;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.spreadtracker.App;
import com.spreadtracker.R;
import com.spreadtracker.contactstracing.ContactTracer;
import com.spreadtracker.contactstracing.Database;
import com.spreadtracker.ui.activity.MainActivity;
import com.spreadtracker.ui.fragment.NavigationBuilder;
import com.spreadtracker.ui.fragment.ViewModelLessNavigationFragment;
import com.spreadtracker.ui.fragment.home.HomeFragmentViewModel;

/**
 * The fragment that displays the info pages in the app.
 */
public class InfoFragment extends ViewModelLessNavigationFragment<MainActivity> {

    public final static String ARGS_USE_INFECTED_COLOR = "useInfectedColor";

    public static InfoFragment create() {
        return new InfoFragment();
    }

    private ImageView mCloseButton;

    @Override
    protected int getLayout() {
        return R.layout.fragment_info;
    }

    @Override
    protected void inOnCreateView(@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final TextView prototypeText = root.findViewById(R.id.fragment_info_prototypetext);
        final Button newUserButton = root.findViewById(R.id.fragment_info_newuserbutton);
        final TextView randomUserName = root.findViewById(R.id.fragment_info_randomuser_name);
        final TextView randomUserPercentage = root.findViewById(R.id.fragment_info_randomuser_percentage);
        final ContactTracer tracer = App.getInstance().getContactTracer();
        final Database database = tracer.getDatabase();

        newUserButton.setOnClickListener(v -> tracer.getRandomPerson(true));

        tracer.addOnPersonChangedListener(person -> {
            randomUserName.setText(person.getFirstName() + " " + person.getLastName());
            randomUserPercentage.setText(getString(R.string.tutorial_d_randompercentage_format, (int) (tracer.getRandomPersonPercentage() * 100)));
            prototypeText.setText(Html.fromHtml(getString(R.string.info_prototype, // using fromHtml to format the text
                    database.countPersons(),
                    database.countInfected(),
                    database.countEvents(),
                    person.getFirstName(),
                    person.getLastName())));
        }, true);
    }

    @NonNull
    @Override
    protected NavigationBuilder buildNavigation(@NonNull Context context) {
        return new NavigationBuilder(this, context);
    }
}
