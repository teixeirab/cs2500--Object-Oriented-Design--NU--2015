package connect_n.mocks;

import connect_n.console_ui.View;

/**
 * A mock view. Displays nothing, but counts how many times it's asked to
 * draw itself.
 */
public class MockView implements View {
  /**
   * The number of times {@link #draw()} has been called on this mock view.
   */
  public int drawCount = 0;

  @Override
  public void draw() {
    ++drawCount;
  }
}
