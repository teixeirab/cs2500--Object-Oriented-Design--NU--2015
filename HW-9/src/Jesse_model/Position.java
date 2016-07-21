package Jesse_model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents game board positions. A checkers board consists of 64 squares,
 * 32 of which are positions used in the game.
 *
 * <p>There are two different ways to refer to positions that this class
 * relates:
 *
 * <ul>
 *   <li>
 *   As a pair of coordinates, the row and the column. We use integers from
 *   0 to 7, with the 0 row being the farthest from the first player and
 *   the 0 column being on the first player's left.
 *
 *   An advantage of this representation is that makes it easy to see how
 *   positions are related in board-space. A disadvantage is that even if
 *   both a row and column are in bounds, they may not represent a valid
 *   position as a pair, since only half the squares are used.
 *
 *   <li>As an index, based on numbering the valid positions in a fixed order.
 *   Traditionally, checkers uses integers from 1 to 32 to refer to valid
 *   positions, counting left-to-right along each row, and then down to the
 *   next row, starting in the first player's upper-left and finishing in
 *   their lower-right. This class uses the same order, but with zero-based
 *   indexing. That is, we count along row 0, starting in column 0 and moving
 *   toward column 7, then row 1 from column 0 to column 7, and so on.
 *
 *   An advantage of this representation is that it's easy to check for
 *   validity (is it in the range?) and as an index into an array or list. A
 *   disadvantage is that it's less clearly related to the geometry of the game.
 * </ul>
 *
 * <p>Positions are interned (generalization of the singleton pattern), and
 * only one object for each valid position will be created. This means that
 * positions can always be compared by physical equality.
 */
public final class Position implements Comparable<Position> {
  /**
   * The board is a {@code BOARD_SIZE}-by-{@code BOARD_SIZE} square. This
   * constant should likely be defined somewhere else in your code and only
   * referenced from here...
   */
  public static final int BOARD_SIZE = 8;

  /**
   * The number of valid positions on the board, given the board size.
   */
  public static final int N_POSITIONS = BOARD_SIZE * BOARD_SIZE / 2;

  /**
   * The row, numbered from the top from first player-perspective.
   */
  private final int row;

  /**
   * The column, numbered from the left from first player-perspective.
   */
  private final int column;

  /**
   * We will create one instance for each board position up front and store
   * them in this array. After this initialization, no more positions will
   * ever be created, since we will have all 32 that we need.
   */
  private static final List<Position> CACHE;

  /*
   * This is a static initialization block, which is run to initialize the
   * class, before any instances are constructed. Here we initialize the
   * position cache.
   */
  static {
    List<Position> newCache = new ArrayList<>(N_POSITIONS);

    for (int row = 0; row < BOARD_SIZE; ++row) {
      for (int column = 0; column < BOARD_SIZE; ++column) {
        if (isValidPosition(row, column)) {
          newCache.add(new Position(row, column));
        }
      }
    }

    CACHE = Collections.unmodifiableList(newCache);
  }

  /**
   * Returns a position object for the given row and column, if the row and
   * column represent a valid position. This static factory method may not
   * allocate a new object every time it's called.
   *
   * @param row     the row
   * @param column  the columntdfx
   * @return        the position
   * @throws IndexOutOfBoundsException if row and column do not refer to a
   *                                   valid position.
   */
  public static Position fromRowColumn(int row, int column) {
    return fromIndex(toIndex(row, column));
  }

  /**
   * Returns a position object for the given index. Indices start at 0 in the
   * first player's upper left (actually, row 0 column 1) and proceed
   * left-to-right along each row before continuing in the next row. This
   * static factory method may not allocate a new object every time
   * it's called.
   *
   * @param index the row
   * @return the position
   * @throws IndexOutOfBoundsException if row and column do not refer to a
   *                                   valid position.
   */
  public static Position fromIndex(int index) {
    return CACHE.get(index);
  }

  /**
   * Determines whether the given row and column together represent a valid
   * position.
   *
   * @param row    the row
   * @param column the column
   * @return whether the position is valid
   */
  public static boolean isValidPosition(int row, int column) {
    return row >= 0 && row < BOARD_SIZE
            && column >= 0 && column < BOARD_SIZE
            && (row + column) % 2 == 1;
  }

  // Helper for validity checks
  private static void ensureValidPosition(int row, int column) {
    if (! isValidPosition(row, column)) {
      throw new IndexOutOfBoundsException("bad row and column row:" + row + " column:" + column);
    }
  }

  /**
   * The row of this position, numbered from top from the first player's
   * perspective. Zero based.
   *
   * @return [0, 7]
   */
  public int row() {
    return row;
  }

  /**
   * The column of this position, numbered from left from the first player's
   * perspective. Zero based.
   *
   * @return [0, 7]
   */
  public int column() {
    return column;
  }

  /**
   * The index of this position, numbered from left to right and from top to
   * bottom, from the first player's perspective. Zero based.
   *
   * @return [0, 31]
   */
  public int index() {
    return toIndexUnchecked(row, column);
  }

  /**
   * Converts a row and column to their index representation.
   *
   * @param row the row
   * @param column the column
   * @return the corresponding index
   * @throws IndexOutOfBoundsException if row and column do not refer to a
   *                                   valid position
  */
  public static int toIndex(int row, int column) {
    ensureValidPosition(row, column);
    return toIndexUnchecked(row, column);
  }

  // Helper for doing the arithmetic once we know a row-column pair is good.
  private static int toIndexUnchecked(int row, int column) {
    return (row * BOARD_SIZE + column) / 2;
  }

  /**
   * Determines whether the row of this position appears above the row of the
   * other position from the perspective of the first player.
   *
   * @param other the other position
   * @return {@code this.row() < other.row()}
   */
  public boolean isAbove(Position other) {
    return this.row < other.row;
  }

  /**
   * Determines whether the row of this position appears below the row of the
   * other position from the perspective of the first player.
   *
   * @param other the other position
   * @return {@code this.row() > other.row()}
   */
  public boolean isBelow(Position other) {
    return this.row > other.row;
  }

  /**
   * Determines whether the other position is a single jump move away
   * from this position.
   *
   * @param other the other position
   * @return whether the positions are one jump apart
   */
  public boolean isJumpAdjacentTo(Position other) {
    return Math.abs(this.row - other.row) == 2
            && Math.abs(this.column - other.column) == 2;
  }

  /**
   * Returns the position that would be jumped over from this position to
   * another position.
   *
   * @param other the other position (must be jump-adjacent to this)
   * @return the position in between
   * @throws IllegalArgumentException f {@code !this.isJumpAdjacentTo(other)}
   */
  public Position findJumpedPosition(Position other) {
    if (! isJumpAdjacentTo(other)) {
      throw new IllegalArgumentException("not jump-adjacent");
    }

    return fromRowColumn((this.row + other.row) / 2,
                                    (this.column + other.column) / 2);
  }


  /**
   * Overloaded adjacentPositions method in order to return an ArrayList</Position>
   * @param d either 1 or 2, represents the distance to be calculated
   * @return the adjacent positions
   */
  public ArrayList<Position> adjacentPositions(int d) {
    ArrayList<Position> temp = new ArrayList<>();
    Integer[] yc = {d, -d, -d, d};
    Integer[] xc = {d, d, -d, -d};

    for (int i = 0; i < 4; i++) {
      int c = row + xc[i];
      int r = column + yc[i];

      if (isValidPosition(c, r)) {
        temp.add(fromRowColumn(c, r));
      }
    }
    return temp;
  }


  /*
   * equals(Object) and hashCode() are not overridden because the interning
   * means that physical equality and extensional equality are the same.
   */

  /**
   * Compares positions by their indices.
   *
   * @param other the other position to compare to
   * @return {@code Integer.compare(this.index(), other.index())}
   */
  @Override
  public int compareTo(Position other) {
    return Integer.compare(this.index(), other.index());
  }


  // Only called to create the 32 interned positions, and never again.
  private Position(int row, int column) {
    assert CACHE == null : "no new positions once initialized";

    this.row = row;
    this.column = column;
  }
}
