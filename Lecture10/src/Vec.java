import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by becogontijo on 3/31/2015.
 */
public final class Vec<E> extends AbstractList<E> {

  private E[] data;
  private int size;

  public static int DEFAULT_CAPACITY = 10;

  public Vec(){
    data = allocate(DEFAULT_CAPACITY);
    size = 0;
  }

  /**
   * Allocates a new vector and initializes it with the contents of a
   * collection. The allocated vector will have no excess capacity.
   *
   * O({@code coll.size()}).
   *
   * @param coll the source of the elements
   */
  public Vec(Collection<?extends E> coll){
    data = allocate(coll.size());
    size = 0;
    for (E element : coll){
      data[size++] = element;
    }
  }

  /**
   * Allocates an array of the requested size at element type {@code E}.
   *
   * @param <E> the element type
   * @param capacity the array capacity
   * @return the new array
   */
  @SuppressWarnings("unchecked")
  private static <E> E[] allocate (int capacity){
    return (E[]) new Object[capacity];
  }



  @Override
  public int size(){
    return size;
  }

  @Override
  public E get(int index) {
    boundsCheck(index, size - 1);
    return data[index];
  }

  @Override
  public E set(int index, E element){
    boundsCheck(index, size - 1);
    E oldElement = data[index];
    data[index] = element;
    return oldElement;
  }

  @Override
  public void add(int index, E element){
    boundsCheck(index, size);
    ensureCapacity(index + 1);
    System.arraycopy(data, index, data, index + 1, size - index);
    ++size;
  }

  @Override
  public E remove(int index){
    boundsCheck(index, size - 1);
    E result = data[index];

    System.arraycopy(data, index + 1, data, index, size- (index + 1));
    return result;
  }

  @Override
  public void removeRange(int fromIndex, int toIndex){
    boundsCheck(fromIndex, size);
    boundsCheck(toIndex, size);

    if (toIndex < fromIndex){
      throw new IllegalArgumentException("bad");
    }
    System.arraycopy(data, toIndex, data, fromIndex, size - toIndex);
    Arrays.fill(data, size - (toIndex - fromIndex), size, null);

    size -= toIndex - fromIndex;
  }

  /**
   * Ensures that the vector has at least the requested capacity. This can be
   * used to preallocate space to avoid future expansions when the required
   * capacity is known.
   *
   * O({@code minCapacity})
   *
   * POSTCONDITION: {@code capacity() >= minCapacity}
   *
   * @param minCapacity the minimum capacity
   */
  public void ensureCapacity(int minCapacity){
    if (minCapacity > data.length){
      grow(minCapacity);
    }
  }

  private void grow(int minCapacity){
    int capacity = Integer.max(minCapacity, 2 * data.length);
    data = Arrays.copyOf(data, capacity);
  }

  /**
   * Ensures that {@code index} is between 0 and {@code limit}, inclusive,
   * or raises an exception otherwise.
   *
   * POSTCONDITION: {@code index >= 0 && index <= limit}
   *
   * @param index the index to check
   * @param limit the largest permissible value for index
   * @throws IndexOutOfBoundsException if {@code index < 0 || index > limit}
   */
  private static void boundsCheck(int index, int limit){
    if (index < 0 || index > limit){
      throw new IndexOutOfBoundsException("Vec");

    }
  }

  /**
   * Returns the capacity of the vector. This is how many elements it can
   * store without being expanded.
   *
   * O(1)
   *
   * @return the capacity.
   */
  public int capacity(){
    return data.length;
  }

  /**
   * Shrinks the capacity to fit the contents exactly.
   *
   * O({@code size()})
   *
   * POSTCONDITION: {@code capacity() == size()}
   */
  public void shrinkToFit(){
    if (size != capacity()){
      data = Arrays.copyOf(data, size);
    }
  }


}
