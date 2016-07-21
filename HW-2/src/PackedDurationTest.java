/** Tests for {@link PackedDuration}. */
public class PackedDurationTest extends AbstractDurationTest {
  @Override
  protected AbstractDuration fromSeconds(long seconds) {
    return new PackedDuration(seconds);
  }
}