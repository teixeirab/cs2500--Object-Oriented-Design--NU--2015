package connect_n.javafx_ui;

import connect_n.javafx_ui.controls.NumericEntry;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import static connect_n.javafx_ui.Constants.*;

final class ConfigView {
  private final VBox root;

  private final NumericEntry rowsEntry;
  private final NumericEntry colsEntry;
  private final NumericEntry goalEntry;
  private final Button playButton;

  private Stage stage;

  public ConfigView() {
    Label title = new Label("Connect N");
    title.setFont(Font.font(2 * Font.getDefault().getSize()));

    colsEntry = new NumericEntry(CONFIG_COLS_MIN, CONFIG_COLS_MAX);
    rowsEntry = new NumericEntry(CONFIG_ROWS_MIN, CONFIG_ROWS_MAX);
    goalEntry = new NumericEntry(CONFIG_GOAL_MIN, CONFIG_GOAL_MAX);

    GridPane table = new GridPane();
    table.setHgap(12);
    table.setVgap(6);

    table.addRow(0, new Label("Grid rows:"), rowsEntry);
    table.addRow(1, new Label("Grid columns:"), colsEntry);
    table.addRow(2, new Label("Goal line length:"), goalEntry);

    playButton = new Button("Play");
    playButton.setDefaultButton(true);
    playButton.setPadding(new Insets(6));
    playButton.disableProperty().bind(
        rowsEntry.isValidProperty()
            .and(colsEntry.isValidProperty())
            .and(goalEntry.isValidProperty())
            .not());

    HBox titleBox = new HBox(title);
    titleBox.setAlignment(Pos.BASELINE_CENTER);

    HBox tableBox = new HBox(table);
    tableBox.setAlignment(Pos.BASELINE_CENTER);

    HBox playBox = new HBox(playButton);
    playBox.setAlignment(Pos.BASELINE_RIGHT);

    root = new VBox(24, titleBox, tableBox, playBox);
    root.setPadding(new Insets(20));
  }

  Node root() {
    return root;
  }

  Stage stage() {
    if (stage == null) {
      stage = new Stage(StageStyle.UNDECORATED);
      stage.setScene(new Scene(new Group(root)));
    }

    return stage;
  }

  Integer getRows() {
    return rowsEntry.getValue();
  }

  Integer getColumns() {
    return colsEntry.getValue();
  }

  Integer getGoal() {
    return goalEntry.getValue();
  }

  public void setRows(int rows) {
    rowsEntry.setValue(rows);
  }

  public void setColumns(int cols) {
    colsEntry.setValue(cols);
  }

  public void setGoal(int goal) {
    goalEntry.setValue(goal);
  }

  EventHandler<ActionEvent> getOnSubmit() {
    return playButton.getOnAction();
  }

  void setOnSubmit(EventHandler<ActionEvent> handler) {
    playButton.setOnAction(handler);
  }
}
