package org.javafx.devtools.factory;

import io.avaje.inject.Bean;
import io.avaje.inject.Factory;
import java.util.prefs.Preferences;
import lombok.extern.slf4j.Slf4j;
import org.javafx.devtools.DevtoolsApplication;

@Factory
@Slf4j
public class PropertiesFactory {

  @Bean
  Preferences preferences() {
    return Preferences.userNodeForPackage(DevtoolsApplication.class);
  }
}
