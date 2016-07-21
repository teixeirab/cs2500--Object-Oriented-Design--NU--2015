/**
 * Created by becogontijo on 4/1/2015.
 */
import java.util.Iterator;

/**
 * Interface for simple sets of integers. This is part of a demonstration of
 * the dangers of inheritance.
 */
public interface IntSet extends Iterable<Integer> {

  /**
   * Adds the given value to this set.
   *
   * @param value the integer add
   */
  void add(int value);

  /**
   * Adds all the values in the given array to this set.
   *
   * @param values the integers to add
   */
  void addAll(int[] values);

  /**
   * Removes the given value from this set, if present.
   *
   * @param value the integer to remove
   */
  void remove(int value);

  /**
   * Determines whether a particular integer value is a member of this set.
   *
   * @param value the integer to check
   * @return whether {@code value} is a member of this set
   */
  boolean member(int value);

  /**
   * Returns an iterator over the elements.
   *
   * @return an iterator
   */
  Iterator<Integer> iterator();
}
