import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by wsgreen on 10/15/15.
 */
public class Kicker {

  public static Map<String, Set<String>> categories = new HashMap<>();
  public static Set<String> words = new HashSet<>();

  public static Map<String, Set<Integer>> problem = new HashMap<>();
  public static Integer length = 0;

  public static void readDict(String file) {
    File f = new File(file);
    Scanner in = null;
    try {
      in = new Scanner(f);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    while(in.hasNextLine()) {
      String[] line= in.nextLine().split(":");

      String category = line[0].trim();
      HashSet<String> ws = new HashSet<>();
      for(String word: line[1].split(",")) {
        String tword = word.trim();
        words.add(tword);
        ws.add(tword);
      }
      categories.put(category, ws);
    }
  }

  public static void readProblem(String file) {
    File f = new File(file);
    Scanner in = null;
    try {
      in = new Scanner(f);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    length = Integer.valueOf(in.nextLine().trim());

    /*for(int i=0;i<length;i++) {
      problem.put(i, new ArrayList<>());
    }*/
    while(in.hasNextLine()) {

      String[] line= in.nextLine().split(":");

      String category = line[0].trim();
      problem.put(category, new HashSet<>());

      for(String s: line[1].split(",")) {
        int index = Integer.valueOf(s.trim())-1;
        problem.get(category).add(index);
      }

    }
  }


  public static void main(String[] args) {
    String[] puzzles = new String[] {
            "./puzzle1.txt",
            "./puzzle2.txt",
            "./puzzle3.txt",
            "./puzzle4.txt",
            "./puzzle5.txt"
    };

    readDict("./wordlist.txt");

    for(String p: puzzles){
      problem = new HashMap<>();
      readProblem(p);
      LetterBased lb = new LetterBased();
      System.out.println("L: "+lb.getWord(""));
      WordBased wb = new WordBased();
      System.out.println("W: "+wb.getWord());
    }

  }
}
