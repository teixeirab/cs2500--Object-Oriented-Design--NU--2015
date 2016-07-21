/**
 * Created by becogontijo on 4/9/2015.
 */
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * Examples of sorting routines.
 */
public class Sorting {
  private Sorting () {} // prevent instantiation

  /**
   * Sorts a list in place using the bubble sort algorithm.
   *
   * @param <E>  the element type
   * @param list the list to sort
   */
  public static <E extends Comparable<E>>
  void bubbleSort(List<E> list) {
    boolean isSorted = false;

    while (! isSorted) {
      isSorted = true;

      for (int i = 0; i < list.size() - 1; ++i) {
        if (compare(list, i, i + 1) > 0) {
          swap(list, i, i + 1);
          isSorted = false;
        }
      }
    }
  }

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

  /**
   * When quicksort reaches a list of this size, it delegates to insertion
   * sort instead.
   */
  private static int QUICKSORT_THRESHOLD = 5;

  /**
   * Sorts a list in place using the quicksort algorithm.
   *
   * @param <E>  the element type
   * @param list the list to sort
   */
  public static <E extends Comparable<E>>
  void quicksort(List<E> list) {
    if (list.size() <= QUICKSORT_THRESHOLD) {
      insertionSort(list);
    } else {
      int pivot = partition(list);
      quicksort(list.subList(0, pivot));
      quicksort(list.subList(pivot + 1, list.size()));
    }
  }

  /**
   * Partitions a list around a random pivot and returns the index of the pivot.
   *
   * @param list the list to partition
   * @param <E> the list element type
   * @return the index of the pivot
   */
  private static <E extends Comparable<E>>
  int partition(List<E> list) {
    // Choose a random element as the pivot, and start it at index 0.
    swap(list, 0, new Random().nextInt(list.size()));

    int lower = 0;
    int upper = list.size() - 1;

    while (true) {
      // Lower is the pivot, so any elements at the end of the array that are
      // greater than it are in the right place.
      while (lower < upper && compare(list, lower, upper) <= 0) {
        --upper;
      }

      // If lower and upper have met, then the partition is finished.
      if (lower == upper) {
        return lower;
      }

      // Now we know that upper isn't in the right place,
      // so swap it into the right place and upper becomes the pivot.
      swap(list, lower++, upper);

      // Same deal, from the bottom...

      while (lower < upper && compare(list, lower, upper) <= 0) {
        ++lower;
      }

      if (lower == upper) {
        return lower;
      }

      swap(list, lower, upper--);
    }
  }

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

  /**
   * Compares two list elements.
   *
   * @param list the list
   * @param j    the first element
   * @param k    the second element
   * @param <E>  the element type
   * @return negative if the first element is less, positive if it's more,
   *         and zero if equal
   */
  private static <E extends Comparable<E>>
  int compare(List<E> list, int j, int k) {
    return list.get(j).compareTo(list.get(k));
  }

  /**
   * Swaps two list elements.
   *
   * @param list the list to sort
   * @param j    one index
   * @param k    the other index
   * @param <E>  the element type
   */
  private static <E> void swap(List<E> list, int j, int k) {
    E temp = list.get(j);
    list.set(j, list.get(k));
    list.set(k, temp);
  }
}
