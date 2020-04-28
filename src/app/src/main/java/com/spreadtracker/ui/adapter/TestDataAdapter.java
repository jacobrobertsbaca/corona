package com.spreadtracker.ui.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.spreadtracker.R;
import com.spreadtracker.contactstracing.Test;
import com.spreadtracker.ui.activity.main.MainActivity;
import com.spreadtracker.ui.fragment.settings.tests.EditTestDataFragment;
import com.spreadtracker.ui.settings.io.SettingsStore;
import com.spreadtracker.ui.settings.io.TestData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Adapter to draw test data list in settings
 */
public class TestDataAdapter extends RecyclerView.Adapter<TestDataAdapter.ViewHolder>
    implements TestData.OnTestDataChangedListener {

    private static final DateFormat DATE_FORMAT = SimpleDateFormat.getDateInstance();

    private Context mCtx;
    private TestData mTestData;
    private MainActivity mActivity;

    public TestDataAdapter(@NonNull MainActivity activity) {
        mCtx = activity;
        mTestData = SettingsStore.getInstance(mCtx).getTests();
        mActivity = activity;
        mTestData.addChangeListener(this);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mCtx).inflate(R.layout.view_setting_icon_content, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Test test = mTestData.get(position);
        holder.titleView.setText(test.getFacilityName());
        holder.textView.setText(DATE_FORMAT.format(new Date(test.getDate())));
        holder.iconView.setImageResource(R.drawable.ic_next);

        // When the element is clicked, go to the settings page
        // in edit mode by passing a bundle to the fragment
        final Bundle args = new Bundle();
        args.putBoolean(EditTestDataFragment.KEY_EDITING, true);
        args.putInt(EditTestDataFragment.KEY_TESTNUMBER, position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.getNav().navigate(R.id.action_testDataFragment_to_editTestDataFragment, args);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTestData.getCount();
    }

    @Override
    public void OnTestDataChanged(TestData data) {
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View itemView;
        public ImageView iconView;
        public TextView titleView, textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            iconView = itemView.findViewById(R.id.view_setting_icon_image);
            titleView = itemView.findViewById(R.id.view_setting_icon_title);
            textView = itemView.findViewById(R.id.view_setting_icon_text);
        }
    }
}
