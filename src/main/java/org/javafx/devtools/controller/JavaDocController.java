package org.javafx.devtools.controller;

import jakarta.inject.Singleton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javafx.devtools.component.ProgressWebView;

import java.net.URL;
import java.util.ResourceBundle;

@Singleton
@RequiredArgsConstructor
@Slf4j
public class JavaDocController implements Initializable {

  @FXML
  private ProgressWebView webview;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    webview.doLoad("https://openjfx.io/javadoc/21/index.html");
  }
}
