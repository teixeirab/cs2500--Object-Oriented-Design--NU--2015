package Jesse_model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by becogontijo on 4/23/2015.
 */
public final class Controller {
  private final Model model;
  private BoardView view;
  private final IntReader in;
  private final Appendable out;
  private int from;
  private Board board;

   /*
   * Messages:
   */

  public static final String NOT_A_NUMBER =
      "That isn't a number! Please try again.";

  public static final String INVALID_MOVE=
      "You Cannot Move this Piece Here";

  public static final String INVALID_PIECE=
      "This is not a movable piece";

  public static final String NOBODY_WINS =
      "Game over! Nobody wins :/";

  public static final String PLAYER_WINS =
      "Game over! %s wins :)";

  /**
   * Constructs a controller given a model. Uses the defaults for the view,
   * input, and output.
   *
   * @param model the model to use for this controller
   */
  public Controller(Model model, Board bd, Appendable out) {
    this(model,
         new BoardView(out),
         IntReader.create(System.in, System.out, NOT_A_NUMBER),
         System.out, bd);
  }

  /**
   * Constructs a controller given the components it will control.
   *
   * @param model the model
   * @param view the view, for rendering the model
   * @param in the source of user moves
   * @param out the output stream
   */
  public Controller(Model model, BoardView view, IntReader in, Appendable out, Board bd) {
    Objects.requireNonNull(model);
    Objects.requireNonNull(view);
    Objects.requireNonNull(in);
    Objects.requireNonNull(out);
    Objects.requireNonNull(bd);

    this.model = model;
    this.view = view;
    this.in = in;
    this.out = out;
    this.board = bd;
  }

  /**
   * Determines whether the game is over.
   * @return whether the game is over
   */
  public boolean isGameOver() {
    return model.isGameOver();
  }

  /**
   * Runs this controller on its model.
   *
   * @throws java.io.IOException if an I/O operation, such as writing to the console,
   * fails
   */
  public void run() throws IOException {
    while (! isGameOver()) {
      step();
    }
  }

  /**
   * Runs one interaction step of this controller on its model.
   *
   * @throws java.io.IOException if an I/O operation, such as writing to the console,
   * fails
   */
  public void step() throws IOException {


    if (model.isGameOver()) {

      if (model.getStatus() == Model.Status.Stalemate) {
        out.append(NOBODY_WINS).append('\n');
      } else {
        String winner = model.getWinner().toString();
        out.append(String.format(PLAYER_WINS, winner)).append('\n');
      }
    }
  }

  private void showPieces(int r) throws IOException {
    Player who = model.getPlayer();
    from = in.nextInt("\n" + "[" + who.toChar() + "]" + " Choose a piece to move: ", this::validatePiece);
    from = from - 1;

    ArrayList<Position> beginnings = model.getValidMovePositions();
    if (model.getPlayer().equals(Player.First)) {
      r = from;
    }
    else {
      r = beginnings.size() - from - 1;
    }
    Position beg = beginnings.get(r);
    from = beg.index();
    board.updateOrigin(beg);

  }

  private void showDestinations(int r, Position beg) throws IOException{
    Player who = model.getPlayer();
    int to = in.nextInt("\n" + "[" + who.toChar() + "]" + "Choose where to move to: ", this::validateMove);
    to = to - 1;
    ArrayList<Position> end = model.getValidDestinations(beg);
    to = end.get(to).index();
    model.move(from, to);
    board.updateOrigin(null);
    board.flipBoard();
  }

  /**
   * Checks whether a particular move is valid in the current model. Returns
   * an error message if the move is invalid, or {@code null} if the move is
   * valid.
   *
   * @param to the move to check
   * @return {@code null} or an error message
   */
  private String validateMove(int to) {
    ArrayList<Position> valid = model.getValidDestinations(Position.fromIndex(from));

    if (to <= valid.size() && to > 0) {
      return null;
    }
    else return INVALID_MOVE;
    }



  /**
   * Checks whether a particular piece is valid in the current model. Returns
   * an error message if the piece is invalid, or {@code null} if the move is
   * valid.
   *
   * @param from the move to check
   * @return {@code null} or an error message
   */
  private String validatePiece(int from) {
    ArrayList<Position> valid = model.getValidMovePositions();
    if (from <= valid.size() && from > 0) {
      return null;
    }
    else {
      return INVALID_PIECE;
    }

  }

}
