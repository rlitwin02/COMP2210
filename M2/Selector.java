import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
//import java.util.List;
import java.util.NoSuchElementException;

/**
 * Defines a library of selection methods on Collections.
 *
 * @author  Ryan Litwin (rpl0010@auburn.edu)
 * @author  Dean Hendrix (dh@auburn.edu)
 * @version 02-07-20
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
    * Returns the minimum value in the Collection coll as defined by the
    * Comparator comp. If either coll or comp is null, this method throws an
    * IllegalArgumentException. If coll is empty, this method throws a
    * NoSuchElementException. This method will not change coll in any way.
    *
    * @param coll    the Collection from which the minimum is selected
    * @param comp    the Comparator that defines the total order on T
    * @return        the minimum value in coll
    * @throws        IllegalArgumentException as per above
    * @throws        NoSuchElementException as per above
    * @param <T> of type T as the array list
    */
   public static <T> T min(Collection<T> coll, Comparator<T> comp) {
      if (coll == null || comp == null) {
         throw new IllegalArgumentException();
      }
      if (coll.isEmpty()) {
         throw new NoSuchElementException();
      }
      else {
         Iterator<T> it = coll.iterator();
         T min = it.next();
         T ia = min;
         while (it.hasNext()) {
            ia = it.next();
            if (comp.compare(ia, min) < 0) {
               min = ia;
            }
         }
         return min;
      }
         
   }


   /**
    * Selects the maximum value in the Collection coll as defined by the
    * Comparator comp. If either coll or comp is null, this method throws an
    * IllegalArgumentException. If coll is empty, this method throws a
    * NoSuchElementException. This method will not change coll in any way.
    *
    * @param coll    the Collection from which the maximum is selected
    * @param comp    the Comparator that defines the total order on T
    * @return        the maximum value in coll
    * @throws        IllegalArgumentException as per above
    * @throws        NoSuchElementException as per above
    * @param <T> of type T as the array list
    */
   public static <T> T max(Collection<T> coll, Comparator<T> comp) {
      if (coll == null || comp == null) {
         throw new IllegalArgumentException();
      }
      if (coll.isEmpty()) {
         throw new NoSuchElementException();
      }
      else {
         Iterator<T> it = coll.iterator();
         T max = it.next();
         T ia = max;
         while (it.hasNext()) {
            ia = it.next();
            if (comp.compare(ia, max) > 0) {
               max = ia;
            }
         }
         return max;
      }
      
   }


   /**
    * Selects the kth minimum value from the Collection coll as defined by the
    * Comparator comp. If either coll or comp is null, this method throws an
    * IllegalArgumentException. If coll is empty or if there is no kth minimum
    * value, this method throws a NoSuchElementException. This method will not
    * change coll in any way.
    *
    * @param coll    the Collection from which the kth minimum is selected
    * @param k       the k-selection value
    * @param comp    the Comparator that defines the total order on T
    * @return        the kth minimum value in coll
    * @throws        IllegalArgumentException as per above
    * @throws        NoSuchElementException as per above
    * @param <T> of type T as the array list
    */
   public static <T> T kmin(Collection<T> coll, int k, Comparator<T> comp) {
      if (coll == null || comp == null) {
         throw new IllegalArgumentException();
      }
      if (coll.isEmpty() || k < 1 || k > coll.size()) {
         throw new NoSuchElementException();
      }
      else {
         ArrayList<T> list = new ArrayList<T>(coll);
         java.util.Collections.sort(list, comp);
         if (k == 1) {
            return list.get(0);
         }
         T count2 = list.get(0);
         int count3 = 1;
         T kmin = null;
         for (int i = 1; i < list.size(); i++) {
            if (!list.get(i).equals(count2)) {
               count3++;
               if (k == count3) {
                  kmin = list.get(i);
               }
            }
            count2 = list.get(i);
         }
         if (k > count3) {
            throw new NoSuchElementException();
         }
         return kmin;
      }
   }


   /**
    * Selects the kth maximum value from the Collection coll as defined by the
    * Comparator comp. If either coll or comp is null, this method throws an
    * IllegalArgumentException. If coll is empty or if there is no kth maximum
    * value, this method throws a NoSuchElementException. This method will not
    * change coll in any way.
    *
    * @param coll    the Collection from which the kth maximum is selected
    * @param k       the k-selection value
    * @param comp    the Comparator that defines the total order on T
    * @return        the kth maximum value in coll
    * @throws        IllegalArgumentException as per above
    * @throws        NoSuchElementException as per above
    * @param <T> of type T as the array list
    */
   public static <T> T kmax(Collection<T> coll, int k, Comparator<T> comp) {
      if (coll == null || comp == null) {
         throw new IllegalArgumentException();
      }
      if (coll.isEmpty() || k < 1 || k > coll.size()) {
         throw new NoSuchElementException();
      }
      else {
         ArrayList<T> list = new ArrayList<T>(coll);
         java.util.Collections.sort(list, comp);
         if (k == 1) {
            return list.get(list.size() - 1);
         }
         int count = 1;
         T count2 = list.get(list.size() - 1);
         T kmax = null;
         for (int i = list.size() - 2; i >= 0; i--) {
            if (!list.get(i).equals(count2)) {
               count++;
               if (k == count) {
                  kmax = list.get(i);
               }
            }
            count2 = list.get(i);
         }
         if (k > count) {
            throw new NoSuchElementException();
         }
         return kmax;
      }
   }
   


   /**
    * Returns a new Collection containing all the values in the Collection coll
    * that are greater than or equal to low and less than or equal to high, as
    * defined by the Comparator comp. The returned collection must contain only
    * these values and no others. The values low and high themselves do not have
    * to be in coll. Any duplicate values that are in coll must also be in the
    * returned Collection. If no values in coll fall into the specified range or
    * if coll is empty, this method throws a NoSuchElementException. If either
    * coll or comp is null, this method throws an IllegalArgumentException. This
    * method will not change coll in any way.
    *
    * @param coll    the Collection from which the range values are selected
    * @param low     the lower bound of the range
    * @param high    the upper bound of the range
    * @param comp    the Comparator that defines the total order on T
    * @return        a Collection of values between low and high
    * @throws        IllegalArgumentException as per above
    * @throws        NoSuchElementException as per above
    * @param <T> of type T as the array list
    */
   public static <T> Collection<T> range(Collection<T> coll, T low, T high,
                                         Comparator<T> comp) {
      if (coll == null || comp == null) {
         throw new IllegalArgumentException();
      }
      if (coll.isEmpty()) {
         throw new NoSuchElementException();
      }
      else {
         ArrayList<T> range = new ArrayList<T>();
         Iterator<T> it = coll.iterator();
         while (it.hasNext()) {
            T i = it.next();
            if (comp.compare(i, low) >= 0 && comp.compare(i, high) <= 0) {
               range.add(i);
            }
         }
      
         if (range.size() == 0) {
            throw new NoSuchElementException();
         }
         return range;
      }
      
   }
      


   /**
    * Returns the smallest value in the Collection coll that is greater than
    * or equal to key, as defined by the Comparator comp. The value of key
    * does not have to be in coll. If coll or comp is null, this method throws
    * an IllegalArgumentException. If coll is empty or if there is no
    * qualifying value, this method throws a NoSuchElementException. This
    * method will not change coll in any way.
    *
    * @param coll    the Collection from which the ceiling value is selected
    * @param key     the reference value
    * @param comp    the Comparator that defines the total order on T
    * @return        the ceiling value of key in coll
    * @throws        IllegalArgumentException as per above
    * @throws        NoSuchElementException as per above
    * @param <T> of type T as the array list
    */
   public static <T> T ceiling(Collection<T> coll, T key, Comparator<T> comp) {
      if (coll == null || comp == null) {
         throw new IllegalArgumentException();
      }
      if (coll.isEmpty()) {
         throw new NoSuchElementException();
      }
      else {
         Iterator<T> it = coll.iterator();
         ArrayList<T> list = new ArrayList<T>();
         Iterator<T> its;
         while (it.hasNext()) {
            T count2 = it.next();
            if (comp.compare(count2, key) >= 0) {
               list.add(count2);
            }
         }
         its = list.iterator();
         T i = its.next();
         while (its.hasNext()) {
            T ia = its.next();
            if (comp.compare(ia, i) < 0) {
               i = ia;
            }
         }
         if (comp.compare(i, key) < 0) {
            throw new NoSuchElementException();
         }
         return i;
      }
   }


   /**
    * Returns the largest value in the Collection coll that is less than
    * or equal to key, as defined by the Comparator comp. The value of key
    * does not have to be in coll. If coll or comp is null, this method throws
    * an IllegalArgumentException. If coll is empty or if there is no
    * qualifying value, this method throws a NoSuchElementException. This
    * method will not change coll in any way.
    *
    * @param coll    the Collection from which the floor value is selected
    * @param key     the reference value
    * @param comp    the Comparator that defines the total order on T
    * @return        the floor value of key in coll
    * @throws        IllegalArgumentException as per above
    * @throws        NoSuchElementException as per above
    * @param <T> of type T as the array list
    */
   public static <T> T floor(Collection<T> coll, T key, Comparator<T> comp) {
      if (coll == null || comp == null) {
         throw new IllegalArgumentException();
      }
      if (coll.isEmpty()) {
         throw new NoSuchElementException();
      }
      else {
         Iterator<T> it = coll.iterator();
         ArrayList<T> list = new ArrayList<T>();
         Iterator<T> its;
         while (it.hasNext()) {
            T count2 = it.next();
            if (comp.compare(count2, key) <= 0) {
               list.add(count2);
            }
         }
         its = list.iterator();
         T ia = its.next();
         while (its.hasNext()) {
            T i = its.next();
            if (comp.compare(i, ia) > 0) {
               ia = i;
            }
         }
         if (comp.compare(ia, key) > 0) {
            throw new NoSuchElementException();
         }
         return ia;
      }
      
   }

}
