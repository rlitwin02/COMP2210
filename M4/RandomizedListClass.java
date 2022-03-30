import java.util.Iterator;
import java.util.Random;
import java.util.NoSuchElementException;


/**
 * @author Ryan Litwin (rpl0010@auburn.edu)
 * @version 3-3-2020
 * @param <T> in this class.
 */
public class RandomizedListClass<T> implements RandomizedList<T> {
   private T[] elements;
   private int size;
   private static final int DEFAULT_CAPACITY = 5;
   
   
   /**
    * Implements a default capacity when there is no capacity set.
    */
   public RandomizedListClass() {
      this(DEFAULT_CAPACITY);
   }
   
   /**
    * RandomizedListClass constructor.
    * @param cap as an int.
    */
   @SuppressWarnings("unchecked")
   public RandomizedListClass(int cap) {
      elements = (T[]) new Object[cap];
      size = 0;
   }
   
   /**
    * @param element as a type of T.
    */
   public void add(T element) {
      if (element == null) {
         throw new IllegalArgumentException();
      }
      if (size == elements.length) {
         resize(elements.length*2);
      }
      elements[size] = element;
      size++;
   }
   
   /**
    * @return rElement as the removed element.
    */
   public T remove() {
      if (isEmpty()) {
         return null;
      }
      else {
         Random rand = new Random();
         int rIndex = rand.nextInt(size);
         T rElement = elements[rIndex];
         elements[rIndex] = null;
         if (rIndex != (size-1)) {
            elements[rIndex] = elements[size-1];
            elements[size-1] = null;
         }
         size--;
         if (size > 0 && size < elements.length / 4) {
            resize(elements.length/2);
         }
         return rElement;
      }
   }
   
   /**
    * @return sElement as a sampled element.
    */
   public T sample() {
      if (isEmpty()) {
         return null;
      }
      Random rand = new Random();
      int sIndex = rand.nextInt(size);
      return elements[sIndex];
      
   }


   
   /**
    * @return size as an int.
    */
   public int size() {
      return size;
   }
   
   /**
    * @return isEmpty if list is empty.
    */
   public boolean isEmpty() {
      return size == 0;
   }
      
   /**
    * resizes the list if needed.
    * @param cap as an int.
    */
   @SuppressWarnings("unchecked")
   private void resize(int cap) {
      T[] newArray = (T[]) new Object[cap];
      for (int i = 0; i < size; i++) {
         newArray[i] = elements[i];
      }
      elements = newArray;
   }
   
   /**
    * @return RLCIterator as a new iterator.
    * creates a new iterator.
    */
    @SuppressWarnings("unchecked")
   public Iterator<T> iterator() {
      return new RLCIterator(elements, size);
   }
   
   
   public class RLCIterator<T> implements Iterator<T> {
      private T[] elem;
      private int count;
      
      
      @SuppressWarnings("unchecked")
      public RLCIterator(T[] elements, int sizeIn) {
         elem = elements;
         count = sizeIn;
      }
      
      public boolean hasNext() {
         return (count > 0);
      }
      
      public T next() {
         if (!hasNext()) {
            throw new NoSuchElementException();
         }
         Random rand = new Random();
         int value = rand.nextInt(count);
         T next = elem[value];
         if (value != (count-1)) {
            elem[value] = elem[count-1];
            elem[count-1] = next;
         }
         count--;
         return next;
      }
      
      public void remove() {
         throw new UnsupportedOperationException();
      }
    }
   
      
            
}   