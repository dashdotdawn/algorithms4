import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;

public class Board {
  private final int[][] grid;
  private final int n;
  private int manhattan;
  private int hamming;
  private int blank;

  public Board(int[][] blocks) {
    n = blocks.length;
    grid = new int[n][n];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        int index = blocks[i][j];
        grid[i][j] = index;
        if (index != 0) {
          int goalX = (index - 1) / n;
          int goalY = (index - 1) % n;
          if (goalX != i || goalY != j) hamming++;
          manhattan += Math.abs(goalX - i) + Math.abs(goalY - j);
        } else {
          blank = i * n + j;
        }
      }
    }
  }

  public int dimension() {
    return n;
  }
  public int hamming() {
    return hamming;
  }
  public int manhattan() {
    return manhattan;
  }
  public boolean isGoal() {
    return manhattan == 0;
  }
  public Board twin() {
    int x1 = -1, x2 = -1, y1 = -1, y2 = -1;
    for (int i = 0; i < n && x2 == -1; i++)
      for (int j = 0; j < n && x2 == -1; j++)
        if (grid[i][j] != 0)
          if (x1 == -1) {
            x1 = i;
            y1 = j;
          } else if (x2 == -1) {
            x2 = i;
            y2 = j;
          }
    return swapBoard(x1, y1, x2, y2);
  }
  public boolean equals(Object y) {
    if (y == this) return true;
    if (y == null) return false;
    if (y.getClass() != this.getClass()) return false;
    Board that = (Board) y;
    if (that.dimension() != dimension() || that.hamming() != hamming() || that.manhattan() != manhattan()) return false;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (grid[i][j] != that.grid[i][j]) return false;
      }
    }
    return true;
  }
  public Iterable<Board> neighbors() {
    Stack<Board> neighbors = new Stack<Board>();
    int blankX = blank / n;
    int blankY = blank % n;

    if (blankX + 1 < n) neighbors.push(swapBoard(blankX + 1, blankY, blankX, blankY));
    if (blankX > 0) neighbors.push(swapBoard(blankX - 1, blankY, blankX, blankY));
    if (blankY + 1 < n) neighbors.push(swapBoard(blankX, blankY + 1, blankX, blankY));
    if (blankY > 0) neighbors.push(swapBoard(blankX, blankY - 1, blankX, blankY));
    return neighbors;
  }
  private Board swapBoard(int x1, int y1, int x2, int y2) {
    int[][] swapBlocks = new int[n][n];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        swapBlocks[i][j] = grid[i][j];
      }
    }
    int index = swapBlocks[x1][y1];
    swapBlocks[x1][y1] = swapBlocks[x2][y2];
    swapBlocks[x2][y2] = index;
    return new Board(swapBlocks);
  }
  public String toString() {
    String s = n + "\n";
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        s += String.format("%2d ", grid[i][j]);
      }
      s += "\n";
    }
    return s;
  }

  public static void main(String[] args) {
    In in = new In(args[0]);
    int n = in.readInt();
    int[][] blocks = new int[n][n];
    for (int i = 0; i < n; i++)
      for (int j = 0; j < n; j++)
        blocks[i][j] = in.readInt();  
    Board initial = new Board(blocks);
    StdOut.println(initial);
    StdOut.println(initial.isGoal());
    StdOut.println(initial.twin());
    StdOut.println(initial.hamming());
    StdOut.println(initial.manhattan());
    for (Board neigbbor : initial.neighbors()) {
      StdOut.print("neigbbor: \n" + neigbbor);
    }
  }
}
