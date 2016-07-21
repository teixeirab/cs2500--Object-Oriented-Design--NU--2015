package connect_n.javafx_ui;

import connect_n.model.Model;
import connect_n.model.Player;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

// Doesn't work yet
/*
public final class MultiController extends Application {
  public static void main(String args[]) {
    launch(args);
  }

  private Stage configStage;

  @Override
  public void start(Stage primaryStage) {
    configStage = primaryStage;

    ConfigController config = new ConfigController(this::onConfigDone);
    Scene scene = new Scene(new Group(config.view().root()));

    scene.setOnKeyPressed(keyEvent -> {
      if (keyEvent.getCode() == KeyCode.ESCAPE) {
        Platform.exit();
      }
    });

    configStage.setTitle("CS 3500 Connect N");
    configStage.setScene(scene);
    configStage.show();
  }

  private void fillStage(Stage stage, Model model) {
    GridController grid = new GridController(model, this::onGridDone);
    Scene scene = new Scene(new Group(grid.view().root()));

    scene.setOnKeyPressed(keyEvent -> {
      if (keyEvent.getCode() == KeyCode.ESCAPE) {
        stage.close();
      }
    });

    stage.setScene(scene);
    stage.setScene(scene);
    stage.show();
  }

  private void onConfigDone(Model.Builder builder) {
    Model model = builder.build();

    Stage stage = new Stage();
    stage.setTitle("Connect " + model.getGoal());

    fillStage(stage, model);
  }

  private void onGridDone(Player winner) {
    String message = winner != null
                      ? winner + " player wins!"
                      : "Stalemate! No one wins :/";

    OkCancelView okCancel = new OkCancelView(message);
    okCancel.setOkayText("Play again");
    okCancel.setCancelText("Quit");

    if (okCancel.ask()) {
    } else {
      Platform.exit();
    }
  }
}
*/
