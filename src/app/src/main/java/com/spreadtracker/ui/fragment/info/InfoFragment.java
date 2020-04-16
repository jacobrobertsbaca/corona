package com.spreadtracker.ui.fragment.info;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.spreadtracker.R;
import com.spreadtracker.ui.activity.main.MainActivity;
import com.spreadtracker.ui.fragment.BaseFragment;
import com.spreadtracker.ui.fragment.NavigationBuilder;
import com.spreadtracker.ui.fragment.NavigationFragment;
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Check arguments passed to this fragment to see if we should use the infected color
        // when creating this fragment. If so, get the infected percentage color and set the background accordingly
        Bundle args = getArguments();
        if (args != null) {
            if (args.containsKey(ARGS_USE_INFECTED_COLOR)) {
                if (args.getBoolean(ARGS_USE_INFECTED_COLOR)) {
                    HomeFragmentViewModel vm = new ViewModelProvider(activity).get(HomeFragmentViewModel.class);
                    root.setBackgroundColor(vm.getInfectedPercentageColor());
                }
            }
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_info;
    }

    @Override
    protected void inOnCreateView(@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

    }

    @NonNull
    @Override
    protected NavigationBuilder buildNavigation(@NonNull Context context) {
        return new NavigationBuilder(this, context);
    }
}
