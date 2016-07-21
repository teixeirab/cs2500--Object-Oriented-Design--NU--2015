package Jesse_model;


public class Board {
  final public String lineBreak = System.getProperty("line.separator");
  Model model;
  Position p;
  boolean showDestination = false;
  boolean flip;

  /**
   * Constructs a board from a given model. Represents the visual representation of a checkers game
   * @param model the model of a game
   */
  public Board(Model model) {
    this.model = model;
  }

  /**
   *
   * @return
   */
  public String drawOrigins() {
    String result = "";
    boolean empty = true;
    int i = 0;
    int moveCount = 0;
    int r = 0;

    if (flip) {
      r = 31;
    }
    else r = 0;

    for (int x = 0; x < 8; x++){
      result = result + "+-----+-----+-----+-----+-----+-----+-----+-----+" + lineBreak;
      for(int y = 0; y < 8; y++){
        if (empty){
          result = result + printEmpty();
        }
        else {
          if (model.getValidMovePositions().contains(Position.fromIndex(Math.abs(r - i)))) {
            result = result + printPiece(moveCount);
            moveCount++;
          }
          else {
            result = result + printPiece(model.getPieces().get(Math.abs(r - i)));
          }
          i = i + 1;
        }

        if (y == 7){
          result = result + "|";
        }
        empty = !empty;
      }
      empty = !empty;
      result = result + lineBreak;
    }
    showDestination = !showDestination;
    result = result + "+-----+-----+-----+-----+-----+-----+-----+-----+" + lineBreak;
    return result;

  }


  /**
   *
   * @return
   */
  public String drawDestinations() {
    String result = "";
    boolean empty = true;
    int i = 0;
    int moveCount = 0;
    int r = 0;

    if (flip) {
      r = 31;
    }
    else r = 0;

    for (int x = 0; x < 8; x++) {
      result = result + "+-----+-----+-----+-----+-----+-----+-----+-----+" + lineBreak;
      for (int y = 0; y < 8; y++) {
        if (empty) {
          result = result + printEmpty();
        } else {
          if (model.getValidDestinations(p).contains(Position.fromIndex(Math.abs(r - i)))) {
            result = result + printPiece(moveCount);
            moveCount++;
          } else if (p.equals(Position.fromIndex(Math.abs(r-i)))) {
            result = result + printOrigin(model.getPieces().get(Math.abs(r - i)));
          } else {
            result = result + printPiece(model.getPieces().get(Math.abs(r - i)));
          }
          i = i + 1;
        }

        if (y == 7) {
          result = result + "|";
        }
        empty = !empty;
      }
      empty = !empty;
      result = result + lineBreak;
    }

    result = result + "+-----+-----+-----+-----+-----+-----+-----+-----+" + lineBreak;
    return result;
  }

  // String output helpers
  private String printOrigin(Piece p) {
    return "| <" + p.toString() + "> ";
  }

  private String printPiece(int m) {
    return "| [" + (m + 1) + "] ";
  }

  private String printPiece(Piece p) {
    return "|  " + p.toString() + "  ";
  }

  private String printEmpty() {
    return "|  " + Piece.Empty.toString() + "  ";
  }

  public String toString() {
    if (p != null) {
      return drawDestinations();
    }
    else {
      return drawOrigins();
    }
  }


  public void updateOrigin(Position x) {
    this.p = x;
  }

  public void flipBoard() {
    flip = !flip;
  }

}





