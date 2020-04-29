package com.spreadtracker.ui.util;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.spreadtracker.R;
import com.spreadtracker.ui.adapter.TestDataAdapter;

// Taken from
// https://medium.com/@zackcosborn/step-by-step-recyclerview-swipe-to-delete-and-undo-7bbae1fce27e
// and modified
public class TestDataDeletionCallback extends ItemTouchHelper.SimpleCallback {
    private TestDataAdapter mAdapter;

    private Drawable mIcon;
    private final ColorDrawable mBackground;

    public TestDataDeletionCallback(TestDataAdapter adapter) {
        super(0, ItemTouchHelper.RIGHT);
        mAdapter = adapter;

        mIcon = ContextCompat.getDrawable(mAdapter.getContext(),
                R.drawable.ic_delete);
        mBackground = new ColorDrawable(ContextCompat.getColor(mAdapter.getContext(),R.color.errorRed));
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        // Delete this test data
        int position = viewHolder.getAdapterPosition();
        mAdapter.getTestData().delete(position);
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        View itemView = viewHolder.itemView;
        int backgroundCornerOffset = 0;

        int iconMargin = (itemView.getHeight() - mIcon.getIntrinsicHeight()) / 2;
        int iconTop = itemView.getTop() + (itemView.getHeight() - mIcon.getIntrinsicHeight()) / 2;
        int iconBottom = iconTop + mIcon.getIntrinsicHeight();

        if (dX > 0) { // Swiping to the right
            int iconLeft = itemView.getLeft() + iconMargin;
            int iconRight = itemView.getLeft() + iconMargin + mIcon.getIntrinsicWidth();
            mIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

            mBackground.setBounds(itemView.getLeft(), itemView.getTop(),
                    itemView.getLeft() + ((int) dX) + backgroundCornerOffset,
                    itemView.getBottom());
        } else mBackground.setBounds(0, 0, 0, 0);

        mBackground.draw(c);
        mIcon.setAlpha(getAlpha(dX));
        mIcon.draw(c);
    }

    private int getAlpha (float dx) {
        final float iconAlphaIncrement = 100;
        if (dx < 0) dx = 0;
        if (dx > iconAlphaIncrement) dx = iconAlphaIncrement;
        return (int)(255f * dx / iconAlphaIncrement);
    }
}
