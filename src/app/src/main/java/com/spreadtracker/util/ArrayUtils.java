package com.spreadtracker.util;

import android.util.SparseArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public final class ArrayUtils {
    private ArrayUtils() {}

    /**
     * This method will take a {@link SparseArray} and return the first integer key in the array
     * that does not map to a value and whose value is at least startValue
     */
    public static int getFirstAvailableIndex (SparseArray<?> array, int startValue) {
        if (startValue < 0) throw new IllegalArgumentException("Start value must be at least 0");
        for (;;) {
            if (array.indexOfKey(startValue) >= 0) return startValue;
            startValue++;
        }
    }

    public static int getFirstAvailableIndex (SparseArray<?> array) {
        return getFirstAvailableIndex(array, 0);
    }

    public static <T> List<T> getValues (SparseArray<T> array) {
        List<T> values = new ArrayList<>();
        int size = array.size();
        for (int i = 0; i < size; i++) {
            T value = array.valueAt(array.keyAt(i));
            values.add(value);
        }
        return values;
    }
}
