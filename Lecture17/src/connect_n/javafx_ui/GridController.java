package connect_n.javafx_ui;

import connect_n.model.Model;
import connect_n.model.Player;
import connect_n.model.Position;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

import java.util.Collection;
import java.util.function.Consumer;

final class GridController {
  private final Model model;
  private final GridView view;
  private final Consumer<Player> onDone;

  GridController(Model model, Consumer<Player> onDone) {
    this.model = model;
    this.onDone = onDone;
    this.view = new GridView(model.getWidth(), model.getHeight());

    for (int i = 0; i < model.getWidth(); ++i) {
      final int column = i;
      Node target = view.columnTarget(column);

      target.setCursor(Cursor.HAND);

      target.setOnMouseClicked(event -> tryMove(column, target, event));
      target.setOnMouseEntered(event -> pulsateNextMove(column));
      target.setOnMouseExited(event -> stopPulsation());
    }
  }

  GridView view() {
    return view;
  }

  private void tryMove(int column, Node columnNode, MouseEvent event) {
    if (model.isGameOver() || model.isColumnFull(column)) {
      view.columnError(column);
      return;
    }

    Player who = model.getNextPlayer();
    int row = model.move(who, column);
    view.setCell(column, row, who);

    if (model.isColumnFull(column)) {
      columnNode.setCursor(Cursor.DEFAULT);
    }

    if (model.isGameOver()) {
      String message;

      switch (model.getStatus()) {
        case Stalemate:
          onDone.accept(null);
          break;

        case Won:
          Player winner = model.getWinner();
          pulsateCells(model.getWinningPositions(), winner);
          onDone.accept(winner);
          break;

        default:
          throw new RuntimeException("should be impossible");
      }
    } else {
      columnNode.getOnMouseEntered().handle(event);
    }
  }

  private void pulsateCells(Collection<Position> positions,
                            Player player)
  {
    for (Position position : positions) {
      view.pulsateAdditionalCell(position.x(), position.y(), player);
    }
  }

  private void pulsateNextMove(int column) {
    if (model.isColumnFull(column) || model.isGameOver()) {
      return;
    }

    Player who = model.getNextPlayer();
    view.pulsateOneCell(column, model.getColumnSize(column), who);
  }

  private void stopPulsation() {
    if (!model.isGameOver()) {
      view.stopPulsation();
    }
  }
}
