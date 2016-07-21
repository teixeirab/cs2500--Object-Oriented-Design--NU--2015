/**
 * Created by becogontijo on 4/9/2015.
 */
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class SortingTestMore {
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

  Employee bill    = new Employee("Bill",    120_000);
  Employee michael = new Employee("Michael",  75_000);
  Employee milton  = new Employee("Milton",        0);
  Employee samir   = new Employee("Samir",    80_000);
  Employee peter   = new Employee("Peter",    72_000);

  List<Employee> office = Arrays.asList(bill, milton, peter, samir, michael);

  // Sort by name:
  @Test
  public void testSelectionSort_employees_name() {
    Sorting.selectionSort(office);
    assertEquals(Arrays.asList(bill, michael, milton, peter, samir), office);
  }

  // Sort by salary (lowest to highest):
  @Test
  public void testSelectionSort_employees_salaryAsc() {
    Sorting.selectionSort(office,
                          (e1, e2) -> e1.getSalary().compareTo(e2.getSalary()));
    assertEquals(Arrays.asList(milton, peter, michael, samir, bill), office);
  }

  // Sort by salary (highest to lowest):
  @Test
  public void testSelectionSort_employees_salaryDesc() {
    Sorting.selectionSort(office,
                          (e1, e2) -> e2.getSalary().compareTo(e1.getSalary()));
    assertEquals(Arrays.asList(bill, samir, michael, peter, milton), office);
  }
}
