package connect_n.javafx_ui;

import connect_n.model.Model;
import connect_n.model.Player;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public final class MainController extends Application {
  public static void main(String args[]) {
    launch(args);
  }

  private Stage configStage;
  private Stage gridStage;

  @Override
  public void start(Stage primaryStage) {
    configStage = primaryStage;
    gridStage = new Stage();

    ConfigController config = new ConfigController(this::onConfigDone);
    Scene scene = new Scene(new Group(config.view().root()));

    scene.setOnKeyPressed(keyEvent -> {
      if (keyEvent.getCode() == KeyCode.ESCAPE) {
        Platform.exit();
      }
    });

    configStage.initStyle(StageStyle.UNDECORATED);
    configStage.setTitle("CS 3500 Connect N");
    configStage.setScene(scene);
    configStage.show();
  }

  private void onConfigDone(Model.Builder builder) {
    Model model = builder.build();
    GridController grid = new GridController(model, this::onGridDone);
    Scene scene = new Scene(new Group(grid.view().root()));

    scene.setOnKeyPressed(keyEvent -> {
      if (keyEvent.getCode() == KeyCode.ESCAPE) {
        configStage.show();
        gridStage.hide();
      }
    });

    gridStage.setTitle("Connect " + model.getGoal());
    gridStage.setScene(scene);
    gridStage.show();
    configStage.hide();
  }

  private void onGridDone(Player winner) {
    String message = winner != null
                     ? winner + " player wins!"
                     : "Stalemate! No one wins :/";

    OkCancelView okCancel = new OkCancelView(message);
    okCancel.setOkayText("Play again");
    okCancel.setCancelText("Quit");

    if (okCancel.ask(gridStage)) {
      configStage.show();
      gridStage.close();
    } else {
      Platform.exit();
    }
  }
}
