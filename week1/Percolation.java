import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Percolation {
    private final int size;
    private final WeightedQuickUnionUF uf;
    private final WeightedQuickUnionUF isFullUF;
    private final int virtualTop;
    private final int virtualBottom;
    private boolean[][] grid;
    private int openSitesCount;

    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("n should be greater than zero.");
        size = n;
        uf = new WeightedQuickUnionUF(n * n + 2);
        isFullUF = new WeightedQuickUnionUF(n * n + 2);
        virtualTop = 0;
        virtualBottom = n * n + 1;
        grid = new boolean[n][n]; // default value false
        openSitesCount = 0;
    }

    private int getIndex(int row, int col) {
        return (row - 1) * size + col;
    }    
    private void connectSites(int centerIndex, int row, int col) {
        if (row == 0) {
            uf.union(centerIndex, virtualTop);
            isFullUF.union(centerIndex, virtualTop);
        } else if (row == size + 1) {
            uf.union(centerIndex, virtualBottom);
        } else if (isOpen(row, col)) {
            uf.union(centerIndex, getIndex(row, col));
            isFullUF.union(centerIndex, getIndex(row, col));
        }
    }

    public void open(int row, int col) {
        if (row <= 0 || row > size || col <= 0 || col > size) {
            throw new IllegalArgumentException("the row and column indices are integers between 1 and n");
        }
        if (!isOpen(row, col)) {
            int index = getIndex(row, col);
            grid[row - 1][col - 1] = true;
            openSitesCount++;
            if (row - 1 >= 0) connectSites(index, row - 1, col);
            if (row + 1 <= size + 1) connectSites(index, row + 1, col);
            if (col - 1 > 0) connectSites(index, row, col - 1);
            if (col + 1 <= size) connectSites(index, row, col + 1);
        }
        
    }
    public boolean isOpen(int row, int col) {
        if (row <= 0 || row > size || col <= 0 || col > size) {
            throw new IllegalArgumentException("the row and column indices are integers between 1 and n");
        }
        return grid[row - 1][col - 1];
    }
    public boolean isFull(int row, int col) {
        if (row <= 0 || row > size || col <= 0 || col > size) {
            throw new IllegalArgumentException("the row and column indices are integers between 1 and n");
        }
        return isOpen(row, col) && isFullUF.connected(virtualTop, getIndex(row, col));
    }
    public int numberOfOpenSites() {
        return openSitesCount;
    }
    public boolean percolates() {
        return uf.connected(virtualTop, virtualBottom);
    }

    public static void main(String[] args) {
        int n = StdIn.readInt();
        Percolation percolation = new Percolation(n);
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            percolation.open(p, q);
            if (percolation.numberOfOpenSites() == 6) StdOut.println(percolation.isFull(3, 1));
        }
        StdOut.println(percolation.numberOfOpenSites() + " open sites");
        StdOut.println(percolation.percolates());
    }
}