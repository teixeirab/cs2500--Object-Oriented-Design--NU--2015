/**
 * Created by becogontijo on 4/6/2015.
 */
public class LaxCoinGameModel extends AbstractCoinGameModel {

  LaxCoinGameModel(String board){
    super(board);
  }

  @Override
  public void move(int coinIndex, int newPosition) {
    if (isGameOver()) {
      throw new IllegalMoveException("Game is over");
    } else if (newPosition - getCoinPosition(coinIndex) > 2) {
      throw new IllegalMoveException("Move is larger than two steps");
    } else if (newPosition > getCoinPosition(coinIndex)) {
      throw new IllegalMoveException("Cannot move to the right");
    } else if (newPosition == getCoinPosition(coinIndex - 1) && coinIndex != 0) {
      throw new IllegalMoveException("Cannot move the coin on top of another");
    } else if (newPosition < 0) {
      throw new IllegalMoveException("New Position is out of bounds");
    }

    putCoin(coinIndex, newPosition);
  }
}
