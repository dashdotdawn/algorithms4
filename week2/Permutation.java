import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
  public static void main(String[] args) {
    int k = Integer.parseInt(args[0]);
    RandomizedQueue<String> q = new RandomizedQueue<String>();
    while (!StdIn.isEmpty()) {
      q.enqueue(StdIn.readString());
    }
    while (k-- > 0) {
      StdOut.println(q.dequeue());
    }
  }
}