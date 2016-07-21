import java.util.List;

/**
 * Created by becogontijo on 4/13/2015.
 */
public class InsertionSort {
  /**
   * Sorts a list in place using the insertion sort algorithm.
   *
   * @param <E>  the element type
   * @param list the list to sort
   */
  public static <E extends Comparable<E>> void insertionSort(List<E> list) {
    for (int numSorted = 0; numSorted < list.size(); ++numSorted) {
      E element = list.get(numSorted);

      int i;
      for (i = numSorted; i > 0; --i) {
        if (element.compareTo(list.get(i - 1)) < 0) {
          list.set(i, list.get(i - 1));
        } else {
          break;
        }
      }

      list.set(i, element);
    }
  }

}
