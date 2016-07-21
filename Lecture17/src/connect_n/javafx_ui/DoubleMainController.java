package connect_n.javafx_ui;

import connect_n.model.Model;
import connect_n.model.Player;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public final class DoubleMainController extends Application {
  public static void main(String args[]) {
    launch(args);
  }

  private Stage configStage;
  private Stage gridStage;

  private GridDone[] gridsDone;

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
    gridsDone = new GridDone[2];
    gridsDone[0] = new GridDone();
    gridsDone[1] = new GridDone();

    Model model0 = builder.build();
    Model model1 = builder.build();

    GridController grid0 = new GridController(model0, gridsDone[0]::onDone);
    GridController grid1 = new GridController(model1, gridsDone[1]::onDone);

    Scene scene = new Scene(new HBox(grid0.view().root(),
                                     grid1.view().root()));

    scene.setOnKeyPressed(keyEvent -> {
      if (keyEvent.getCode() == KeyCode.ESCAPE) {
        configStage.show();
        gridStage.hide();
      }
    });

    gridStage.setTitle("Double Connect " + model0.getGoal());
    gridStage.setScene(scene);
    gridStage.show();
    configStage.hide();
  }

  final class GridDone {
    boolean isDone;
    Player winner;

    void onDone(Player winner) {
      this.winner = winner;
      this.isDone = true;

      GridDone grid0 = gridsDone[0];
      GridDone grid1 = gridsDone[1];

      if (grid0.isDone && grid1.isDone) {
        String message;

        if (grid0.winner == null && grid1.winner == null) {
          message = "Stalemate! No one wins :/";
        } else if (grid0.winner == grid1.winner) {
          message = grid0.winner + " player wins big!";
        } else if (grid0.winner == null) {
          message = grid1.winner + " player wins!";
        } else if (grid1.winner == null) {
          message = grid0.winner + " player wins!";
        } else {
          message = "It's a tie!";
        }

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
  }
}
