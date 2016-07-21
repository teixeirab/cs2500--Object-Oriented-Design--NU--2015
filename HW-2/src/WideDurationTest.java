/** Tests for {@link WideDuration}. */
public class WideDurationTest extends AbstractDurationTest {
  @Override
  protected AbstractDuration fromSeconds(long seconds) {
    return new WideDuration(seconds);
  }
}