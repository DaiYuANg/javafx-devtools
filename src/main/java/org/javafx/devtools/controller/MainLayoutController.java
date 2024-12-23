package org.javafx.devtools.controller;

import jakarta.inject.Singleton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javafx.devtools.listener.SceneListener;

@Singleton
@Slf4j
@RequiredArgsConstructor
public class MainLayoutController implements Initializable {

  @FXML
  private VBox root;

  private final SceneListener sceneListener;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    log.info("MainLayoutController initialized");
    root.sceneProperty().addListener(sceneListener);
  }
}
