import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

    private final LineSegment[] segments;

    public FastCollinearPoints(Point[] points) {
        validateInput(points);

        int n = points.length;
        ArrayList<LineSegment> found = new ArrayList<>();

        Point[] sorted = Arrays.copyOf(points, n);
        Arrays.sort(sorted);

        for (int i = 0; i < n; i++) {
            Point p = sorted[i];

            Point[] bySlope = Arrays.copyOf(sorted, n);
            Arrays.sort(bySlope, p.slopeOrder());

            int j = 1;
            while (j < n) {
                double slope = p.slopeTo(bySlope[j]);
                int k = j;

                while (k < n && p.slopeTo(bySlope[k]) == slope) k++;

                if (k - j >= 3) {
                    Point min = p, max = p;
                    for (int m = j; m < k; m++) {
                        if (bySlope[m].compareTo(min) < 0) min = bySlope[m];
                        if (bySlope[m].compareTo(max) > 0) max = bySlope[m];
                    }
                    if (p.compareTo(min) == 0) {
                        found.add(new LineSegment(min, max));
                    }
                }
                j = k;
            }
        }

        segments = found.toArray(new LineSegment[0]);
    }

    public int numberOfSegments() {
        return segments.length;
    }

    public LineSegment[] segments() {
        return Arrays.copyOf(segments, segments.length);
    }

    private void validateInput(Point[] points) {
        if (points == null) throw new IllegalArgumentException("Points array is null");
        for (Point p : points) {
            if (p == null) throw new IllegalArgumentException("Point is null");
        }
        Point[] copy = Arrays.copyOf(points, points.length);
        Arrays.sort(copy);
        for (int i = 0; i < copy.length - 1; i++) {
            if (copy[i].compareTo(copy[i + 1]) == 0)
                throw new IllegalArgumentException("Duplicate point: " + copy[i]);
        }
    }
}
