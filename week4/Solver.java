import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {
  private final int moves;
  private SearchNode lastNode;

  public Solver(Board initial) {
    if (initial == null) throw new IllegalArgumentException();
    MinPQ<SearchNode> pq = new MinPQ<SearchNode>();
    SearchNode initialNode = new SearchNode(initial, null);
    pq.insert(initialNode);
    MinPQ<SearchNode> twinPQ = new MinPQ<SearchNode>();
    SearchNode twinInitialNode = new SearchNode(initial.twin(), null);
    twinPQ.insert(twinInitialNode);
    while (true) {
      SearchNode currentNode = pq.delMin();
      Board currentBoard = currentNode.board;
      SearchNode twinCurrentNode = twinPQ.delMin();
      Board twinCurrentBoard = twinCurrentNode.board;
      if (currentBoard.isGoal()) {
        moves = currentNode.moves;
        lastNode = currentNode;
        break;
      }
      if (twinCurrentBoard.isGoal()) {
        moves = -1;
        break;
      }
      for (Board neigbbor : currentBoard.neighbors()) {
        if (currentNode.predecessor != null && neigbbor.equals(currentNode.predecessor.board)) continue;
        SearchNode next = new SearchNode(neigbbor, currentNode);
        pq.insert(next);
      }
      for (Board neigbbor : twinCurrentBoard.neighbors()) {
        if (twinCurrentNode.predecessor != null && neigbbor.equals(twinCurrentNode.predecessor.board)) continue;
        SearchNode next = new SearchNode(neigbbor, twinCurrentNode);
        twinPQ.insert(next);
      }
    }
  }
  private class SearchNode implements Comparable<SearchNode> {
    private final Board board;
    private final int moves;
    private final int priority;
    private final SearchNode predecessor;
    public SearchNode(Board b, SearchNode pre) {
      board = b;
      predecessor = pre;
      if (pre != null) moves = pre.moves + 1;
      else moves = 0;
      priority = moves + b.manhattan();
    }
    public int compareTo(SearchNode that) {
      return this.priority - that.priority;
    }
  }
  public boolean isSolvable() {
    return moves != -1;
  }
  public int moves() {
    return moves;
  }
  public Iterable<Board> solution() {
    if (moves == -1) return null;
    Stack<Board> path = new Stack<Board>();
    SearchNode node = lastNode;
    while (node != null) {
      path.push(node.board);
      node = node.predecessor;
    }
    return path;
  }

  public static void main(String[] args) {
    // create initial board from file
    In in = new In(args[0]);
    int n = in.readInt();
    int[][] blocks = new int[n][n];
    for (int i = 0; i < n; i++)
      for (int j = 0; j < n; j++)
        blocks[i][j] = in.readInt();  
    Board initial = new Board(blocks);

    // solve the puzzle
    Solver solver = new Solver(initial);

    // print solution to standard output
    if (!solver.isSolvable())
      StdOut.println("No solution possible");
    else {
      StdOut.println("Minimum number of moves = " + solver.moves());
      for (Board board : solver.solution())
        StdOut.println(board);
    }
  }
}
