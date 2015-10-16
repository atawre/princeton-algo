
import java.util.ArrayList;
import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private LineSegment[] lns;
    
    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        // Check if the input is null
        if (points == null) throw new java.lang.NullPointerException();
        
        // Copy over input points to auxiliary array to avoid mutability issues
        Point[] aux = points.clone();
        
        // Check if any of the points are null
        for (int i = 0; i < aux.length; i++) {
            if (aux[i] == null) throw new java.lang.NullPointerException();
        }
        
        // Check for duplicate points
        for (int i = 0; i < aux.length; i++) {
            for (int j = 0; j < i; j++) {
                    if (aux[i].slopeTo(aux[j]) == Double.NEGATIVE_INFINITY)
                        throw new java.lang.IllegalArgumentException();
            }
        }

        ArrayList<LineSegment> lines = new ArrayList<LineSegment>();
        Arrays.sort(aux);
        int N = aux.length;
        double slopePQ, slopePR, slopePS;

        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                slopePQ = aux[i].slopeTo(aux[j]);
                for (int k = j + 1; k < N; k++) {
                    slopePR = aux[i].slopeTo(aux[k]);
                    if (slopePQ != slopePR)
                        continue;
                    for (int l =  k + 1; l < N; l++) {
                        slopePS = aux[i].slopeTo(aux[l]);
                        if (slopePQ != slopePS)
                            continue;
                        
                        if (aux[i].compareTo(aux[j]) < 1
                                && aux[j].compareTo(aux[k]) < 1
                                && aux[k].compareTo(aux[l]) < 1) {

                            //StdOut.println(point1.toString()
                            //        + " -> " + point2.toString()
                            //       + " -> " + point3.toString()
                            //        + " -> " + point4.toString()
                            //        );
                            lines.add(new LineSegment(aux[i], aux[l]));
                        }
                    }
                }
            }
        }
        lns = new LineSegment[lines.size()];
        int i = 0;
        for (LineSegment s : lines) {
            lns[i++] = s;
        }
        lines = null;
        aux = null;
    }
    
    // the number of line segments
    public int numberOfSegments() {
        return lns.length;
    }
    
    // the line segments
    public LineSegment[] segments() {        
        return Arrays.copyOf(lns, lns.length);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // read the N points from a file
        In in = new In(args[0]);
        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.show(0);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }       
        
        
    }
}
