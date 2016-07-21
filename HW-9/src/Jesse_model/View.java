package Jesse_model;

/**
 * Created by becogontijo on 4/23/2015.
 */

import java.io.IOException;
import java.util.Objects;

/**
 * A text-based view for the Connect N model.
 */
@FunctionalInterface
public interface View {

  /**
   * Renders the model as text.
   *
   * @throws java.io.IOException if writing the output throws
   */
  void draw() throws IOException;



  /**
   * Constructs a new console view for the given game model. Output will be
   * to standard out.
   *
   * @param model the model to view (non-null)
   * @throws IllegalArgumentException {@code model.getWidth() > MAX_MODEL_WIDTH}
   */
  public static View create(Model model, Board board) {
    return create(model,board, System.out);
  }

  /**
   * Constructs a new console view for the given game model. Output will be
   * to the given {@link Appendable}.
   *
   * @param model the model to view (non-null)
   * @param out where to write the output (non-null)
   * @throws IllegalArgumentException {@code model.getWidth() > MAX_MODEL_WIDTH}
   */
    public static View create(final Model model, Board bd, final Appendable out) {
      Objects.requireNonNull(out);
      Board board = bd;

      return () -> {
      out.append(board.toString());
    };

  }
}
