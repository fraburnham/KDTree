package com.evolvingneuron;


/**
 * This interface describes the functions that KDTree needs the caller to
 * supply in order to be as data-agnostic as possible. This interface will
 * allow simple use of KDTree from clojure as well as java.
 *
 * @param <Point> The caller defined point type.
 */
public interface IKDTree<Point> {
    /**
     * Return the distance between two points. This allows the caller to
     * use any distance function.
     *
     * @param a Point a
     * @param b Point b
     * @return A float distance between both points.
     */
    float distance(Point a, Point b);

    /**
     * Return the number of dimensions that point has.
     *
     * @param point A point of generic type Point.
     * @return An int containing the number of dimensions.
     */
    int getDimensions(Point point);

    /**
     * Return the point's value in dimension.
     *
     * @param dimension The dimension to return. Index starts with 0.
     * @param point The k dimensional point.
     * @return A float that is equal to the point's value in the given
     *         dimension.
     */
    float getDimensionValue(int dimension, Point point);

    /**
     *
     * @param dimension
     * @param value
     * @param point
     * @return
     */
    Point setDimensionValue(int dimension, float value, Point point);
}
