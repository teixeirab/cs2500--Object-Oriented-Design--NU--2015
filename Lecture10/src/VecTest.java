import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the {@link Vec} class.
 */
public class VecTest {
  Vec<Integer> vec0 = new Vec<>();
  Vec<Integer> vec5 = new Vec<>(Arrays.asList(3, 4, 5, 6, 7));

  // Lower boundary:
  @Test
  public void testGet_0() {
    assertEquals((Integer) 3, vec5.get(0));
  }

  // Ordinary case:
  @Test
  public void testGet_1() {
    assertEquals((Integer) 4, vec5.get(1));
  }

  // Upper boundary:
  @Test
  public void testGet_4() {
    assertEquals((Integer) 7, vec5.get(4));
  }

  // Just out of bounds, above:
  @Test(expected=IndexOutOfBoundsException.class)
  public void testGet_oob() {
    vec5.get(5);
  }

  // Just out of bounds, below:
  @Test(expected=IndexOutOfBoundsException.class)
  public void testGet_neg() {
    vec5.get(-1);
  }

  // Out of bounds because empty:
  @Test(expected=IndexOutOfBoundsException.class)
  public void testGet_empty() {
    vec0.get(0);
  }
  }