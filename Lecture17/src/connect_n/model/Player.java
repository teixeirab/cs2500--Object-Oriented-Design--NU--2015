package connect_n.model;

/**
 * Represents the two players in the game, White and Red.
 */
public enum Player {
  White, Red;

  public final Player other() {
    switch (this) {
      case White: return Red;
      case Red:   return White;
      default:    throw new RuntimeException("impossible");
    }
  }
}
