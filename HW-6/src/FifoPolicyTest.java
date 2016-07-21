import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


public class FifoPolicyTest {

  /**
   * Here's an example of using the Fifo policy. The comments on the right
   * show the state of the cache/queue after each {@code require} operation.
   */
/*
  ReplacementPolicy<Integer> policy = new FifoPolicy<>(5);

  @Test
  public void extendedExample() {
    assertEquals(5, policy.capacity());
    assertEquals(0, policy.size());

    // Requiring items starts to fill the cache.

    policy.require(1);                            // 1 _ _ _ _
    assertEquals(1, policy.size());

    policy.require(2);                            // 1 2 _ _ _
    assertEquals(2, policy.size());



    // Requiring an item that's already in the cache moves it to the beginning:
    policy.require(1);                            // 1 2 _ _ _
    assertEquals(2, policy.size());


    policy.require(3);                            // 3 2 1  _ _
    policy.require(4);                            // 4 3 2 1  _
    assertEquals(4, policy.size());

    policy.require(1);                            // 4 3 2 1 _
    assertEquals(4, policy.size());

    policy.require(5);                            // 5 4 3 2 1
    assertEquals(5, policy.size());

    assertTrue(policy.size() <= policy.capacity());


    // Once the cache is full, requiring an item that is absent causes the
    // first item to be evicted;

    assertEquals((Integer) 1, policy.require(6)); // 6 5 4 3 2
    assertEquals(5, policy.size());
    assertNull(policy.require(2));                // 6 5 4 3 2
    assertEquals((Integer) 2, policy.require(7)); // 7 6 5 4 3
    assertNull(policy.require(4));                // 7 6 5 4 3
    assertEquals((Integer) 3, policy.require(8)); // 8 7 6 5 4
    assertNull(policy.require(7));                // 8 7 6 5 4
    assertEquals((Integer) 4, policy.require(1)); // 1 8 7 6 5
    assertNull(policy.require(6));                // 1 8 7 6 5
    assertEquals((Integer) 5, policy.require(2)); // 2 1 8 7 6
    assertEquals((Integer) 6, policy.require(9)); // 9 2 1 8 7
    assertEquals((Integer) 7, policy.require(4)); // 4 9 2 1 8
    assertEquals((Integer) 8, policy.require(3)); // 3 4 9 2 1

    assertEquals(5, policy.capacity());
    assertEquals(5, policy.size());

    assertEquals(policy.size(), policy.size());

  }
  /*
    tests to see if the invariant size == capacity remains true
   */
  /*
  @Test
  public void testCapacitySize(){
    assertTrue(policy.size() <= policy.capacity());
  }

  @Test
  public void testSizeIncrease(){
    ReplacementPolicy<Integer> policy3 = new FifoPolicy<>(3);
    assertEquals(0, policy3.size());
    policy3.require(1);
    assertEquals(1, policy3.size());
    assertTrue(policy3.size() <= policy.capacity());
    policy3.require(2);
    assertEquals(2, policy3.size());
    assertTrue(policy3.size() <= policy.capacity());
    policy3.require(3);
    assertEquals(3, policy3.size());
    assertTrue(policy3.size() <= policy.capacity());
  }

  @Test
  public void testDifferentK(){
    ReplacementPolicy<String> policy3 = new FifoPolicy<>(3);
    assertEquals(0, policy3.size());
    policy3.require("n");
    policy3.require("i");
    policy3.require("r");
    policy3.require("e");
    assertEquals("i", policy3.require("n"));
  }

  @Test
  public void testNoRepetitiveNums(){
    ReplacementPolicy<Integer> policy2 = new FifoPolicy<>(3);
    assertEquals(null, policy2.require(3));
    assertEquals(null , policy2.require(3));
    assertEquals(null, policy2.require(3));
    assertEquals(null, policy2.require(3));
  }

  @Test (expected = IllegalArgumentException.class)
  public void testBadPolicy(){
    ReplacementPolicy<Integer> policy = new FifoPolicy<>(0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testBadPolicy2(){
    ReplacementPolicy<Integer> policy = new FifoPolicy<>(-2);
  }
  */
}