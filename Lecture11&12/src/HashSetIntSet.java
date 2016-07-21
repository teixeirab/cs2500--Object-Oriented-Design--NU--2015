/**
 * Created by becogontijo on 4/1/2015.
 */
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * An implementation of {@link IntSet} using the Collections Framework's
 * {@link java.util.HashSet}. Yes, this is silly, but it's the quickest route to the
 * point of the demonstration.
 */
public final class HashSetIntSet implements IntSet {
  private final Set<Integer> set = new HashSet<>();

  @Override
  public void add(int value) {
    set.add(value);
  }

  @Override
  public void addAll(int[] values) {
    for (int i : values) {
      add(i);
    }
  }

  @Override
  public void remove(int value) {
    set.remove(value);
  }

  @Override
  public boolean member(int value) {
    return set.contains(value);
  }

  @Override
  public Iterator<Integer> iterator() {
    return set.iterator();
  }
}