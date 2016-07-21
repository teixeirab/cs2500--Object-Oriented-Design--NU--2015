package connect_n.javafx_ui;

import connect_n.model.Player;
import javafx.animation.Animation;
import javafx.animation.FillTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import connect_n.util.Action;

import static connect_n.javafx_ui.Constants.*;

final class CellView {
  private final Pane root;
  private final Circle token;

  private Player state;
  private Animation errorAnimation = null;

  CellView() {
    token = new Circle(CELL_SIZE / 2 - CELL_PADDING);
    token.setCenterX(CELL_SIZE / 2);
    token.setCenterY(CELL_SIZE / 2);

    root = new Pane(token);
    root.setPadding(new Insets(CELL_PADDING));

    setState(null);
  }

  Node root() {
    return root;
  }

  Node target() {
    return token;
  }

  void setState(Player newState) {
    state = newState;
    refresh();
  }

  private void refresh() {
    token.setFill(stateToColor(state));
  }

  Action pulsate(Player player) {
    Color brighter = stateToColor(player);
    Color darker   = brighter.deriveColor(0, 1.0, CELL_HOVER_DARKEN, 1.0);

    FillTransition ft = new FillTransition(CELL_HOVER_PERIOD, token);

    ft.setCycleCount(Timeline.INDEFINITE);
    ft.setAutoReverse(true);
    ft.setFromValue(brighter);
    ft.setToValue(darker);
    ft.play();

    return () -> {
      ft.stop();
      refresh();
    };
  }

  void error() {
    if (errorAnimation == null) {
      TranslateTransition ft =
          new TranslateTransition(CELL_ERROR_ANIMATION_DURATION, token);
      errorAnimation = ft;

      ft.setByY(-CELL_ERROR_ANIMATION_OFFSET);

      // The next two lines restore the initial position:
      ft.setCycleCount(2);
      ft.setAutoReverse(true);

      ft.setOnFinished(event -> errorAnimation = null);
      ft.play();
    }
  }

  private static Color stateToColor(Player state) {
    if (state == null) {
      return CELL_EMPTY_COLOR;
    }

    switch (state) {
      case White:
        return CELL_WHITE_COLOR;

      case Red:
        return CELL_RED_COLOR;

      default:
        throw new RuntimeException("should be impossible");
    }
  }
}
