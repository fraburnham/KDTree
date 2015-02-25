package com.evolvingneuron;

import java.math.BigDecimal;
import java.util.Stack;

/**
 * K-Dimensional Tree
 * Created by Frank Burnham on 2/24/15.
 */

/**
 *
 * @param <Point>
 */
public class KDTree<Point> extends BST<Point> {

    class Node extends BST<Point>.Node {
        /** Stores the depth of the node */
        private int depth;

        /**
         *
         * @param parent
         * @param k
         * @param v
         * @param depth
         */
        public Node(Node parent, Comparable k, Point v, int depth) {
            super(parent, k, v);
            this.depth = depth;
        }

        /**
         *
         * @return
         */
        public int getDepth() {
            return depth;
        }

        /**
         *
         * @param depth
         */
        public void setDepth(int depth) {
            this.depth = depth;
        }
    }

    /** The root node of this tree. null for an empty tree */
    private Node root = null;
    /** The methods needed to provide data abstraction */
    private IKDTree<Point> methods;

    /**
     * KDTree constructor.
     * @param methods A IKDTree interface that has methods needed to provide
     *                data abstraction.
     */
    public KDTree(final IKDTree<Point> methods) {
        if (methods == null) {
            //TODO: throw exception if methods == null
        }

        this.methods = methods;
    }

    /**
     *
     * @param point
     * @return
     */
    private Node walk(final Point point) {
        Node n = root;
        int k = methods.getDimensions(point);

        for (int depth = 0;; depth++) {
            int comp = n.getKey().compareTo(methods.getDimensionValue((depth % k), point));

            if (comp > 0) {
                if (n.getRightChild() == null) {
                    break;
                }
                //should be safe to upcast since we found it on a KDTree right?
                n = (KDTree.Node) n.getRightChild();
            } else {
                if (n.getLeftChild() == null) {
                    break;
                }
                n = (KDTree.Node) n.getLeftChild();
            }
        }

        return n;
    }

    /**
     * Insert a new point into the existing tree.
     * @param point The point to insert.
     */
    public void insert(final Point point) {
        if (root == null) {
            //do insert here
            root = new Node(null, methods.getDimensionValue(0, point), point, 0);
            return;
        }
        //so the insert loop, what will it look like

        //this is insert
        //compare the relevant dimension until you get to the depth of the new
        //points partition, once at that dimension find a place to put the point
        Node n = walk(point);
        final int depth = n.getDepth();
        final int k = methods.getDimensions(point);
        final int dimension = depth % k;
        final int comp = n.getKey().compareTo(methods.getDimensionValue(dimension, point));
        //check for right or left and write it out
        if (comp > 0) {
            n.setRightChild(new Node(n, methods.getDimensionValue(((dimension + 1) % k), point), point, depth + 1));
        } else {
            n.setLeftChild(new Node(n, methods.getDimensionValue(((dimension + 1) % k), point), point, depth + 1));
        }
    }

    /**
     *
     * @param point
     * @return
     */
    public final Node closest(final Point point) {
        if (root == null) {
            //TODO: raise exception can't search an empty tree
        }


        //finding the closest point will need a stack of nodes
        Node champion = walk(point);
        BigDecimal distance = methods.distance(point, champion.getValue());
        Stack<Node> nodes = new Stack<Node>();
        nodes.push((KDTree.Node) champion.getParent());
        int k = methods.getDimensions(point);

        while (!nodes.empty()) {
            //pop a node
            Node n = nodes.pop();

            //push the parent node on
            Node parent = (KDTree.Node) n.getParent();
            if (parent != null) {
                nodes.push(parent);
            }

            //check the current node to see if it is closer than the champion
            BigDecimal nDistance = methods.distance(point, n.getValue());
            if (nDistance.compareTo(distance) < 0) {
                distance = nDistance;
                champion = n;
            }

            //check the distance in the dimension solved for with depth
            int dimension = n.getDepth() % k;
            //absolute value of the distance between
            //maybe use the sign, find out if we're left or right and the sign will
            //tell us which side to ignore (duh) need to use the sign
            nDistance = methods.getDimensionValue(dimension, n.getValue()).add(
                    methods.getDimensionValue(dimension, point).negate()).abs();

            //if the division line crosses the circle then push its child on that side
            //of the division onto the stack.

        }

        //once the stack is empty we've searched the nearby space for
        //neighbors, return the champion
        return champion;
    }
}
