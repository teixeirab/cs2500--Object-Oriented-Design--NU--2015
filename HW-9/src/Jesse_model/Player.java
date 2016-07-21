package Jesse_model;

/**
 * Represents a draughts player.
 */
public enum Player {
  First {
    public Player other() {
      return Second;
    }

    public char toChar() {
      return '+';
    }
  },

  Second {
    public Player other() {
      return First;
    }

    public char toChar() {
      return 'o';
    }
  },

  Empty {
    public Player other(){return Empty;}

    public char toChar() {return ' ';}
  } ;



  /**
   * Returns the player that is not this player.
   *
   * @return the other player
   */
  public abstract Player other();

  /**
   * Returns a character as a printable representation of this player.
   *
   * @return 'o' or '+'
   */
  public abstract char toChar();
}
