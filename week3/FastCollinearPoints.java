package week3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
	private static ArrayList<Point> pointsArray;
	LineSegment[] lns;	

	public FastCollinearPoints(Point[] points) {
		// TODO Auto-generated constructor stub
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
		int N = pointsArray.size();

    	ArrayList<Point> slopeOrderPoints = (ArrayList<Point>) pointsArray.clone();
        // loop through each naturally sorted point
        for (Point currentPoint : pointsArray) {

            // sort to slope order based on slope to first point
        	Collections.sort(slopeOrderPoints, currentPoint.slopeOrder());

            // create new segment
            LinkedList<Point> segment = new LinkedList<Point>();
            // add point i as origin
            segment.add(currentPoint);

            // index 0 in slope order sorted is always the origin point (slope negative infinity)
            // this loop compares i to j and i to j+1. j only needs to loop through N-2
            for (int j = 1; j < N-1; j++) {

                Point slopePoint = slopeOrderPoints.get(j);
                Point nextSlopePoint = slopeOrderPoints.get(j+1);

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
                        //outputSegment(segment);
                    	
                        Point first = segment.removeFirst();
                        Point second = segment.removeFirst();
                        Point last = segment.removeLast();
                        lines.add(new LineSegment(first, last));
                        if (first.compareTo(second) < 0) {
                            //first.drawTo(last);                            
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
	}
}


/**
 * 
    private static void outputSegment(LinkedList<Point> segment) {
        // to remove sub-segments, we rely on the following logic:
        // the outer loop's array is sorted via natural order
        // the inner loop is sorted in slope order according
        // to the current number in the outer loop
        // a discovered segment should always start at
        // its naturally lowest point
        // in the case of a sub-segment, the outer loop will
        // start the segment at somewhere other than its lowest
        // in this case, we can discover this by comparing
        // whether the first point of the segment is in fact the lowest

        Point first = segment.removeFirst();
        Point second = segment.removeFirst();
        Point last = segment.removeLast();
        if (first.compareTo(second) < 0) {

            first.drawTo(last);
            StdOut.print(first + " -> " + second + " -> ");

            for (Point point : segment) {
                StdOut.print(point + " -> ");
            }

            StdOut.print(last);
            StdOut.println();
        }
    }

 * */
