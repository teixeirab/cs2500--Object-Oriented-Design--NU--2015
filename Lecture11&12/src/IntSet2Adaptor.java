/**
 * Created by becogontijo on 4/1/2015.
 */
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Adapts an {@link IntSet2} to the {@link UnifiedIntSet} interface, which
 * extends both {@link IntSet} and {@link IntSet2}.
 */
public final class IntSet2Adaptor implements UnifiedIntSet {
  private final IntSet2 adaptee;

  public IntSet2Adaptor(IntSet2 adaptee) {
    Objects.requireNonNull(adaptee);
    this.adaptee = adaptee;
  }

  @Override
  public void add(int value) {
    adaptee.unionWith(HashSetIntSet2.singleton(value));
  }

  @Override
  public void addAll(int[] values) {
    for (int i : values) {
      add(i);
    }
  }

  @Override
  public void remove(int value) {
    adaptee.differenceFrom(HashSetIntSet2.singleton(value));
  }

  @Override
  public boolean member(int value) {
    return adaptee.contains(value);
  }

  @Override
  public Iterator<Integer> iterator() {
    return adaptee.asList().iterator();
  }

  @Override
  public void unionWith(IntSet2 other) {
    adaptee.unionWith(other);
  }

  @Override
  public void differenceFrom(IntSet2 other) {
    adaptee.differenceFrom(other);
  }

  @Override
  public boolean contains(int value) {
    return adaptee.contains(value);
  }

  @Override
  public List<Integer> asList() {
    return adaptee.asList();
  }
}
