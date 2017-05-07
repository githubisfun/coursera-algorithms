/*************************************************************************
 *  Compilation:  javac BruteCollinearPoints.java
 *  Execution:    none
 *  Dependencies: Point.java
 *
 *
 *************************************************************************/
 
 import java.util.Arrays;
 import java.util.ArrayList;


 public class BruteCollinearPoints {
    private LineSegment[] segments;

    public BruteCollinearPoints(Point[] pointsCopy) {
        int n = pointsCopy.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (pointsCopy[i].compareTo(pointsCopy[j]) == 0) 
                    throw new IllegalArgumentException("Repeated points");
            }
        }
        ArrayList<LineSegment> foundSegments = new ArrayList<>();
        Point[] points = Arrays.copyOf(pointsCopy, pointsCopy.length);
        Arrays.sort(points);

        for (int p = 0; p < n - 3; p++) {
            for (int q = p + 1; q < n - 2; q++) {
                for (int r = q + 1; r < n - 1; r++) {
                    for (int s = r + 1; s < n; s++) {
                        double slope1 = points[p].slopeTo(points[q]);
                        double slope2 = points[p].slopeTo(points[r]);
                        double slope3 = points[p].slopeTo(points[s]);
                        if ((Double.compare(slope1, slope2) == 0) && (Double.compare(slope1, slope3) == 0)) {
                            foundSegments.add(new LineSegment(points[p], points[s]));
                        }
                    }
                }
            }
        }
        segments = foundSegments.toArray(new LineSegment[foundSegments.size()]);
    }

    /**
     * Return the number of line segments.
     */
    public int numberOfSegments() {
        return segments.length;
    }

    /**
     * Return line segements.
     */
    public LineSegment[] segments() {
        // return a copy of array to preserve encapsulation
        return Arrays.copyOf(segments, numberOfSegments());
    }

    public static void main(String[] args) {

    }
 }