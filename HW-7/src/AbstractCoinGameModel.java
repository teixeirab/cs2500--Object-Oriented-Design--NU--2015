/**
 * Created by becogontijo on 4/1/2015.
 */
public abstract class AbstractCoinGameModel implements CoinGameModel {

  /*
    @param board is the string that represents the initial state of the coin game
    @throw illegalArgumentException if the board is less than 1 char long
    @throw illegalArgumentException if tbe board contains chars that are not "O" or "-"
  */
  protected abstract AbstractCoinGameModel fromBoard(String board);

  public String board; /* INVARIANT board must not be an empty string and can only
                                 contains only "O" & "-" */
  /*
    Constructs a strict coin game given a string

    @param board is the string that represents the initial state of the coin game
    @throw illegalArgumentException if the board is less than 1 char long
    @throw illegalArgumentException if tbe board contains chars that are not "O" or "-"
   */

  public AbstractCoinGameModel(String board) {

    if (board.length() < 1) {
      throw new IllegalArgumentException("board cannot be an empty string");
    }

    if (countString(board, '-') + countString(board, 'O') != board.length()) {
      throw new IllegalArgumentException("board contains invalid characters");
    }
    this.board = board;
  }

  /*
  Counts the number of a given {@code char} in a string. Helper method used in the constructor
  in other to make sure there are only "O" and "-" in the board

  @param t1 represents the string that contains all the chars that need to be counted
  @param c2 represents the char that will be counted
 */
  public int countString(String t1, char c2) {
    int temp = 0;
    for (int index = 0; index < t1.length(); index++) {
      if (t1.charAt(index) == c2) {
        temp++;
      }
    }
    return temp;
  }

  @Override
  public int boardSize() {
    return getBoard().length();
  }

  @Override
  public int coinCount() {
    int result = 0;
    for (int index = 0; index < boardSize(); index++) {
      if (getBoard().charAt(index) == 'O') {
        result = result + 1;
      }
    }
    return result;
  }

  @Override
  /*
    @throw illegalArgumentException when the given coin index is not found in the board
   */
  public int getCoinPosition(int coinIndex) {
    int index = 0;
    while (coinIndex >= 0) {
      if (index >= boardSize()) {
        throw new IllegalArgumentException("Coin could not be found at the given index");
      } else if (getBoard().charAt(index) == 'O' && coinIndex == 0) {
        coinIndex = -1;
      } else if (getBoard().charAt(index) == 'O') {
        index = index + 1;
        coinIndex = coinIndex - 1;
      } else {
        index = index + 1;
      }
    }
    return index;
  }

  @Override
  public boolean isGameOver() {
    return coinCount() == 0 || coinCount() == boardSize() || coinCount() == 1 || outOfMoves();
  }

  /*
    Identifies whether there are any moves left in a strict coin game. For Example "-O-O-" still
    has two more possible moves but "00---" has no more possible moves. In a strict coin game there
    are no more moves when all the left most spaces are filled with coins and there are no coins in
    the right side of the game.
   */
  private boolean outOfMoves() {
    return !(getBoard().substring(0, coinCount()).contains("-"));
  }

  @Override
  public String toString() {
    return getBoard();
  }

  /*
    Places a given coin in a {@code newPosition} of a board. Helper method of move()
    @param coinIndex represents the index of the coin that will be replaced
    @param newPosition represents the index that the coin will be put
   */
  public String putCoin(int coinIndex, int newPosition) {
    String temp = "";
    for (int index = 0; index < boardSize(); index++) {
      if (index == newPosition) {
        temp = temp + "O";
      } else if (index == getCoinPosition(coinIndex)) {
        temp = temp + "-";
      } else {
        temp = temp + getBoard().charAt(index);
      }
    }
    return temp;
  }

}
