package connect_n.util;

/**
 * Created by becogontijo on 4/14/2015.
 */


/**
 * A function object for encapsulation a piece of code to be executed for its
 * side-effects. The action takes no arguments and returns no results.
 */
@FunctionalInterface
public interface Action {
  /**
   * Executes this action.
   */
  void execute();

  /**
   * Constructs a new action by sequencing the next action after this on.
   *
   * @param next the next action
   * @return an action that performs this action and then the next action
   */
  default Action andThen(Action next) {
    Action first = this;

    return () -> {
      first.execute();
      next.execute();
    };
  }

  /**
   * The empty action, which does nothing.
   */
  public static final Action NOTHING = () -> {};
}
