import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
  private static final double CONFIDENCE_95 = 1.96;  
  private final double mean;
  private final double stddev;
  private final double confidenceLo;
  private final double confidenceHi;

  public PercolationStats(int n, int trials) {
    if (n <= 0 || trials <= 0) throw new java.lang.IllegalArgumentException();
    double[] tests = new double[trials];
    for (int i = 0; i < trials; i++) {
      Percolation p = new Percolation(n);
      while (!p.percolates()) {
        p.open(StdRandom.uniform(n) + 1, StdRandom.uniform(n) + 1);
      }
      tests[i] = 1.0 * p.numberOfOpenSites() / (n * n);
    }
    mean = StdStats.mean(tests);
    stddev = StdStats.stddev(tests);
    confidenceLo = mean - CONFIDENCE_95 * stddev / Math.sqrt(trials);
    confidenceHi = mean + CONFIDENCE_95 * stddev / Math.sqrt(trials);
  }
  public double mean() {
    return mean;
  }
  public double stddev() {
    return stddev;
  }
  public double confidenceLo() {
    return confidenceLo;
  }
  public double confidenceHi() {
    return confidenceHi;
  }

  public static void main(String[] args) {
    int n = Integer.parseInt(args[0]);
    int trials = Integer.parseInt(args[1]);
    PercolationStats s = new PercolationStats(n, trials);
    StdOut.println("mean                    = " + s.mean());
    StdOut.println("stddev                  = " + s.stddev());
    StdOut.printf("95%% confidence interval = [%f, %f]\n", s.confidenceLo(), s.confidenceHi());
  }
}
