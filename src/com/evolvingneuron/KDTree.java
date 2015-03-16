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
public class KDTree<Point> extends BST {

    /** The root node of this tree. null for an empty tree */
    private KDNode<Point> root = null;
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
    private KDNode<Point> walk(final Point point) {
        KDNode<Point> n = root;
        int k = methods.getDimensions(point);

        for (int depth = 0;; depth++) {
            int comp = n.getKey().compareTo(methods.getDimensionValue((depth % k), point));

            if (comp > 0) {
                if (n.getRightChild() == null) {
                    break;
                }
                n = (KDNode<Point>) n.getRightChild();
            } else {
                if (n.getLeftChild() == null) {
                    break;
                }
                n = (KDNode<Point>) n.getLeftChild();
            }
        }

        return n;
    }

    /**
     * Insert a new point into the existing tree.
     * @param point The point to insert.
     */
    public final void insert(final Point point) {
        if (root == null) {
            root = new KDNode<Point>(null, methods.getDimensionValue(0, point), point, 0);
            return;
        }

        KDNode<Point> n = walk(point);
        final int depth = n.getDepth();
        final int k = methods.getDimensions(point);
        final int dimension = depth % k;
        final int comp = n.getKey().compareTo(methods.getDimensionValue(dimension, point));
        //check for right or left and write it out
        if (comp > 0) {
            n.setRightChild(new KDNode<Point>(n, methods.getDimensionValue(((dimension + 1) % k), point), point, depth + 1));
        } else {
            n.setLeftChild(new KDNode<Point>(n, methods.getDimensionValue(((dimension + 1) % k), point), point, depth + 1));
        }
    }

    /**
     *
     * @param point
     * @return
     */
    public final KDNode<Point> closest(final Point point) {
        if (root == null) {
            //TODO: raise exception can't search an empty tree
        }


        //finding the closest point will need a stack of nodes
        KDNode<Point> champion = walk(point);
        BigDecimal distance = methods.distance(point, champion.getValue());
        Stack<KDNode<Point>> nodes = new Stack<KDNode<Point>>();
        nodes.push((KDNode<Point>) champion.getParent());
        int k = methods.getDimensions(point);

        while (!nodes.empty()) {
            //pop a node
            KDNode<Point> n = nodes.pop();
            Point nPoint = n.getValue();
            int dimension = n.getDepth() % k;

            //push the parent node on
            KDNode<Point> parent = (KDNode<Point>) n.getParent();
            if (parent != null) {
                nodes.push(parent);
            }

            //check the current node to see if it is closer than the champion
            BigDecimal nDistance = methods.distance(point, nPoint);
            if (nDistance.compareTo(distance) < 0) {
                distance = nDistance;
                champion = n;
            }

            //the closest point on the division line to determine if the other child
            //could contain a closer result.
            Point divisionLine = methods.setDimensionalValue(
                    dimension,
                    methods.getDimensionValue(dimension, nPoint),
                    point);

            BigDecimal dDistance = methods.distance(point, divisionLine);

            BigDecimal side = methods.getDimensionValue(dimension, nPoint).add(
                    methods.getDimensionValue(dimension, point).negate());

            int comp = side.compareTo(BigDecimal.ZERO);

            if (dDistance.compareTo(nDistance) < 0) {
                //if the dDistance is closer than the champion there may be a point
                //on the other side of the division that is closer than the champion
                nodes.push((KDNode<Point>) n.getLeftChild());
                nodes.push((KDNode<Point>) n.getRightChild());
            } else if (comp < 0) {
                //if dDistance is not closer then just push the correct side of the division
                //based on n.Point[dimension] - point[dimension] if neg go left if pos go right
                nodes.push((KDNode<Point>) n.getLeftChild());
            } else if (comp > 0) {
                nodes.push((KDNode<Point>) n.getRightChild());
            }
        }

        //once the stack is empty we've searched the nearby space for
        //neighbors, return the champion
        return champion;
    }
}
