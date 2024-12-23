package org.javafx.devtools.factory;

import com.jthemedetecor.OsThemeDetector;
import io.avaje.inject.Bean;
import io.avaje.inject.Factory;
import io.avaje.inject.Lazy;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.javafx.devtools.constant.ViewConstant;
import org.javafx.devtools.util.FXMLHelper;

@Factory
@Lazy
@RequiredArgsConstructor
@Slf4j
public class UIFactory {

  private final FXMLHelper fxmlHelper;

  @Bean
  VBox mainLayout() {
    return fxmlHelper.loadView(ViewConstant.MAIN_LAYOUT, VBox.class);
  }

  @Bean
  Scene scene(VBox mainLayout) {
    val scene = new Scene(mainLayout);
    scene.setOnMouseDragged(event -> {
      log.info("Drag detected");
    });
    return scene;
  }

  @Bean
  OsThemeDetector themeDetector() {
    return OsThemeDetector.getDetector();
  }
}
