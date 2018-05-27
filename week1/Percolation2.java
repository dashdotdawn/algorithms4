import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
  private static final int OPEN = 1;  
  private static final int CONNECTED_TO_TOP = 1 | 2;
  private static final int CONNECTED_TO_BOTTOM = 1 | 4;
  private static final int PERCOLATE = 1 | 2 | 4;
  private final int sz;
  private final WeightedQuickUnionUF uf;
  private int[] sites;
  private int openSites;
  private boolean isPercolates;

  public Percolation(int n) {
    if (n <= 0) throw new java.lang.IllegalArgumentException();
    sz = n;
    uf = new WeightedQuickUnionUF(n * n);
    sites = new int[n * n]; // 000 BIT represents status: rtl-> isOpen, isConnectToTop, isConnectToBottom
  }

  private void checkIndices(int row, int col) {
    if (row > sz || row < 1 || col > sz || col < 1) throw new java.lang.IllegalArgumentException();
  }
  private int calculateIndex(int row, int col) {
    return (row - 1) * sz + col - 1;
  }
  private void union(int row, int col, int center) {
    if (row > sz || row < 1 || col > sz || col < 1) return;
    int neighbor = calculateIndex(row, col);
    if (sites[neighbor] > 0) {
      int neighborStatus = sites[uf.find(neighbor)];
      int centerStatus = sites[uf.find(center)];
      uf.union(center, neighbor);
      int newIndex = uf.find(center);
      sites[newIndex] = neighborStatus | centerStatus;
      if (sites[newIndex] == PERCOLATE) isPercolates = true;
    }

  }
  public void open(int row, int col) {
    checkIndices(row, col);
    int index = calculateIndex(row, col);
    if (sites[index] == 0) {
      openSites++;
      if (sz == 1) {
        sites[index] = PERCOLATE;
        isPercolates = true;
      } else {
        sites[index] = OPEN;
        if (row == 1) sites[index] = CONNECTED_TO_TOP;
        if (row == sz) sites[index] = CONNECTED_TO_BOTTOM;
        union(row - 1, col, index);
        union(row + 1, col, index);
        union(row, col - 1, index);
        union(row, col + 1, index);
      }
    }
  }
  public boolean isOpen(int row, int col) {
    checkIndices(row, col);
    int index = calculateIndex(row, col);
    return sites[index] > 0;
  }
  public boolean isFull(int row, int col) {
    checkIndices(row, col);
    int index = calculateIndex(row, col);
    int status = sites[uf.find(index)];
    return (status & CONNECTED_TO_TOP) == CONNECTED_TO_TOP;
  }
  public int numberOfOpenSites() {
    return openSites;
  }
  public boolean percolates() {
    return isPercolates;
  }

  public static void main(String[] args) {
    int n = StdIn.readInt();
    Percolation p = new Percolation(n);
    while (!StdIn.isEmpty()) {
      int row = StdIn.readInt();
      int col = StdIn.readInt();
      p.open(row, col);
    }
    StdOut.printf("%d of open sites\n", p.numberOfOpenSites());
    StdOut.println(p.percolates());
  }
}
