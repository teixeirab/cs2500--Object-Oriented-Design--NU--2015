import java.util.Objects;

/**
 * Provides functionality common to both representational subclasses,
 * including comparisons, hashing, addition, subtraction, and the ability
 * to construct new durations of the same class as a given instance.
 */
abstract class AbstractDuration implements Duration {
  protected static final int SECS_IN_DAY = 24 * 60 * 60;

  /**
   * Constructs a new duration having the same class as {@code this}.
   *
   * @param seconds length of the new duration in seconds (non-negative)
   * @return the new duration
   * @throws IllegalArgumentException {@code seconds} is negative
   */
  protected abstract AbstractDuration fromSeconds(long seconds);

  /**
   * Constructs a new duration having the same class as {@code this}.
   *
   * @param days the days component of the new duration (non-negative)
   * @param hours the hours component of the new duration (non-negative)
   * @param minutes the minutes component of the new duration (non-negative)
   * @param seconds the seconds component of the new duration (non-negative)
   * @return the new duration
   * @throws IllegalArgumentException if any argument is negative
   */
  protected abstract AbstractDuration fromDHMS(long days, int hours,
                                               int minutes, int seconds);

  /**
   * Returns the sum of two durations. The result will have the same dynamic
   * class as {@code this}.
   *
   * @param other the duration to add to {@code this}
   * @return the sum of the durations
   */
  @Override
  public Duration plus(Duration other) {
    long result = inSeconds() + other.inSeconds();

    if (result < 0) {
      throw new RuntimeException("Duration overflow");
    }

    return fromSeconds(result);
  }

  /**
   * Returns the difference of two durations. Returns the zero duration rather
   * than negative. The result will have the same dynamic class as
   * {@code this}.
   *
   * @param other the duration to subtract from {@code this}
   * @return the difference of the durations
   */
  @Override
  public Duration minus(Duration other) {
    long result = inSeconds() - other.inSeconds();
    return fromSeconds(result < 0 ? 0 : result);
  }

  @Override
  public int compareTo(Duration other) {
    return Long.compare(inSeconds(), other.inSeconds());
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof Duration)) return false;
    return inSeconds() == ((Duration) other).inSeconds();
  }

  @Override
  public int hashCode() {
    return (int) (inSeconds() ^ (inSeconds() >>> 32));
  }

  @Override
  public String format(String template) {
    String result = "";

    for (int i = 0; i < template.length(); i = i + 1){
      if(template.charAt(i) == '%'){
        result = result + codeReplacer(template.charAt(1+ i));
        i = i + 1;

      }
      else result = result + template.charAt(i);
    }

    return result;
  }


  /**
   *
   * @param code
   * @return
   */
  public String codeReplacer(char code){
    String result = "";
    switch (code){
      case 't': result = Objects.toString(inSeconds()); break;
      case 'd': result = Objects.toString(getDaysComponent()); break;
      case 'h': result = Objects.toString(getHoursComponent()); break;
      case 'H': result = addZero(getHoursComponent()); break;
      case 'm': result = Objects.toString(getMinutesComponent()); break;
      case 'M': result = addZero(getMinutesComponent()); break;
      case 's': result = Objects.toString(getSecondsComponent()); break;
      case 'S': result = addZero(getSecondsComponent()); break;
      case '%': result = "%"; break;
    }
    return result;
  }

  /**
   *
   * @param component
   * @return
   */
  private String addZero(int component) {
    if (component < 10){
      return "0" + component;
    }
    else return Integer.toString(component);
  }

}
