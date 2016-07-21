package connect_n.console_ui;

import java.io.IOException;
import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.IntFunction;

/**
 * A source of {@code int}s. Usually this will work by interacting with the
 * user. The client can provide a validator object, which the {@code
 * IntReader} uses to determine if the input integer is valid and to produce
 * an error message to inform the user if there is a problem.
 */
@FunctionalInterface
public interface IntReader {
  /**
   * The interface for validators. The validator's
   * {@link Validator#apply(int)} method should return {@code null} if the
   * {@code int} is valid, or an error message string if it isn't.
   */
  public interface Validator extends IntFunction<String> { }

  /**
   * Obtains and validates the next {@code int} from the input stream. The
   * move is validated using the {@link Validator} object, which either
   * succeeds or produces an error message to display to the user. This
   * method asks the user to try again until the user enters a valid input.
   *
   * @param prompt The prompt to show the user
   * @param validator The validator to determine input validity
   * @return the move
   * @throws IOException for I/O problems with the underlying input and
   * output streams
   * @throws NoSuchElementException if the input stream ends
   */
  int nextInt(String prompt, Validator validator) throws IOException;


  /**
   * Constructs an {@link IntReader} that uses the given input and output
   * streams. The client also provides a message to print on the output
   * stream in case the user's input cannot be parsed.
   *
   * @param inputStream the stream to read input from
   * @param out the stream to write the prompt and error messages to
   * @param nanMessage the error message to write for non-{@code int} input
   * @return the new {@code IntReader}
   */
  public static IntReader create(final InputStream inputStream,
                                 final Appendable out,
                                 final String nanMessage)
  {
    Objects.requireNonNull(out);
    Scanner in = new Scanner(inputStream);

    return (prompt, validator) -> {
      while (true) {
        try {
          out.append(prompt);
          int where = in.nextInt();

          String message = validator.apply(where);

          if (message == null) {
            return where;
          } else {
            out.append(message).append('\n');
          }
        } catch (InputMismatchException e) {
          out.append(nanMessage).append('\n');
          in.nextLine();
        }
      }
    };
  }
}