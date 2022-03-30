import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Ryan Litwin (rpl0010@auburn.edu)
 * @version 3-6-2020
 * using nodes to create a double-ended list.
 * @param <T> as a type
 */
public class DoubleEndedListArray<T> implements DoubleEndedList<T> {
   private Node front;
   private int size;
   private Node end;
   
   private class Node {
      private T elements;
      private Node nextOne;
      private Node previous;
      
      public Node(T e) {
         elements = e;
         nextOne = null;
         previous = null;
      }
      
      public Node(T e, Node n) {
         elements = e;
         nextOne = n;
      }
      
      public int length(Node n) {
         Node p = n;
         int len = 0;
         while (p != null) {
            len++;
            p = p.nextOne;
         }
         return len;
      }
   }
   
   /**
    * the constructor for this program.
    */
   public DoubleEndedListArray() {
      front = null;
      size = 0;
      end = null;
   }
   
   /**
    * creates an int that gets the size of the list.
    * @return size as an int
    */
   public int size() {
      return size;
   }
   
   /**
    * a boolean that checks if the list is empty-
    * if it's empty, it returns size as 0.
    * @return size as an int.
    */
   public boolean isEmpty() {
      return size == 0;
   }
   
   /**
    * @param element as a type of T
    * adds first element to the list.
    */
   public void addFirst(T element) {
      if (element == null) {
         throw new IllegalArgumentException();
      }
      Node n = new Node(element);
      if (front == null) {
         front = n;
         end = n;
      }
      else {
         front.previous = n;
         n.nextOne = front;
         front = n;
      }
      size++;
   }
   
   /**
    * @param element as a type of T
    * adds an element to the end of the list.
    */
   public void addLast(T element) {
      if (element == null) {
         throw new IllegalArgumentException();
      }
      Node n = new Node(element);
      if (front == null) {
         front = n;
         end = front;
      }
      else {
         n.previous = end;
         end.nextOne = n;
         end = n;
      }
      size++;
   }
   
   /**
    * @return removed as the element removed.
    * removes the first element of the array.
    */
   public T removeFirst() {
      if (isEmpty()) {
         return null;
      }
      T removed = front.elements;
      if (size == 1) {
         front = null;
         end = null;
      }
      else {
         front = front.nextOne;
         front.previous = null;
      }
      size--;
      return removed;
   }
   
   /**
    * @return removed as the last element removed.
    * removes the last element in the list.
    */
   public T removeLast() {
      if (isEmpty()) {
         return null;
      }
      T removed = end.elements;
      if (size == 1) {
         front = null;
         end = null;
      }
      else { 
         end = end.previous;
         end.nextOne = null;
      }
      size--;
      return removed;
   }
   
   /**
    * sets up an iterator in the program.
    * @return LinkedIterator as the iterator
    */
   public Iterator<T> iterator() {
      return new LinkedIterator();
   }
   
   private class LinkedIterator implements Iterator<T> {
      private Node current = front;
      
      public boolean hasNext() {
         return current != null;
      }
      
      public T next() {
         if (!hasNext()) {
            throw new NoSuchElementException();
         }
         T result = current.elements;
         current = current.nextOne;
         return result;
      }
      
      public void remove() {
         throw new UnsupportedOperationException();
      }
   }
}