package connect_n.javafx_ui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

/**
 *
 */
final class OkCancelView {
  private final Stage stage;
  private final Button okay;
  private final Button cancel;

  private boolean result;

  OkCancelView(String message) {
    stage = new Stage(StageStyle.UNDECORATED);

    Text text = new Text(message);

    okay = new Button("OK");
    okay.setDefaultButton(true);
    okay.setPadding(new Insets(6));
    okay.setOnAction(event -> {
      result = true;
      stage.hide();
    });

    cancel = new Button("Cancel");
    cancel.setCancelButton(true);
    cancel.setPadding(new Insets(6));
    cancel.setOnAction(event -> {
      result = false;
      stage.hide();
    });

    HBox buttons = new HBox(cancel, okay);
    buttons.setSpacing(10);

    VBox root = new VBox(text, buttons);
    root.setSpacing(10);
    root.setPadding(new Insets(20));

    stage.initModality(Modality.APPLICATION_MODAL);
    stage.setScene(new Scene(root));
  }

  void setOkayText(String text) {
    okay.setText(text);
  }

  void setCancelText(String text) {
    cancel.setText(text);
  }

  String getOkayText() {
    return okay.getText();
  }

  String getCancelText() {
    return cancel.getText();
  }

  boolean ask() {
    return ask(null);
  }

  boolean ask(Window parent) {
    stage.initOwner(parent);
    if (parent != null) {
      stage.initModality(Modality.WINDOW_MODAL);
    } else {
      stage.initModality(Modality.APPLICATION_MODAL);
    }

    result = false;
    stage.showAndWait();
    return result;
  }
}
