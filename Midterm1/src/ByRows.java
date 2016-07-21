import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by becogontijo on 4/14/2015.
 */
public class ByRows<T> implements Iterator<T> {
  private final AbstractMatrix<T> support;
  private int x = 0;
  private int y = 0;

  public ByRows(AbstractMatrix<T> matrix) {
    support = matrix;
  }

  @Override
  public boolean hasNext() {
    return support.checkCordinates(x, y);
  }

  @Override
  public T next() {
    if (hasNext()){
      T result = support.get(x, y);

      if (++x == support.width()){
        x = 0;
        ++y;
      }
      return result;
    } else{
      throw new NoSuchElementException("iterator");
    }
  }
}
