package connect_n.console_ui;

import connect_n.model.Model;
import connect_n.model.Player;

import java.io.IOException;
import java.util.Objects;

/**
 * A text-based view for the Connect N model.
 */
@FunctionalInterface
public interface View {
  /**
   * Maximum number of columns allowed in the connect_n.model's grid.
   */
  public static final int MAX_MODEL_WIDTH = 10;

  /**
   * Renders the model as text.
   *
   * @throws IOException if writing the output throws
   */
  void draw() throws IOException;



  /**
   * Constructs a new console view for the given game model. Output will be
   * to standard out.
   *
   * @param model the model to view (non-null)
   * @throws IllegalArgumentException {@code model.getWidth() > MAX_MODEL_WIDTH}
   */
  public static View create(Model model) {
    return create(model, System.out);
  }

  /**
   * Constructs a new console view for the given game model. Output will be
   * to the given {@link Appendable}.
   *
   * @param model the model to view (non-null)
   * @param out where to write the output (non-null)
   * @throws IllegalArgumentException {@code model.getWidth() > MAX_MODEL_WIDTH}
   */
  public static View create(final Model model, final Appendable out) {
    if (model.getWidth() > MAX_MODEL_WIDTH) {
      throw new IllegalArgumentException("model has too many columns");
    }

    Objects.requireNonNull(out);

    return () -> {
      for (int row = model.getHeight() - 1; row >= 0; --row) {
        for (int column = 0; column < model.getWidth(); ++column) {
          out.append('|');
          Player token = model.getPlayerAt(column, row);

          if (token == null) {
            out.append(' ');
            continue;
          }

          switch (token) {
            case White:
              out.append('W');
              continue;

            case Red:
              out.append('R');
              continue;

            default:
              throw new RuntimeException("unknown Player value");
          }
        }

        out.append("|\n");
      }

      for (int column = 0; column < model.getWidth(); ++column) {
        out.append("+-");
      }
      out.append("+\n");

      for (int column = 0; column < model.getWidth(); ++column) {
        out.append(' ')
            .append(Integer.toString(column));
      }
      out.append('\n');
    };
  }
}
