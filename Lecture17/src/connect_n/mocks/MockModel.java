package connect_n.mocks;

import connect_n.model.Model;
import connect_n.model.Player;
import connect_n.model.Position;
import connect_n.util.Action;

import java.util.*;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * A mock implementation of the {@link Model} interface. This facilitates
 * tests that script the model's interaction with the client. The script
 * specifies the expected sequence of moves and their results, as well as the
 * values to be returned by model getters. Because the specification of a
 * correct model client says nothing about when it accesses getters, we do
 * not script the sequence of getter calls but merely specify what their
 * results should be at each point.
 */
public class MockModel implements Model {
  /**
   * These fields are used to hold what each corresponding getter should
   * return. It is the tester's responsibility to update these fields
   * to provide the right responses to the client under test.
   */
  int width;
  int height;
  int goal;
  Model.Status status;
  Player nextPlayer;
  Player winner;
  Set<Position> winningPositions;

  /**
   * These tables specify the results for the getPlayerAt and getColumnSize
   * methods by mapping arguments to results.
   */
  Map<Position, Player> playerAtTable = new HashMap<>();
  Map<Integer, Integer> columnSizeTable = new HashMap<>();

  /**
   * The script of expected moves and results, along with a sequence of field
   * update actions after each move.
   */
  MoveScript moveScript = new MoveScript();

  /**
   * Constructs a mock model.
   *
   * @param width the width of the mock model
   * @param height the height of the mock model
   * @param goal the goal length of the mock model
   */
  public MockModel(int width, int height, int goal) {
    this.width = width;
    this.height = height;
    this.goal = goal;
  }

  @Override
  public int getWidth() {
    return width;
  }

  @Override
  public int getHeight() {
    return height;
  }

  @Override
  public int getGoal() {
    return goal;
  }

  @Override
  public Status getStatus() {
    return status;
  }

  /**
   * Sets the status for subsequent calls to {@link #getStatus()}.
   *
   * @param status the new status
   * @return {@code this}, for method chaining
   */
  public MockModel setStatus(Status status) {
    this.status = status;
    return this;
  }

  @Override
  public Player getNextPlayer() {
    return nextPlayer;
  }

  /**
   * Sets the next player for subsequent calls to {@link #getNextPlayer()}.
   *
   * @param nextPlayer the new next player
   * @return {@code this}, for method chaining
   */
  public MockModel setNextPlayer(Player nextPlayer) {
    this.nextPlayer = nextPlayer;
    return this;
  }

  @Override
  public Player getWinner() {
    return winner;
  }

  /**
   * Sets the winner for subsequent calls to {@link #getWinner()}.
   *
   * @param winner the winner
   * @return {@code this}, for method chaining
   */
  public MockModel setWinner(Player winner) {
    this.winner = winner;
    return this;
  }

  @Override
  public Player getPlayerAt(int x, int y) {
    return playerAtTable.get(new Position(x, y));
  }

  /**
   * Sets the player token at a particular grid location for subsequent calls
   * to {@link #getPlayerAt(int, int)}.
   *
   * @param x the column of the location
   * @param y the row of the location
   * @param player the player to return for (x, y)
   * @return {@code this}, for method chaining
   */
  public MockModel setPlayerAt(int x, int y, Player player) {
    playerAtTable.put(new Position(x, y), player);
    return this;
  }

  @Override
  public int getColumnSize(int which) {
    return columnSizeTable.get(which);
  }

  /**
   * Sets the size of a particular column for subsequent calls to
   * {@link #getColumnSize(int)}.
   *
   * @param where the column to update
   * @param size the new size for that column
   * @return {@code this}, for method chaining
   */
  public MockModel setColumnSize(int where, int size) {
    columnSizeTable.put(where, size);
    return this;
  }

  @Override
  public Set<Position> getWinningPositions() {
    return winningPositions;
  }

  /**
   * Sets the set of winning positions for for subsequent calls to
   * {@link #getWinningPositions()}.
   *
   * @param winningPositions the winning positions set
   * @return {@code this}, for method chaining
   */
  public MockModel setWinningPositions(Set<Position> winningPositions) {
    this.winningPositions = winningPositions;
    return this;
  }

  /**
   * Adds a winning position to the winning positions set for subsequent
   * calls to {@link #getWinningPositions()}.
   *
   * @param column the column of the position to add
   * @param row the row of the position to add
   * @return {@code this}, for method chaining
   */
  public MockModel winning(int column, int row) {
    if (winningPositions == null) {
      winningPositions = new HashSet<>();
    }

    winningPositions.add(new Position(column, row));
    return this;
  }

  @Override
  public int move(Player who, int where) {
    return moveScript.move(who, where);
  }

  /**
   * Asserts that the move script is empty, meaning that the client has made
   * all the moves that we expect.
   */
  public void assertFinished() {
    assertTrue(moveScript.isFinished());
  }

  /**
   * Interface for specifying the results of calls to
   * {@link #move(Player, int)} in the script. In particular, when we specify
   * an expected move, the {@link #onMove(Player, int)} method returns an
   * {@link IntReply} object that we use to provide the result value for that
   * expected call to {@code move}.
   */
  public interface IntReply {
    /**
     * Receives the result that the script should return for a particular
     * expected call to {@link #move(Player, int)}.
     *
     * @param reply the value to return from {@code move}
     * @return the model, for further method chaining.
     */
    MockModel reply(int reply);
  }

  /**
   * Adds an expected move to the end of the move script. This means that we
   * expect the client under test to call the move method on this model with
   * the given parameters. The result of this method is an {@link IntReply}
   * object, which allows us to specify what the result of the added {@code
   * move} call should be.
   *
   * @param who the expected player
   * @param where the expected column to play in
   * @return an object for setting the result that the mock model should
   * return from the move
   */
  public IntReply onMove(Player who, int where) {
    return result -> {
      moveScript.add(who, where, result);
      return this;
    };
  }

  /**
   * Chains an action to be executed as part of the last move in the move
   * script. We use this to update the state of the mock model after each
   * move, so that subsequent calls to getters from the client under test
   * return the right values. If this method is called multiple times without
   * a call to {@link #onMove(Player, int)} in between then all of the
   * actions are accumulated and executed in the order that they were added.
   * <p>
   * If the move script is empty then the action is executed immediately
   * instead.
   *
   * @param action an action to execute along with the most recently
   *               enqueued move
   * @return {@code this}, for method chaining
   */
  public MockModel and(Action action) {
    moveScript.add(action);
    return this;
  }

  /**
   * The representation of the move script, which scripts the mock's
   * interaction with the client under test.
   */
  private static class MoveScript {
    private Deque<MoveCommand> script = new ArrayDeque<>();

    /**
     * Simulates the model's handling of the next move based on the first
     * move in the move script. If the actual parameters don't match the
     * expected parameters of the script then a failed assertion is signaled.
     *
     * @param who the player provided by the client under test
     * @param where the column provided by the client under test
     * @return the scripted result for the expected move
     * @throws AssertionError if the parameters are not what the script expects
     */
    int move(Player who, int where) {
      return script.remove().move(who, where);
    }

    /**
     * Adds an action to execute after the current last move in the move script.
     *
     * @param action the action to append
     * @return {@code this}, for method chaining
     */
    MoveScript add(Action action) {
      if (script.isEmpty()) {
        action.execute();
      } else {
        script.getLast().andThen(action);
      }

      return this;
    }

    /**
     * Adds an expected move to the move script. The expected move specifies
     * the expected parameters and the result to return.
     *
     * @param who the expected player
     * @param where the expected column
     * @param result the result to return
     * @return {@code this}, for method chaining
     */
    MoveScript add(Player who, int where, int result) {
      script.addLast(new MoveCommand(who, where, result));
      return this;
    }

    /**
     * Determines whether the script has finished, meaning there are no more
     * moves expected.
     *
     * @return whether the script has finished successfully
     */
    boolean isFinished() {
      return script.isEmpty();
    }

    /**
     * Represents each expected move and its result.
     */
    class MoveCommand {
      Player who;
      int where;
      int result;

      // The actions to execute when this move is simulated.
      List<Action> actions = new ArrayList<>();

      MoveCommand(Player who, int where, int result) {
        this.who = who;
        this.where = where;
        this.result = result;
      }

      /**
       * Simulates the specified move by checking the actual parameters
       * against the expected parameters specified when this {@code
       * MoveCommand} was constructed.
       *
       * @param who the player provided by the client under test
       * @param where the column provided by the client under test
       * @return the result to return to the client
       */
      int move(Player who, int where) {
        assertEquals(this.who, who);
        assertEquals(this.where, where);

        for (Action action : actions) {
          action.execute();
        }

        return result;
      }

      /**
       * Chains an action to be executed as part of the this expected move.
       *
       * @param action an action to execute along with this move
       * @return {@code this}, for method chaining
       * @see MockModel#and(Action)
       */
      MoveCommand andThen(Action action) {
        actions.add(action);
        return this;
      }
    }
  }
}
