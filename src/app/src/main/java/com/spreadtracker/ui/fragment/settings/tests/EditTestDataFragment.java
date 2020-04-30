package com.spreadtracker.ui.fragment.settings.tests;

import android.os.Bundle;
import android.view.ViewGroup;

import com.spreadtracker.R;
import com.spreadtracker.contactstracing.Test;
import com.spreadtracker.ui.fragment.settings.SettingsFragment;
import com.spreadtracker.ui.settings.LabelSettings;
import com.spreadtracker.ui.settings.NavigationSettingsPage;
import com.spreadtracker.ui.settings.io.SettingsStore;
import com.spreadtracker.ui.settings.io.TestData;
import com.spreadtracker.ui.settings.io.TestDataSerializers;
import com.spreadtracker.ui.settings.value.DatePickerSetting;
import com.spreadtracker.ui.settings.value.RadioSetting;
import com.spreadtracker.ui.settings.value.RadioSettings;
import com.spreadtracker.ui.settings.value.TextSetting;

public class EditTestDataFragment extends SettingsFragment {

    public static final String KEY_EDITING = "editting";
    public static final String KEY_TESTNUMBER = "testnumber";

    public static final String RESULT_POSITIVE = "positive";
    public static final String RESULT_NEGATIVE = "negative";

    private boolean mEdit = false;
    private int mPos = -1;

    @Override
    protected int getTitle() {
        Bundle args = getArguments();
        if (args != null && args.getBoolean(KEY_EDITING))
            return R.string.settings_testdata_edit;
        else return R.string.settings_testdata_create;
    }

    @Override
    protected void createSettingsHierarchy(ViewGroup container) {
        mEdit = false; mPos = -1;

        TestData testData = SettingsStore.getInstance().getTests();

        Bundle args = getArguments();
        if (args != null) {
            mEdit = args.getBoolean(KEY_EDITING);
            if (args.containsKey(KEY_TESTNUMBER))
                mPos = args.getInt(KEY_TESTNUMBER);
        }

        Test test;
        if (mEdit) test = testData.get(mPos);
        else test = new Test();
        test.setDisease(Test.DISEASE_COVID19);

        // Runnable that determines what should happen
        // when the user clicks the save button.
        //
        // If we are creating a new test, then we will save the new test to memory.
        // If we are editting an old test, then we will save the test data.
        final Runnable onSave = new Runnable() {
            @Override
            public void run() {
                if (mEdit) testData.save();
                else testData.create(test);
            }
        };

        // Get serializers/validators from helper class
        TestDataSerializers tds = new TestDataSerializers(test, mEdit, getContext());

        NavigationSettingsPage settingsPage = new NavigationSettingsPage(this, container, onSave,
                new TextSetting(R.string.settings_testdata_facilityname, tds.testFacility()).setValidator(tds.testFacilityValidator()),
                new DatePickerSetting(R.string.settings_testdata_date, R.string.settings_error_date, tds.testDate()),
                new LabelSettings(R.string.settings_testdata_result_title,
                        new RadioSettings(tds.testResult(),
                                new RadioSetting(RESULT_POSITIVE, getString(R.string.settings_testdata_result_positive)),
                                new RadioSetting(RESULT_NEGATIVE, getString(R.string.settings_testdata_result_negative))))
        ).build();


    }
}
