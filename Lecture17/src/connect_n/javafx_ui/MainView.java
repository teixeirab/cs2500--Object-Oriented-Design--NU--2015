package connect_n.javafx_ui;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;

final class MainView {
  private final Parent root;
  private final Scene scene;

  MainView(GridView gridView) {
    root = new Group(gridView.root());
    scene = new Scene(root);
  }
}