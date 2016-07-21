package connect_n.model;

import java.util.*;

/**
 * Implementation of a Connect-N game model interface. This class represents
 * the state of the game and allows making moves.
 */
public final class ModelImpl implements Model {
  /*
   * CLASS INVARIANTS:
   *
   *  - width > 0
   *
   *  - height > 0
   *
   *  - goal > 0
   *
   *  - columns.size() == width
   *
   *  - for every column in columns, column.size() <= height
   *
   *  - all the elements of the elements of columns are non-null
   *
   *  - status != null
   *
   *     - if status == Playing, then winningPositions == null and
   *       the grid isn't full
   *
   *     - if status == Stalemate, then winningPositions == null and
   *       the grid *is* full
   *
   *     - if status == Won, then winningPositions.size() >= goal, the
   *       positions form a line, and that line belongs to player {@code turn}
   *
   *  - turn != null
   */

  private final int width;
  private final int height;
  private final int goal;

  private Set<Position> winningPositions;

  private Status status;
  private Player turn;    // if the status is Won then turn is the winner

  private final List<List<Player>> columns;

  private ModelImpl(int w, int h, int g, String... ps)
  {
    width = w;
    height = h;
    goal = g;

    status = Status.Playing;
    turn = Player.White;

    columns = new ArrayList<>(width);
    for (int i = 0; i < width; ++i) {
      columns.add(new ArrayList<>(height));
    }
  }

  /**
   * Constructs a builder for configuring and then creating a game connect_n.model
   * instance. Defaults to a game of Connect Four with players named â€œWhiteâ€
   * and â€œRedâ€.
   *
   * @return the new builder
   */
  static Builder builder() {
    return new Builder();
  }

  @Override
  public int getWidth() {
    return width;
  }

  @Override
  public int getHeight() {
    return height;
  }

  @Override
  public int getGoal() {
    return goal;
  }

  @Override
  public Status getStatus() {
    return status;
  }


  @Override
  public Player getNextPlayer() {
    if (isGameOver()) {
      throw new IllegalStateException("the game is over");
    }

    return turn;
  }

  @Override
  public Player getWinner() {
    if (getStatus() != Status.Won) {
      throw new IllegalStateException("the game isn't over");
    }

    return turn;
  }

  @Override
  public Set<Position> getWinningPositions() {
    if (getStatus() != Status.Won) {
      throw new IllegalStateException("the game isn't over");
    }

    return winningPositions;
  }
  @Override
  public Player getPlayerAt(int x, int y) {
    ensureBounds(x, y);

    List<Player> column = columns.get(x);

    if (y < column.size()) {
      return column.get(y);
    } else {
      return null;
    }
  }

  @Override
  public int getColumnSize(int which) {
    // every column has a row 0, so this effectively checks the column:
    ensureBounds(which, 0);

    return columns.get(which).size();
  }

  @Override
  public int move(Player who, int where) {
    if (status != Status.Playing) {
      throw new IllegalStateException("game over");
    }

    if (who != turn) {
      throw new IllegalStateException("out of turn");
    }

    // every column has a row 0, so this effectively checks the column:
    ensureBounds(where, 0);

    if (isColumnFull(where)) {
      throw new IllegalStateException("attempt to play in full column");
    }

    /* The column list is only long enough to contain the tokens played
     * thus far, so it's possible that a valid row is above the filled
     * portion of the column. */
    List<Player> column = columns.get(where);
    int row = column.size();
    column.add(who);

    if (!checkForWinner(where, row) && !checkForStalemate()) {
      turn = turn.other();
    }

    return row;
  }

  /** Offsets specifying the directions in which lines may be made. */
  private static int[][] DIRECTIONS =
      new int[][] { {  0,  1 },  // Up and down
                    {  1,  0 },  // Left and right
                    {  1,  1 },  // Up-right and down-left
                    {  1, -1 },  // Up-left and down-right
      };

  /**
   * Checks for a winner and updates the state. Takes the coordinates of
   * the most recent move and looks for newly completed lines starting there.
   * If a winner is found, updates the state to reflect this.
   *
   * @param x the column of the most recent move
   * @param y the row of the most recent move
   * @return whether the game is over
   */
  private boolean checkForWinner(int x, int y) {
    for (int[] dir : DIRECTIONS) {
      int dx = dir[0];
      int dy = dir[1];

      // We count in the forward direction using the pair of offsets and the
      // backward direction by using their opposites.
      Set<Position> positions = lookInDirection(x, y, dx, dy);
      positions.addAll(lookInDirection(x, y, -dx, -dy));

      // Counting both directions and the current move, if we find a line
      // longer than goal then the game is won! (We need to subtract 1 so
      // that we don't double-count the current move.)
      if (positions.size() >= goal) {
        status = Status.Won;
        winningPositions = positions;
        return true;
      }
    }

    return false;
  }

  /**
   * Counts the length of the same-player line in a given direction. Includes
   * the player at the specified coordinates in the count. The direction is
   * specified as x and y offsets.
   *
   * @param x column to start at
   * @param y row to start at
   * @param dx step in the x direction
   * @param dy step in the y direction
   * @return the length of the line in the given direction
   */
  private Set<Position> lookInDirection(int x, int y, int dx, int dy) {
    Set<Position> result = new HashSet<>();

    Player player = getPlayerAt(x, y);

    // There should always be a token at (x, y) when this method is called
    assert(player != null);

    while (areInBounds(x, y)) {
      // Important that player is the receiver here, since getPlayerAt may
      // return null
      if (player.equals(getPlayerAt(x, y))) {
        result.add(new Position(x, y));
        x += dx;
        y += dy;
      } else {
        break;
      }
    }

    return result;
  }

  /**
   * Sets the game state to stalemate if the grid is full with no winner.
   *
   * @return whether the game is over
   */
  private boolean checkForStalemate() {
    for (List<?> column : columns) {
      if (column.size() < getHeight()) {
        return false;
      }
    }

    status = Status.Stalemate;
    return true;
  }

  /**
   * Ensures that the coordinates are in bounds for this game.
   *
   * @param x the column, 0-based from left
   * @param y the row, 0-based from bottom
   * @throws IndexOutOfBoundsException if (x, y) are out of bounds
   */
  private void ensureBounds(int x, int y) {
    if (!areInBounds(x, y)) {
      throw new IndexOutOfBoundsException("coordinates are out of bounds");
    }
  }

  /**
   * Checks whether the coordinates are in bounds for this game.
   *
   * @param x the column, 0-based from left
   * @param y the row, 0-based from bottom
   * @return whether (x, y) are in bounds
   */
  private boolean areInBounds(int x, int y) {
    return x >= 0 && x < width && y >= 0 && y < height;
  }

  /**
   * Builds a {@link Model}, allowing the client to configure several
   * parameters. This is an instance of the <em>builder pattern</em>.
   */
  protected static final class Builder implements Model.Builder {
    /*
     * INVARIANTS:
     *  - width is positive
     *  - height is positive
     *  - goal > 1
     *  - players is non-null, non-empty, and elements are non-null
     */
    private int width = DEFAULT_WIDTH;
    private int height = DEFAULT_HEIGHT;
    private int goal = DEFAULT_GOAL;

    @Override
    public Model build() {
      return new ModelImpl(width, height, goal);
    }

    @Override
    public Builder width(int width) {
      if (width < 1) {
        throw new IllegalArgumentException("dimensions must be positive");
      }

      this.width = width;
      return this;
    }

    @Override
    public Builder height(int height) {
      if (height < 1) {
        throw new IllegalArgumentException("dimensions must be positive");
      }

      this.height = height;
      return this;
    }

    @Override
    public Builder goal(int goal) {
      if (goal < 2) {
        throw new IllegalArgumentException("N must be at least 2");
      }

      this.goal = goal;
      return this;
    }
  }
}