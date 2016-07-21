package Jesse_model;

import org.junit.Test;

import java.io.IOException;


public class ControllerTest {

  @Test
  public void testGameMatch() throws IOException {
    String errorMessae = "fuck";
    ModelImpl model = new ModelImpl();
    Board board = new Board(model);
    BoardView view = new BoardView(System.out);

    try {
      NetworkClientTester.GameRunner runner = (input, output) -> {
        IntReader reader = IntReader.create(input, output, errorMessae);
        Controller controller = new Controller(model, view, reader, output, board);
        controller.run();
      };

      NetworkClientTester.assertGameMatches(runner, 1, 1);
    }
    catch (Exception e) {
    }
  }
}