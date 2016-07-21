/**
 * Created by becogontijo on 4/13/2015.
 */
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class SelectionSort {
  private SelectionSort () {} // prevent instantiation

  /**
   * Sorts a list in place using the selection sort algorithm.
   *
   * @param <E>  the element type
   * @param list the list to sort
   */
  public static <E extends Comparable<E>> void selectionSort(List<E> list) {
    selectionSort(list, Comparator.naturalOrder());
  }

  /**
   * Sorts a list in place using the selection sort algorithm and an explicit
   * comparator.
   *
   * @param comparator the comparator specifying the order on {@code E}
   * @param list the list to sort
   * @param <E>  the element type
   */
  public static <E> void selectionSort(List<E> list, Comparator<E> comparator) {
    for (int numSorted = 0; numSorted < list.size(); ++numSorted) {
      int minIndex = numSorted;

      for (int i = numSorted + 1; i < list.size(); ++i) {
        if (comparator.compare(list.get(i), list.get(minIndex)) < 0) {
          minIndex = i;
        }
      }

      swap(list, numSorted, minIndex);
    }
  }

  private static <E> void swap(List<E> list, int j, int k) {
    E temp = list.get(j);
    list.set(j, list.get(k));
    list.set(k, temp);
  }

}
