package com.evolvingneuron;

import junit.framework.TestCase;
import java.math.BigDecimal;

public class KDTreeTest extends TestCase {

    public static class TestPoint {
        public TestPoint(BigDecimal x, BigDecimal y) {
            data[0] = x;
            data[1] = y;
        }

        public BigDecimal[] data = new BigDecimal[2];
    }

    public static class Methods implements IKDTree<TestPoint> {
        public BigDecimal distance(TestPoint a, TestPoint b) {
            BigDecimal sqDistance = new BigDecimal(0);
            int dimensions = 2;

            for(int i = 0; i < dimensions; i++) {
                //add the (a - b)^2 to the total
                sqDistance = sqDistance.add((a.data[i].add(b.data[i].negate())).pow(2));
            }

            return sqDistance;
        }

        public int getDimensions(TestPoint point) {
            return 2;
        }

        public BigDecimal getDimensionValue(int dimension, TestPoint point) {
            return point.data[dimension];
        }

        public TestPoint setDimensionValue(int dimension, BigDecimal value, TestPoint point) {
            TestPoint ret = new TestPoint(point.data[0], point.data[1]);

            ret.data[dimension] = value;
            System.out.println("Verify that point is cloned for ret");
            System.out.println("Point (x, y): " + point.data[0] + ", " + point.data[1]);
            System.out.println("ret (x, y): " + ret.data[0] + ", " + ret.data[1]);
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
        TestPoint a = new TestPoint(new BigDecimal(3), new BigDecimal(5));
        TestPoint b = new TestPoint(new BigDecimal(1), new BigDecimal(1));
        TestPoint findPoint = new TestPoint(new BigDecimal(2), new BigDecimal(1));
        kd.insert(a);
        kd.insert(b);
        kd.insert(new TestPoint(new BigDecimal(1), new BigDecimal(5)));
        kd.insert(new TestPoint(new BigDecimal(6), new BigDecimal(2)));
        kd.insert(new TestPoint(new BigDecimal(3), new BigDecimal(1)));
        kd.insert(new TestPoint(new BigDecimal(13), new BigDecimal(6)));
        kd.insert(new TestPoint(new BigDecimal(31), new BigDecimal(15)));
        kd.insert(new TestPoint(new BigDecimal(3), new BigDecimal(25)));
        kd.insert(new TestPoint(new BigDecimal(13), new BigDecimal(0)));
        kd.insert(new TestPoint(new BigDecimal(0), new BigDecimal(0)));
        KDNode<TestPoint> neighbor = kd.closest(findPoint);
        System.out.println("Neighbor (x, y): " + neighbor.getValue().data[0] + ", " + neighbor.getValue().data[1]);
    }
}
