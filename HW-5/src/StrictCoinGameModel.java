import java.util.ArrayList;


public class StrictCoinGameModel implements CoinGameModel {

  public ArrayList<String> playerIds = new ArrayList<String>(); /*Invariant: The list of Strings
                                        is unique and no string is empty*/

  private ArrayList<Boolean> board = new ArrayList<Boolean>(); /* Invariant: board is at least size
                                        1 and only contains
                                        O and - */

  int currentTurn = 0; /* Invariant: currentTurn must be equal or greater than 0 and cannot be
                              bigger than the number of players - 1." */

  /**
   * Constructs a strict coin game given a string
   *
   * @param board is the string that represents the initial state of the coin game
   * @throw illegalArgumentException if the board is less than 1 char long
   * @throw illegalArgumentException if tbe board contains chars that are not "O" or "-"
   */
  public StrictCoinGameModel(String board) {
    if (board.length() < 1) {
      throw new IllegalArgumentException("board must have at least 1 piece");
    }
    for (int i = 0; i < board.length(); i++) {
      if (!(board.charAt(i) == 'O' || board.charAt(i) == '-')) {
        throw new IllegalArgumentException("board can only contain - and O");
      }
    }
    for (int i = 0; i < board.length(); i++) {
      this.board.add(board.charAt(i) == 'O');
    }
  }

  @Override
  public int boardSize() {
    return board.size();
  }

  @Override
  public String getBoard() {
    String temp = "";

    for (boolean b : board) {
      if (b) {
        temp = temp + "O";
      } else {
        temp = temp + "-";
      }
    }
    return temp;
  }

  @Override
  public int coinCount() {

    int i = 0;

    for (boolean b : board) {
      if (b) {
        i++;
      }
    }
    return i;
  }

  @Override
  public int getCoinPosition(int coinIndex) {
    if (coinIndex > coinCount()) {
      throw new IllegalArgumentException("That coin does not exist");
    }

    int numIn = 0;
    int index = 0;
    for (int i = 0; i < boardSize(); ++i) {
      if (board.get(i)) {
        if (numIn == coinIndex) {
          index = i;
        }
        numIn = numIn + 1;
      }
    }
    return index;
  }

  @Override
  public boolean isGameOver() {

    boolean b = true;

    for (int i = 0; i < coinCount(); i++) {
      b = b && board.get(i);
    }

    return b;
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
    } else if (newPosition < getCoinPosition(coinIndex - 1) && coinIndex != 0) {
      throw new IllegalMoveException("Cannot move over another coin");
    } else if (newPosition < 0) {
      throw new IllegalMoveException("New Position is out of bounds");
    } else if (playerIds.size() == 0) {
      throw new IllegalArgumentException("cannot move without a player");
    }

    putCoin(coinIndex, newPosition);

    nextTurn();
  }
  /*
    Puts a coin in the {@code newPosition} and removes the coin from its previous location
    @param coinIndex is the index of the coin in the array of booleans
    @param newPosition is the index of the new position which the coin has to be placed
   */
  private void putCoin(int coinIndex, int newPosition) {

    ArrayList<Boolean> temp = new ArrayList<Boolean>();
    //INVARIANT the same number of coins will be present before and after
    //INVARIANT the board will be the same size
    for (int i = 0; i < boardSize(); ++i) {
      if (i == newPosition) {
        temp.add(true);
      } else if (i == getCoinPosition(coinIndex)) {
        temp.add(false);
      } else {
        temp.add(board.get(i));
      }
    }
    board = temp;
  }

  @Override
  public String getCurrentPlayer() {
    return playerIds.get(currentTurn);
  }

  @Override
  public void addPlayer(int location, String name) {

    for (String s : playerIds) {
      if (name.equals(s)) {
        throw new IllegalArgumentException("cannot add a player with a duplicate name");
      }
    }

    if (name.length() == 0) {
      throw new IllegalArgumentException("Players must have names");
    }

    if (location < 0 || location > board.size()) {
      throw new IllegalArgumentException("Player's turn must be within bounds: 0 - " +
                        Integer.toString(playerIds.size()));
    }

    if (location < currentTurn && location != playerIds.size()) {
      nextTurn();
    }

    playerIds.add(location, name);

  }

  @Override
  public void nextTurn() {

    if (currentTurn + 1 >= playerIds.size()) {
      currentTurn = 0;
    } else {
      currentTurn = currentTurn + 1;
    }

  }

  @Override
  public String playerWon() {

    if (isGameOver()) {
      return getPreviousPlayer();
    } else {
      return "No one has won yet";
    }

  }

  /*
    Extracts the previous player of the list of PlayersIds. Helper method for the method
    playerWon()
   */
  private String getPreviousPlayer() {

    if (currentTurn == 0) {
      return playerIds.get(playerIds.size() - 1);
    } else {
      return playerIds.get(currentTurn - 1);
    }

  }


  public int getCurrentTurn() {
    return currentTurn;
  }


  @Override
  public String toString() {

    String temp = "";
    String cc = "";

    for (boolean b : board) {
      if (b) {
        cc = "O";
      } else {
        cc = "-";
      }
      temp = temp + cc;
    }
    return temp;
  }
}
