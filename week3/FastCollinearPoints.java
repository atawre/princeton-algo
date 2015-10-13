package week3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
	LineSegment[] lns;	

	public FastCollinearPoints(Point[] points) {
		
        // Check if the input is null
        if (points == null) throw new java.lang.NullPointerException();
        
        // Copy over input points to auxiliary array to avoid mutability issues
        Point[] aux = new Point[points.length];
        aux = points;
        
        // Check if any of the points are null
        for (int i = 0; i < aux.length; i++) {
            if (aux[i] == null) throw new java.lang.NullPointerException();
        }
        
        // Check for duplicate points by comparing slope of each point to slope of all preceding points
        for (int i = 0; i < aux.length; i++) {
            for (int j = 0; j < i ; j++) {
                    if (aux[i].slopeTo(aux[j]) == Double.NEGATIVE_INFINITY) throw new java.lang.IllegalArgumentException();
            }
        }

        // Natural-sort array
        Arrays.sort(aux);

        ArrayList<LineSegment>lines = new ArrayList<LineSegment>();
        int N = aux.length;
        
        for (int i = 0; i < aux.length; i++) {
        	Point currentPoint = aux[i];
            // sort to slope order based on slope to first point
            Point[] slopeOrderPoints = aux.clone();
        	Arrays.sort(slopeOrderPoints, currentPoint.slopeOrder());

            // create new segment
            LinkedList<Point> segment = new LinkedList<Point>();
            // add point i as origin
            segment.add(currentPoint);

            // index 0 in slope order sorted is always the origin point (slope negative infinity)
            // this loop compares i to j and i to j+1. j only needs to loop through N-2
            for (int j = 1; j < N-1; j++) {

                Point slopePoint = slopeOrderPoints[j];
                Point nextSlopePoint = slopeOrderPoints[j+1];

                double slope = currentPoint.slopeTo(slopePoint);
                double nextSlope = currentPoint.slopeTo(nextSlopePoint);

                if (slope == nextSlope) {
                    if (segment.peekLast() != slopePoint) {
                        segment.add(slopePoint);
                    }
                    segment.add(nextSlopePoint);
                }

                // clear segment if no match (end of segment or loop)
                if (slope != nextSlope || j == N-2) {
                    // first output segment if it is large enough
                    if (segment.size() > 3) {                   	
                        Point first = segment.removeFirst();
                        Point second = segment.removeFirst();
                        Point last = segment.removeLast();
                        if (first.compareTo(second) < 0) {
                            lines.add(new LineSegment(first, last));                        	
                            StdOut.print(first + " -> " + second + " -> ");
                            for (Point point : segment) {
                                StdOut.print(point + " -> ");
                            }
                            StdOut.print(last);
                            StdOut.println();
                        }
                    }
                    segment.clear();
                    segment.add(currentPoint);
                }
            }
            slopeOrderPoints = null;
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
	public int numberOfSegments(){
		return lns.length;
	}
	
	// the line segments
	public LineSegment[] segments(){
		return lns;
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
	}
}

