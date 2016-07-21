package Jesse_model;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Created by becogontijo on 4/21/2015.
 */
public class ModelImpl implements Model {

  ArrayList<Piece> pieces = new ArrayList<>();
  private Status status;
  private Player turn;
  private Player otherTurn;

  public ModelImpl() {
    this.pieces = defaultPieces();
    this.status = Status.Playing;
    this.turn = Player.First;
    this.otherTurn = Player.Second;
  }

  /**
   * Creates a default initial set of pieces. It has 12 normal pieces from the first player, 12
   * normal pieces from the second player, and 8 empty spots
   * @return the default set of pieces
   */
  private ArrayList<Piece> defaultPieces(){
    for(int i = 0; i < 32; i++){
      if (i < 12) {
        pieces.add(Piece.NormalSecond);
      }
      if (i >= 12 && i <= 19){
        pieces.add(Piece.Empty);
      }
      if (i > 19){
        pieces.add(Piece.NormalFirst);
      }
    }
    return pieces;
  }

  @Override
  public Player getPlayer() {
    if (isGameOver()) {
      throw new IllegalStateException("the game is over");
    }

    return turn;
  }

  @Override
  public Player getWinner() {
    if (getStatus() != Status.Won) {
      throw new IllegalStateException("the game isn't over");
    }

    return turn;
  }

  @Override
  public Piece getPieceAt(int x, int y) {
    return pieces.get(Position.toIndex(y,x));
  }

  @Override
  public Piece getPieceAt(Position p) {
    return pieces.get(p.index());
  }

  @Override
  public Player getNextPlayer() {
    return otherTurn;
  }

  @Override
  public ArrayList<Piece> getPieces() {
    return pieces;
  }

  @Override
  public void move(int original, int newPlace) {
    if (isGameOver()){
      throw new RuntimeException("Game is already over");
    }

    Position p = Position.fromIndex(original);
    Position p1 = Position.fromIndex(newPlace);

    if (p.isJumpAdjacentTo(p1)){
      Position temp = p.findJumpedPosition(p1);
      pieces.set(temp.index(), Piece.Empty);
    }

    Piece n = pieces.get(original);

    if (p1.row() == 7 && turn.equals(Player.First)) {
      n = n.crowned();
    }

    if (p1.row() == 0 && turn.equals(Player.Second)) {
      n = n.crowned();
    }

    pieces.set(original, Piece.Empty);
    pieces.set(newPlace, n);

    Player temp = turn;
    turn = otherTurn;
    otherTurn = temp;
  }

  /**
   * Private helper of move method that sees whether the move is going to a valid direction
   * Player First normal pieces can only move down and Player Second normal pieces
   * can only move up
   *
   * @param from the original position of the piece
   * @param to the new position of the piece
   * @return whether the move obeys the natural law directions of the game
   */
  private boolean isValidDirection(Position from, Position to){
    if (getPieceAt(from).isCrowned()){
      return true;
    }
    if (getPieceAt(from).player().equals(Player.Second)){
      return from.isAbove(to);
    }
    if (getPieceAt(from).player().equals(Player.First)){
      return from.isBelow(to);
    }
    else return false;
  }

  @Override
  public ArrayList<Position> getValidMovePositions() {
    if (getValidAdjacentPositions(2).size() > 0){
      return getValidAdjacentPositions(2);
    }
    else return getValidAdjacentPositions(1);
  }

  /**
   * Helper method for getValidMovePositions, gets the adjacent position given a distance
   * @param d the distance that needs to be get
   * @return
   */
  private ArrayList<Position> getValidAdjacentPositions(int d) {
    ArrayList<Position> result = new ArrayList<Position>();
    ArrayList<Position> currentPos = getCurrentPositions();

    result.addAll(getCurrentPositions().stream()
                      .filter(p -> destinations(p, d).size() > 0)
                      .collect(Collectors.toList()));
    return result;
  }



  @Override
  public ArrayList<Position> getValidDestinations(Position p) {
    if (destinations(p, 2).size() > 0) {
      return destinations(p, 2);
    }
    else return destinations(p, 1);
  }

  private ArrayList<Position> destinations(Position p, int d) {
    ArrayList<Position> result = new ArrayList<Position>();

    for (Position x : p.adjacentPositions(d)) {
      if (d % 2 == 0 &&
          isUnoccupied(x) &&
          isValidDirection(p, x)) {
        if (isOtherPlayer(x.findJumpedPosition(p))) {
          result.add(x);
        }

      } else if (d == 1 &&
                 isUnoccupied(x) &&
                 isValidDirection(p, x)) {
        result.add(x);
      }
    }

    return result;
  }


  /**
   * Checks whether p is unoccupied
   * @param p the position that needs to be checked
   * @return whether the psition is empty
   */
  private boolean isUnoccupied(Position p) {
    return pieces.get(p.index()) == Piece.Empty;
  }

  /**
   * Checks whether p is a piece from a different player than the current player
   * @param p the position that needs to be checked
   * @return whether the position is from a different player
   */
  private boolean isOtherPlayer(Position p) {
    return pieces.get(p.index()).player().equals(otherTurn);
  }

  @Override
  public ArrayList<Position> getCurrentPositions(){
    ArrayList<Position> currentPlayerPositions = new ArrayList<Position>();
    int index = Position.N_POSITIONS;

    for (int i = 0; i < index; ++i) {
      if (pieces.get(i).player().equals(turn)){
        currentPlayerPositions.add(Position.fromIndex(i));
      }
    }
    return currentPlayerPositions;
  }

  /**
   * Sets the game state to stalemate if there are no more valid moves.
   *
   * @return whether the game is over
   */
  private boolean checkForStalemate() {
    status = Status.Stalemate;
    return getValidAdjacentPositions(1).size() == 0;
  }

  @Override
  public Status getStatus() {
    return status;
  }


  @Override
  public boolean isGameOver() {
    ArrayList<Player> playerPieces = new ArrayList<>();

    for (Piece p : pieces){
      playerPieces.add(p.player());
    }
    return checkForStalemate() ||
           !(playerPieces.contains(Player.Second) && playerPieces.contains(Player.First));
  }

  /**
   * Checks for a winner and updates the state. Takes the coordinates of
   * the most recent move and looks for newly completed lines starting there.
   * If a winner is found, updates the state to reflect this.
   *
   * @param x the column of the most recent move
   * @param y the row of the most recent move
   * @return whether the game is over
   */
  private boolean checkForWinner(int x, int y) {
    return false;
  }
}
