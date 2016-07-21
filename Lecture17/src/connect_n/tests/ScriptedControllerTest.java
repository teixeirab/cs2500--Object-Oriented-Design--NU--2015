package connect_n.tests;

import connect_n.console_ui.Controller;
import connect_n.mocks.MockIntReader;
import connect_n.mocks.MockModel;
import connect_n.mocks.MockView;
import org.junit.Test;

import java.io.IOException;

import static connect_n.model.Model.Status.*;
import static connect_n.model.Player.Red;
import static connect_n.model.Player.White;
import static org.junit.Assert.assertEquals;

/**
 * Unit tests for the controller. These tests use mocks for the other
 * components that the controller interacts with (the model, the view, and
 * the int reader) in order to isolate its behavior.
 */
public class ScriptedControllerTest {
  MockModel model = new MockModel(2, 3, 3);
  MockView view = new MockView();
  MockIntReader reader = new MockIntReader();
  Appendable out = new StringBuilder();

  /**
   * Here we test the controller on a game script in which player Red wins.
   */
  @Test
  public void testWin() throws IOException {
    // Set the initial values for some model getters
    model
        .setColumnSize(0, 0)
        .setColumnSize(1, 0)
        .setStatus(Playing)
        .setNextPlayer(Red);

    // Expect the controller to ask the reader for a move with the prompt
    // {@code "Red: "}, and then return {@code 0} to the controller.
    reader.add("Red: ", 0);
    // Expect the controller to ask the model to play Red in column 0,
    // returning 0 from that call to move. Then updates the state for
    // subsequent calls to getters: White moves next, column 0 has grown to
    // height 1, and there is a red token at (0, 0).
    model.onMove(Red, 0).reply(0);
    model.and(() -> model.setNextPlayer(White));
    model.and(() -> model.setColumnSize(0, 1));
    model.and(() -> model.setPlayerAt(0, 0, Red));

    // Next we anticipate the controller to prompt White for a move, and
    // return 1 from the reader. Then the controller should ask the model to
    // move White to column 1, and the model replies with 0 as the row that
    // the move lands in.
    reader.add("White: ", 1);
    model.onMove(White, 1).reply(0).and(() -> {
      model.setNextPlayer(Red)
          .setColumnSize(1, 1)
          .setPlayerAt(1, 0, White);
    });

    reader.add("Red: ", 0);
    model.onMove(Red, 0).reply(1).and(() -> {
      model.setNextPlayer(White)
          .setColumnSize(0, 2)
          .setPlayerAt(0, 1, Red);
    });

    reader.add("White: ", 1);
    model.onMove(White, 1).reply(1).and(() -> {
      model.setNextPlayer(Red)
          .setColumnSize(1, 2)
          .setPlayerAt(1, 1, White);
    });

    // Here we expect Red to win the game, so in the argument to and() we
    // update the state to reflect this.
    reader.add("Red: ", 0);
    model.onMove(Red, 0).reply(2).and(() -> {
      model
          .setColumnSize(0, 3)
          .setPlayerAt(0, 2, Red)
          .setStatus(Won)
          .setWinner(Red)
          .winning(0, 0).winning(0, 1).winning(0, 2);
    });

    // Create a controller using the mocks, and run it.
    new Controller(model, view, reader, out).run();

    // The script should now be empty, because the controller should make
    // exactly the number of moves that we scripted.
    model.assertFinished();

    // The controller should ask the view to render itself 6 times, once
    // before each move and once at the end.
    assertEquals(6, view.drawCount);

    // The controller should print out a message that Red wins.
    assertEquals("Game over! Red wins :)\n", out.toString());
  }

  /**
   * Here we test the controller on a game script in which the grid fills
   * without reaching a winner, yielding a stalemate.
   */
  @Test
  public void testStalemate() throws IOException {
    model
        .setColumnSize(0, 0)
        .setColumnSize(1, 0)
        .setStatus(Playing)
        .setNextPlayer(Red);

    reader.add("Red: ", 0);
    model.onMove(Red, 0).reply(0).and(() -> {
      model.setNextPlayer(White)
          .setColumnSize(0, 1)
          .setPlayerAt(0, 0, Red);
    });

    reader.add("White: ", 0);
    model.onMove(White, 0).reply(1).and(() -> {
      model.setNextPlayer(Red)
          .setColumnSize(0, 2)
          .setPlayerAt(0, 1, White);
    });

    reader.add("Red: ", 0);
    model.onMove(Red, 0).reply(2).and(() -> {
      model.setNextPlayer(White)
          .setColumnSize(0, 3)
          .setPlayerAt(0, 2, Red);
    });

    // The next line simulates player White attempt to play in column 0,
    // which should be judged invalid by the int validator provided to the
    // reader by the controller. Then White plays 1, which is accepted and
    // returned to the controller.
    reader.add("White: ", 0, 1);
    model.onMove(White, 1).reply(0).and(() -> {
      model.setNextPlayer(Red)
          .setColumnSize(1, 1)
          .setPlayerAt(1, 0, White);
    });

    // The next line simulates Red attempting to play in two non-existent
    // columns before making a valid move. The client-supplied validator
    // should reject the two bad moves and accept the good one.
    reader.add("Red: ", -2, 2, 1);
    model.onMove(Red, 1).reply(1).and(() -> {
      model.setNextPlayer(White)
          .setColumnSize(1, 2)
          .setPlayerAt(1, 1, Red);
    });

    reader.add("White: ", 1);
    model.onMove(White, 1).reply(2).and(() -> {
      model.setNextPlayer(null)
          .setColumnSize(1, 3)
          .setPlayerAt(1, 2, White)
          .setStatus(Stalemate);
    });

    new Controller(model, view, reader, out).run();

    model.assertFinished();
    assertEquals(7, view.drawCount);
    assertEquals("Game over! Nobody wins :/\n", out.toString());
  }
}
