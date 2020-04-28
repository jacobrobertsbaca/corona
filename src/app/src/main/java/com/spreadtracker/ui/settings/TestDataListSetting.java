package com.spreadtracker.ui.settings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.spreadtracker.R;
import com.spreadtracker.ui.activity.main.MainActivity;
import com.spreadtracker.ui.adapter.TestDataAdapter;

/**
 * Setting that creates a RecyclerView containing the list of the user's settings
 */
public class TestDataListSetting extends SettingsNode {

    private MainActivity mActivity;

    public TestDataListSetting(MainActivity activity) {
        mActivity = activity;
    }

    @NonNull
    @Override
    public View inflateLayout(@NonNull LayoutInflater inflater) {
        View root = inflater.inflate(R.layout.view_setting_tests_list, getParentView(), false);

        RecyclerView recycler = root.findViewById(R.id.view_settings_tests_list_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recycler.setLayoutManager(layoutManager);
        TestDataAdapter adapter = new TestDataAdapter(mActivity);
        recycler.setAdapter(adapter);

        DividerItemDecoration itemDecor = new DividerItemDecoration(recycler.getContext(), layoutManager.getOrientation());
        itemDecor.setDrawable(getContext().getDrawable(R.drawable.settings_divider));
        recycler.addItemDecoration(itemDecor);

        return root;
    }
}
