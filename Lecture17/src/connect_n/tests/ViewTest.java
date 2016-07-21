package connect_n.tests;

import connect_n.console_ui.View;
import connect_n.mocks.MockModel;
import org.junit.Test;

import java.io.IOException;

import static connect_n.model.Player.Red;
import static connect_n.model.Player.White;
import static org.junit.Assert.assertEquals;

/**
 * Unit tests for the view. We use a mock model to isolate the view from
 * potential bugs in the model.
 */
public class ViewTest {
  MockModel model = new MockModel(2, 3, 3);
  StringBuilder out = new StringBuilder();
  View view = View.create(model, out);

  @Test
  public void testDraw() throws IOException {
    model
        .setPlayerAt(0, 0, White)
        .setPlayerAt(1, 0, Red)
        .setPlayerAt(0, 1, Red);

    view.draw();

    assertEquals("| | |\n" +
                 "|R| |\n" +
                 "|W|R|\n" +
                 "+-+-+\n" +
                 " 0 1\n",
                 out.toString());


    model.setPlayerAt(1, 1, White);

    out.delete(0, out.length());
    view.draw();

    assertEquals("| | |\n" +
                 "|R|W|\n" +
                 "|W|R|\n" +
                 "+-+-+\n" +
                 " 0 1\n",
                 out.toString());
  }
}