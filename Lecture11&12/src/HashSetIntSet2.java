/**
 * Created by becogontijo on 4/1/2015.
 */
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * An implementation of the alternative {@link IntSet2} interface, using
 * the Collections Framework's {@link java.util.HashSet} as {@link HashSetIntSet}
 * does.
 */
public class HashSetIntSet2 implements IntSet2 {
  private final Set<Integer> set = new HashSet<>();

  private HashSetIntSet2() { }

  /**
   * Constructs a new empty {@code IntSet2}.
   */
  public static IntSet2 empty() {
    return new HashSetIntSet2();
  }

  /**
   * Constructs a new single-element {@code IntSet2}.
   */
  public static IntSet2 singleton(int i) {
    HashSetIntSet2 result = new HashSetIntSet2();
    result.set.add(i);
    return result;
  }

  @Override
  public void unionWith(IntSet2 other) {
    set.addAll(other.asList());
  }

  @Override
  public void differenceFrom(IntSet2 other) {
    set.removeAll(other.asList());
  }

  @Override
  public boolean contains(int value) {
    return set.contains(value);
  }

  @Override
  public List<Integer> asList() {
    return new ArrayList<>(set);
  }
}
