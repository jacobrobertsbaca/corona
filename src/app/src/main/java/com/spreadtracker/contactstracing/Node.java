package com.spreadtracker.contactstracing;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private Node parent;
    private List<Node> children = new ArrayList<Node>();
    private Database.Connection connection;
//    the Connection of the event that this node represents.

    public Node(Node parent, Database.Connection connection) {
//      pass null if this node has no parent (is a leaf node)
        this.parent = parent;
        this.connection = connection;
    }

    public void buildGroupTree(List<Database.Connection[]> pathsForward){
//        takes the paths from calculator.getPaths and groups them by shared events.

        List<Database.Connection[]> myPaths = new ArrayList<Database.Connection[]>();
//        all the paths that actually contain my Id as the first element (but with my Id removed)
        for (Database.Connection[] path : pathsForward){
            if (path[0].eventId == this.connection.eventId){
                Database.Connection[] pathPopped = new Database.Connection[path.length - 1];
//                the path without my Connection at the beginning
                System.arraycopy(path, 1, pathPopped, 0, pathPopped.length);

                myPaths.add(pathPopped);
            }
        }

        for (Database.Connection childConnection : getUniqueChildConnections(myPaths)){
//            these are all the Connections that extend ours in one or more paths.
            Node childNode = new Node(this, childConnection);
            this.children.add(childNode);
            childNode.buildGroupTree(myPaths);
//            recurse and pass the childNode our own paths, which already have our own Connection removed.
        }
    }

    public double getInfectionContribution(){
        double myContribution = 0.0;
        if (this.children.size() > 0) {
            for (Node child : this.children) {
                myContribution = Calculator.parallelCombination(myContribution, child.getInfectionContribution());
                //            combine the contribution of all children in parallel
//                starting with 0 and combining in parallel every time is the same as having the parallel combination of all children
            }
            myContribution = myContribution * this.connection.eventWeight;
//                    multiply the parallel combination of all children by my own event weight
        } else {
            myContribution = this.connection.eventWeight;
        }
        return(myContribution);
    }

    public static List<Database.Connection> getUniqueChildConnections(List<Database.Connection[]> paths) {
//        gets a list of the unique Ids at the first position of all paths.
        List<Database.Connection> uniqueChildConnections = new ArrayList<Database.Connection>();
        for (Database.Connection[] path : paths){
            if (!uniqueChildConnections.contains(path[0])) {
                uniqueChildConnections.add(path[0]);
//                only add the first element if we don't have it already.
            }
        }
        return(uniqueChildConnections);
    }
}
