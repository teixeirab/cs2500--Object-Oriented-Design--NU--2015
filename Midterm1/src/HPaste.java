/**
 * Created by becogontijo on 4/14/2015.
 */
public class HPaste<T> extends AbstractMatrix<T> {
  private final Matrix<T> base1;
  private final Matrix<T> base2;

  HPaste(Matrix<T> matrix1, Matrix<T> matrix2){
    super (matrix1.width() + matrix1.width(), matrix1.height());

    if (matrix1.height() != matrix2.height()){
      throw new IllegalArgumentException("different heights");
    }
    base1 = matrix1;
    base2 = matrix2;
  }

  @Override
  public T get(int x, int y) {
    if(x < base1.width()){
      base2.get(base1.width() - x, y);
    }
    return base1.get(x, y);
  }

  @Override
  public void set(int x, int y, T value) {

  }
}
