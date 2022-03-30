import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Deque;
import java.util.Arrays;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.Iterator;

/**
 * Provides an implementation of the WordLadderGame interface. 
 *
 * @author Ryan Litwin (rpl0010@auburn.edu)
 * @author Dean Hendrix (dh@auburn.edu)
 * @version 2020-04-11
 */
public class Doublets implements WordLadderGame {

   private TreeSet<String> lexicon;
   private static final List<String> EMPTY_LADDER = new ArrayList<String>();

   public Doublets(InputStream in) {
      try {
         lexicon = new TreeSet<String>();
         Scanner s =
            new Scanner(new BufferedReader(new InputStreamReader(in)));
         while (s.hasNext()) {
            String str = s.next();
            lexicon.add(str.toLowerCase());
            s.nextLine();
         }
         in.close();
         s.close();
      }
      catch (java.io.IOException e) {
         System.err.println("Error reading from InputStream.");
         System.exit(1);
      }
   }

   public int getHammingDistance(String str1, String str2) {
      if (str1.length() != str2.length()) {
         return -1;
      }
      int hamDist = 0;
      for (int i = 0; i < str1.length(); i++) {
         if (str1.charAt(i) != str2.charAt(i)) {
            hamDist++;
         }
      }
      return hamDist;
   }

   public List<String> getMinLadder(String start, String end) {
      if (start == null || end == null) {
         return EMPTY_LADDER;
      }
      if (getHammingDistance(start, end) == -1) {
         return EMPTY_LADDER;
      }
      if (!isWord(start) || !isWord(end)) {
         return EMPTY_LADDER;
      }
   
      String lStart = start.toLowerCase();
      String lEnd = end.toLowerCase();
      List<String> ladder = new ArrayList<String>();
   
      if (lStart.equals(lEnd)) {
         ladder.add(lStart);
         return ladder;
      }
      ArrayDeque<Node> queue = new ArrayDeque<Node>();
      HashSet<String> visited = new HashSet<String>();
      
      Node startNode = new Node(lStart); 
      visited.add(startNode.word);
      queue.add(startNode);
         
      while (!queue.isEmpty()) {
         Node current = queue.removeFirst();
         List<String> neighbors = current.neighbors;
         for (String neighbor : neighbors) {   
            if (!visited.contains(neighbor)) { 
               Node neighborNode = new Node(neighbor, current);
               visited.add(neighbor);
               queue.addLast(neighborNode);
               if (neighbor.equals(lEnd)) { 
                  return toList(new Node(neighbor, current));
               }
            }
         }
      }
      return EMPTY_LADDER;
   }

  public List<String> getNeighbors(String word) {
      ArrayList<String> result = new ArrayList<String>();
      if (word == null) {
         return EMPTY_LADDER;
      }
      for (String s: lexicon) {
         if (getHammingDistance(word, s) == 1 && !word.equals(s)) {
            result.add(s);
         }
      }
      return result;
   }       
   public int getWordCount() {
      return lexicon.size();
   }

   public boolean isWord(String str) {
      str = str.toLowerCase();
      return lexicon.contains(str);
   }

   public boolean isWordLadder(List<String> sequence) {
      if (sequence == null || sequence.isEmpty()) {
         return false;
      }
      if (!isWord(sequence.get(0).toLowerCase())) {
         return false;
      }
      for (int i = 1; i < sequence.size(); i++) {
         String s1 = sequence.get(i).toLowerCase();
         String s2 = sequence.get(i - 1).toLowerCase();
         if (!isWord(s1)) {
            return false;
         }
         if (getHammingDistance(s1, s2) != 1) {
            return false;
         }
      }
      return true;
   }

   private class Node {
      private String word;
      private Node next;
      private List<String> neighbors;
         
      Node(String w) {
         this.word = w;
         this.neighbors = getNeighbors(this.word);
      }
   
     
      Node(String w, Node n) {
         this.word = w;
         this.next = n;
         this.neighbors = getNeighbors(this.word);
      }
   }
   private List<String> toList(Node n) {
      List<String> list = new ArrayList<String>();
      String word = n.word;
      Node prev = n.next;
      list.add(word);
      while (prev != null) {
         word = prev.word;
         list.add(0, word);
         prev = prev.next;
      }
      return list;
   }
}    
    
