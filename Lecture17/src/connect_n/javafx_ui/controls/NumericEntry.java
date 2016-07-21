package connect_n.javafx_ui.controls;

import javafx.beans.property.BooleanPropertyBase;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;

/**
 *
 */
public final class NumericEntry extends TextField {
  private final int min;
  private final int max;

  private final String regex;

  private final ValidProperty isValidProperty;

  public NumericEntry(int min, int max) {
    this.min = min;
    this.max = max;

    if (min > max) {
      throw new IllegalArgumentException("min cannot exceed max");
    }

    if (min < 0) {
      regex = "-?[0-9]*";
    } else {
      regex = "[0-9]*";
    }

    isValidProperty = new ValidProperty();

    String promptText = min + "â€“" + max;
    setPromptText(promptText);

    setPrefColumnCount(promptText.length());
    setAlignment(Pos.BASELINE_RIGHT);

    textProperty().addListener((observable, oldValue, newValue) -> {
      if (doNotCheck || newValue == null) {
        return;
      }

      if (newValue.matches(regex)) {
        uncheckedSetText(newValue);
        isValidProperty.update();
      } else {
        uncheckedSetText(oldValue);
      }
    });

    focusedProperty().addListener((observable, oldValue, newValue) -> {
      if (oldValue && ! newValue) {
        forceValid();
      }
    });
  }

  public Integer getValue() {
    try {
      return Integer.parseUnsignedInt(getText());
    } catch (NumberFormatException e) {
      return null;
    }
  }
  public void setValue(int value) {
    setText(Integer.toString(value));
  }

  public void forceValid() {
    Integer value = getValue();

    if (value == null) {
      uncheckedSetText("");
    } else if (value < min) {
      setValue(min);
    } else if (value > max) {
      setValue(max);
    }

    isValidProperty.update();
  }

  public ReadOnlyBooleanProperty isValidProperty() {
    return isValidProperty;
  }

  private boolean doNotCheck = false;

  private void uncheckedSetText(String newText) {
    try {
      doNotCheck = true;
      setText(newText);
    } finally {
      doNotCheck = false;
    }
  }

  private final class ValidProperty extends BooleanPropertyBase {
    void update() {
      Integer value = NumericEntry.this.getValue();
      set(value != null && value >= min && value <= max);
    }

    @Override
    public Object getBean() {
      return NumericEntry.this;
    }

    @Override
    public String getName() {
      return "valid";
    }
  }
}
