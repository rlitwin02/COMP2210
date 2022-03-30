import java.io.File;
import java.util.HashMap;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.ArrayList;

/**
 * MarkovModel.java Creates an order K Markov model of the supplied source
 * text. The value of K determines the size of the "kgrams" used to generate
 * the model. A kgram is a sequence of k consecutive characters in the source
 * text.
 *
 * @author     Ryan Litwin (rpl0010@auburn.edu)
 * @author     Dean Hendrix (dh@auburn.edu)
 * @version    2018-04-17
 *
 */
public class MarkovModel {

   // Map of <kgram, chars following> pairs that stores the Markov model.
   private HashMap<String, String> model;
   private Random rand = new Random();
   private ArrayList<String> list;
   private String firstKgram;
   


   /**
    * Reads the contents of the file sourceText into a string, then calls
    * buildModel to construct the order K model.
    *
    * DO NOT CHANGE THIS CONSTRUCTOR.
    *
    */
   public MarkovModel(int K, File sourceText) {
      model = new HashMap<>();
      try {
         String text = new Scanner(sourceText).useDelimiter("\\Z").next();
         buildModel(K, text);
      }
      catch (IOException e) {
         System.out.println("Error loading source text: " + e);
      }
   }


   /**
    * Calls buildModel to construct the order K model of the string sourceText.
    *
    * DO NOT CHANGE THIS CONSTRUCTOR.
    *
    */
   public MarkovModel(int K, String sourceText) {
      model = new HashMap<>();
      buildModel(K, sourceText);
   }


   /**
    * Builds an order K Markov model of the string sourceText.
    */
   private void buildModel(int K, String sourceText) {
      firstKgram = sourceText.substring(0, K);
      int start = 0;
      while (start + K <= sourceText.length()) {
         String key = sourceText.substring(start, start + K);
         if (!model.containsKey(key)) {
            if (start + K == sourceText.length()) {
               model.put(key, Character.toString('\u0000'));
            }
            else {
               model.put(key, Character.toString(sourceText.charAt(start + K)));
            }
         }
         else {
            String mapKgram = model.get(key);
            if (start + K == sourceText.length()) {
               mapKgram += Character.toString('\u0000');
            }
            else {
               mapKgram += sourceText.charAt(start + K);
            }
            model.replace(key, mapKgram);
         }
         start++;
      }
      

   }


   /** Returns the first kgram found in the source text. */
   public String getFirstKgram() {
      return firstKgram;
   }


   /** Returns a kgram chosen at random from the source text. */
   public String getRandomKgram() {
      if (list == null || list.size() != model.size()) {
         list = new ArrayList<String>(getAllKgrams());
      }
      return list.get(rand.nextInt(list.size()));
   }


   /**
    * Returns the set of kgrams in the source text.
    *
    * DO NOT CHANGE THIS METHOD.
    *
    */
    public Set<String> getAllKgrams() {
      return model.keySet();
   }


   /**
    * Returns a single character that follows the given kgram in the source
    * text. This method selects the character according to the probability
    * distribution of all characters that follow the given kgram in the source
    * text.
    */
   public char getNextChar(String kgram) {
      if (model.containsKey(kgram)) {
         String value = model.get(kgram);
         return value.charAt(rand.nextInt(value.length()));
      }
      else { 
         return '\u0000';
      }
   }


   /**
    * Returns a string representation of the model.
    * This is not part of the provided shell for the assignment.
    *
    * DO NOT CHANGE THIS METHOD.
    *
    */
    @Override
    public String toString() {
      return model.toString();
   }

}
