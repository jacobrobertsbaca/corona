package com.spreadtracker.contactstracing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Dictionary;

public class Calculator {
    private final Database database;

    public Calculator(Database database) {
        this.database = database;
    }

    public double getInfectedPercentage(long personId, long date) {
//        calculates the chance that a person is infected by propagating back through events

        List<Test> myTests = this.database.getTestsBeforeDate(personId, date);
        if (myTests.size() > 0){
            if (myTests.get(0).isPositive()){
//                if we actually tested positive in the past, return 100% chance to be infected.
                return(1.0);
            }
        }

        List<Database.Connection[]> paths = getPaths(personId, date);
        List<Node> initialNodes = new ArrayList<Node>();
//        the leaf nodes that will be created by grouping

        for (Database.Connection initialConnection : Node.getUniqueChildConnections(paths)){
//            get the unique initial connections and start the recursion from those.
            Node initialNode = new Node(null, initialConnection);
            initialNodes.add(initialNode);
            initialNode.buildGroupTree(paths);
//            Will add child nodes to the initial nodes, which can then be used for calculation
        }

        double percentage = 0.0;
//        check if there are no nodes or we have tested positive?
        for (Node initialNode : initialNodes){
            percentage = parallelCombination(percentage, initialNode.getInfectionContribution());
//            gets the contribution of that node, including all of its children, so the contributions of the
//            initial nodes include all the information we need when combined in parallel.
        }

        return(percentage);
    }

    private List<Database.Connection[]> getPaths(long personId, long date){
//        returns a list containing arrays of Connections for each path backwards.
        List<Database.Connection[]> paths = new ArrayList<Database.Connection[]>();

        Database.Connection[] emptyParentConnectionChain = {};
        buildConnectionList(personId, date, emptyParentConnectionChain, paths);
//        buildTree will recursively add Connections to parent chains and add the parent chains to paths when it hits an infection.

        return(paths);
    }

    private void buildConnectionList(long rootPersonId, long date, Database.Connection[] parentConnectionChain, List<Database.Connection[]>resultPaths){
//        parentWeightIdChain is an array of Connections that led to this connection.
//        resultPaths is the master list of paths, which is directly altered in the method when a leaf node is found.

        List<Database.Connection> connections = this.database.getConnectionsBeforeDate(rootPersonId, date);
        CONNECTIONS: for(Database.Connection connection : connections){
            for(Database.Connection parentConnection : parentConnectionChain){
                if(parentConnection.personId == connection.personId) {
//                    if the connection is to a person we have already looked at on this path, ignore it
                    continue CONNECTIONS;
                }
            }
//            if this is a connection to a person we haven't seen yet, check if they are infected (leaf node),
//            and if not, then recurse to the person in the connection.

            Database.Connection[] newConnectionChain = new Database.Connection[parentConnectionChain.length + 1];
            System.arraycopy(parentConnectionChain, 0, newConnectionChain, 1, parentConnectionChain.length);
//              copy all entries of parent chain to a new chain, but leaving the 1st index empty
            newConnectionChain[0] = connection;
//            copy the connection to the beginning of the new chain.

            if (isLeafNode(connection)){
//                if this connection goes to a leaf node (infected person), save the path and stop recursion
                resultPaths.add(newConnectionChain);
            } else {
//                if this is not a leaf node, recurse to the connection
                buildConnectionList(connection.personId, connection.eventDate, newConnectionChain, resultPaths);
            }
        }
    }

    private boolean isLeafNode(Database.Connection connection){
        List<Test> tests = this.database.getTestsBeforeDate(connection.personId, connection.eventDate);
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

    public static double parallelCombination(double p1, double p2){
//        returns the parallel combination of two event weights or probabilities
//        effectively the probability that either of these things took place, if they are independent
        return(1-(1-p1)*(1-p2));
    }

    public class weightedId{
//        a tuple of event Id and weight
        public long eventId;
        public double eventWeight;

        public weightedId(long eventId, double eventWeight){
            this.eventId = eventId;
            this.eventWeight = eventWeight;
        }
    }
}
