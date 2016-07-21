package connect_n.javafx_ui;

import connect_n.model.Player;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import connect_n.util.Action;

final class GridView {
  private final CellView[][] cells;
  private final Pane[] columns;
  private final Pane root;

  private Stage stage;

  private Action pulsationCanceler = Action.NOTHING;

  Player turn = Player.Red;

  GridView(int width, int height) {
    cells = new CellView[width][height];
    columns = new Pane[width];
    root = new HBox();

    root.setBackground(Constants.GRID_BACKGROUND);
    root.setBorder(Constants.GRID_BORDER);

    for (int x = 0; x < width; ++x) {
      Pane column = new VBox();
      columns[x] = column;

      for (int y = height - 1; y >= 0; --y) {
        CellView c = new CellView();

        cells[x][y] = c;
        column.getChildren().add(c.root());
      }
    }

    root.getChildren().addAll(columns);
  }

  Stage stage() {
    if (stage == null) {
      stage = new Stage();
      stage.setScene(new Scene(new Group(root)));
    }

    return stage;
  }

  Node root() {
    return root;
  }

  Node cellTarget(int column, int row) {
    return cells[column][row].target();
  }

  Node columnTarget(int column) {
    return columns[column];
  }

  void setCell(int column, int row, Player newState) {
    stopPulsation();
    cells[column][row].setState(newState);
  }

  void pulsateOneCell(int column, int row, Player newState) {
    stopPulsation();
    pulsationCanceler = cells[column][row].pulsate(newState);
  }

  void pulsateAdditionalCell(int column, int row, Player player) {
    Action next = cells[column][row].pulsate(player);
    pulsationCanceler = pulsationCanceler.andThen(next);
  }

  void stopPulsation() {
    pulsationCanceler.execute();
    pulsationCanceler = Action.NOTHING;
  }

  void columnError(int column) {
    for (CellView cell : cells[column]) {
      cell.error();
    }
  }
}
