/**
 * Created by becogontijo on 4/9/2015.
 */
import org.junit.Test;

import java.util.*;
import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for sorting.
 */
public class SortingTest {
  /**
   *
   */
  private static final int MAX_SIZE = 32;

  @Test
  public void testBubbleSort() {
    testSort_sizes(Sorting::bubbleSort);
  }

  @Test
  public void testSelectionSort() {
    testSort_sizes(SelectionSort::selectionSort);
  }

  @Test
  public void testInsertionSort() {
    testSort_sizes((sort) -> Sorting.insertionSort(sort));
  }

  @Test
  public void testQuicksort() {
    testSort_sizes(Sorting::quicksort);
  }

  @Test
  public void testMergesort() {
    testSort_sizes(Sorting::mergesort);
  }

  /**
   * Tests a sorting routine at the sizes listed in {@link #TODO}.
   *
   * @param sort the sort routine to test
   */
  public void testSort_sizes(Consumer<List<Integer>> sort) {
    for (int size = 0; size < MAX_SIZE; ++size) {
      testSort_size(size, sort);
    }
  }

  /**
   * Tests that a sort procedure works on a random integer list of the given
   * size. The sort procedure is permitted to modify the input list. In-place
   * sorts should thus return the same list, sorted.
   *
   * @param size the size of the list to sort
   * @param sort the sort procedure
   */
  public void testSort_size(int size, Consumer<List<Integer>> sort) {
    List<Integer> unsorted = makeRandomIntegerList(size);
    List<Integer> sorted   = new ArrayList<>(unsorted);
    sort.accept(sorted);

    assertPermutation(sorted, unsorted);
    assertSorted(sorted);
  }

  private final Random rnd = new Random();

  /**
   * Returns a list of random integers of the given size.
   *
   * @param size the size of the result list
   * @return the list of random integers
   */
  private List<Integer> makeRandomIntegerList(int size) {
    // Why is the next line DRYer than its concrete and type-instantiated
    // elaboration?
    List<Integer> result = new ArrayList<>(size);

    for (int i = 0; i < size; i++) {
      result.add(rnd.nextInt());
    }

    return result;
  }

  /**
   * Asserts that two lists contain the same elements (including the same
   * numbers of each).
   *
   * @param <E> the element type
   * @param xs one list
   * @param ys the other list
   */
  private <E> void assertPermutation(List<E> xs, List<E> ys) {
    assertEquals(countCollection(xs), countCollection(ys));
  }

  /**
   * Counts the elements in a collection and returns a map from each element
   * to its count.
   *
   * @param <E> the element type
   * @param elts the collection
   * @return the counts, as a map
   */
  private <E> Map<E, Integer> countCollection(Collection<E> elts) {
    Map<E, Integer> counts = new HashMap<>();

    for (E elt : elts) {
      incMap(counts, elt);
    }

    return counts;
  }

  /**
   * Increment the integer value in a map associated with a given key,
   * or creates it (set to 1) if the key is unmapped.
   *
   * @param <K> the key type
   * @param map the map
   * @param key the key
   */
  private <K> void incMap(Map<K, Integer> map, K key) {
    Integer current = map.get(key);
    map.put(key, current == null ? 1 : current + 1);
  }

  /**
   * Asserts that the list is sorted.
   *
   * @param sorted the list to check
   */
  private <E extends Comparable<E>>
  void assertSorted(List<E> sorted) {
    E previous = null;

    for (E elt : sorted) {
      if (previous != null) {
        assertTrue("out of order: " + sorted, previous.compareTo(elt) <= 0);
      }
      previous = elt;
    }
  }

  ///
  /// Object sorting tests/examples
  ///

  @Test
  public void testSelectionSort_comparator() {
    List<String> list = Arrays.asList("we", "hold", "these",
                                      "truths", "to", "be");

    /*
    Comparator<String> compareStringLength = new Comparator<String>() {
      @Override
      public int compare(String s1, String s2) {
        return Integer.compare(s1.length(), s2.length());
      }
    };
    */

    Sorting.selectionSort(list,
                          (s1, s2) -> Integer.compare(s1.length(),  s2.length()));

    assertEquals(Arrays.asList("we", "to", "be", "hold", "these", "truths"),
                 list);
  }

  /**
   * An absurdly simple employee class for sorting.
   */
  static final class Employee implements Comparable<Employee> {
    private final String name;
    private final Integer salary;

    /**
     * Constructs an employee object from the name and salary.
     *
     * @param name the name
     * @param salary the salary
     */
    Employee(String name, Integer salary) {
      this.name = name;
      this.salary = salary;
    }

    /**
     * Gets the employee's name
     * @return the name
     */
    public String getName() {
      return name;
    }

    /**
     * Gets the employee's salary
     * @return the salary
     */
    public Integer getSalary() {
      return salary;
    }

    @Override
    public int compareTo(Employee other) {
      return name.compareTo(other.name);
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof Employee)) return false;

      Employee other = (Employee) o;

      return name.equals(other.name) && salary.equals(other.salary);
    }

    @Override
    public int hashCode() {
      return 31 * name.hashCode() + salary;
    }
  }

  Employee bill    = new Employee("Bill",    120_000),
      michael = new Employee("Michael",  75_000),
      milton  = new Employee("Milton",        0),
      samir   = new Employee("Samir",    80_000),
      peter   = new Employee("Peter",    72_000),
      peter2  = new Employee("Peter",    71_000),
      peter3  = new Employee("Peter",    70_000);

  List<Employee> office = Arrays.asList(bill, milton, peter, samir, michael,
                                        peter2, peter3);

  // Sort by name:
  /*
  @Test
  public void testSelectionSort_employees_name() {
    Sorting.selectionSort(office);
    assertEquals(Arrays.asList(bill, michael, milton, peter, samir), office);
  }
  */

  // Sort by salary (lowest to highest):
  @Test
  public void testSelectionSort_employees_salaryAsc() {
    Sorting.selectionSort(office, Comparator.comparing(Employee::getSalary));
    assertEquals(Arrays.asList(milton, peter3, peter2, peter, michael, samir,
                               bill), office);
  }

  // Sort by salary (highest to lowest):
  @Test
  public void testSelectionSort_employees_salaryDesc() {
    Sorting.selectionSort(office,
                          Comparator.comparing(Employee::getSalary)
                              .reversed());
    assertEquals(Arrays.asList(bill, samir, michael,
                               peter, peter2, peter3,
                               milton),
                 office);
  }

  // Sort lexicographically by name and then salary?
  @Test
  public void testSelectionSort_employees_name_then_salary() {
    Sorting.selectionSort(office,
                          Comparator.comparing(Employee::getName)
                              .thenComparing(Employee::getSalary)
    );
    assertEquals(Arrays.asList(bill, michael, milton,
                               peter3, peter2, peter, samir),
                 office);
  }
}

