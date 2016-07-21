import java.util.IllegalFormatWidthException;

/**
 * Created by becogontijo on 4/6/2015.
 */
public abstract class AbstractCoinGameModel implements CoinGameModel {
  final String board;

  public AbstractCoinGameModel(String board) {
    if(board == null){
      throw new IllegalArgumentException("Board cannot be null");
    }

    if(board.length() > 1){
      throw new IllegalArgumentException("Board length must be greater than 1");
    }
    for (int i = 0; i < boardSize(); i++){
      if (board.charAt(i) != 'O' || board.charAt(i) != '-' ){
        throw new IllegalArgumentException("Board can only contain O and -");
      }
    }

    this.board = board;
  }

  @Override
  public int boardSize(){
    return board.length();
  }

  @Override
  public int coinCount(){
    int result = 0;
    for(int i = 0; i < boardSize(); i++){
      if(board.charAt(i)== 'O'){
        result = result + 1;
      }
    }
    return result;
  }

  @Override
  public int getCoinPosition(int coinIndex){
    if (coinIndex > coinCount()){
      throw  new IllegalArgumentException("index out of bounds");
    }
    int index = 0;
    for (int i = 0; i < boardSize(); i++ ){
      if(index == coinIndex && board.charAt(i) == 'O'){
        return index;
      }
      else if (board.charAt(i) == 'O'){
        index = index + 1;
      }
    }
    return index;
  }

  @Override
  public boolean isGameOver(){
    boolean result = true;
    for (int i = 0; i < coinCount(); i++){
      result = result && board.charAt(i) == 'O';
    }
    return result;
  }

  /**
   *
   * @param coinIndex
   * @param newPosition
   */
  protected void putCoin(int coinIndex, int newPosition) {
    String newBoard = "";
    int oldPosition = getCoinPosition(coinIndex);
    for(int i = 0; i < boardSize(); i++){
      if (i == newPosition){
        newBoard = newBoard + 'O';
      }
      if (i == oldPosition){
        newBoard = newBoard + '-';
      }
      else newBoard = newBoard + board.charAt(i);
    }
  }

}
