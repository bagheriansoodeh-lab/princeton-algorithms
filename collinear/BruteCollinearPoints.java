import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private final LineSegment[] segments;

    public BruteCollinearPoints(Point[] points) {
        validateInput(points);

        int n = points.length;
        Point[] pts = Arrays.copyOf(points, n);
        Arrays.sort(pts);

        ArrayList<LineSegment> found = new ArrayList<>();

        for (int i = 0; i < n - 3; i++) {
            for (int j = i + 1; j < n - 2; j++) {
                for (int k = j + 1; k < n - 1; k++) {
                    for (int l = k + 1; l < n; l++) {
                        Point p = pts[i], q = pts[j], r = pts[k], s = pts[l];
                        if (p.slopeTo(q) == p.slopeTo(r) && p.slopeTo(q) == p.slopeTo(s)) {
                            found.add(new LineSegment(p, s));
                        }
                    }
                }
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
