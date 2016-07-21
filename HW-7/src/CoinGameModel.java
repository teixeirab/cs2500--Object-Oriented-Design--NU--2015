import java.util.ArrayList;

/**
 * An interface for playing a coin game. The rules of a particular coin game will be implemented by
 * classes that implement this interface.
 */
public interface CoinGameModel {

  /**
   * Gets the size of the board (the number of squares)
   *
   * @return the board size
   */
  int boardSize();

  /**
   * Gets the board of the game
   *
   * @return the board of the gmae
   */
  String getBoard();

  /**
   * Gets the number of coins.
   *
   * @return the number of coins
   */
  int coinCount();

  /**
   * Gets the (zero-based) position of coin number {@code coinIndex}.
   *
   * @param coinIndex which coin to look up
   * @return the coin's position
   */
  int getCoinPosition(int coinIndex);

  /**
   * Returns whether the current game is over. The game is over if there are no valid moves.
   *
   * @return whether the game is over
   */
  boolean isGameOver();

  /**
   * Moves coin {@code coinIndex} to position {@code newPosition}, if the requested move is legal.
   * Throws {@code IllegalMoveException} if the requested move is illegal, which can happen in
   * several ways:
   *
   * <ul> <li>There is no coin with the requested index.</li> <li>The new position is occupied by
   * another coin.</li> <li>There is some other reason the move is illegal, as specified by the
   * concrete game.</li> </ul>
   *
   * @param coinIndex   which coin to move (numbered from the left)
   * @param newPosition where to move it to
   * @throws CoinGameModel.IllegalMoveException the move is illegal
   */
  void move(int coinIndex, int newPosition);

  /**
    Gets the player whose current turn is
   */
  String getCurrentPlayer();

  /**
    Adds a new Player at a {@code location} in the list of Id. A player can be added at
    any time during the game
    @throws IllegalArgumentException if the player is added at an index that is not available. An
    index is not available if it is larger than the size of the list or if it is negative
   */
   void addPlayer(int location, String name);

  /**
     Rotates the turn by one. If the current Player is 1 then the following player should be player
     2. If the game does not have a next player, nextTurn starts over at the first player in the
     game.
   */
  void nextTurn();

  /**
    Returns the player that has won the game in case isGameOver() is true.
    A game is won when there are no more valid moves left to be made.
    For example "OOO----" is a won game in the Strict and Lax coin games.
    This method can only be called if isGameOver is true

   */
  String playerWon();


  /**
   * Returns a list of players in the game
   *
   * @return an ArrayList of player names
   */
  ArrayList<String> getPlayers();


  /**
   * The exception thrown by {@code move} when the requested move is illegal.
   *
   * <p>(Implementation Note: Implementing this interface doesn't require "implementing" the {@code
   * IllegalMoveException} class. Nesting a class within an interface is a way to strongly associate
   * that class with the interface, which makes sense here because the exception is intended to be
   * used specifically by implementations and clients of this interface.)
   */
  static class IllegalMoveException extends IllegalArgumentException {

    /**
     * Constructs a illegal move exception with no description.
     */
    public IllegalMoveException() {
      super();
    }

    /**
     * Constructs a illegal move exception with the given description.
     *
     * @param msg the description
     */
    public IllegalMoveException(String msg) {
      super(msg);
    }
  }
}
