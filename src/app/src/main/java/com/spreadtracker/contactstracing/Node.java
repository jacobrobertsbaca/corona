package com.spreadtracker.contactstracing;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private Node parent;
    private long Id;
//    the eventId that this node represents.

    public Node(Node parent, long Id) {
//      pass null if this node has no parent (is a leaf node)
        this.parent = parent;
        this.Id = Id;
    }

    public void buildGroupTree(List<long[]> pathsForward, List<Node> nodes){
//        takes the paths from calculator.getPaths and groups them by shared events.
//        adds nodes to the nodes list with every recursion, which will then be used for calcualtion
        nodes.add(this);

        List<long[]> myPaths = new ArrayList<long[]>();
//        all the paths that actually contain my Id as the first element (but with my Id removed)
        for (long[] path : pathsForward){
            if (path[0] == this.Id){
                long[] pathPopped = new long[path.length - 1];
//                the path without my Id at the beginning
                System.arraycopy(path, 1, pathPopped, 0, pathPopped.length);

                myPaths.add(pathPopped);
            }
        }

        for (long childId : getUniqueChildIds(myPaths)){
//            these are all the Ids that extend ours in one or more paths.
            Node childNode = new Node(this, childId);
            childNode.buildGroupTree(myPaths, nodes);
//            recurse and pass the childNode our own paths, which already have our own ID removed.
        }
    }

    public static List<Long> getUniqueChildIds(List<long[]> paths) {
//        gets a list of the unique Ids at the first position of all paths.
        List<Long> uniqueChildIds = new ArrayList<Long>();
        for (long[] path : paths){
            if (!uniqueChildIds.contains(path[0])) {
                uniqueChildIds.add(path[0]);
//                only add the first element if we don't have it already.
            }
        }
        return(uniqueChildIds);
    }
}
