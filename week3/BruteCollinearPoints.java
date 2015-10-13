package week3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
	LineSegment[] lns;
	
	// finds all line segments containing 4 points
	public BruteCollinearPoints(Point[] points){
        // Check if the input is null
        if (points == null) throw new java.lang.NullPointerException();
        
        // Copy over input points to auxiliary array to avoid mutability issues
        //Point[] pointsArray = new Point[points.length];
        Point[] pointsArray = points.clone();
        
        // Check if any of the points are null
        for (int i = 0; i < pointsArray.length; i++) {
            if (pointsArray[i] == null) throw new java.lang.NullPointerException();
        }
        
        // Check for duplicate points by comparing slope of each point to slope of all preceding points
        for (int i = 0; i < pointsArray.length; i++) {
            for (int j = 0; j < i ; j++) {
                    if (pointsArray[i].slopeTo(pointsArray[j]) == Double.NEGATIVE_INFINITY) throw new java.lang.IllegalArgumentException();
            }
        }

        ArrayList<LineSegment>lines = new ArrayList<LineSegment>();
        Arrays.sort(pointsArray);
        int N = pointsArray.length;
		
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                for (int k = j + 1; k < N; k++) {
                    for (int l =  k + 1; l < N; l++) {
                    	Point point1 = pointsArray[i];
                    	Point point2 = pointsArray[j];
                    	Point point3 = pointsArray[k];
                        Point point4 = pointsArray[l];
                        if (point1.slopeTo(point2) == point2.slopeTo(point3)
                                && point2.slopeTo(point3) == point3.slopeTo(point4)
                                && point1.compareTo(point2) < 1
                                && point2.compareTo(point3) < 1
                                && point3.compareTo(point4) < 1) {

                            //StdOut.println(point1.toString()
                            //        + " -> " + point2.toString()
                            //       + " -> " + point3.toString()
                            //        + " -> " + point4.toString()
                            //        );
                            lines.add(new LineSegment(point1, point4));
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
        pointsArray = null;
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
	}
}
