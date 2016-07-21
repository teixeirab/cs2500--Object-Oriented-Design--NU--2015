import java.util.ArrayList;

/**
 * Created by andrewhombach on 4/3/15.
 */
public class CoinGameModelAdaptor implements NewCoinGameModel {

  CoinGameModel adaptee;

  /**
   *
   * @param adaptee
   */
  private CoinGameModelAdaptor(CoinGameModel adaptee) {
    this.adaptee = adaptee;
  }

  public static CoinGameModelAdaptor fromString(String initialBoard, String... players) {
    CoinGameModel adapteeCoin =  new StrictCoinGameModel(initialBoard, players);
    return new CoinGameModelAdaptor(adapteeCoin);
  }

  @Override
  public int boardSize() {
    return adaptee.boardSize();
  }

  @Override
  public int coinCount() {
    return adaptee.coinCount();
  }

  @Override
  public int[] getCoinPositions() {
    int[] result = new int[boardSize()];

    for (int i = 0; i < adaptee.coinCount(); ++i) {
      result[i] = adaptee.getCoinPosition(i);
    }
    return result;
  }

  @Override
  public CoinGamePlayer[] getPlayOrder() {
    CoinGamePlayer[] result = new CoinGamePlayer[adaptee.getPlayers().size()];

    ArrayList<String> listOfPlayers = new ArrayList<String>();
    listOfPlayers = adaptee.getPlayers();

    for (int i = 0; i < listOfPlayers.size(); ++i){
      result[i] = new StrictCoinGamePlayer(listOfPlayers.get(i), adaptee);

    }
    return result;
  }

  @Override
  public CoinGamePlayer getWinner() {
    CoinGamePlayer[] l = getPlayOrder();
    CoinGamePlayer p = getCurrentPlayer();

    if (adaptee.isGameOver()) {
      for (int i = 0; i < l.length; i++) {
        if (l[i].getName().equals(p.getName())) {
          if (i == 0) {
            return l[l.length - 1];
          } else {
            return l[i - 1];
          }

        }
      }

    }
    return null;
  }

  @Override
  public CoinGamePlayer getCurrentPlayer() {
    CoinGamePlayer[] x = getPlayOrder();

    for (CoinGamePlayer c: x) {
      if (c.isTurn()) {
        return c;
      }
    }
    return null;
  }

  @Override
  public CoinGamePlayer addPlayerAfter(CoinGamePlayer predecessor, String name) {

    if (predecessor == null || name == null) {
      throw new IllegalArgumentException("the parameters for this method cannot be null");
    }

    boolean found = false;
    int i = 0;
    int newLoc = 0;


    for(CoinGamePlayer c: getPlayOrder()) {
      if (c.getName().equals(predecessor.getName())) {
        adaptee.addPlayer(i + 1, name);
        found = true;
        newLoc = i + 1;
      }
      i++;
    }

    if (!found) {
      throw new IllegalArgumentException("the predecessor is not a player");
    }

    return getPlayOrder()[newLoc];
  }
}
