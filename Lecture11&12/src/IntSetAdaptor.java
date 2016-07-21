/**
 * Created by becogontijo on 4/1/2015.
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Adapts an {@link IntSet} to the {@link UnifiedIntSet} interface, which
 * extends both {@link IntSet} and {@link IntSet2}.
 */
public class IntSetAdaptor implements UnifiedIntSet {
  private final IntSet adaptee;

  public IntSetAdaptor(IntSet adaptee) {
    Objects.requireNonNull(adaptee);
    this.adaptee = adaptee;
  }

  @Override
  public void unionWith(IntSet2 other) {
    for (int i : other.asList()) {
      adaptee.add(i);
    }
  }

  @Override
  public void differenceFrom(IntSet2 other) {
    for (int i : other.asList()) {
      adaptee.remove(i);
    }
  }

  @Override
  public boolean contains(int value) {
    return adaptee.member(value);
  }

  @Override
  public List<Integer> asList() {
    List<Integer> result = new ArrayList<>();
    for (int i : adaptee) {
      result.add(i);
    }
    return result;
  }

  @Override
  public void add(int value) {
    adaptee.add(value);
  }

  @Override
  public void addAll(int[] values) {
    adaptee.addAll(values);
  }

  @Override
  public void remove(int value) {
    adaptee.remove(value);
  }

  @Override
  public boolean member(int value) {
    return adaptee.member(value);
  }

  @Override
  public Iterator<Integer> iterator() {
    return adaptee.iterator();
  }
}
