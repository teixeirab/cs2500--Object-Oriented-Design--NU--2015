package connect_n.javafx_ui;

import connect_n.model.Model;

import java.util.Objects;
import java.util.function.Consumer;

final class ConfigController {
  private final ConfigView view;
  private final Consumer<Model.Builder> onDone;

  ConfigController(Consumer<Model.Builder> onDone) {
    Objects.requireNonNull(onDone);
    this.onDone = onDone;

    view = new ConfigView();
    view.setColumns(Model.DEFAULT_WIDTH);
    view.setRows(Model.DEFAULT_HEIGHT);
    view.setGoal(Model.DEFAULT_GOAL);

    view.setOnSubmit(event -> {
      Model.Builder builder =
          Model.builder()
              .width(view.getColumns())
              .height(view.getRows())
              .goal(view.getGoal());

      onDone.accept(builder);
    });
  }

  ConfigView view() {
    return view;
  }
}
