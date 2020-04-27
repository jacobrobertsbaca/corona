package com.spreadtracker.ui.settings.value;

import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;

import com.spreadtracker.R;
import com.spreadtracker.ui.settings.IconSetting;

public class RadioSetting extends IconSetting {
    private String mName;
    private boolean mActive;

    public RadioSetting (String name) {
        mName = name;
    }

    public String getName() {return mName;}

    public boolean isActive() {return mActive;}
    public void setActive(boolean active) {
        mActive = active;
        iconView.setVisibility(active ? View.VISIBLE : View.GONE);
    }

    @NonNull
    @Override
    public View inflateLayout(@NonNull LayoutInflater inflater) {
        View root = super.inflateLayout(inflater);

        iconView.setImageResource(R.drawable.ic_checkmark);
        titleView.setText(mName);

        getRootView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mActive) {
                    setActive(true);
                    if (getParent() instanceof ValueSetting)
                        ((ValueSetting) getParent()).setValue(getName());
                }
            }
        });

        return root;
    }
}
