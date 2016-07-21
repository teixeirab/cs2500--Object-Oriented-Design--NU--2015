

import org.junit.Test;

import static org.junit.Assert.*;

public class DenseMatrixTest {
  Matrix<String> m1 = new DenseMatrix<String>(6, 4);
  Matrix<String> m2 = new DenseMatrix<String>(3, 3, "1", "2");
  Matrix<String> m3 = new DenseMatrix<String>(4, 3, "1", "2", "3", "4",
                                                    "5", "6", "7", "8",
                                                    "9", "A", "B", "C");

  final public String lineBreak = System.getProperty("line.separator");


  @Test
  public void testGet(){
    assertNull(m1.get(3,2));

    assertEquals("1", m3.get(0,0));
    assertEquals("C", m3.get(3,2));
  }

  @Test
  public void testSet(){
    m2.set(0,0, "foo");
    m2.set(1, 1, "bar");
    m2.set(2,2, "baz");

    assertEquals("foo", m2.get(0,0));
    assertEquals("bar", m2.get(1,1));
    assertEquals("baz", m2.get(2,2));
  }

  @Test
  public void testTranspose(){
    Matrix<String> m = m3.transpose();

    m.set(0,2,"foo");
    m3.set(3,1, "bar");

    assertEquals("bar", m.get(1,3));


    System.out.print(m3.get(0, 0) +" ," + m3.get(1, 0) +" ," + m3.get(2, 0) +" ," + m3.get(3,0) + lineBreak +
                     m3.get(0, 1) +" ," + m3.get(1, 1) +" ," + m3.get(2, 1) +" ," + m3.get(3,1) + lineBreak +
                     m3.get(1, 2) +" ," + m3.get(1, 2) +" ," + m3.get(2, 2) +" ," + m3.get(3,2) + lineBreak + lineBreak);

    System.out.print(m.get(0, 0) +" ," + m.get(1, 0) +" ," + m.get(2, 0) + lineBreak +
                     m.get(0, 1) +" ," + m.get(1, 1) +" ," + m.get(2, 1) + lineBreak +
                     m.get(0, 2) +" ," + m.get(1, 2) +" ," + m.get(2, 2) + lineBreak +
                     m.get(0, 3) +" ," + m.get(1, 3) +" ," + m.get(2, 3) + lineBreak);
  }

  @Test
  public void testSlice(){

  }
}