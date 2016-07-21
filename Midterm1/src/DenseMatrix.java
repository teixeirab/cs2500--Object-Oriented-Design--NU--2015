/**
 * Created by becogontijo on 4/14/2015.
 */
public class DenseMatrix<T> extends AbstractMatrix<T> {
  final public String lineBreak = System.getProperty("line.separator");
  private final T[][] rows;

  public DenseMatrix(int width, int height){
    super(width, height);

    rows = (T[][]) new Object[height][width];
  }

  /**
   *
   * @param source
   */
  public DenseMatrix(Matrix<T> source){
    this(source.width(), source.height());
    for(int y = 0; y < height(); ++y){
      for (int x = 0; x <width(); ++x){
        set(x, y, source.get(x, y));
      }
    }
  }

  public DenseMatrix(int width, int height, T... fill){
    this(width, height);
    int fillIndex = 0;

    if (fill.length > 0){
      fillIndex = 0;
    }

    for (int y = 0; y < height; ++y){
      for(int x = 0; x <width; ++x){
        rows[y][x] = fill[fillIndex++];
        fillIndex %= fill.length;
      }
    }
  }

  @Override
  public T get(int x, int y) {
    enforceCoordinates(x, y);
    return rows[y][x];
  }

  @Override
  public void set(int x, int y, T value) {
    enforceCoordinates(x, y);
    rows[y][x] = value;
  }

  @Override
  public String toString() {
    String result = "";

    for (int x = 0; x < width(); ++x) {
      for (int y = 0; y < width(); ++y) {
        result = result + get(x, y);
      }
      result = result + lineBreak;
    }
    return result;
  }

}


