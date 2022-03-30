import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Extractor.java. Implements feature extraction for collinear points in
 * two dimensional data.
 *
 * @author  Ryan Litwin (rpl0010@auburn.edu)
 * @author  Dean Hendrix (dh@auburn.edu)
 * @version 2-19-2020
 *
 */
public class Extractor {
   
   /** raw data: all (x,y) points from source data. */
   private Point[] points;
   
   /** lines identified from raw data. */
   private SortedSet<Line> lines;
  
   /**
    * Builds an extractor based on the points in the file named by filename. 
    * @param filename as a string.
    */
   public Extractor(String filename) {
      try {
         Scanner nScan = new Scanner(new File(filename));
         int size = nScan.nextInt();
         points = new Point[size];
         int x;
         int y;
         for (int i = 0; i < size; i++) {
            x = nScan.nextInt();
            y = nScan.nextInt();
            
            points[i] = new Point(x, y);
         }
      }
      catch (java.io.FileNotFoundException e) {
         System.err.println("File Not Found");
      }
   }

   
  
   /**
    * Builds an extractor based on the points in the Collection named by pcoll. 
    *
    * THIS METHOD IS PROVIDED FOR YOU AND MUST NOT BE CHANGED.
    * @param pcoll as a point.
    */
   public Extractor(Collection<Point> pcoll) {
      points = pcoll.toArray(new Point[]{});
   }
  
   /**
    * Returns a sorted set of all line segments of exactly four collinear
    * points. Uses a brute-force combinatorial strategy. Returns an empty set
    * if there are no qualifying line segments.
    * @return lines in the program.
    */
   public SortedSet<Line> getLinesBrute() {
      lines = new TreeSet<Line>();
      Line newL = new Line();
      for (int i = 0; i < points.length; i++) {
         for (int j = i + 1; j < points.length; j++) {
            for (int a = j + 1; a < points.length; a++) {
               for (int b = a + 1; b < points.length; b++) {
                  newL.add(points[i]);
                  newL.add(points[j]);
                  if (newL.add(points[a]) && newL.add(points[b]) 
                     && newL.length() == 4) {
                     lines.add(newL);
                  }
                  newL = new Line();
               }
            }
         }
      }
      return lines;
   } 
  
   /**
    * Returns a sorted set of all line segments of at least four collinear
    * points. The line segments are maximal; that is, no sub-segments are
    * identified separately. A sort-and-scan strategy is used. Returns an empty
    * set if there are no qualifying line segments.
    * @return lines in the program.
    */
   public SortedSet<Line> getLinesFast() {
      lines = new TreeSet<Line>();
      Point[] sorted = Arrays.copyOf(points, points.length);
      Line newL = new Line();
      boolean isAdded = true;
      for (int i = 0; i < points.length; i++) {
         Arrays.sort(sorted, points[i].slopeOrder);
         
         for (int j = 0; j < points.length; j++) {
            newL.add(sorted[0]);
            isAdded = newL.add(sorted[j]);
            
            if (!isAdded) {
               if (newL.length() >= 4) {
                  lines.add(newL);
               }
               newL = new Line();
               newL.add(sorted[j]);
            }
         }
      }
      return lines;
   }
   
}
