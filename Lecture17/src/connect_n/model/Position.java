package connect_n.model;

import java.util.Comparator;
import java.util.Objects;

/**
 * Represents grid positions.
 */
public final class Position implements Comparable<Position> {
  private final int x;
  private final int y;

  public Position(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int x() {
    return x;
  }

  public int y() {
    return y;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Position)) return false;
    Position other = (Position) o;
    return this.x == other.x && this.y == other.y;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }

  private static final Comparator<Position> COMPARATOR =
      Comparator
          .comparingInt(Position::y)
          .thenComparingInt(Position::x);

  @Override
  public int compareTo(Position other) {
    return COMPARATOR.compare(this, other);
  }
}