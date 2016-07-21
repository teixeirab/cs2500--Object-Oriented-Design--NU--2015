import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
 * Created by becogontijo on 4/15/2015.
 */

public final class Hangman {
  private final String secret;
  private final char[] guess;
  private final Collection<Character> pastGuesses = new HashSet<>();
  private int guessesRemaining = GUESS_LIMIT;
  private int blanksRemaning;

  public static final int GUESS_LIMIT = 7;

  public Hangman(String word){
    if (word.length() == 0){
      throw new IllegalArgumentException("bad");
    }
    for (char c : word.toCharArray()){
      checkValidCharacter(c);
    }
    secret = word;
    guess = new char[word.length()];
    blanksRemaning = word.length();
    Arrays.fill(guess, '_');
  }

  public boolean guess(char c) {
    checkValidCharacter(c);

    if (isLost() || isWon()){
      throw new IllegalStateException("game is over");
    }
    if (pastGuesses.contains(c)) {
      throw new IllegalArgumentException("Already_guessed:" + c);
    }

    pastGuesses.add(c);

    boolean goodGuess = false;

    for (int i = 0; i < secret.length(); ++i){
      if (secret.charAt(i) == c){
        guess[i] = c;
        goodGuess = true;
        --blanksRemaning;
      }
    }
    if (!goodGuess){
      --guessesRemaining;
    }
    return goodGuess;
  }

  private static void checkValidCharacter(char c){
    if (! Character.isUpperCase(c)){
      throw new IllegalArgumentException("bad");
    }
  }

  public char[] wordState(){
    return guess.clone();
  }

  public boolean isWon(){
    return blanksRemaning == 0;
  }

  public boolean isLost(){
    return guessesRemaining == 0;
  }

  public int guessesRemaining(){
    return guessesRemaining;
  }

  public int blanksRemaining(){
    return blanksRemaning;
  }
}
