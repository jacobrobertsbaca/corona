package com.spreadtracker.contactstracing;

import java.util.Date;

public class Calculator {

    DataHandler delegate;
    public Calculator(DataHandler delegate){
        this.delegate = delegate;
    }

    public double getInfectedPercentage(Person person, Date date, Database database) {
//        calculates the chance that a person is infected by propagating back through events
        double percentage = 0;
        long[][] paths = getPaths(person.getId(), date);


        return(percentage);
    }

    public long[][] getPaths(long personId, Date date){
//        returns an array containing an array of eventIds for each path back.
        long[][] paths = {{}};

        return(paths);
    }

    public static double getWeight(double duration, double avgDist){
        double weight = 1;
//        come up with expression for weight
        return(weight);
    }

    private static double parallelCombination(double p1, double p2){
//        returns the parallel combination of two event weights or probabilities
//        effectively the probability that either of these things took place, if they are independent
        return(1-(1-p1)*(1-p2));
    }
}
