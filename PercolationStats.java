import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    private final int t;
    private final int size;
    private final double[] percolationThreshold;
    private static final double CONFIDENCE_95 = 1.96;  

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException("n and trials should be greater than zero.");
        t = trials;
        percolationThreshold = new double[t];
        size = n;
        for (int i = 0; i < t; i++) {
            percolationThreshold[i] = getThreshold();
        }
    }
    public double mean() {
        return StdStats.mean(percolationThreshold);
    }
    public double stddev() {
        return StdStats.stddev(percolationThreshold);
    }
    public double confidenceLo() {
        return mean() - CONFIDENCE_95 * stddev() / Math.sqrt(t);
    }
    public double confidenceHi() {
        return mean() + CONFIDENCE_95 * stddev() / Math.sqrt(t);
    }
    private double getThreshold() {
        Percolation p = new Percolation(size);
        while (!p.percolates()) {
            p.open(StdRandom.uniform(size) + 1, StdRandom.uniform(size) + 1);
        }
        return p.numberOfOpenSites() / (double) (size * size);
    }    
    
    public static void main(String[] args) {
        int n = StdIn.readInt();
        int trials = StdIn.readInt();
        PercolationStats stats = new PercolationStats(n, trials);
        
        StdOut.println("mean = " + stats.mean());
        StdOut.println("stddev = " + stats.stddev());
        StdOut.println("95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
    }
}