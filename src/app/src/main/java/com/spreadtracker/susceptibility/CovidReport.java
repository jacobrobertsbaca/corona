package com.spreadtracker.susceptibility;

import com.spreadtracker.contactstracing.Test;

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

    @Override
    public Report generateSusceptibilityReport() {
        Report report = new Report(Test.DISEASE_COVID19);



        return report;
    }
}
