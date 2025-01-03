package org.javafx.devtools.controller;

import jakarta.inject.Singleton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Slf4j
public class MainLayoutController implements Initializable {
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    log.info("MainLayoutController initialized");
  }
}
