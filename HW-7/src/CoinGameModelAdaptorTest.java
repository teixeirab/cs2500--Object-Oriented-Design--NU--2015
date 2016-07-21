import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class CoinGameModelAdaptorTest {

  // Instances of the game
  // empty game with no spaces
  StrictCoinGameModel game1 = new StrictCoinGameModel("-");
  // empty game with one space
  StrictCoinGameModel game2 = new StrictCoinGameModel("-");
  // game with only one coin
  StrictCoinGameModel game3 = new StrictCoinGameModel("O");
  // empty game with multiple spaces
  StrictCoinGameModel game4 = new StrictCoinGameModel("-----");
  // full game
  StrictCoinGameModel game5 = new StrictCoinGameModel("OOOO");
  // movable games
  StrictCoinGameModel game6 = new StrictCoinGameModel("-O-O-");
  StrictCoinGameModel game7 = new StrictCoinGameModel("O-O--");

  //Adapts the games
  String[] n ={"Andrew","Beco","Dick" ,"Bob" , "Sally", "Chris"};
  NewCoinGameModel newGame1 = CoinGameModelAdaptor.fromString(game1.getBoard(), n);
  NewCoinGameModel newGame2 = CoinGameModelAdaptor.fromString(game2.getBoard(), n);
  NewCoinGameModel newGame3 = CoinGameModelAdaptor.fromString(game3.getBoard(), n);
  NewCoinGameModel newGame4 = CoinGameModelAdaptor.fromString(game4.getBoard(), n);
  NewCoinGameModel newGame5 = CoinGameModelAdaptor.fromString(game5.getBoard(), n);
  NewCoinGameModel newGame6 = CoinGameModelAdaptor.fromString(game6.getBoard(), n);
  NewCoinGameModel newGame7 = CoinGameModelAdaptor.fromString(game7.getBoard(), n);

  // New Coin Game Tests

  @Test
  public void testNewGetCoinPosition(){
    int [] a = new int[1];
    assertEquals(a[0], newGame1.getCoinPositions()[0]);
    assertEquals(a[0], newGame2.getCoinPositions()[0]);
    int [] b = new int [5]; b[0] = 1; b[1] = 3;
    assertEquals(b[0], newGame6.getCoinPositions()[0]);
    assertEquals(b[1], newGame6.getCoinPositions()[1]);
    int [] c = new int [5]; c[0] = 0; c[1] = 2;
    assertEquals(c[0], newGame7.getCoinPositions()[0]);
    assertEquals(c[1], newGame7.getCoinPositions()[1]);
  }

  @Test
  public void testGetPlayerOrder(){
    assertEquals("Beco",newGame1.getPlayOrder()[1].getName());
    assertEquals("Dick",newGame1.getPlayOrder()[2].getName());
    newGame1.addPlayerAfter(newGame1.getPlayOrder()[1], "Jesse");
    assertEquals("Dick", newGame1.getPlayOrder()[3].getName());
  }

  @Test
  public void testGetWinner(){
    assertEquals("Chris", newGame5.getWinner().getName());
    newGame7.getCurrentPlayer().move(1,1);
    assertEquals("Andrew", newGame7.getWinner().getName());
  }

  @Test
  public void testNewGetCurrentPlayer(){
    assertEquals("Andrew", newGame7.getCurrentPlayer().getName());
    newGame7.getCurrentPlayer().move(1, 1);
    assertEquals("Beco", newGame7.getCurrentPlayer().getName());
  }

  @Test
  public void testAddPlayerAfter(){
    assertEquals("Bill", newGame7.addPlayerAfter(newGame7.getPlayOrder()[0], "Bill").getName());
    assertEquals("Andrew", newGame7.getPlayOrder()[0].getName());
    assertEquals("Bill", newGame7.getPlayOrder()[1].getName());
    assertEquals("Beco", newGame7.getPlayOrder()[2].getName());
  }


  //NewPlayerModelTests

  @Test
  public void testNewMove() {
    int[] coinPosition1 = {0,2};
    int[] coinPosition2 = {0,1};
    assertEquals(coinPosition1[1], newGame7.getCoinPositions()[1]);
    assertEquals("Andrew", newGame7.getCurrentPlayer().getName());
    newGame7.getCurrentPlayer().move(1,1);
    assertEquals(coinPosition2[1], newGame7.getCoinPositions()[1]);
    assertEquals("Beco", newGame7.getCurrentPlayer().getName());
  }

  @Test
  public void testNewIsMove() {
    assertEquals(true, newGame7.getCurrentPlayer().isTurn());
    assertEquals(false, newGame7.getPlayOrder()[3].isTurn());
    newGame7.getCurrentPlayer().move(1,1);
    assertEquals(true, newGame7.getCurrentPlayer().isTurn());
  }



  // Old Strict Coin Game Tests:
  @Test
  public void testAddNewPlayer() {
    game5.addPlayer(0, "Bob");
    game5.addPlayer(1, "Sam");
    assertEquals("Bob", game5.getPlayers().get(0));
    assertEquals("Sam", game5.getPlayers().get(1));

    game6.addPlayer(0, "Bob");
    game6.addPlayer(1, "Sam");
    game6.addPlayer(2, "Jess");
    assertEquals("Bob", game6.getPlayers().get(0));
    assertEquals("Sam", game6.getPlayers().get(1));
    assertEquals("Jess", game6.getPlayers().get(2));

    game5.addPlayer(0, "Jess");
    assertEquals("Sam", game5.getPlayers().get(2));
  }


  @Test
  public void testNextTurn()  {
    game5.addPlayer(0, "Bob");
    game5.addPlayer(1, "Sam");

    assertEquals("Bob", game5.getPlayers().get(0));

    game5.nextTurn();
    assertEquals("Sam", game5.getPlayers().get(1));

    game5.nextTurn();
    assertEquals("Bob", game5.getPlayers().get(0));
  }

  @Test
  public void testGetCurrentPlayer()  {
    game4.addPlayer(0, "Bob");
    game4.addPlayer(1, "Sam");
    game4.nextTurn();
    assertEquals("Sam", game4.getCurrentPlayer());

    game6.addPlayer(0, "Bob");
    game6.addPlayer(1, "Sam");
    game6.addPlayer(2, "Jess");
    game6.nextTurn();
    game6.nextTurn();
    assertEquals("Jess", game6.getCurrentPlayer());
  }


  @Test
  public void testPlayerWon() {
    game5.addPlayer(0, "Bob");
    game5.addPlayer(1, "Sam");

    assertEquals("Sam", game5.playerWon());

    game4.addPlayer(0, "Bob");
    game4.addPlayer(1, "Sam");
    game4.addPlayer(2, "Jess");
    assertEquals("Jess", game4.playerWon());

  }

  @Test
  public void testToString(){
    assertEquals("-O-O-", game6.toString());
    assertEquals("OOOO", game5.toString());
  }


  @Test
  public void testGetCoinPosition() {
    assertEquals(0, game3.getCoinPosition(0));
    assertEquals(2, game5.getCoinPosition(2));
    assertEquals(1, game6.getCoinPosition(0));
  }


  @Test
  public void testMove() {
    game6.addPlayer(0, "Bob");

    game6.move(0, 0);
    assertEquals("O--O-", game6.toString());
    game6.move(1, 1);
    assertEquals("OO---", game6.toString());

    game7.addPlayer(0, "Bob");
    game7.move(1, 1);
    assertEquals("OO---", game7.toString());

  }

  @Test
  public void testIsGameOver() {
    assertEquals(game1.isGameOver(), true);
    assertEquals(game2.isGameOver(), true);
    assertEquals(game3.isGameOver(), true);
    assertEquals(game4.isGameOver(), true);
    assertEquals(game5.isGameOver(), true);
    assertEquals(game6.isGameOver(), false);
  }


  @Test (expected = IllegalArgumentException.class)
  public void testAddNewPlayerException() {
    game3.addPlayer(0, "Bob");
    game3.addPlayer(0, "Bob");
  }


  public void testPlayerWonNoWin() {
    assertEquals(game6.playerWon(), "No one has won yet");
  }

  @Test (expected = IllegalArgumentException.class)
  public void testIllegalConstructor1() {
    StrictCoinGameModel game0 = new StrictCoinGameModel("");
  }

  @Test (expected = IllegalArgumentException.class)
  public void testIllegalConstructor2() {
    StrictCoinGameModel game0 = new StrictCoinGameModel("safsafasf");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBadCoinIndex(){
    game2.getCoinPosition(10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBadPlayerInsertion(){
    game2.addPlayer(-1, "a");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBadPlayerInsertion2(){
    game2.addPlayer(100, "a");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBadPlayerInsertion3(){
    game2.addPlayer(1, "");
  }

  @Test(expected = CoinGameModel.IllegalMoveException.class)
  public void testBadMove(){
    game1.move(1, 1); //Game is already over
  }

  @Test(expected = CoinGameModel.IllegalMoveException.class)
  public void testBadMove2(){
    game5.move(2, 0);
  }
  @Test(expected = CoinGameModel.IllegalMoveException.class)
  public void testBadMove3(){
    game5.move(0,-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBadAddPlayerAfter() {
    newGame2.addPlayerAfter(new StrictCoinGamePlayer("brian", game2), "fail");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBadAddPlayerAfter2() {
    newGame2.addPlayerAfter(new StrictCoinGamePlayer("Andrew", game2), null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateNewPlayer() {
    CoinGamePlayer y = new StrictCoinGamePlayer("Andrew", null);

  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateNewPlayer2() {
    CoinGamePlayer y = new StrictCoinGamePlayer("", game2);

  }

}