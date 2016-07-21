/**
 * Created by becogontijo on 4/14/2015.
 */
public class Slice<T> extends AbstractMatrix<T> {
  final public String lineBreak = System.getProperty("line.separator");
  private final Matrix<T> base;
  private final int xOffset;
  private final int yOffset;

  Slice(AbstractMatrix<T> matrix, int x, int y, int width, int height){
    super(width, height);

    if (x < 0 || y < 0){
      throw new IllegalArgumentException("exce");
    }
    if (x + width > matrix.width() || y + height > matrix.height()){
      throw new IndexOutOfBoundsException("not a sub");
    }
    base = matrix;
    xOffset = x;
    yOffset = y;
  }

  @Override
  public T get(int x, int y) {
    enforceCoordinates(x, y);
    return base.get(x + xOffset, y + yOffset);
  }

  @Override
  public void set(int x, int y, T value) {
    enforceCoordinates(x, y);
    base.set(x + xOffset, y + yOffset, value);
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