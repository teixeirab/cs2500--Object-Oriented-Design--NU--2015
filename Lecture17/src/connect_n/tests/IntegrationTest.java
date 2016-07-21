package connect_n.tests;

import connect_n.console_ui.Controller;
import connect_n.console_ui.IntReader;
import connect_n.console_ui.View;
import connect_n.model.Model;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

/**
 * Integration tests for the whole program. We avoid mocking the view, model,
 * and int reader, and instead mock only stdin and stdout so that we can
 * provide input to the program and check its output.
 */
public class IntegrationTest {
  Model model = Model.builder().width(2).height(3).goal(3).build();

  public static final String NAN_MESSAGE = "NAN";

  @Test
  public void testRun1() throws IOException {
    testRun(model,
            prints("| | |",
                   "| | |",
                   "| | |",
                   "+-+-+",
                   " 0 1"),
            prompts("White: ", "0"),
            prints("| | |",
                   "| | |",
                   "|W| |",
                   "+-+-+",
                   " 0 1"),
            prompts("Red: ", "1"),
            prints("| | |",
                   "| | |",
                   "|W|R|",
                   "+-+-+",
                   " 0 1"),
            prompts("White: ", "0"),
            prints("| | |",
                   "|W| |",
                   "|W|R|",
                   "+-+-+",
                   " 0 1"),
            prompts("Red: ", "1"),
            prints("| | |",
                   "|W|R|",
                   "|W|R|",
                   "+-+-+",
                   " 0 1"),
            prompts("White: ", "0"),
            prints("|W| |",
                   "|W|R|",
                   "|W|R|",
                   "+-+-+",
                   " 0 1"),
            prints("Game over! White wins :)"));
  }

  @Test
  public void testRun2() throws IOException {
    // "0\n1\n1\n0\n0\n1\n",
    testRun(model,
            prints("| | |",
                   "| | |",
                   "| | |",
                   "+-+-+",
                   " 0 1"),
            prompts("White: ", "0"),
            prints("| | |",
                   "| | |",
                   "|W| |",
                   "+-+-+",
                   " 0 1"),
            prompts("Red: ", "1"),
            prints("| | |",
                   "| | |",
                   "|W|R|",
                   "+-+-+",
                   " 0 1"),
            prompts("White: ", "8"),
            prints(Controller.COLUMN_DNE),
            prompts("White: ", "one"),
            prints(NAN_MESSAGE),
            prompts("White: ", "1"),
            prints("| | |",
                   "| |W|",
                   "|W|R|",
                   "+-+-+",
                   " 0 1"),
            prompts("Red: ", "0"),
            prints("| | |",
                   "|R|W|",
                   "|W|R|",
                   "+-+-+",
                   " 0 1"),
            prompts("White: ", "0"),
            prints("|W| |",
                   "|R|W|",
                   "|W|R|",
                   "+-+-+",
                   " 0 1"),
            prompts("Red: ", "0"),
            prints(Controller.COLUMN_FULL),
            prompts("Red: ", "1"),
            prints("|W|R|",
                   "|R|W|",
                   "|W|R|",
                   "+-+-+",
                   " 0 1"),
            prints("Game over! Nobody wins :/"));
  }

  interface Interaction {
    void apply(StringBuilder in, StringBuilder out);
  }

  static Interaction prints(String... lines) {
    return (input, output) -> {
      for (String line : lines) {
        output.append(line).append('\n');
      }
    };
  }

  static Interaction prompts(String prompt, String response) {
    return (input, output) -> {
      output.append(prompt);
      input.append(response).append('\n');
    };
  }

  void testRun(Model model, Interaction... interactions)
      throws IOException
  {
    StringBuilder typed = new StringBuilder();
    StringBuilder expected = new StringBuilder();

    for (Interaction interaction : interactions) {
      interaction.apply(typed, expected);
    }

    InputStream in =
        new ByteArrayInputStream(typed.toString().getBytes("UTF8"));
    StringBuilder out = new StringBuilder();

    IntReader reader = IntReader.create(in, out, NAN_MESSAGE);
    Controller controller = new Controller(model, View.create(model, out),
                                           reader, out);
    controller.run();

    assertEquals(expected.toString(), out.toString());
  }
}