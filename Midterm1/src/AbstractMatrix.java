import java.util.Iterator;

/**
 * Created by becogontijo on 4/14/2015.
 */
public abstract class AbstractMatrix<T> implements Matrix<T> {
  private final int width;
  private final int height;

  protected AbstractMatrix(int width, int height){
    if (width < 0 || height < 0){
      throw new IllegalArgumentException("dimensions must be positive");
    }
    this.height = height;
    this.width = width;
  }

  /**
   *
   * @param x
   * @param y
   * @return
   */
  protected final boolean checkCordinates(int x, int y){
    return 0<= x && x < width && 0 <= y && y < height;
  }

  /**
   *
   * @param x
   * @param y
   */
  protected final void enforceCoordinates(int x, int y){
    if (! checkCordinates(x, y)){
      throw new IndexOutOfBoundsException("out");
    }
  }

  @Override
  public final int width(){
    return width;
  }

  @Override
  public final int height(){
    return height;
  }

  @Override
  public Matrix<T> transpose(){
    return new Transpose<>(this);
  }

  @Override
  public Matrix<T> slice(int x, int y, int width, int height){
    return new Slice<>(this, x, y, width, height);
  }

  @Override
  public Matrix<T> copy(){
    return new DenseMatrix<T>(this);
  }

  @Override
  public Iterator<T> iterator(){
    return new ByRows<>(this);
  }


}
