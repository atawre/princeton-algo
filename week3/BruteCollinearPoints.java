package week3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
	//private static Point []pointsArray;
	private static ArrayList<Point> pointsArray;
	LineSegment[] lns;
	private static int N;
	
	// finds all line segments containing 4 points
	public BruteCollinearPoints(Point[] points){
		if(points == null)
			throw new java.lang.NullPointerException("No Points present!");
		 
		ArrayList<LineSegment>lines = new ArrayList<LineSegment>();
		pointsArray = new ArrayList<Point>();
		int i=0;
		for (Point p : points) {
			if(p == null)
				throw new java.lang.NullPointerException("One of the point is null!");
			
			if( pointsArray.contains(p) )
				throw new java.lang.IllegalArgumentException("Duplicate point.");
			
			pointsArray.add(p);
		}

		Collections.sort(pointsArray);
        N = pointsArray.size();
		
        for (i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                for (int k = j + 1; k < N; k++) {
                    for (int l =  k + 1; l < N; l++) {
                    	Point point1 = pointsArray.get(i);
                    	Point point2 = pointsArray.get(j);
                    	Point point3 = pointsArray.get(k);
                        Point point4 = pointsArray.get(l);
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
        i = 0;
        for (LineSegment s : lines) {
        	lns[i++] = s;
        }
        lines = null;
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
