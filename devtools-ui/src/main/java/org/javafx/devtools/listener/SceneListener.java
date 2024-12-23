package org.javafx.devtools.listener;

import jakarta.inject.Singleton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;


@Singleton
@Slf4j
@RequiredArgsConstructor
public class SceneListener implements ChangeListener<Scene> {

  private final WindowListener windowListener;

  @Override
  public void changed(
    ObservableValue<? extends Scene> observable,
    Scene oldValue, @NotNull Scene newValue) {
    newValue.windowProperty().addListener(windowListener);
  }
}
