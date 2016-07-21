import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class StrictCoinGamePlayerTest {

  StrictCoinGameModel game6 = new StrictCoinGameModel("-O-O-");

  StrictCoinGamePlayer a = new StrictCoinGamePlayer("Bob",game6 );
  StrictCoinGamePlayer b = new StrictCoinGamePlayer("Greg",game6 );
  @Test
  public void testGetName() {
    assertEquals("Bob", a.getName());
    assertEquals("Greg", b.getName());
  }

  @Test
  public void testMove() {
  }

  @Test
  public void testIsTurn() {
  }
}