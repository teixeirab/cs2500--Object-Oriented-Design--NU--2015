import java.util.ArrayList;
import java.util.List;

/**
 * Created by becogontijo on 4/13/2015.
 */
public class MergeSort {
  /**
   * Sorts a list using the mergesort algorithm.
   *
   * @param <E>  the element type
   * @param list the list to sort
   */
  public static <E extends Comparable<E>>
  void mergesort(List<E> list) {
    mergesortRec(list, new ArrayList<>(list));
  }


  /**
   * Mergesorts {@code list}, using {@code temp} for auxiliary storage.
   *
   * <p> PRECONDITION: {@code temp.size() >= list.size()}
   *
   * @param list the list to sort
   * @param temp auxiliary storage
   * @param <E> the element type
   */
  private static <E extends Comparable<E>>
  void mergesortRec(List<E> list, List<E> temp) {
    int size = list.size();

    if (size < 2) {
      return;
    }

    int middle = size / 2;

    for (int i = 0; i < middle; ++i) {
      temp.set(i, list.get(i));
    }

    mergesortRec(list.subList(middle, size), temp.subList(middle, size));
    mergesortRec(temp.subList(0, middle), list.subList(0, middle));
    merge(list, temp, middle);
  }

  /**
   * Merges the upper half of {@code list} with the lower half of {@code
   * temp} into {@code list}.
   *
   * @param list the destination list; upper half is sorted
   * @param temp the temporary list; lower half is sorted
   * @param middle marker between lower and upper halves (included in upper)
   * @param <E> the element type
   */
  private static <E extends Comparable<E>>
  void merge(List<E> list, List<E> temp, int middle) {
    int dst     = 0;
    int listSrc = middle;
    int tempSrc = 0;

    while (listSrc < list.size() && tempSrc < middle) {
      if (list.get(listSrc).compareTo(temp.get(tempSrc)) < 0) {
        list.set(dst++, list.get(listSrc++));
      } else {
        list.set(dst++, temp.get(tempSrc++));
      }
    }

    while (tempSrc < middle) {
      list.set(dst++, temp.get(tempSrc++));
    }
  }

}
