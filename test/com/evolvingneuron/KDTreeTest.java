package com.evolvingneuron;

import junit.framework.TestCase;

public class KDTreeTest extends TestCase {

    public static class TestPoint {
        public TestPoint(float x, float y) {
            data[0] = x;
            data[1] = y;
        }

        public float[] data = new float[2];
    }

    public static class Methods implements IKDTree<TestPoint> {
        public float distance(TestPoint a, TestPoint b) {
            float sqDistance = 0;
            int dimensions = 2;

            for(int i = 0; i < dimensions; i++) {
                //add the (a - b)^2 to the total
                sqDistance += Math.pow(a.data[i] - b.data[i], 2);
            }

            return sqDistance;
        }

        public int getDimensions(TestPoint point) {
            return 2;
        }

        public float getDimensionValue(int dimension, TestPoint point) {
            return point.data[dimension];
        }

        public TestPoint setDimensionValue(int dimension, float value, TestPoint point) {
            TestPoint ret = new TestPoint(point.data[0], point.data[1]);

            ret.data[dimension] = value;
            return ret;
        }
    }

    public void testInsert() throws Exception {

    }

    public void testClosest() throws Exception {

    }

    public void testOverall() throws Exception {
        //start by inserting some points into the tree
        KDTree<TestPoint> kd = new KDTree<TestPoint>(new Methods());
        kd.insert(new TestPoint(3, 5));
        kd.insert(new TestPoint(1, 1));
        kd.insert(new TestPoint(1, 5));
        kd.insert(new TestPoint(6, 2));
        kd.insert(new TestPoint(3, 1));
        kd.insert(new TestPoint(13, 6));
        kd.insert(new TestPoint(31, 15));
        kd.insert(new TestPoint(3, 25));
        kd.insert(new TestPoint(13, 0));
        kd.insert(new TestPoint(0, 0));
        KDNode<TestPoint> neighbor = kd.closest(new TestPoint(2, 1));
        System.out.println("Neighbor (x, y): " + neighbor.getValue().data[0] + ", " + neighbor.getValue().data[1]);
        neighbor = kd.closest(new TestPoint(5, 10));
        System.out.println("Neighbor (x, y): " + neighbor.getValue().data[0] + ", " + neighbor.getValue().data[1]);
        neighbor = kd.closest(new TestPoint(21, 11));
        System.out.println("Neighbor (x, y): " + neighbor.getValue().data[0] + ", " + neighbor.getValue().data[1]);
    }
}
