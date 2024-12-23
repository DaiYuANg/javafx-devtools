package org.javafx.devtools.controller;

import jakarta.inject.Singleton;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.javafx.devtools.constant.ViewConstant;
import org.javafx.devtools.util.FXMLHelper;

import java.net.URL;
import java.util.ResourceBundle;

@Singleton
@Slf4j
@RequiredArgsConstructor
public class GlobalMenuController implements Initializable {

  private final FXMLHelper fxmlHelper;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

  }

  public void openAbout() {
    val stage = new Stage();
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.initStyle(StageStyle.UTILITY);
    val about = fxmlHelper.loadView(ViewConstant.ABOUT_DIALOG, VBox.class);
    stage.setScene(new Scene(about));
    stage.show();
  }
}
