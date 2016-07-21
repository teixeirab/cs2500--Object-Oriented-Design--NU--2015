import org.junit.Test;

import static org.junit.Assert.*;

public class WebPageTest {

  Publication cs3500 = new WebPage("Object Oriented Design",
                                   "http://www.ccs.neu.edu/home/tov/course/cs3500/",
                                   "August 11, 2014");
  Publication cs2500 = new WebPage("Fundies 1", "http://www.fundies1", "August 12 2013");

  @Test
  public void testCiteAPA() {
    assertEquals(
        "Object Oriented Design. Retrieved August 11, 2014, "
        + "from http://www.ccs.neu.edu/home/tov/course/cs3500/.",
        cs3500.citeApa());
    assertEquals(
        "Fundies 1. Retrieved August 12 2013, from http://www.fundies1.",
        cs2500.citeApa());
  }
  @Test
  public void testCiteMLA() {
    assertEquals(
        "Object Oriented Design. Web. August 11, "
        + "2014 <http://www.ccs.neu.edu/home/tov/course/cs3500/>.",
        cs3500.citeMla());
    assertEquals(
        "Fundies 1. Web. August 12 2013 <http://www.fundies1>.",
        cs2500.citeMla());
  }
}