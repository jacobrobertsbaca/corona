package com.spreadtracker.ui.fragment.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * A ViewModel containing shared data for the {@link HomeFragment} and {@link OverlayFragment}
 * fragments. Note that this view model is shared across multiple fragments.
 */
public class HomeFragmentViewModel extends ViewModel {

    /**
     * Represents the infected percentage of the user,
     * as shown on the overlay screen.
     * Values range from 0 to 1.
     */
    private MutableLiveData<Double> infectedPercentage;
    public MutableLiveData<Double> getInfectedPercentage() {
        if (infectedPercentage == null) infectedPercentage = new MutableLiveData<>();
        return infectedPercentage;
    }
}
