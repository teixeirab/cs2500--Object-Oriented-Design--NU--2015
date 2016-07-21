import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class InstrumentedIntSetTest {
  InstrumentedIntSet set = new InstrumentedIntSet();
  int[] ar = { 1, 2, 3 };

  @Test
  public void testAdd() {
    for (int i : ar) {
      set.add(i);
    }

    assertEquals(3, set.getAddCount());
  }

  @Test
  public void testAddAll() {
    set.addAll(ar);
    assertEquals(3, set.getAddCount());
  }
}