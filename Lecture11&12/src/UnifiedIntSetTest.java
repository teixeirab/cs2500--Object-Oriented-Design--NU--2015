/**
 * Created by becogontijo on 4/1/2015.
 */
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class UnifiedIntSetTest {

  IntSet set = new HashSetIntSet();
  IntSet2 set2 = HashSetIntSet2.singleton(5);

  UnifiedIntSet uSet = new IntSetAdaptor(set);
  UnifiedIntSet uSet2 = new IntSet2Adaptor(set2);

  @Before
  public void setUp() throws Exception {
    set.add(2);
    set.add(3);
  }

  @Test
  public void testAdaptedMember() {
    assertTrue(uSet.member(2));
    assertTrue(uSet.member(3));
    assertFalse(uSet.member(4));

    assertTrue(uSet2.member(5));
    assertFalse(uSet2.member(4));
  }

  @Test
  public void testAdaptedAdd() {
    assertFalse(uSet.member(4));
    assertFalse(set.member(4));
    uSet.add(4);
    assertTrue(uSet.member(4));
    assertTrue(set.member(4));

    assertFalse(uSet.member(5));
    assertFalse(set.member(5));
    set.add(5);
    assertTrue(uSet.member(5));
    assertTrue(set.member(5));

    assertFalse(uSet2.contains(4));
    assertFalse(set2.contains(4));
    uSet2.add(4);
    assertTrue(uSet2.contains(4));
    assertTrue(set2.contains(4));
  }
}


