import java.util.ArrayList;

/**
 * A cache policy implementing the <a
 * href="http://en.wikipedia.org/wiki/Page_replacement_algorithm"></a>.
 */
public final class LruPolicy<K> implements ReplacementPolicy<K>{
  // The capacity of the cache:
  private final int capacity;

  // The number of items in the cache:
  private int size;

  // The circular buffer of items:
  private ArrayList<K> buffer;

  /**
   * Class Invariants:
   * capacity > 0
   * Creates a new clock queue with capacity {@code capacity}.
   *
   * @param cap the capacity of the queue.
   * @throws IllegalArgumentException {@code cap < 1}
   */
  public LruPolicy(int cap) {
    if (cap < 1) {
      throw new IllegalArgumentException("capacity must be at least 1");
    }

    capacity = cap;
    buffer = new ArrayList<>(cap);
  }

  @Override
  public int capacity() {
    return capacity;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public K require(K item) {

    for(int i = 0; i < size; i++) {
      if (buffer.get(i).equals(item)) {
        //its a hit
        buffer.add(buffer.remove(i));
        return null;
      }
    }

    //its a miss

    int temp = 0;
    int index = 0;
    K evicted = null;

    if(size == capacity) {
      //its full
      evicted = buffer.remove(0);
    }

    buffer.add(item);
    size = buffer.size();
    return evicted;
  }

}
