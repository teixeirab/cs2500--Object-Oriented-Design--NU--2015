/**
 * Durations in seconds, represented compactly.
 */
public final class PackedDuration extends AbstractDuration {
  /* CLASS INVARIANT: seconds >= 0 */
  private final long seconds;

  /**
   * Constructs a duration in terms of its length in seconds.
   *
   * @param seconds the number of seconds (non-negative)
   * @throws IllegalArgumentException if {@code seconds < 0}
   */
  public PackedDuration(long seconds) {
    if (seconds < 0) {
      throw new IllegalArgumentException("duration must be non-negative");
    }

    this.seconds = seconds;
  }

  @Override
  protected AbstractDuration fromSeconds(long seconds) {
    return new PackedDuration(seconds);
  }

  @Override
  protected AbstractDuration fromDHMS(long days, int hours, int minutes,
                                      int seconds)
  {
    return new PackedDuration(new WideDuration(days, hours, minutes, seconds)
                               .inSeconds());
  }

  @Override
  public long inSeconds() {
    return seconds;
  }

  @Override
  public long getDaysComponent() {
    return seconds / SECS_IN_DAY;
  }

  @Override
  public int getHoursComponent() {
    return (int) (seconds / 3600) % 24;
  }

  @Override
  public int getMinutesComponent() {
    return (int) (seconds / 60) % 60;
  }

  @Override
  public int getSecondsComponent() {
    return (int) (seconds % 60);
  }

  @Override
  public String toString() {
    return "PackedDuration{" +
        "seconds=" + seconds +
        '}';
  }

}
