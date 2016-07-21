package Jesse_model;

import java.io.IOException;

/**
 * Provides a simple console UI for Connect Four.
 */
public final class Main {
  public static void main(String[] args) throws IOException {
    ModelImpl model = new ModelImpl();
    Board board = new Board(model);
    Controller c = new Controller(model, board, System.out);
    c.run();
  }


}
