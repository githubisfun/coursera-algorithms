/*************************************************************************
 *  Compilation:  javac FastCollinearPoints.java
 *  Execution:    none
 *  Dependencies: Point.java
 *
 *  Find all lines containing 4 or more points.
 *************************************************************************/
import java.util.Arrays;
import java.util.ArrayList;

public class FastCollinearPoints {

    private ArrayList<LineSegment> segments = new ArrayList<>();    // lines containing 4 or more points
    private ArrayList<Point> xPoints, yPoints = new ArrayList<>();  // two endpoints of lines
    private ArrayList<Integer> segLens = new ArrayList<>();         // length of lines
    private ArrayList<Double> slopes = new ArrayList<>();           // slope of lines


    /**
     * Find all lines containing 4 or more points. 
     * Only the line with maximal length will be recorded. 
     * @param pointsCopy points on 2-D plane
     */
    public FastCollinearPoints(Point[] pointsCopy) {
        int n = pointsCopy.length;
        // remove duplicated points
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (pointsCopy[i].compareTo(pointsCopy[j]) == 0) 
                    throw new IllegalArgumentException("Repeated points not allowed!");
            }
        }

        Point[] points1 = Arrays.copyOf(pointsCopy, n);
        Arrays.sort(points1);

        for (Point startPoint : points1) {
            Point[] points = Arrays.copyOf(points1, n);
            Arrays.sort(points, startPoint.slopeOrder());
            ArrayList<Point> segPoints = new ArrayList<>();
            double slope = 0;
            double prevSlope = Double.NEGATIVE_INFINITY;

            for (int i = 1; i < n; i++) {
                slope = startPoint.slopeTo(points[i]);
                if (Double.compare(slope, prevSlope) == 0) {
                    segPoints.add(points[i]);
                }
                else {
                    if (segPoints.size() >= 3)  {
                        segPoints.add(startPoint);
                        addSegment(segPoints.toArray(new Point[segPoints.size()]), prevSlope);
                    }
                    segPoints.clear();
                    segPoints.add(points[i]);
                }
                prevSlope = slope;
            }

            if (segPoints.size() >= 3)  {
                segPoints.add(startPoint);
                addSegment(segPoints.toArray(new Point[segPoints.size()]), prevSlope);
            }


        }

    }


    /**
     * @param toAdd the line to be added
     * @param slope the slope of the line
     */
    private void addSegment(Point[] toAdd, double slope) {
        int segLen;
        segLen = toAdd.length;

        Point xPoint, yPoint;
        xPoint = toAdd[segLen - 1];
        yPoint = toAdd[segLen - 2];


        boolean deleteThisOne;
        deleteThisOne = false;

        ArrayList<Point> newXPoints = new ArrayList<>();
        ArrayList<Point> newYPoints = new ArrayList<>();
        ArrayList<Integer> newSegLens = new ArrayList<>();
        ArrayList<LineSegment> newSegments = new ArrayList<>();
        ArrayList<Double> newSlopes = new ArrayList<>();

        if (segments.size() != 0) {

            for (int i = 0; i < segments.size(); i++) {
                deleteThisOne = false;
                if ((Double.compare(slopes.get(i), slope) == 0) && 
                    ((xPoint.compareTo(xPoints.get(i)) == 0) || 
                        (yPoint.compareTo(yPoints.get(i)) == 0) || 
                            (yPoint.compareTo(xPoints.get(i)) == 0) || 
                                (xPoint.compareTo(yPoints.get(i)) == 0))) {
                    if (segLens.get(i) >= segLen) {
                        return;
                    }
                    else {
                        deleteThisOne = true;
                    }
                }
                if (!deleteThisOne) {
                    newSegments.add(segments.get(i));
                    newXPoints.add(xPoints.get(i));
                    newYPoints.add(yPoints.get(i));
                    newSegLens.add(segLens.get(i));
                    newSlopes.add(slopes.get(i));
                }
            }
        }

        newSegments.add(new LineSegment(xPoint, yPoint));
        newXPoints.add(xPoint);
        newYPoints.add(yPoint);
        newSegLens.add(segLen);
        newSlopes.add(slope);

        xPoints = newXPoints;
        yPoints = newYPoints;
        segLens = newSegLens;
        segments = newSegments;
        slopes = newSlopes;

    }

    /**
     * Return the number of line segments.
     * @return
     */
    public int numberOfSegments() {
        return segments.size();
    }

    /**
     * Return line segments
     * @return
     */
    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[segments.size()]);
    }

    public static void main(String[] args) {

    }
}