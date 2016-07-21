package Jesse_model;

import java.util.ArrayList;


/**
 * Created by becogontijo on 4/21/2015.
 */
public interface Model {

  /**
   * Represents the status of the game.
   */
  enum Status { Playing, Stalemate, Won, }

  /**
   * Gets the current status of the game.
   *
   * @return the status
   */
  Status getStatus();

  /**
   * Determines whether the game is over.
   *
   * @return whether the game is over
   */
  default boolean isGameOver() {
    return getStatus() != Status.Playing;
  }

  /**
   * Gets the player whose turn it is now.
   *
   * <p><strong>PRECONDITION:</strong> the game isn't over
   *
   * @return the next player
   * @throws IllegalStateException if {@code isGameOver()}
   */
  Player getPlayer();


  /**
   * Returns the winner of the game.
   *
   * <p><strong>PRECONDITION:</strong> the game is over and has a winner
   *
   * @return the winner
   * @throws IllegalStateException if {@code getStatus() != Status.Won}
   */
  Player getWinner();

  /**
   * Gets the piece of the game given a x and y coordinate
   *
   * @param x the column coordinate ({@code 0 <= x < width})
   * @param y the row coordinate ({@code 0 <= y < height})
   * @return the piece in the given position
   */
  Piece getPieceAt(int x, int y);


  /**
   * Overloaded getPieceAt for convenience purposes
   * @param p the position of the piece that needs to be extracted
   * @return the piece in the given position
   */
  Piece getPieceAt(Position p);

  /**
   * Gets the available movable positions of the given position for the current player
   * @param p the pieces that is selected to move
   * @return an array of valid movable positions given the selected initial origin
   */
  public ArrayList<Position> getValidDestinations(Position p);

  /**
   * Plays a move. Given the player (whose turn it must be) and the position
   * x-y coordinate (zero-based from the left) of the place to move the piece,
   * If this move ends the game then the game state is updated to
   * reflect that.

   * @return the height of the column after playing
   *
   * @throws IllegalStateException if the game is over
   */
  void move(int original, int newPlace);

  /**
   * Gets all pieces of the current player that have movable adjacent positions.
   * @return an array of positions in which the player can move
   */
  ArrayList<Position> getValidMovePositions();

  /**
   * Gets all pieces from the current player
   * @return the array of the pieces of the current player
   */
  ArrayList<Position> getCurrentPositions();

  /**
   * Gets the next Player
   * @return the next player of a game
   */
  Player getNextPlayer();

  /**
   * Gets the Pieces of a game
   * @return return pieces
   */
  ArrayList<Piece> getPieces();
}
