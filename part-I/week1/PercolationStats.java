
public class PercolationStats {
    private double [] attemps;
    // perform T experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) throw new IllegalArgumentException();
        attemps = new double [T];
        for (int i = 0; i < T; i++) {
            Percolation p = new Percolation(N);
            int steps = 0;
            while (!p.percolates()) {
                int r = StdRandom.uniform(N)+1;
                int c = StdRandom.uniform(N)+1;
                if (!p.isOpen(r, c)) {
                    p.open(r, c);
                    steps++;
                }
            }
            attemps[i] = (double) steps / (N*N);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(attemps);
    }      
      // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(attemps);
    } 

    // returns lower bound of the 95% confidence interval
    public double confidenceLo() {
        return mean()-((1.96*stddev())/Math.sqrt(attemps.length)); 
    }

    // returns upper bound of the 95% confidence interval
    public double confidenceHi() {
        return mean()+((1.96*stddev())/Math.sqrt(attemps.length)); 
    }

    // test client
    public static void main(String[] args) {
        int N = 10; 
        int T = 1000;
        double lo, hi;
        if (args.length == 2) {
            N = Integer.parseInt(args[0]);
            T = Integer.parseInt(args[1]);
        }

        PercolationStats ps = new PercolationStats(N, T); 
        StdOut.print("mean = "+ps.mean()+"\n");
        StdOut.print("std dev = "+ps.stddev()+"\n");
        lo = ps.confidenceLo();
        hi = ps.confidenceHi();        
        StdOut.print("95% confidence interval = "+lo+", "+hi);
    }
}
