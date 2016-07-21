/**
 * Created by becogontijo on 4/14/2015.
 */
import java.util.Iterator;

public interface Matrix<T> extends Iterable<T> {

  /**
   *
   * @return
   */
  int width();

  /**
   *
   * @return
   */
  int height();

  /**
   *
   * @param x
   * @param y
   * @return
   */
  T get(int x, int y);

  /**
   *
   * @param x
   * @param y
   * @param value
   */
  void set(int x, int y, T value);

  /**
   *
   * @param x
   * @param y
   * @param width
   * @param height
   * @return
   */
  Matrix<T> slice(int x, int y, int width, int height);

  /**
   *
   * @return
   */
  Matrix<T> transpose();

  /**
   *
   * @return
   */
  Matrix<T> copy();

  /**
   *
   */
  Iterator<T> iterator();
}
