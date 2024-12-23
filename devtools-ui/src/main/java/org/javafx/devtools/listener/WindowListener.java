package org.javafx.devtools.listener;

import jakarta.inject.Singleton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.stage.Window;
import lombok.extern.slf4j.Slf4j;
import org.javafx.devtools.constant.ConfigKey;
import org.javafx.devtools.service.ConfigService;
import org.jetbrains.annotations.NotNull;


@Singleton
@Slf4j
public class WindowListener implements ChangeListener<Window> {

  private final ChangeListener<Number> widthListener;

  private final ChangeListener<Number> heightListener;

  public WindowListener(ConfigService configService) {
    widthListener = (observable, oldValue, newValue) -> {
      configService.set(ConfigKey.STAGE_WIDTH, newValue);
    };
    heightListener = (observable, oldValue, newValue) -> {
      configService.set(ConfigKey.STAGE_HEIGHT, newValue);
    };
  }

  @Override
  public void changed(
    ObservableValue<? extends Window> observable,
    Window oldValue,
    @NotNull Window newValue
  ) {
    newValue.widthProperty().addListener(widthListener);
    newValue.heightProperty().addListener(heightListener);
  }
}
