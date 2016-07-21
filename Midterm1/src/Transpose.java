/**
 * Created by becogontijo on 4/14/2015.
 */
public class Transpose<T> extends AbstractMatrix<T> {
  final public String lineBreak = System.getProperty("line.separator");

  private final Matrix<T> base;

  Transpose(Matrix<T> matrix){
    super(matrix.height(), matrix.width());
    base = matrix;
  }

  @Override
  public T get(int x, int y) {
    return base.get(y,x);
  }

  @Override
  public void set(int x, int y, T value) {
    base.set(y, x, value);
  }

  @Override
  public Matrix<T> transpose(){
    return base;
  }

  @Override
  public String toString() {
    String result = "";

    for (int x = 0; x < width(); ++x) {
      for (int y = 0; y < width(); ++y) {
        result = result + get(x, y) + ", ";
      }
      result = result + lineBreak;
    }
    return result;
  }
}
