import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

// Models an N-by-N percolation system.
public class Percolation {
    private WeightedQuickUnionUF first; // UnionFind
    private WeightedQuickUnionUF backwashy; //BackWash
    private int source; 
    private int sink;
    private int N;
    private boolean[][] percs; // N * N Array Of Booleans
    private int counter = 0;
    
    // Create an N-by-N grid, with all sites blocked.
    public Percolation(int N) {
        if (N <= 0) { // This will Throw Exception if N = 0 Or N < 0
     throw new IllegalArgumentException("You've entered an invalid Argument");
        }
        this.N = N;
        this.first = new WeightedQuickUnionUF(N * N + 2); 
        // The 2 is to Make sure that you include the Sink and Source
        this.backwashy = new WeightedQuickUnionUF(N * N + 1); 
        // No Sink is needed for backwash.
        this.counter = counter;
        this.percs = new boolean[N][N];
        this.sink = (N * N + 1);
        this.source = 0;
        for (int i = 0; i <= N; i++) {
            first.union(source, encode(0, i)); // Connects top to source
            backwashy.union(source, encode(0, i));
            first.union(sink, encode(N-1, i)); // Connects bottom to sink
        }
    }

    // Open site (i, j) if it is not open already.
    public void open(int i, int j) {
        if (i < 0 || i > N - 1 || j < 0 || j > N - 1) {
    throw new IndexOutOfBoundsException("You've entered an illegal index");
        }
        if (!isOpen(i, j)) {
          percs[i][j] = true; // it is all false by default to set to true.
          counter++;
          if ((j-1) >= 0 && isOpen(i, j - 1)) { //Checks neighbors 
             first.union(encode(i, j), encode(i, (j-1)));
             backwashy.union(encode(i, j), encode(i, (j-1)));
          }
          if ((j+1) < N && isOpen(i, j + 1)) {
             first.union(encode(i, j), encode(i, (j+1)));
             backwashy.union(encode(i, j), encode(i, (j+1)));
          }
          if ((i-1) >= 0 && isOpen(i - 1, j)) {
             first.union(encode(i, j), encode((i-1), j));
             backwashy.union(encode(i, j), encode((i-1), j));
          }
          if ((i+1) < N && isOpen(i + 1, j)) {
             first.union(encode(i, j), encode((i+1), j));
             backwashy.union(encode(i, j), encode((i+1), j)); 
          }
        }
    }  

    // Is site (i, j) open?
    public boolean isOpen(int i, int j) {
        if (i < 0 || i > N - 1 || j < 0 || j > N - 1) {
    throw new IndexOutOfBoundsException("You've entered an illegal index");
        }
        if (percs[i][j]) {
            return true;
        }
        return false;

    }

    // Is site (i, j) full?
    public boolean isFull(int i, int j) {
        if (i < 0 || i > N - 1 || j < 0 || j > N - 1) {
     throw new IndexOutOfBoundsException("You've entered an illegal index"); 
        }
        if (percs[i][j] && backwashy.connected(encode(i, j), source)) {
            return true;
        }
        return false;
    }

    // Number of open sites.
    public int numberOfOpenSites() {
        return counter;
    }

    // Does the system percolate?
    public boolean percolates() {
        if (first.connected(source, sink)) { //connect source & sink
            return true;
        }
        return false;
    }

    // An integer ID (1...N) for site (i, j).
    private int encode(int i, int j) {
        return i * N + j + 1;

    }
    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename); 
        int N = in.readInt();
        Percolation perc = new Percolation(N);
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            perc.open(i, j);
        }
        StdOut.println(perc.numberOfOpenSites() + " open sites");
        if (perc.percolates()) {
            StdOut.println("percolates");
        }
        else {
            StdOut.println("does not percolate");
        }
        // Check if site (i, j) optionally specified on the command line
        // is full.
        if (args.length == 3) {
            int i = Integer.parseInt(args[1]);
            int j = Integer.parseInt(args[2]);
            StdOut.println(perc.isFull(i, j));
        }
    }
}