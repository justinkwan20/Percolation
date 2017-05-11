import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

// Estimates percolation threshold for an N-by-N percolation system.
public class PercolationStats {
    private int T;
    private double[] p;
    private int N;
    
    // Perform T independent experiments (Monte Carlo simulations) on an 
    // N-by-N grid.
    
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) { //Will throw Exception if N & T not legal
      throw new IllegalArgumentException("You've entered an illegal argument");
     }
        
        this.N = N;
        this.T = T;
        this.p =  new double [T];
        for (int h = 0; h < T; h++) {
            Percolation percs = new Percolation(N);
            //this loop will iterate if it is not percolated
            while (!percs.percolates()) { 
                int i = StdRandom.uniform(0, N); //its random value for i
                int j = StdRandom.uniform(0, N); //its random value for j
                if (!percs.isOpen(i, j)) { //checks if its not open at i,j
                    percs.open(i, j); //opens i,j
                }
        }
        double value = 1.0 * percs.numberOfOpenSites() / (N*N);
        //number of open sites over total amount to get fraction.
        p[h] = value;
     }
    }
   
    
    // Sample mean of percolation threshold.
    public double mean() {
       return StdStats.mean(p);
    }

    // Sample standard deviation of percolation threshold.
    public double stddev() {
        return StdStats.stddev(p);
    }

    // Low endpoint of the 95% confidence interval.
    public double confidenceLow() {
        return (mean() - (1.96*stddev()/Math.sqrt(T)));
    }

    // High endpoint of the 95% confidence interval.
    public double confidenceHigh() {
        return (mean() + (1.96*stddev()/Math.sqrt(T)));
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(N, T);
        StdOut.printf("mean           = %f\n", stats.mean());
        StdOut.printf("stddev         = %f\n", stats.stddev());
        StdOut.printf("confidenceLow  = %f\n", stats.confidenceLow());
        StdOut.printf("confidenceHigh = %f\n", stats.confidenceHigh());
        
    }
}

