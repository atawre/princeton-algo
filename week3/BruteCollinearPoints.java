import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {

    private LineSegment[] segments;


    public BruteCollinearPoints(Point[] points) {
        checkDuplicatedEntries(points);
        ArrayList<LineSegment> foundSegments = new ArrayList<>();

        Point[] aux = Arrays.copyOf(points, points.length);
        Arrays.sort(aux);

        double pq, pr, ps;
        for (int p = 0; p < aux.length - 3; p++) {
            for (int q = p + 1; q < aux.length - 2; q++) {
                pq = aux[p].slopeTo(aux[q]);
                for (int r = q + 1; r < aux.length - 1; r++) {
                    pr = aux[p].slopeTo(aux[r]);
                    if (pq != pr)
                        continue;
                    for (int s = r + 1; s < aux.length; s++) {
                        ps = aux[p].slopeTo(aux[s]);
                        if (pq != ps)
                            continue;
                        foundSegments.add(new LineSegment(aux[p], aux[s]));
                    }
                }
            }
        }

        segments = foundSegments.toArray(new LineSegment[foundSegments.size()]);
    }

    public int numberOfSegments() {
        return segments.length;
    }

    public LineSegment[] segments() {
        return Arrays.copyOf(segments, numberOfSegments());
    }

    private void checkDuplicatedEntries(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException("Duplicate points.");
                }
            }
        }
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
