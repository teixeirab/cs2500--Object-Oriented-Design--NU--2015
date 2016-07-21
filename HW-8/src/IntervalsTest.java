import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Comparator;

import static org.junit.Assert.*;

public class IntervalsTest {
  // Natural Order
  Interval<Double> double1 = Intervals.closed(1d, 5d);
  Interval<Double> double2 = Intervals.closedOpen(1d, 5d);
  Interval<Double> double3 = Intervals.interval(1d, BoundType.Closed, 5d, BoundType.Closed);
  Interval<Double> double4 = Intervals.open(1d, 5d);
  Interval<Double> double5 = Intervals.openClosed(1d, 5d);
  Interval<Double> double6 = Intervals.empty();
  Interval<Double> double7 = Intervals.singleton(1d);
  Interval<Double> double8 = Intervals.openClosed(-1d, 10d);
  Interval<Double> double9 = Intervals.openClosed(-1d, 4d);

  Interval<Double> double10 = Intervals.openClosed(2d, 2d);

  Interval<String> string1 = Intervals.closed("a", "g");
  Interval<String> string2 = Intervals.closedOpen("a", "g");
  Interval<String> string3 = Intervals.interval("a", BoundType.Closed, "g", BoundType.Closed);
  Interval<String> string4 = Intervals.open("a", "g");
  Interval<String> string5 = Intervals.openClosed("a", "g");
  Interval<String> string6 = Intervals.empty();
  Interval<String> string7 = Intervals.singleton("a");
  Interval<String> string8 = Intervals.open("b", "e");
  Interval<String> string9 = Intervals.open("b", "z");

  // Reversed Natural Order
  Interval<Double> double1Reversed =
      Intervals.closed(5d, 1d,Comparator.<Double>naturalOrder().reversed());
  Interval<Double> double2Reversed =
      Intervals.closedOpen(5d, 1d, Comparator.<Double>naturalOrder().reversed());
  Interval<Double> double8Reversed =
      Intervals.openClosed(10d, -1d, Comparator.<Double>naturalOrder().reversed());

  Interval<String> string1Reversed =
      Intervals.closed("g", "a", Comparator.<String>naturalOrder().reversed());
  Interval<String> string2Reversed =
      Intervals.closedOpen("g", "a", Comparator.<String>naturalOrder().reversed());
  Interval<String> string7Reversed =
      Intervals.singleton("a", Comparator.<String>naturalOrder()
      .reversed());


  @Test
  public void testContains() {
    // Natural Order
    assertTrue(double1.contains(4d)); // closed
    assertTrue(double2.contains(5d)); // closedOpen
    assertFalse(double2.contains(1d)); // closedOpen
    assertFalse(double3.contains(1d)); // closed
    assertTrue(double4.contains(1d)); // open
    assertTrue(double4.contains(5d)); // open
    assertTrue(double5.contains(1d)); // openClosed
    assertFalse(double5.contains(5d)); // openClosed
    assertTrue(double7.contains(1d)); // singleton
    assertFalse(double7.contains(5d)); // singleton

    assertTrue(string1.contains("b")); // closed
    assertTrue(string2.contains("g")); // closedOpen
    assertFalse(string2.contains("a")); // closedOpen
    assertFalse(string3.contains("a")); // closed
    assertTrue(string4.contains("a")); // open
    assertTrue(string4.contains("g")); // open
    assertTrue(string5.contains("a")); // openClosed
    assertFalse(string5.contains("g")); // openClosed
    assertTrue(string7.contains("a")); // singleton
    assertFalse(string7.contains("g")); // singleton

    //Reversed Order
    assertTrue(double1Reversed.contains(4d)); // closed
    assertTrue(double2Reversed.contains(1d)); // closedOpen
    assertFalse(double8Reversed.contains(-1d)); // closedOpen

    assertTrue(string1Reversed.contains("b")); // closed
    assertFalse(string2Reversed.contains("g")); // closedOpen
    assertTrue(string7Reversed.contains("a")); // singleton

  }

  @Test
  public void testIsEmpty() {
    assertFalse(double1.isEmpty()); // closed
    assertFalse(double2.isEmpty()); // closed
    assertTrue(double6.isEmpty()); // closed
    assertTrue(double10.isEmpty()); // closed

    assertFalse(string1.isEmpty()); // closed
    assertFalse(string2.isEmpty()); // closed
    assertTrue(string6.isEmpty()); // closed
  }

  @Test
  public void testIncludes() {
    assertTrue(double4.includes(double7)); // closed76
    assertFalse(double4.includes(double8)); // open
    assertTrue(double8.includes(double4)); //openClosed
    assertFalse(double7.includes(double4)); //singleton
    assertTrue(double8.includes(Intervals.empty())); //openClosed

    assertTrue(string1.includes(string8)); // closed
    assertTrue(string2.includes(string8)); // closedOpen
    assertTrue(string4.includes(string1)); // open
    assertFalse(string8.includes(string1));
    assertTrue(string8.includes(Intervals.empty())); // open

  }

  @Test
  public void testIntersection() {
    assertEquals(double1.toString(),
                 double1.intersection(double4).toString()); // [1d 5d] (1d 5d) -> [1d 5d]
    assertEquals(double7.toString(),
                 double4.intersection(double7).toString()); // (1d] (1d 5d) -> (1d]
    assertEquals(double4.toString(),
                 double8.intersection(double4).toString()); // [-1d 10d] (1d 5d) -> (1d 5d)
    Interval<Double> temp = Intervals.openClosed(1d, 4d);
    assertEquals(temp.toString() ,
                 double9.intersection(double4).toString()); // (-1d 4d] (1d 5d) -> (1d 4d]

    assertEquals(string1.toString(),
                 string1.intersection(string4).toString()); // [a g] (a g) -> [a g]
    assertEquals(string7.toString(),
                 string4.intersection(string7).toString()); // (a] (a g) -> (a]
    assertEquals(string8.toString(),
                 string8.intersection(string4).toString()); // (b e) (a g) -> (b e)
    Interval<String> temp2 = Intervals.open("b", "g");
    assertEquals(temp2.toString() ,
                 string9.intersection(string4).toString()); // (a g) (b z) -> (b g)
  }

  @Test
  public void testSpan() {
    assertEquals(double4.toString(),
                 double1.span(double4).toString()); // [1d 5d] (1d 5d) -> (1d 5d)
    assertEquals(double4.toString(),
                 double4.span(double7).toString()); // (1d] (1d 5d) -> (1d 5d)
    assertEquals(double8.toString(),
                 double8.span(double4).toString()); // [-1d 10d] (1d 5d) -> [-1d 10d]
    Interval<Double> temp = Intervals.open(-1d, 5d);
    assertEquals(temp.toString() ,
                 double9.span(double4).toString()); // (-1d 4d] (1d 5d) -> (-1d 5d)
  }

  @Test
  public void testToString(){
    assertEquals("[1.0, 5.0]",double1.toString());
    assertEquals("[1.0, 5.0]",double3.toString());
    assertEquals("(1.0, 5.0)",double4.toString());
    assertEquals("(1.0, 5.0]",double5.toString());
    assertEquals("Empty",double6.toString());
    assertEquals("Empty", double10.toString());

    assertEquals("[a, g]",string1.toString());
    assertEquals("[a, g]",string3.toString());
    assertEquals("(a, g)",string4.toString());
    assertEquals("(a, g]",string5.toString());
    assertEquals("Empty",string6.toString());
  }

  @Test
  public void testEquals(){
    assertTrue(double1.equals(double1));
    assertFalse(double1.equals(double8));
  }
}

