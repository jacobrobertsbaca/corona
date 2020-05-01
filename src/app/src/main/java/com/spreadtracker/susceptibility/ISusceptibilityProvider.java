package com.spreadtracker.susceptibility;

/**
 * Using the settings that the player opted to supply,
 * this class generates a report of their susceptibility to a virus.
 *
 * Namely, it generates a susceptibility index from 0 to 1.
 */
public interface ISusceptibilityProvider {

    int MILD = 0;
    int MODERATE = 1;
    int SEVERE = 1 >> 1;
    int LIFETHREATENING = 1 >> 2;

    class Report {

        private String mDiseaseName;
        private String[] mAilments = new String[0];
        private String[] mAdvice = new String[0];
        private int mSusceptibility;

        /**
         * Gets the name of the disease this susceptibility report was created for
         */
        public String getDiseaseName () { return mDiseaseName; }

        /**
         * Gets a list of ailments visible to the user to help explain why they got
         * the susceptibility score they got.
         *
         * For example, the returned String array might be:
         *
         *      {asthma, aids, age}
         *
         * And could be used to display text such as:
         *
         *      "Due to your asthma, AIDS, and age, you should stay home and get well."
         */
        public String[] getUserAilments() { return mAilments; }

        public String[] getAdvice () {return mAdvice;}

        /**
         * Gets a double indicating the user's susceptibility to {@link Report#getDiseaseName()},
         * where 0 indicates not susceptible at all and 1 indicates maximally susceptible.
         */
        public int getSusceptibility () { return mSusceptibility; }

        void setAdvice(String[] advice) {mAdvice=advice;}
        void setDiseaseName (String disease) { mDiseaseName = disease; }
        void setAilments (String[] ailments) {mAilments = ailments; }
        void setSusceptibility (int susceptibility) { mSusceptibility = susceptibility; }

        Report() {}
        Report(String diseaseName) {
            mDiseaseName = diseaseName;
        }
    }

    Report generateSusceptibilityReport();
}
