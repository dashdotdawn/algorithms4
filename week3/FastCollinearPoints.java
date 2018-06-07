import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import java.util.Arrays;
import java.util.ArrayList;

public class FastCollinearPoints {
  private final LineSegment[] segments;

  public FastCollinearPoints(Point[] points) {
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
      if (copyPoints[i] == null) throw new java.lang.IllegalArgumentException();
      Point p = copyPoints[i];
      Arrays.sort(copyPoints, p.slopeOrder());
      int linePoints;
      for (int j = 1; j < n; j += linePoints) {
        linePoints = 1;
        double slope = p.slopeTo(copyPoints[j]);
        while (j + linePoints < n && p.slopeTo(copyPoints[j + linePoints]) == slope) {
          linePoints++;
        }
        if (linePoints > 2) {
          Arrays.sort(copyPoints, j, j + linePoints);
          Point min =  copyPoints[j];
          if (p.compareTo(min) < 0) {
            s.add(new LineSegment(p, copyPoints[j + linePoints - 1]));
          }
        }
      }
      Arrays.sort(copyPoints);
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
      FastCollinearPoints collinear = new FastCollinearPoints(points);
      for (LineSegment segment : collinear.segments()) {
          StdOut.println(segment);
          segment.draw();
      }
      StdDraw.show();
  }
}