package connect_n.javafx_ui;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

final class Constants {
  private Constants() { } // prevents instantiation

  public static final int CONFIG_ROWS_MIN = 2;
  public static final int CONFIG_COLS_MIN = 2;
  public static final int CONFIG_GOAL_MIN = 2;
  public static final int CONFIG_ROWS_MAX = 12;
  public static final int CONFIG_COLS_MAX = 14;
  public static final int CONFIG_GOAL_MAX = 8;

  public static final Background GRID_BACKGROUND =
      new Background(new BackgroundFill(Color.GOLDENROD, null, null));

  public static final int GRID_BORDER_WIDTH = 4;

  public static Border GRID_BORDER =
      new Border(new BorderStroke(Color.DARKGOLDENROD,
                                  BorderStrokeStyle.SOLID,
                                  CornerRadii.EMPTY,
                                  new BorderWidths(GRID_BORDER_WIDTH)));

  public static final int CELL_SIZE    = 60;

  public static final int CELL_PADDING = 4;

  public static final Color CELL_EMPTY_COLOR = Color.BLACK;

  public static final Color CELL_RED_COLOR   = Color.RED;

  public static final Color CELL_WHITE_COLOR = Color.WHITE;

  public static final Duration CELL_HOVER_PERIOD = Duration.millis(400);

  public static final double CELL_HOVER_DARKEN = 0.8;

  public static final Duration CELL_ERROR_ANIMATION_DURATION
      = Duration.millis(100);

  public static final int CELL_ERROR_ANIMATION_OFFSET = 4;
}
