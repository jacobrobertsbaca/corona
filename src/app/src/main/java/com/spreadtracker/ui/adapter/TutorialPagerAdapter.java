package com.spreadtracker.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import com.google.android.material.tabs.TabLayout;
import com.spreadtracker.ui.fragment.tutorial.TutorialPage;

import java.util.ArrayList;
import java.util.List;

public class TutorialPagerAdapter extends PagerAdapter {

    private List<TutorialPage> mPages = new ArrayList<>();
    private Context mCtx;
    private LayoutInflater mInflater;

    public TutorialPagerAdapter(@NonNull Context context) {
        mCtx = context;
        mInflater = LayoutInflater.from(context);
    }

    public Context getContext () {return mCtx;}

    @Override
    public int getCount() {
        return mPages.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View pageView = mPages.get(position).inflateLayout(mInflater);
        container.addView(pageView);
        return pageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    public TutorialPagerAdapter addPage (@NonNull TutorialPage page) {
        mPages.add(page);
        page.setContext(mCtx);
        notifyDataSetChanged();
        return this;
    }

    public boolean removePage (@NonNull TutorialPage page) {
        boolean success = mPages.remove(page);
        if (success) notifyDataSetChanged();
        return success;
    }
}
