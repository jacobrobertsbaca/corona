package com.spreadtracker.model;

public class calculator {

    public static double updateInfectedPercentage(double initPercentage, double delta_t) {
        double dP = 0;
//      some function of GPS location and surroundings
        double newPercentage = initPercentage + dP * delta_t;
        return newPercentage;
    }

    public static double getBasePercentage() {
        double basePercentage = 0.75;
//        some function of home location and other intrinsic factors
        return basePercentage;
    }

    public static double getRiskFactor(){
        double riskFactor = 0;
//        between 0 and 1, some function of health, age, activity
        return riskFactor;
    }
}
