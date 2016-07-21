package connect_n.model;

import java.util.Set;

/**
 * Models a Connect-N game, which is a generalization of Connect Four. This
 * interface describes a class that represents the state of the game and allows
 * making moves.
 */
public interface Model {
  /** The default width of the grid. */
  int DEFAULT_WIDTH  = 7;
  /** The default height of the grid. */
  int DEFAULT_HEIGHT = 6;
  /** The default length of the line needed to win. */
  int DEFAULT_GOAL   = 4;

  /**
   * Constructs a new game model for Connect Four with the default parameters.
   *
   * @return the new game model
   * @see #connectN(int, int, int)
   * @see #builder()
   */
  static Model connectFour() {
    return builder().build();
  }

  /**
   * Constructs a new game model with the given parameters.
   *
   * @param width the width of the grid (positive)
   * @param height the height of the grid (positive)
   * @param goal the goal line length for the game ({@code > 1})
   * @return the new game model
   *
   * @see #connectFour()
   * @see #builder()
   */
  static Model connectN(int width, int height, int goal) {
    return builder()
        .width(width)
        .height(height)
        .goal(goal)
        .build();
  }

  static Builder builder() {
    return new ModelImpl.Builder();
  }

  /**
   * Gets the width of the game.
   *
   * @return the width
   */
  int getWidth();

  /**
   * Gets the height of the game.
   *
   * @return the height
   */
  int getHeight();

  /**
   * Gets the goal of the game.
   *
   * @return the goal
   */
  int getGoal();


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
  Player getNextPlayer();

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
   * Returns the set of positions in the grid forming the winning line.
   *
   * <p><strong>PRECONDITION:</strong> the game is over and has a winner
   *
   * @return the set of positions
   * @throws IllegalStateException if {@code getStatus() != Status.Won}
   */
  Set<Position> getWinningPositions();

  /**
   * Gets the player whose token is at the given column and row. The coordinates
   * are zero-based and start in the lower left. Returns {@code null} if there
   * is no token in the given position.
   *
   * @param x the column coordinate ({@code 0 <= x < width})
   * @param y the row coordinate ({@code 0 <= y < height})
   * @return the player in the given position, or {@code null}
   * @throws IndexOutOfBoundsException if (x, y) is out of bounds
   */
  Player getPlayerAt(int x, int y);

  /**
   * Determines whether the specified column is full and thus cannot be played
   * in.
   *
   * @param which the column to check
   * @return whether column {@code which} is full
   * @throws IndexOutOfBoundsException if {@code which} is out of bounds
   */
  default boolean isColumnFull(int which) {
    return getColumnSize(which) == getHeight();
  }

  /**
   * Determines the current size of a column, meaning how many tokens are in
   * it. If the column isn't full, then this is the row index of the first
   * empty cell in the given column.
   *
   * @param which the column
   * @return the column's size
   * @throws IndexOutOfBoundsException if {@code which} is out of bounds
   */
  int getColumnSize(int which);

  /**
   * Plays a move. Given the player (whose turn it must be) and the column
   * number (zero-based from the left), attempts to add that player to that
   * column. If this move ends the game then the game state is updated to
   * reflect that.
   *
   * @param who the player who is moving
   * @param where which column to play in
   * @return the height of the column after playing
   *
   * @throws IllegalStateException if the game is over
   * @throws IllegalStateException if it isn't {@code who}'s turn
   * @throws IllegalStateException if the requested column is full
   * @throws IndexOutOfBoundsException if the requested column does not exist
   */
  int move(Player who, int where);

  /**
   * Builds a {@link Model}, allowing the client to configure several
   * parameters. This is an instance of the <em>builder pattern</em>.
   */
  interface Builder {
    /**
     * Builds and returns the specified {@link Model}.
     * @return a new {@code ConnectNModel}
     */
    Model build();

    /**
     * Sets the width of the game grid.
     *
     * @param width the width (positive)
     * @return {@code this}, for method chaining
     */
    Builder width(int width);

    /**
     * Sets the height of the game grid.
     *
     * @param height the height (positive)
     * @return {@code this}, for method chaining
     */
    Builder height(int height);

    /**
     * Sets the goal line length for the game.
     *
     * @param goal the goal (positive)
     * @return {@code this}, for method chaining
     */
    Builder goal(int goal);
  }
}