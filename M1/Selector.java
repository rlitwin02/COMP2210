import java.util.Arrays;

/**
* Defines a library of selection methods
* on arrays of ints.
*
* @author   Ryan Litwin (rpl0010@auburn.edu)
* @author   Dean Hendrix (dh@auburn.edu)
* @version  1/23/2020
*
*/
public final class Selector {

   /**
    * Can't instantiate this class.
    *
    * D O   N O T   C H A N G E   T H I S   C O N S T R U C T O R
    *
    */
   private Selector() { }


   /**
    * Selects the minimum value from the array a. This method
    * throws IllegalArgumentException if a is null or has zero
    * length. The array a is not changed by this method.
    * @return the min
    * @param a as an array
    */
   public static int min(int[] a) {
      if (a == null || a.length == 0) {
         throw new IllegalArgumentException();
      }
      else {
         int min = a[0];
         for (int i = 1; i < a.length; i++) {
            if (min > a[i]) {
               min = a[i];
            }
         }
         return min;
      }
   }


   /**
    * Selects the maximum value from the array a. This method
    * throws IllegalArgumentException if a is null or has zero
    * length. The array a is not changed by this method.
    * @return the max
    * @param a as an array
    */
   public static int max(int[] a) {
      if (a == null || a.length == 0) {
         throw new IllegalArgumentException();
      }
      else {
         int max = a[0];
         for (int i = 1; i < a.length; i++) {
            if (max < a[i]) {
               max = a[i];
            }
         } 
         return max;
      }
   }


   /**
    * Selects the kth minimum value from the array a. This method
    * throws IllegalArgumentException if a is null, has zero length,
    * or if there is no kth minimum value. Note that there is no kth
    * minimum value if k < 1, k > a.length, or if k is larger than
    * the number of distinct values in the array. The array a is not
    * changed by this method.
    * @return the kmin
    * @param a as an array
    * @param k as an int
    */
   public static int kmin(int[] a, int k) {
      if (a == null || a.length == 0 || k < 1 || k > a.length) {
         throw new IllegalArgumentException();
      }
      else {
         k--;
         int[] b = Arrays.copyOf(a, a.length);
         Arrays.sort(b);
         
         int count = 0;
         for (int i = 1; i <= k; i++) {
            if (k >= b.length) {
               throw new IllegalArgumentException();
            }
            if (b[count] == b[i]) {
               k++;
            }
            count++;
         }
         if (k >= b.length) {
            throw new IllegalArgumentException();
         }
         return b[k];
      }
   }


   /**
    * Selects the kth maximum value from the array a. This method
    * throws IllegalArgumentException if a is null, has zero length,
    * or if there is no kth maximum value. Note that there is no kth
    * maximum value if k < 1, k > a.length, or if k is larger than
    * the number of distinct values in the array. The array a is not
    * changed by this method.
    * @return kmax
    * @param a as an array
    * @param k as an int
    */
   public static int kmax(int[] a, int k) {
      if (a == null || a.length == 0 || k < 1 || k > a.length) {
         throw new IllegalArgumentException();
      }
      else {
         int[] b = Arrays.copyOf(a, a.length);
         Arrays.sort(b);
         k--;
         
         int[] d = new int[b.length];
         int ii = d.length - 1;
         for (int i:b) {
            if (ii >= 0) {
               d[ii] = i;
               ii--;
            }
         }
         
         int count = 0;
         for (int i = 1; i <= k; i++) {
            if (k >= b.length) {
               throw new IllegalArgumentException();
            }
            if (d[count] == d[i]) {
               k++;
            }
            count++;
         }
         
         if (k >= b.length) {
            throw new IllegalArgumentException();
         }
         return d[k];
      }
   }


   /**
    * Returns an array containing all the values in a in the
    * range [low..high]; that is, all the values that are greater
    * than or equal to low and less than or equal to high,
    * including duplicate values. The length of the returned array
    * is the same as the number of values in the range [low..high].
    * If there are no qualifying values, this method returns a
    * zero-length array. Note that low and high do not have
    * to be actual values in a. This method throws an
    * IllegalArgumentException if a is null or has zero length.
    * The array a is not changed by this method.
    * @return ab
    * @param a as an array
    * @param low as an int
    * @param high as an int
    */
   public static int[] range(int[] a, int low, int high) {
      if (a == null || a.length == 0) {
         throw new IllegalArgumentException();
      }
      else {
         int count = 0;
         for (int i:a) {
            if (i >= low && i <= high) {
               count++;
            }
         }
         int[] ab = new int[count];
         int id = 0;
         for (int i:a) {
            if (i >= low && i <= high) {
               ab[id] = i;
               id++;
            }
         }
         return ab;
      }
   }


   /**
    * Returns the smallest value in a that is greater than or equal to
    * the given key. This method throws an IllegalArgumentException if
    * a is null or has zero length, or if there is no qualifying
    * value. Note that key does not have to be an actual value in a.
    * The array a is not changed by this method.
    * @return ceiling
    * @param a as an array
    * @param key as an int
    */
   public static int ceiling(int[] a, int key) {
      if (a == null || a.length == 0 || key > max(a)) {
         throw new IllegalArgumentException();
      }
      else {
         int[] ab = new int[a.length];
         int count = 0;
         int count2 = 0;
         for (int i:a) {
            ab[count] = key - i;
            count++;
         }
         for (int i:ab) {
            if (i <= 0) {
               count2++;
            }
         }
         int[] c = new int[count2];
         int id = 0;
         for (int i:ab) {
            if (i <= 0) {
               c[id] = i;
               id++;
            }
         }
         int maxSize = c[0];
         for (int i:c) {
            if (i > maxSize) {
               maxSize = i;
            }
         }         
         return key - maxSize;
      }
   }


   /**
    * Returns the largest value in a that is less than or equal to
    * the given key. This method throws an IllegalArgumentException if
    * a is null or has zero length, or if there is no qualifying
    * value. Note that key does not have to be an actual value in a.
    * The array a is not changed by this method.
    * @return floor
    * @param a as an array
    * @param key as an int
    */
   public static int floor(int[] a, int key) {
      if (a == null || a.length == 0 || key < min(a)) {
         throw new IllegalArgumentException();
      }
      else {
         int[] b = new int[a.length];
         int count = 0;
         int count2 = 0;
         for (int i:a) {
            b[count] = key - i;
            count++;
         }
         for (int i:b) {
            if (i >= 0) {
               count2++;
            }
         }
         int[] c = new int[count2];
         int id = 0;
         for (int i:b) {
            if (i >= 0) {
               c[id] = i;
               id++;
            }
         }
         int minSize = c[0];
         for (int i:c) {
            if (i < minSize) {
               minSize = i;
            }
         }
         return key - minSize;
      }
   }

}
