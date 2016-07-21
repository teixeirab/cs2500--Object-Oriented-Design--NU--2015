package Jesse_model;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ModelImplTest {

  ModelImpl model = new ModelImpl();

  @Test
  public void testInitialBoard(){
    assertEquals(Player.First, model.pieces.get(0).player());
    assertEquals(Player.First, model.pieces.get(1).player());
    assertEquals(Player.First, model.pieces.get(2).player());
    assertEquals(Player.First, model.pieces.get(3).player());
    assertEquals(Player.First, model.pieces.get(4).player());
    assertEquals(Player.First, model.pieces.get(5).player());
    assertEquals(Player.First, model.pieces.get(6).player());
    assertEquals(Player.First, model.pieces.get(7).player());
    assertEquals(Player.First, model.pieces.get(8).player());
    assertEquals(Player.First, model.pieces.get(9).player());
    assertEquals(Player.First, model.pieces.get(10).player());
    assertEquals(Player.First, model.pieces.get(11).player());
    assertEquals(Player.Second, model.pieces.get(20).player());
    assertEquals(Player.Second, model.pieces.get(22).player());
  }

  @Test
  public void testGetPlayer() throws Exception {
    assertEquals(Player.First, model.getPlayer());
  }

  @Test
  public void testGetWinner() throws Exception {

  }

  @Test
  public void testGetPieceAt() throws Exception {
    assertEquals(Piece.NormalFirst, model.getPieceAt(1,0));
    assertEquals(Piece.NormalSecond, model.getPieceAt(1,6));
    assertEquals(Piece.NormalSecond, model.getPieceAt(3,6));
  }

  @Test
  public void testMove() throws Exception {
    assertEquals(Player.Second, model.pieces.get(20).player());
    model.move(8, 13);
    assertEquals(Player.Second, model.pieces.get(21).player());
    model.move(20, 16);
    assertEquals(Player.Second, model.pieces.get(16).player());
    model.move(13,20);

    assertEquals(Player.Empty, model.pieces.get(8).player());
    assertEquals(Player.Empty, model.pieces.get(13).player());
    assertEquals(Player.Empty, model.pieces.get(16).player());
    assertEquals(Player.First, model.pieces.get(20).player());

  }

  @Test
  public void testGetValidMovePositions() throws Exception {
    ArrayList<Position> temp = new ArrayList<Position>();
    temp.add(Position.fromIndex(8));
    temp.add(Position.fromIndex(9));
    temp.add(Position.fromIndex(10));
    temp.add(Position.fromIndex(11));



   assertEquals(temp, model.getValidMovePositions());
  }

  @Test
  public void testGetValidDestinations() throws Exception {
    ArrayList<Position> temp = new ArrayList<Position>();
    temp.add(Position.fromIndex(13));
    temp.add(Position.fromIndex(12));
    //assertEquals(temp.get(0).index() , model.getValidDestinations(Position.fromIndex(8), 1).get(0).index());
  }

  @Test
  public void testGetCurrentPositions() throws Exception {
    ArrayList<Position> temp = new ArrayList<Position>();
    temp.add(Position.fromIndex(8));
    temp.add(Position.fromIndex(9));
    temp.add(Position.fromIndex(10));
    temp.add(Position.fromIndex(11));

    assertEquals(8, model.getCurrentPositions().get(8).index());
  }

  @Test
  public void testGetStatus() throws Exception {
    assertEquals(Model.Status.Playing, model.getStatus());
  }

  @Test
  public void testIsGameOver() throws Exception {
    assertEquals(false, model.isGameOver());
  }
}