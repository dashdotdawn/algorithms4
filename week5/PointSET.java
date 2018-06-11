import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Queue;

public class PointSET {
  private final SET<Point2D> points;

  public PointSET() {
    points = new SET<Point2D>();
  }
  private void checkArgument(Object arg) {
    if (arg == null) throw new java.lang.IllegalArgumentException();
  }
  public boolean isEmpty() {
    return points.isEmpty();
  }
  public int size() {
    return points.size();
  }
  public void insert(Point2D p) {
    checkArgument(p);
    points.add(p);
  }
  public boolean contains(Point2D p) {
    checkArgument(p);
    return points.contains(p);
  }
  public void draw() {
    for (Point2D p: points)
      p.draw();
  }
  public Iterable<Point2D> range(RectHV rect) {
    checkArgument(rect);
    Queue<Point2D> rangePoints = new Queue<Point2D>();
    for (Point2D p: points)
      if (rect.contains(p)) rangePoints.enqueue(p);
    return rangePoints;
  }
  public Point2D nearest(Point2D p) {
    checkArgument(p);
    Point2D point = null;
    double minDistance = 3;
    for (Point2D that: points) {
      double distance = p.distanceSquaredTo(that);
      if (distance < minDistance) {
        minDistance = distance;
        point = that;
      }
    }
    return point;
  }

  public static void main(String[] args) {
    String filename = args[0];
    In in = new In(filename);
    PointSET brute = new PointSET();
    while (!in.isEmpty()) {
      double x = in.readDouble();
      double y = in.readDouble();
      Point2D p = new Point2D(x, y);
      brute.insert(p);
    }
    StdOut.println(brute.nearest(new Point2D(0.5, 0.1)));
    StdOut.println(brute.nearest(new Point2D(0.1, 0.5)));
    StdOut.println(brute.nearest(new Point2D(0.7, 0.5)));
    StdOut.println(brute.nearest(new Point2D(0.5, 0.9)));
  }
}