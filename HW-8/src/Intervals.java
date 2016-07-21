import java.util.Comparator;
import java.util.function.Predicate;

/**
 * Created by becogontijo on 4/8/2015.
 */
public class Intervals<T> extends Object{
  /**
   * Constructs a closed interval with the given bounds, using the natural comparison order.
   * @param lower lower - the lower bound (non-null)
   * @param upper upper - the upper bound (non-null)
   * @param <T> T - the domain of the interval
   * @return [lower, upper]
   */
  public static <T extends Comparable<T>> Interval<T> closed(T lower, T upper){
    return new ClassInterval<T>(lower, BoundType.Closed,
                                                      upper, BoundType.Closed,
                                                      Comparator.<T>naturalOrder());
  }

  /**
   * Constructs a closed interval with the given bounds, using the given comparator to
   * define the order.
   * @param lower lower - the lower bound (non-null)
   * @param upper upper - the upper bound (non-null)
   * @param <T> T - the domain of the interval
   * @return [lower, upper]
   */
  public static <T> Interval<T> closed(T lower, T upper, Comparator<T> comparator){
    return new ClassInterval<T>(lower, BoundType.Closed,
                                                      upper, BoundType.Closed,
                                                      comparator);
  }


  /**
   * Constructs an closed/open interval with the given bounds, using the natural comparison order.
   * @param lower lower - the lower bound (non-null)
   * @param upper upper - the upper bound (non-null)
   * @param <T> T - the domain of the interval
   * @return [lower, upper)
   */
  public static <T extends Comparable<T>> Interval <T> closedOpen(T lower, T upper){
    return new ClassInterval<T>(lower, BoundType.Closed,
                                                      upper, BoundType.Open,
                                                      Comparator.<T>naturalOrder());
  }

  /**
   * Constructs an closed/open interval with the given bounds, using the given
   * comparator to define the order.
   * @param lower lower - the lower bound (non-null)
   * @param upper upper - the upper bound (non-null)
   * @param <T> T - the domain of the interval
   * @return [lower, upper)
   */
  public static <T> Interval <T> closedOpen(T lower, T upper, Comparator<T> comparator){
    return new ClassInterval<T>(lower, BoundType.Closed,
                                                          upper, BoundType.Open,
                                                          comparator);
  }

  /**
   * Constructs an interval with the given bounds and bound types, using the natural
   * comparison order.
   * @param lower lower - the lower bound (non-null)
   * @param lowerType whether the lower bound is open or closed (non-null)
   * @param upper upper - the upper bound (non-null)
   * @param upperType whether the upper bound is open or closed (non-null)
   * @param <T> T - the domain of the interval
   * @return the new interval
   * @throws IllegalArgumentException - if the endpoint precondition is violated
   */
  public static <T extends Comparable<T>> Interval <T> interval(T lower, BoundType lowerType,
                                                                T upper, BoundType upperType){
    return  new ClassInterval<T>(lower, lowerType,
                                                       upper, upperType,
                                                       Comparator.<T>naturalOrder());
  }

  /**
   * Constructs an interval with the given bounds and bound types, using the given
   * comparator to define the order.
   * @param lower lower - the lower bound (non-null)
   * @param lowerType whether the lower bound is open or closed (non-null)
   * @param upper upper - the upper bound (non-null)
   * @param upperType whether the upper bound is open or closed (non-null)
   * @param comparator the comparator that defines the order (non-null)
   * @param <T> T - the domain of the interval
   * @return the new interval
   */
  public static <T> Interval <T> interval(T lower, BoundType lowerType,
                                          T upper, BoundType upperType, Comparator<T> comparator){
    return new ClassInterval<T>(lower, lowerType,
                                                       upper, upperType,
                                                       comparator);
  }

  /**
   * Constructs an open interval with the given bounds, using the natural comparison order.
   * @param lower lower - the lower bound (non-null)
   * @param upper upper - the upper bound (non-null)
   * @param <T> T - the domain of the interval
   * @return (lower, upper)
   */
  public static <T extends Comparable<T>> Interval <T> open(T lower, T upper){
    return new ClassInterval<T>(lower, BoundType.Open,
                                                       upper, BoundType.Open,
                                                       Comparator.<T>naturalOrder());

  }

  /**
   * Constructs an open interval with the given bounds, using the given comparator to
   * define the order.
   * @param lower lower - the lower bound (non-null)
   * @param upper upper - the upper bound (non-null)
   * @param comparator the comparator that defines the order (non-null)
   * @param <T> T - the domain of the interval
   * @return (lower, upper)
   */
  public static <T> Interval <T> open(T lower, T upper, Comparator<T> comparator){
    return new ClassInterval<T>(lower, BoundType.Open,
                                                       upper, BoundType.Open,
                                                       comparator);
  }

  /**
   * Constructs an open/closed interval with the given bounds, using the natural comparison order.
   * @param lower lower - the lower bound (non-null)
   * @param upper upper - the upper bound (non-null)
   * @param <T> T - the domain of the interval
   * @return (lower, upper]
   */
  public static <T extends Comparable<T>> Interval <T> openClosed(T lower, T upper){
    return new ClassInterval<T>(lower, BoundType.Open,
                                                       upper, BoundType.Closed,
                                                       Comparator.<T>naturalOrder());
  }

  /**
   * Constructs an open/closed interval with the given bounds, using the given comparator to
   * define the order.
   * @param lower lower - the lower bound (non-null)
   * @param upper upper - the upper bound (non-null)
   * @param <T> T - the domain of the interval
   * @return (lower, upper]
   */
  public static <T> Interval <T> openClosed(T lower, T upper, Comparator<T> comparator){
    return new ClassInterval<T>(lower, BoundType.Open,
                                                       upper, BoundType.Closed,
                                                       comparator);
  }


  /**
   * Constructs the empty interval using the natural comparison order.
   * @param <T> T - the domain of the interval
   * @return the empty interval
   */
  public static <T extends Comparable<T>> Interval <T> empty(){
    return new ClassInterval<T>();
  }

  /**
   * Constructs the empty interval using the given comparator to define the order.
   * @param comparator the comparator that defines the order (non-null)
   * @param <T> T - the domain of the interval
   * @return the empty interval
   */
  public static <T> Interval <T> empty(Comparator<T> comparator){
    return new ClassInterval<T>();
  }
  /**
   * Constructs a one-value interval containing the given value, using the
   * natural comparison order.
   * @param value the single value in the interval (non-null)
   * @param <T> T - the domain of the interval
   * @return [value, value]
   */
  public static <T extends Comparable<T>> Interval <T> singleton(T value){
    return new ClassInterval<T>(value, BoundType.Closed,
                                                       value, BoundType.Closed,
                                                       Comparator.<T>naturalOrder());
  }

  /**
   * Constructs a one-value interval containing the given value, using the given comparator to
   * define the order.
   * @param value the single value in the interval (non-null)
   * @param <T> T - the domain of the interval
   * @return [value, value]
   */
  public static <T> Interval <T> singleton(T value, Comparator<T> comparator){
    return new ClassInterval<T>(value, BoundType.Closed,
                                                       value, BoundType.Closed,
                                                       comparator);
  }




}
