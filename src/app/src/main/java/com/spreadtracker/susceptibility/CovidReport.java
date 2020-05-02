package com.spreadtracker.susceptibility;

import android.content.Context;

import androidx.annotation.NonNull;

import com.spreadtracker.R;
import com.spreadtracker.contactstracing.Test;
import com.spreadtracker.ui.fragment.settings.general.GeneralSettingsFragment;
import com.spreadtracker.ui.fragment.settings.general.General_PhysicalActivitySettingsFragment;
import com.spreadtracker.ui.fragment.settings.general.General_ResidenceFragment;
import com.spreadtracker.ui.fragment.settings.general.General_SexFragment;
import com.spreadtracker.ui.fragment.settings.medicalhistory.MedicalHistorySettingsFragment;
import com.spreadtracker.ui.fragment.settings.medicalhistory.MedicalHistory_AsthmaFragment;
import com.spreadtracker.ui.fragment.settings.medicalhistory.MedicalHistory_DiabetesFragment;
import com.spreadtracker.ui.fragment.settings.medicalhistory.MedicalHistory_HeartDiseaseFragment;
import com.spreadtracker.ui.fragment.settings.medicalhistory.MedicalHistory_ImmuneSystemFragment;
import com.spreadtracker.ui.fragment.settings.medicalhistory.MedicalHistory_KidneyDisease;
import com.spreadtracker.ui.fragment.settings.medicalhistory.MedicalHistory_LiverDiseaseFragment;
import com.spreadtracker.ui.fragment.settings.medicalhistory.MedicalHistory_LungDiseaseFragment;
import com.spreadtracker.ui.settings.io.SettingsStore;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * A susceptibility report for the Covid-19 virus.
 */
// Research Links for implementing this class:
    //
    // https://www.worldometers.info/coronavirus/coronavirus-age-sex-demographics/
    //      There appears to be a sharper increase in deaths around the 65-74 yo age group.
    //      It also appears that men are twice as likely to die as women, independent of their underlying conditions.
    //
public class CovidReport implements ISusceptibilityProvider {
    private interface SusceptibilityIndicator {
        int getSeverity (@NonNull Context context,
                                       @NonNull SettingsStore store,
                                       @NonNull ArrayList<String> ailments,
                                       @NonNull Set<String> advice);
    }

    private Context mCtx;
    private SettingsStore mSettings;

    private ArrayList<SusceptibilityIndicator> mIndicators = new ArrayList<>();

    public CovidReport(@NonNull Context context) {
        mCtx = context;
        mSettings = SettingsStore.getInstance(mCtx);

        mIndicators.add(General_PhysicalActivitySettingsFragment::getSeverity);
        mIndicators.add(General_ResidenceFragment::getSeverity);
        mIndicators.add(General_SexFragment::getSeverity);
        mIndicators.add(GeneralSettingsFragment::getSeverity);
        mIndicators.add(MedicalHistory_AsthmaFragment::getSeverity);
        mIndicators.add(MedicalHistory_DiabetesFragment::getSeverity);
        mIndicators.add(MedicalHistory_HeartDiseaseFragment::getSeverity);
        mIndicators.add(MedicalHistory_ImmuneSystemFragment::getSeverity);
        mIndicators.add(MedicalHistory_KidneyDisease::getSeverity);
        mIndicators.add(MedicalHistory_LiverDiseaseFragment::getSeverity);
        mIndicators.add(MedicalHistory_LungDiseaseFragment::getSeverity);
    }

    @Override
    public Report generateSusceptibilityReport() {
        Report report = new Report(Test.DISEASE_COVID19);

        int susceptibilityCategory = MILD;
        ArrayList<String> ailments = new ArrayList<>();
        Set<String> advice = new HashSet<>();
        for (SusceptibilityIndicator indicator : mIndicators) {
            susceptibilityCategory |= indicator.getSeverity(mCtx, mSettings, ailments, advice);
        }

        report.setSusceptibility(susceptibilityCategory);

        String[] ailmentsArr = new String[ailments.size()];
        String[] adviceArr = new String[advice.size()];
        report.setAilments(ailments.toArray(ailmentsArr));
        report.setAdvice(advice.toArray(adviceArr));

        return report;
    }
}
