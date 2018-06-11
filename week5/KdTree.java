import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Queue;

public class KdTree {
  private static final int SQUARE_DISTANCE_UPPER = 3; // should be larger than 2
  private Node root;
  private int size;

  public KdTree() {
    root = null;
  }
  private class Node {
    private final Point2D point;
    private final RectHV rect;
    private Node left;
    private Node right;

    public Node(Point2D p, RectHV r) {
      point = p;
      rect = r;
    }
  }
  private void checkArgument(Object arg) {
    if (arg == null) throw new java.lang.IllegalArgumentException();
  }
  public boolean isEmpty() {
    return size == 0;
  }
  public int size() {
    return size;
  }
  public void insert(Point2D p) {
    checkArgument(p);
    root = insert(root, p, true, new RectHV(0, 0, 1, 1));
  }
  private Node insert(Node x, Point2D p, boolean isVertical, RectHV rect) {
    if (x == null) {
      size++;
      return new Node(p, rect);
    }
    if (x.point.equals(p)) return x;
    RectHV r = null;
    if (isVertical) {
      if (x.point.x() >= p.x()) {
        if (x.left == null) r = new RectHV(x.rect.xmin(), x.rect.ymin(), x.point.x(), x.rect.ymax());
        x.left = insert(x.left, p, !isVertical, r);
      } else {
        if (x.right == null) r = new RectHV(x.point.x(), x.rect.ymin(), x.rect.xmax(), x.rect.ymax());
        x.right = insert(x.right, p, !isVertical, r);
      }
    } else {
      if (x.point.y() >= p.y()) {
        if (x.left == null) r = new RectHV(x.rect.xmin(), x.rect.ymin(), x.rect.xmax(), x.point.y());
        x.left = insert(x.left, p, !isVertical, r);
      } else {
        if (x.right == null) r = new RectHV(x.rect.xmin(), x.point.y(), x.rect.xmax(), x.rect.ymax());
        x.right = insert(x.right, p, !isVertical, r);
      } 
    }
    return x;
  }
  public boolean contains(Point2D p) {
    checkArgument(p);
    return contains(root, p);
  }
  private boolean contains(Node x, Point2D p) {
    if (x == null) return false;
    if (x.point.equals(p)) return true;

    if (x.left != null && x.left.rect.contains(p)) return contains(x.left, p);
    return contains(x.right, p);
  }
  public void draw() {
    for (Point2D p : range(new RectHV(0, 0, 1, 1))) {
      p.draw();
    }
  }
  public Iterable<Point2D> range(RectHV rect) {
    checkArgument(rect);
    Queue<Point2D> points = new Queue<Point2D>();
    range(points, root, rect);
    return points;
  }
  private void range(Queue<Point2D> points, Node x, RectHV rect) {
    if (x == null) return;
    if (rect.contains(x.point)) points.enqueue(x.point);
    if (x.left != null && x.left.rect.intersects(rect)) range(points, x.left, rect);
    if (x.right != null && x.right.rect.intersects(rect)) range(points, x.right, rect);
  }
  public Point2D nearest(Point2D p) {
    checkArgument(p);
    if (isEmpty()) return null;
    return nearest(root, p, SQUARE_DISTANCE_UPPER, null);
  }
  private Point2D nearest(Node x, Point2D p, double min, Point2D nearest) {
    if (x == null) return nearest;
    Point2D t = nearest;
    double minimun = min;
    double distance = p.distanceSquaredTo(x.point);
    if (distance < minimun) {
      minimun = distance;
      t = x.point;
    }
    // StdOut.printf("x %s, left %s, right %s, minimun %f\n", x.point, x.left, x.right, minimun);
    double left = SQUARE_DISTANCE_UPPER;
    double right = SQUARE_DISTANCE_UPPER;
    if (x.left != null) left = x.left.rect.distanceSquaredTo(p);
    if (x.right != null) right = x.right.rect.distanceSquaredTo(p);

    if (left < right) {
      if (left < minimun && x.left != null) {
        t = nearest(x.left, p, minimun, t);
        minimun = t.distanceSquaredTo(p);
      }
      if (right < minimun && x.right != null) t = nearest(x.right, p, minimun, t);
    } else {
      if (right < minimun && x.right != null) {
        t = nearest(x.right, p, minimun, t);
        minimun = t.distanceSquaredTo(p);
      }
      if (left < minimun && x.left != null) t = nearest(x.left, p, minimun, t);
    }

    return t;
  }

  public static void main(String[] args) {

    // initialize the two data structures with point from file
    // String filename = args[0];
    // In in = new In(filename);
    KdTree st = new KdTree();
    PointSET brute = new PointSET();
    // while (!in.isEmpty()) {
    //     double x = in.readDouble();
    //     double y = in.readDouble();
    //     Point2D p = new Point2D(x, y);
    //     st.insert(p);
    //     brute.insert(p);
    // }

    // for (int i = 0; i < 100; i++) {
    //     double x = StdRandom.uniform(0.0, 1.0);
    //     double y = StdRandom.uniform(0.0, 1.0);
    //     if (!brute.nearest(new Point2D(x, y)).equals(st.nearest(new Point2D(x, y)))) {
    //       StdOut.printf("%8.6f %8.6f\n", x, y);
    //       StdOut.printf("bug");
    //     }
    // }
    st.insert(new Point2D(0.0, 1.0));
    StdOut.println(st.nearest(new Point2D(1.0, 0.0)));
  }
}