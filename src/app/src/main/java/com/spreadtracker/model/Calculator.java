package com.spreadtracker.model;

import com.spreadtracker.model.Person;

import java.util.ArrayList;

public class Calculator {

    private static double MU_INTERACTON = 1;
//    constant of proportionality for dP in interpersonal interactions

    private static ArrayList<Person> nearbyPersons;

    private static double longitude;
    private static double latitude;

    public static double updateInfectedPercentage(double initPercentage, double delta_t) {
        double dP = 0;

        /**
         * calculate the adjustment to our percentage based on interpersonal interactions.
         */

        ArrayList<Double> inverseSquaredDistances = null;
//        the inverse squares of distances to nearby people
        ArrayList<Double> infectedPercentageDifferences = null;
        for(int i=0; i<nearbyPersons.size(); i++) {
            inverseSquaredDistances.add(1/(Math.pow(nearbyPersons.get(i).distance, 2)));
            infectedPercentageDifferences.add(Math.max(nearbyPersons.get(i).infectedPercentage - initPercentage, 0));
//            if this person has a lower percentage, don't make any adjustment to ours
        }

        dP += MU_INTERACTON * dot(infectedPercentageDifferences, inverseSquaredDistances);

        /**
         * calculate adjustment to our percentage based on location
         */



        double newPercentage = initPercentage + dP * delta_t;
        return newPercentage;
    }

    public static double getBasePercentage() {
        double basePercentage = 0.71;
//        some function of home location and other intrinsic factors
        return basePercentage;
    }

    public static double getRiskFactor(){
        double riskFactor = 0;
//        between 0 and 1, some function of health, age, activity
        return riskFactor;
    }

    private static double dot(ArrayList<Double> list1, ArrayList<Double> list2){
        if(list1.size() == list2.size()) {
            double product = 0;
            for (int i = 0; i < list1.size(); i++) {
                product += list1.get(i) * list2.get(i);
            }
            return(product);
        } else {
            throw new IllegalArgumentException("Array sizes do not match");
        }
    }
}

