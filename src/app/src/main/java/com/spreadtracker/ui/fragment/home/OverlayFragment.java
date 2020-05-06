package com.spreadtracker.ui.fragment.home;

import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.spreadtracker.App;
import com.spreadtracker.R;
import com.spreadtracker.contactstracing.Database;
import com.spreadtracker.susceptibility.CovidReport;
import com.spreadtracker.susceptibility.ISusceptibilityProvider;
import com.spreadtracker.ui.activity.MainActivity;
import com.spreadtracker.ui.fragment.ViewModelFragment;



/**
 * Represents the percentage overlay screen that shows the user the percentage
 * chance that they have contracted the coronavirus.
 */
public class OverlayFragment extends ViewModelFragment<MainActivity, HomeFragmentViewModel> {

    private TextView mInfectedPercentage;
    private TextView mInfectedSubtitle;
    private TextView mAdviceAndInfo;

    /**
     * Creates and returns a new {@link OverlayFragment} instance.
     * @return A new {@link OverlayFragment} object.
     */
    public static OverlayFragment create() {
        return new OverlayFragment();
    }

    @Override
    protected void inOnCreateView(@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mInfectedPercentage = root.findViewById(R.id.fragment_overlay_textPercentage);
        mInfectedSubtitle = root.findViewById(R.id.fragment_overlay_textPercentageSubtitle);

        viewModel.getInfectedPercentage().observe(this, aDouble -> {
            OverlayFragment.this.mInfectedPercentage.setText(getString(R.string.overlay_percentage, (int) (aDouble * 100)));
            root.setBackgroundColor(viewModel.getInfectedPercentageColor());
        });

        App.getInstance().getContactTracer().addOnPersonChangedListener(person -> {
            // Set the percentage on screen to that of a random person in our 100-person sample.
            double percentage = App.getInstance().getContactTracer().getRandomPersonPercentage();
            viewModel.getInfectedPercentage().setValue(percentage);

            // Set risk text
            String subtitle;
            if (percentage >= .9) subtitle = getString(R.string.overlay_chance_90);
            else if (percentage >= .6) subtitle = getString(R.string.overlay_chance_60);
            else if (percentage >= .4) subtitle = getString(R.string.overlay_chance_40);
            else if (percentage >= .2) subtitle = getString(R.string.overlay_chance_20);
            else subtitle = getString(R.string.overlay_chance_0);
            mInfectedSubtitle.setText(subtitle);
        }, true);
        mAdviceAndInfo = root.findViewById(R.id.fragment_overlay_textAdviceAndInfo);

        // Generate susceptibility report and set advice text
        CovidReport covidReport = new CovidReport(App.getContext());
        ISusceptibilityProvider.Report report = covidReport.generateSusceptibilityReport();
        mAdviceAndInfo.setText(getAdviceString(report));
    }

    private String getAdviceString (ISusceptibilityProvider.Report report) {
        StringBuilder adviceString = new StringBuilder();
        StringBuilder ailmentsString = new StringBuilder();

        String[] ailments = report.getUserAilments();
        for (int i = 0; i < ailments.length; i++) ailments[i] = formatAilment(ailments[i]);

        if (ailments.length == 1) ailmentsString.append(ailments[0]);
        else if (ailments.length == 2) ailmentsString
                .append(ailments[0])
                .append(" ")
                .append(getString(R.string.overlay_ailments_conjunctor))
                .append(" ")
                .append(ailments[1]);
        else if (ailments.length > 2) {
            for (int i = 0; i < ailments.length; i++) {
                if (i == ailments.length - 1)
                    ailmentsString.append(getString(R.string.overlay_ailments_conjunctor)).append(" ");
                ailmentsString.append(ailments[i]);
                if (i != ailments.length - 1) ailmentsString.append(", ");
            }
        }

        if ((report.getSusceptibility() & ISusceptibilityProvider.LIFETHREATENING) > 0)
            adviceString.append(getString(R.string.overlay_susceptibility_dangerous, ailmentsString));
        else if ((report.getSusceptibility() & ISusceptibilityProvider.SEVERE) > 0)
            adviceString.append(getString(R.string.overlay_susceptibility_notable, ailmentsString, getString(R.string.overlay_susceptibility_adverb_severe)));
        else if ((report.getSusceptibility() & ISusceptibilityProvider.MODERATE) > 0)
        adviceString.append(getString(R.string.overlay_susceptibility_notable, ailmentsString, getString(R.string.overlay_susceptibility_adverb_moderate)));
        else if ((report.getSusceptibility() & ISusceptibilityProvider.MILD) > 0 && ailments.length > 0)
            adviceString.append(getString(R.string.overlay_susceptibility_notable, ailmentsString, getString(R.string.overlay_susceptibility_adverb_mild)));
        else adviceString.append(getString(R.string.overlay_susceptibility_minimal));

        adviceString.append(" ");
        for (String str : report.getAdvice()) {
            adviceString.append(str);
            adviceString.append(" ");
        }

        return adviceString.toString();
    }

    private String formatAilment(String ailment) {
        boolean isAcronym = true;
        for (int i = 0; i < ailment.length(); i++) {
            char c = ailment.charAt(i);
            if (Character.isAlphabetic(c) && Character.isLowerCase(c)) {
                isAcronym = false;
                break;
            }
        }

        if (!isAcronym) return ailment.toLowerCase();
        else return ailment;
    }

    /*
     * For use with ViewModelFragment
     */
    @NonNull
    @Override
    protected Class<HomeFragmentViewModel> getViewModelClass() { return HomeFragmentViewModel.class; }

    @Override
    protected int getLayout() { return R.layout.fragment_overlay; }
}
