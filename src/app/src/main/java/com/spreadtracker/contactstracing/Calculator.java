package com.spreadtracker.contactstracing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Calculator {
    private final DataHandler delegate;

    public Calculator(DataHandler delegate){
        this.delegate = delegate;
    }

    public double getInfectedPercentage(Person person, Date date) {
//        calculates the chance that a person is infected by propagating back through events
        double percentage = 0;


        return(percentage);
    }

    public List<long[]> getPaths(long personId, Date date){
//        returns a list containing arrays of eventIds for each path backwards.
        List<long[]> paths = new ArrayList<long[]>();

        long[] emptyParentIdChain = {};
        buildTree(personId, date.getTime(), emptyParentIdChain, paths);
//        buildTree will recursively add eventIds to parent chains and add the parent chains to paths when it hits an infection.

        return(paths);
    }

    private void buildTree(long rootPersonId, long date, long[] parentIdChain, List<long[]>resultPaths){
//        parentIdChain is an array of eventIds that led to this connection.
//        resultPaths is the master list of paths, which is directly altered in the method when a leaf node is found.

        List<Database.Connection> connections = this.delegate.database.getConnectionsBeforeDate(rootPersonId, date);
        CONNECTIONS: for(Database.Connection connection : connections){
            for(long parentId : parentIdChain){
                if(parentId == connection.personId) {
//                    if the connection is to a person we have already looked at on this path, ignore it
                    continue CONNECTIONS;
                }
            }
//            if this is a connection to a person we haven't seen yet, check if they are infected (leaf node),
//            and if not, then recurse to the person in the connection.

            long[] connectionIdChain = new long[parentIdChain.length + 1];
            System.arraycopy(parentIdChain, 0, connectionIdChain, 1, parentIdChain.length);
//              copy all entries of parent chain to a new chain, but leaving the 1st index empty
            connectionIdChain[0] = connection.eventId;
//            copy the connection eventId to the beginning of the new chain.

            if (isLeafNode(connection)){
//                if this connection goes to a leaf node (infected person), save the path and stop recursion
                resultPaths.add(connectionIdChain);
            } else {
//                if this is not a leaf node, recurse to the connection
                buildTree(connection.personId, connection.eventDate, connectionIdChain, resultPaths);
            }
        }
    }

    private boolean isLeafNode(Database.Connection connection){
        List<Test> tests = this.delegate.database.getTestsBeforeDate(connection.personId, connection.eventDate);
//            get the tests for the person in this connection before the date of the connection.
        if (!tests.isEmpty()) {
            Test mostRecentTest = tests.get(0);
            if (mostRecentTest.isPositive()) {
//              this is a leaf node
                return (true);
            } else {
                return (false);
            }
        } else {
            return (false);
        }
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
