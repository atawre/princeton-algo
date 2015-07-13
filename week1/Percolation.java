

public class Percolation {
    private boolean []grid;
    private int N;
    private int vtop;
    private int vbottom;
    private WeightedQuickUnionUF uf1, uf2;
    private enum direction { self, left, right, up, down };

    // create N-by-N grid, with all sites blocked
    public Percolation(int N) {
        if (N <= 0) throw new IllegalArgumentException();
        this.N = N;
        grid = new boolean[N*N + 2];
        for (int i = 0; i < N*N; i++)
            grid[i] = false;
        uf1 = new WeightedQuickUnionUF(N*N + 2);
        uf2 = new WeightedQuickUnionUF(N*N + 1);       
        vtop = N*N;
        vbottom = N*N + 1;
        grid[vtop] = true;
        grid[vbottom] = true;
    }

    //returns the ID for UF data structure corresponding to grid.
    private int xy2id(int i, int j, direction d) {
        int id;
        int ii = i-1;
        int jj = j-1;
        switch (d) {
            case self:
                id = ii*N + jj;
                break;
            case left:
                if (jj == 0)
                    id = -1;
                else
                    id = ii*N + (jj-1);
                break;
            case right:
                if (jj == N-1)
                    id = -1;
                else
                    id = ii*N + (jj+1);
                break;
            case up:
                if (ii == 0)
                    id = vtop;
                else
                    id = (ii-1)*N + jj;
                break;
            case down:
                if (ii == N-1)
                    id = vbottom;
                else
                    id = (ii+1)*N + jj;
                break;
            default:
                System.out.println("No direction specified ?");
                id = -1;
        }
        return id;
    }

    // open site (row i, column j) if it is not open already
    public void open(int i, int j) {
        validate(i, j);
        if (isOpen(i, j)) return;
        grid[(i-1)*N + j-1] = true;

        int s = xy2id(i, j, direction.self);
        int l = xy2id(i, j, direction.left);
        int r = xy2id(i, j, direction.right);
        int u = xy2id(i, j, direction.up);
        int d = xy2id(i, j, direction.down);

        //Left

        if (l != -1 && isOpen(i, j-1))
            union(s, l);

        //Right
        if (r != -1 && isOpen(i, j+1))
            union(s, r);

        //Up
        if (u == vtop || isOpen(i-1, j))
            union(s, u);

        //Down
        //if (d == vbottom || isOpen(i+1, j))
        //    union(s, d);

        if (d == vbottom)
            uf1.union(s, d);
        else if (isOpen(i+1, j))
            union(s, d);
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        validate(i, j);
        return grid[(i-1)*N + j-1];
    }

    // validate that p is a valid index
    private void validate(int p, int q) {
        if (p < 1 || p > N || q < 1 || q > N) {
            throw new IndexOutOfBoundsException("Wrong index ("+p+", "+q+")");
        }
    }

    private void union(int a, int b) {
        //if (!uf2.connected(a, b)) {
        uf1.union(a, b);
        uf2.union(a, b);
        //}
    }
    
    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        validate(i, j);
        int cell = xy2id(i, j, direction.self);
        return uf2.connected(cell, vtop);
    }

    // does the system percolate?
    public boolean percolates() {
        return uf1.connected(vbottom, vtop);
    }

    /**
    * @param args
    */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
    }
}
