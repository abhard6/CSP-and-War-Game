import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by wsgreen on 10/15/15.
 */
public class LetterBased {
  private String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  private Map<String, TrieNode> map;
  private PrintWriter out=null;

  public LetterBased() {
    map = new HashMap<>();
    for(Map.Entry<String, Set<String>> entry: Kicker.categories.entrySet()) {
      TrieNode root = new TrieNode('-');
      for(String s: entry.getValue()) {
        TrieNode curr = root;
        for(int i=0;i<s.length();i++) {
          char c = s.charAt(i);
          if(curr.children.containsKey(c)) {
            curr =curr.children.get(c);
          }else{
            TrieNode temp = new TrieNode(s.charAt(i));
            curr.children.put(s.charAt(i), temp);
            curr = temp;
          }
        }
        curr.mark=true;
      }
      map.put(entry.getKey(), root);
    }

    try {
      out = new PrintWriter("./letterTrace.txt");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      System.exit(-1);
    }
  }

  public Set<String> getWord(String word) {
    if(word.isEmpty())
      out.print("root");
    else
      out.print(" -> "+word.charAt(word.length() - 1));

    Set<String> ret = new HashSet<>();
    if(word.length() == Kicker.length){
      ret.add(word);
      out.println(" (found word: "+word+")");
      return ret;
    }

    boolean hit = false;
    for(int i=0;i<alpha.length();i++) {
      char c = alpha.charAt(i);
      if(isValid(word+c)) {
        hit = true;
        ret.addAll(getWord(word + c));
      }
    }

    if(!hit) {
      out.println();
    }



    return ret;
  }

  public boolean isValid(String word) {
    for(Map.Entry<String, Set<Integer>> entry: Kicker.problem.entrySet()) {
      TrieNode curr = map.get(entry.getKey());
      for(int ind: entry.getValue()) {
        if(ind < word.length()) {
          if (curr.children.containsKey(word.charAt(ind)))
            curr = curr.children.get(word.charAt(ind));
          else
            return false;
        }else
          break;
      }
    }

    return true;

  }

  class TrieNode {
    char element;
    HashMap<Character, TrieNode> children;
    boolean mark = false;
    public TrieNode(char c) {
      element= c;
      children = new HashMap<>();
    }
  }

}
