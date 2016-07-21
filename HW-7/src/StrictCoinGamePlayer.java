/**
 * Created by andrewhombach on 4/3/15.
 */
public class StrictCoinGamePlayer implements CoinGamePlayer {

  private CoinGameModel game;
  private String name;

  /**
   * Creates a new player to play on the provided game
   *
   *
   * @param name the name of the player being constructed
   * @param game the game the player will play on
   * @throws IllegalArgumentException if either name or game is null
   * @throws IllegalArgumentException if name's length is < 1
   */
  StrictCoinGamePlayer(String name, CoinGameModel game) {
    this.game = game;
    this.name = name;

    if (name == null || game == null) {
      throw new IllegalArgumentException("Neither parameter can be null");
    }

    if (name.length() < 1 ) {
      throw new IllegalArgumentException("Name must be longer than 1");
    }

  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void move(int coinIndex, int newPosition) {
    if (isTurn()) {
      game.move(coinIndex, newPosition);
    }
    else {
      throw new IllegalArgumentException("it is not this player's turn");
    }
  }

  @Override
  public boolean isTurn() {
    return game.getCurrentPlayer().equals(name);
  }
}
