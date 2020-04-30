package com.spreadtracker.ui.fragment.home;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.spreadtracker.R;
import com.spreadtracker.ui.activity.MainActivity;
import com.spreadtracker.ui.fragment.ViewModelFragment;



/**
 * Represents the percentage overlay screen that shows the user the percentage
 * chance that they have contracted the coronavirus.
 */
public class OverlayFragment extends ViewModelFragment<MainActivity, HomeFragmentViewModel> {

    private TextView mInfectedPercentage;
    private TextView mInfectedSubtitle;

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

        viewModel.getInfectedPercentage().observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                OverlayFragment.this.mInfectedPercentage.setText(getString(R.string.overlay_percentage, (int) (aDouble * 100)));
                root.setBackgroundColor(viewModel.getInfectedPercentageColor());
            }
        });

       // viewModel.getInfectedPercentage().setValue(0.0);
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
