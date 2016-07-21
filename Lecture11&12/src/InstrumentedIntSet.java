/**
 * Created by becogontijo on 4/1/2015.
 */
import java.util.Iterator;
import java.util.Objects;

/**
 * Instruments an {@link IntSet} to count the number of {@code add}
 * operations. (Idea from Joshua Bloch, Effective Java 2/e.) This class
 * illustrates how to instrument the {@link IntSet} class using object
 * composition (via the delegate pattern) rather than inheritance.
 */
public final class InstrumentedIntSet implements IntSet {
  private final IntSet delegate;
  private int addCount = 0;

  public InstrumentedIntSet() {
    delegate = new HashSetIntSet();
  }

  public InstrumentedIntSet(IntSet base) {
    Objects.requireNonNull(base);
    delegate = base;
  }

  public int getAddCount() {
    return addCount;
  }

  @Override
  public void add(int value) {
    delegate.add(value);
    ++addCount;
  }

  @Override
  public void addAll(int[] values) {
    delegate.addAll(values);
    addCount += values.length;
  }

  @Override
  public void remove(int value) {
    delegate.remove(value);
  }

  @Override
  public boolean member(int value) {
    return delegate.member(value);
  }

  @Override
  public Iterator<Integer> iterator() {
    return delegate.iterator();
  }
}
