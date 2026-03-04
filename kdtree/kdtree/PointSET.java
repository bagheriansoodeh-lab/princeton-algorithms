import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import java.util.ArrayList;
import java.util.TreeSet;

public class PointSET {

    private final TreeSet<Point2D> set;

    public PointSET() {
        set = new TreeSet<>();
    }

    public boolean isEmpty() {
        return set.isEmpty();
    }

    public int size() {
        return set.size();
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Point is null");
        set.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Point is null");
        return set.contains(p);
    }

    public void draw() {
        for (Point2D p : set) p.draw();
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("Rectangle is null");
        ArrayList<Point2D> result = new ArrayList<>();
        for (Point2D p : set)
            if (rect.contains(p)) result.add(p);
        return result;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Point is null");
        if (isEmpty()) return null;
        Point2D nearest = null;
        double bestDist = Double.POSITIVE_INFINITY;
        for (Point2D q : set) {
            double d = p.distanceSquaredTo(q);
            if (d < bestDist) { bestDist = d; nearest = q; }
        }
        return nearest;
    }

    public static void main(String[] args) {
        PointSET ps = new PointSET();
        ps.insert(new Point2D(0.1, 0.2));
        ps.insert(new Point2D(0.5, 0.5));
        ps.insert(new Point2D(0.9, 0.8));
        System.out.println("size: " + ps.size());
        System.out.println("contains (0.5,0.5): " + ps.contains(new Point2D(0.5, 0.5)));
        System.out.println("nearest to (0.4,0.4): " + ps.nearest(new Point2D(0.4, 0.4)));
        System.out.println("range [0,0.6]x[0,0.6]:");
        for (Point2D p : ps.range(new RectHV(0, 0, 0.6, 0.6))) System.out.println("  " + p);
    }
}
