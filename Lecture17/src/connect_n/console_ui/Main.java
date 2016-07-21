package connect_n.console_ui;

import connect_n.model.Model;

import java.io.IOException;

/**
 * Provides a simple console UI for Connect Four.
 */
public final class Main {
  public static void main(String[] args) throws IOException {
    new Controller(Model.connectFour()).run();
  }


}
