/**
 * Created by becogontijo on 4/1/2015.
 */

import java.util.List;

public interface IntSet2 {
  /**
   * Inserts the elements of other into this set.
   */
  void unionWith(IntSet2 other);

  /**
   * Removes the elements of other from this set.
   */
  void differenceFrom(IntSet2 other);

  /**
   * Checks whether an {@link int} is a member of this set.
   * @param value the {@link int} to check for
   * @return whether {@code value} is in this set
   */
  boolean contains(int value);

  /**
   * The contents of the set as a list of integers. Modifying the returned
   * list will have no effect on this set.
   * @return the list of integers in this set
   */
  List<Integer> asList();
}