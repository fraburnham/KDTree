package com.evolvingneuron;

/**
 * Created by fraburnham on 3/14/15.
 */

class KDNode<Point> extends Node<Point> {
    /** Stores the depth of the node */
    private int depth;

    /**
     *
     * @param parent
     * @param k
     * @param v
     * @param depth
     */
    public KDNode(KDNode<Point> parent, Comparable k, Point v, int depth) {
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
