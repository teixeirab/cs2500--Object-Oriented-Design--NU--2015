import java.util.Comparator;

/**
 * Created by becogontijo on 4/8/2015.
 */
public class ClassInterval<T> extends Intervals<T> implements Interval<T> {

  private T lowerBound;
  private BoundType lowerType;
  private T upperBound;
  private BoundType upperType;
  private Comparator<T> comparator;
  private final boolean empty;

  /**
   * Constructs a ClassInterval, may only be accessed by the client through Intervals<T> static
   * factory methods
   *
   * @param lowerBound represents the lowerBound of a interval
   * @param lowerType  represents the type of the leftmost bound [ or (
   * @param upperBound represents the upperBound of a interval
   * @param upperType  represents the type of the leftmost bound ] or )
   * @param comparator represents the comparator<T> used to compare the other of Ts
   * @throws java.lang.IllegalArgumentException if lower bound is greater than upper bound
   * @throws java.lang.IllegalAccessException   for undefined intervals such as (6,6)
   */
  protected ClassInterval(T lowerBound, BoundType lowerType,
                          T upperBound, BoundType upperType,
                          Comparator<T> comparator) {

    if (comparator.compare(lowerBound, upperBound) == 1) {
      throw new IllegalArgumentException("Lower Bound is greater than Upper Bound");
    }

    if (comparator.compare(lowerBound, upperBound) == 0 &&
        (lowerType.isOpen() && upperType.isOpen())) {
      throw new IllegalArgumentException("Interval set is undefined and cannot be created");
    }
    if ((lowerBound.equals(upperBound) && upperType.isClosed() && lowerType.isOpen()) ||
        (lowerBound.equals(upperBound) && upperType.isOpen() && lowerType.isClosed())){
      this.empty = true;
    }
    else this.empty = false;

    this.lowerBound = lowerBound;
    this.lowerType = lowerType;
    this.upperBound = upperBound;
    this.upperType = upperType;
    this.comparator = comparator;
  }

  /**
   * Constructs an empty Class, may only be accessed by the client through Intervals<T> static
   * factory methods
   */
  protected ClassInterval() {
    this.empty = true;
  }

  /**
   * Compares two Bounds in order to find the larger one by a given comparator
   *
   * @param bound1 the first bound to be compared
   * @param boundType1 the type of the first bound
   * @param lowerOrUpper1 if given false it means it was given the upper bound of the interval,
   *                      otherwise it was given the lower bound
   * @param bound2 the bound that is to be compared with the first one
   * @param boundType2 the type of the second bound
   * @param comparator1 the comparator used to compare the bounds
   * @return +1 if bound1 is greater than bound2, 0 if both bounds are the same, and -1 if bound1
   * is less than bound2
   */
  private int compareTwoBounds(T bound1, BoundType boundType1, boolean lowerOrUpper1,
                               T bound2, BoundType boundType2,
                               Comparator<T> comparator1) {
    int comparedBounds = comparator1.compare(bound1, bound2);
    if (comparedBounds == 0) {
      if ((boundType1.isOpen() && boundType2.isOpen()) ||
          (boundType1.isClosed() && boundType2.isClosed())) {
        return 0;
      } else if (boundType1.isClosed() && boundType2.isOpen() && lowerOrUpper1) {
        return 1;
      } else if (boundType1.isOpen() && boundType2.isClosed() && lowerOrUpper1) {
        return -1;
      } else if (boundType1.isClosed() && boundType2.isOpen() && !lowerOrUpper1) {
        return -1;
      } else if (boundType1.isOpen() && boundType2.isClosed() && !lowerOrUpper1) {
        return 1;
      }
    }
    return comparedBounds;
  }

  @Override
  public boolean contains(T value) {
    if(lowerBound.equals(value) && upperBound.equals(value)){
      return true;
    }
    return (compareTwoBounds
                (lowerBound, lowerBoundType(), true, value, BoundType.Open, comparator) <= 0) &&
           (compareTwoBounds
                (upperBound, upperBoundType(), false, value, BoundType.Open, comparator) >= 0);
  }

  @Override
  public boolean includes(Interval<T> other) {
    if (other.isEmpty()) {
      return true;
    }
    return compareTwoBounds(lowerBound, lowerBoundType(), true,
                            other.lowerBound(), other.lowerBoundType(), comparator) <= 0 &&
           compareTwoBounds(upperBound, upperBoundType(), false,
                            other.upperBound(), other.upperBoundType(), comparator) >= 0;
  }

  @Override
  public Interval intersection(Interval<T> other) {
    T newLowerBound = lowerBound();
    BoundType newLowerBoundType = lowerBoundType();
    T newUpperBound = upperBound();
    BoundType newUpperBoundType = upperBoundType();

    if (includes(other)) {
      return other;
    }
    if (other.includes(this)) {
      return this;
    }
    if (!contains(other.lowerBound()) && !contains(other.upperBound())) {
      return Intervals.empty();

    } else if (compareTwoBounds(lowerBound, lowerBoundType(), true,
                                other.lowerBound(), other.lowerBoundType(), comparator) < 0) {
      newLowerBound = other.lowerBound();
      newLowerBoundType = other.lowerBoundType();

    } else if (compareTwoBounds(upperBound, upperBoundType(), false,
                                other.upperBound(), other.upperBoundType(), comparator) > 0) {
      newUpperBound = other.upperBound();
      newUpperBoundType = other.upperBoundType();
    }
    return Intervals.interval(newLowerBound, newLowerBoundType,
                              newUpperBound, newUpperBoundType, getComparator());
  }

  @Override
  public Interval<T> span(Interval<T> other) {
    T newLowerBound = lowerBound();
    BoundType newLowerBoundType = lowerBoundType();
    T newUpperBound = upperBound();
    BoundType newUpperBoundType = upperBoundType();

    if (compareTwoBounds(lowerBound, lowerBoundType(), true,
                         other.lowerBound(), other.lowerBoundType(), comparator) > 0) {
      newLowerBound = other.lowerBound();
      newLowerBoundType = other.lowerBoundType();
    }
    if (compareTwoBounds(upperBound, upperBoundType(), false,
                         other.upperBound(), other.upperBoundType(), comparator) < 0) {
      newUpperBound = other.upperBound();
      newUpperBoundType = other.upperBoundType();
    }
    return Intervals.interval(newLowerBound, newLowerBoundType,
                              newUpperBound, newUpperBoundType, getComparator());
  }

  @Override
  public boolean isEmpty() {
    return this.empty;
  }

  @Override
  public T lowerBound() {
    return lowerBound;
  }

  @Override
  public T upperBound() {
    return upperBound;
  }

  @Override
  public BoundType lowerBoundType() {
    return lowerType;
  }

  @Override
  public BoundType upperBoundType() {
    return upperType;
  }


  @Override
  public Comparator<T> getComparator() {
    return comparator;
  }

  @Override
  public boolean test(T value) {
    return contains(value);
  }

  public boolean equals(Interval<T> other) {
    if (this == other) {
      return true;
    }
    if (other == null) {
      return false;
    }

    if (comparator != null ? !comparator.equals(other.getComparator())
                           : other.getComparator()!= null) {
      return false;
    }
    if (lowerBound != null ? !lowerBound.equals(other.lowerBound()) : other.lowerBound() != null) {
      return false;
    }
    if (lowerType != other.lowerBoundType()) {
      return false;
    }
    if (upperBound != null ? !upperBound.equals(other.upperBound()) : other.upperBound()!= null) {
      return false;
    }
    if (upperType != other.upperBoundType()) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = lowerBound.hashCode();
    result = 31 * result + lowerType.hashCode();
    result = 31 * result + upperBound.hashCode();
    result = 31 * result + upperType.hashCode();
    result = 31 * result + comparator.hashCode();
    return result;
  }

  @Override
  public String toString() {
    String result = "";

    if (isEmpty()) {
      return "Empty";
    }
    if (lowerType.isClosed()) {
      result = result + "[";
    }
    if (lowerType.isOpen()) {
      result = result + "(";
    }
    result = result + lowerBound + ", " + upperBound;

    if (upperType.isClosed()) {
      result = result + "]";
    }
    if (upperType.isOpen()) {
      result = result + ")";
    }
    return result;
  }

}
