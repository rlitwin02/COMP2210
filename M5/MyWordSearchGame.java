import java.util.List;
import java.util.LinkedList;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.lang.Math;


/**
 * Defines methods for my word search game.
 * @author Ryan Litwin (rpl0010@auburn.edu)
 * @version 3-20-20
 */

public class MyWordSearchGame implements WordSearchGame {
   private TreeSet<String> lex;
   private String[][] gameBoard;
   private int size;
   private SortedSet<String> allWords;
   
   /**
    * constructor for my word search game.
    */
   public MyWordSearchGame() {
      lex = null;
      size = 4;
      gameBoard = new String[size][size];
      gameBoard[0][0] = "E";
      gameBoard[0][1] = "E";
      gameBoard[0][2] = "C";
      gameBoard[0][3] = "A";
      gameBoard[1][0] = "A";
      gameBoard[1][1] = "L";
      gameBoard[1][2] = "E";
      gameBoard[1][3] = "P";
      gameBoard[2][0] = "H";
      gameBoard[2][1] = "N";
      gameBoard[2][2] = "B";
      gameBoard[2][3] = "O";
      gameBoard[3][0] = "Q";
      gameBoard[3][1] = "T";
      gameBoard[3][2] = "T";
      gameBoard[3][3] = "Y";
   }
   
   /**
    * @param fileName as a string
    * @throws IllegalArgumentException
    * loads the lexicon into the data.
    */
   public void loadLexicon(String fileName) {
      if (fileName == null) {
         throw new IllegalArgumentException();
      }
      try {
         lex = new TreeSet<String>();
         Scanner scan = new Scanner(new File(fileName));
         while (scan.hasNext()) {
            lex.add(scan.next().toUpperCase());
         }
         scan.close();
      }
      catch (FileNotFoundException e) {
         throw new IllegalArgumentException();
      }
   }
   
   /**
    * @param letterArray as a string array.
    * @throws  IllegalArgumentException.
    * sets up and stores the array of strings in the data structure.
    */
   public void setBoard(String[] letterArray) {
      if (letterArray == null) {
         throw new IllegalArgumentException();
      }
      double n = Math.sqrt((double) letterArray.length);
      if (n % 1 > 0.00001) {
         throw new IllegalArgumentException();
      }
      gameBoard = new String[(int) n][(int) n];
      int i = 0;
      for (int j = 0; j < n; j++) {
         for (int k = 0; k < n; k++) {
            gameBoard[j][k] = letterArray[i++];
         }
      }
      size = (int) n;
   }
   
   /**
    * creates a string representation of the board.
    * @return stringBoard as a string
    */
   public String getBoard() {
      String stringBoard = "";
      for (int i = 0; i < size; i++) {
         stringBoard += "\n| ";
         for (int j = 0; j < size; j++) {
            stringBoard += gameBoard[i][j] + " ";
         }
         stringBoard += "|";
      }
      return stringBoard;
   }
   
   /**
    * gets all the valid words on the game board.
    * @param minimumWordLength as an int
    * @throws IllegalArgumentException
    * throws IllegalStateException
    * @return allWords as a sorted set
    */
   public SortedSet<String> getAllValidWords(int minimumWordLength) {
      if (minimumWordLength < 1) {
         throw new IllegalArgumentException();
      }
      if (lex == null) {
         throw new IllegalStateException();
      }
      allWords = new TreeSet<String>();
      LinkedList<Integer> wordProgress = new LinkedList<Integer>();
      for (int i = 0; i < (size * size); i++) {
         wordProgress.add(i);
         if (isValidWord(toWord(wordProgress)) 
            && toWord(wordProgress).length() >= minimumWordLength) {
            allWords.add(toWord(wordProgress));
         }
         if (isValidPrefix(toWord(wordProgress))) {
            wordBoardSearch(wordProgress, minimumWordLength);
         }
         wordProgress.clear();
      }
      return allWords;
   }
   
   private LinkedList<Integer> wordBoardSearch(LinkedList<Integer> 
      wordProgress, int min) {
      Position[] adjArray = new Position(wordProgress.getLast()).adjacent(wordProgress);
      for (Position p : adjArray) {
         if (p == null) {
            break;
         }
         wordProgress.add(p.getIndex());
         if (isValidPrefix(toWord(wordProgress))) {
            if (isValidWord(toWord(wordProgress)) 
               && toWord(wordProgress).length() >= min) {
               allWords.add(toWord(wordProgress));
            }
            wordBoardSearch(wordProgress, min);
         }
         else {
            wordProgress.removeLast();
         }
      }
      wordProgress.removeLast();
      return wordProgress;
   }
   
   /**
    * gets the score for all the scorable words in the set.
    * @param words as the sorted set
    * @param minimumWordLength as an int
    * @throws IllegalArgumentException
    * @throws IllegalStateException
    * @return gameScore as an int
    */
   public int getScoreForWords(SortedSet<String> words, int minimumWordLength) {
      if (minimumWordLength < 1) {
         throw new IllegalArgumentException();
      }
      if (lex == null) {
         throw new IllegalStateException();
      }
      int gameScore = 0;
      Iterator<String> itr = words.iterator();
      while (itr.hasNext()) {
         String word = itr.next();
         if (word.length() >= minimumWordLength 
            && isValidWord(word) && !isOnBoard(word).isEmpty()) {
            gameScore += (word.length() - minimumWordLength) + 1;
         }
      }
      return gameScore;
   }
   
   /**
    * checks if the word in the set is in the lexicon.
    * @param wordToCheck as a string
    * @throws IllegalArgumentException
    * @throws IllegalStateException
    * @return wordToCheck as the word to be checked
    */
   public boolean isValidWord(String wordToCheck) {
      if (wordToCheck == null) {
         throw new IllegalArgumentException();
      }
      if (lex == null) {
         throw new IllegalStateException();
      }
      return lex.contains(wordToCheck);
   }
   
   
   /**
    * checks if there is at least one word in the
    * lexicon with the given prefix.
    * @param prefixToCheck as a string
    * @throws IllegalArgumentException
    * @throws IllegalStateException
    * @return true if it appears in lexicon, false if not
    */
   public boolean isValidPrefix(String prefixToCheck) {
      if (prefixToCheck == null) {
         throw new IllegalArgumentException();
      }
      if (lex == null) {
         throw new IllegalStateException();
      }
      String aB = lex.ceiling(prefixToCheck);
      if (aB != null) {
         return aB.startsWith(prefixToCheck);
      }
      return false;
   }
   
   
   /**
    * checks if a word is on the board.
    * @param wordToCheck as a string
    * @throws IllegalArgumentException
    * @throws IllegalStateException
    * @return wordPath to see the path of the word
    */
   public List<Integer> isOnBoard(String wordToCheck) {
      if (wordToCheck == null) {
         throw new IllegalArgumentException();
      }
      if (lex == null) {
         throw new IllegalStateException();
      }
      LinkedList<Integer> aBList = new LinkedList<Integer>();
      List<Integer> wordPath = boardSearch(wordToCheck, aBList, 0);
      return wordPath;
   }
   
   
   private LinkedList<Integer> boardSearch(String 
      wordToCheck, LinkedList<Integer> listProgress, int posIndex) {
      if (listProgress.size() > 0 && !wordToCheck.equals(toWord(listProgress))) {
         Position[] adjArray = new Position(posIndex).adjacent(listProgress);
         for (Position p : adjArray) {
            if (p == null) {
               break;
            }
            listProgress.add(p.getIndex());
            if (wordToCheck.equals(toWord(listProgress))) {
               break;
            }
            if (wordToCheck.startsWith(toWord(listProgress))) {
               boardSearch(wordToCheck, listProgress, p.getIndex());
            }
            else {
               listProgress.removeLast();
            }
         }
      }
      if (listProgress.size() == 0) {
         while (posIndex < (size * size)) {
            if (wordToCheck.startsWith(new Position(posIndex).getLetter())) {
               listProgress.add(posIndex);
               boardSearch(wordToCheck, listProgress, posIndex);
            }
            posIndex++;
         }
         return listProgress;
      }
      if (toWord(listProgress).equals(wordToCheck)) {
         return listProgress;
      }
      else {
         listProgress.removeLast();
         return listProgress;
      }
   }
   
   /**
    * takes an integer list and creates its corresponding word.
    * @param listIn as a linked list(integer list)
    * @return word as a string
    */
   public String toWord(LinkedList<Integer> listIn) {
      String word = "";
      for (int i : listIn) {
         word += new Position(i).getLetter();
      }
      return word;
   }
   
   
   private class Position {
      private int x;
      private int y;
      private int index;
      private String letter;
      private static final int MAX_ADJ = 8;
      
      Position(int indexIn) {
         this.index = indexIn;
         if (index == 0) {
            this.x = 0;
            this.y = 0;
         }
         else {
            this.x = index % size;
            this.y = index / size;
         }
         this.letter = gameBoard[y][x];
      }
      
      
      Position(int xIn, int yIn) {
         this.x = xIn;
         this.y = yIn;
         this.index = (y * size) + x;
         this.letter = gameBoard[y][x];
      }
      
      
      public Position[] adjacent(LinkedList<Integer> visitedP) {
         Position[] adj = new Position[MAX_ADJ];
         int k = 0;
         for (int i = this.x - 1; i <= this.x + 1; i++) {
            for (int j = this.y - 1; j <= this.y + 1; j++) {
               if (!(i == this.x && j == this.y)) {
                  if (isValid(i, j) && !visitedP.contains((j * size) + i)) {
                     Position aB = new Position(i, j);
                     adj[k++] = aB;
                  }
               }
            }
         }
         return adj;
      }
      
      
      public boolean isValid(int xT, int yT) {
         return xT >= 0 && xT < size && yT >= 0 && yT < size;
      }
      
      public int getIndex() {
         return index;
      }
      
      public String getLetter() {
         return letter;
      }

   }
}

            
   