import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import java.util.Arrays;
import java.util.ArrayList;

public class BruteCollinearPoints {
  private final LineSegment[] segments;
  public BruteCollinearPoints(Point[] points) {
    if (points == null) throw new java.lang.IllegalArgumentException();
    int n = points.length;
    Point[] copyPoints = new Point[n];
    for (int i = 0; i < n; i++) {
      if (points[i] == null) throw new java.lang.IllegalArgumentException();
      copyPoints[i] = points[i];
    }
    ArrayList<LineSegment> s = new ArrayList<LineSegment>();
    Arrays.sort(copyPoints);
    for (int i = 1; i < n; i++) if (copyPoints[i].compareTo(copyPoints[i - 1]) == 0) throw new java.lang.IllegalArgumentException();
    for (int i = 0; i < n; i++) {
      for (int j = i + 1; j < n; j++) {
        for (int k = j + 1; k < n; k++) {
          for (int m = k + 1; m < n; m++) {
            double slope = copyPoints[i].slopeTo(copyPoints[j]);
            if (copyPoints[i].slopeTo(copyPoints[k]) == slope && 
              copyPoints[i].slopeTo(copyPoints[m]) == slope) {
              s.add(new LineSegment(copyPoints[i], copyPoints[m]));
            }
          }
        }
      }
    }
    segments = s.toArray(new LineSegment[s.size()]);
  }
  public int numberOfSegments() {
    return segments.length;
  }
  public LineSegment[] segments() {
    LineSegment[] s = new LineSegment[segments.length];
    for (int i = 0; i < segments.length; i++) {
      s[i] = segments[i];
    }
    return s;
  }

  public static void main(String[] args) {

      // read the n points from a file
      In in = new In(args[0]);
      int n = in.readInt();
      Point[] points = new Point[n];
      for (int i = 0; i < n; i++) {
          int x = in.readInt();
          int y = in.readInt();
          points[i] = new Point(x, y);
      }

      // draw the points
      StdDraw.enableDoubleBuffering();
      StdDraw.setXscale(0, 32768);
      StdDraw.setYscale(0, 32768);
      for (Point p : points) {
          p.draw();
      }
      StdDraw.show();

      // print and draw the line segments
      BruteCollinearPoints collinear = new BruteCollinearPoints(points);
      for (LineSegment segment : collinear.segments()) {
          StdOut.println(segment);
          segment.draw();
      }
      StdDraw.show();
  }
}
