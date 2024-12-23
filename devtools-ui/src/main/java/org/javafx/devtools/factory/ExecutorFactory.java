package org.javafx.devtools.factory;

import io.avaje.inject.Bean;
import io.avaje.inject.Factory;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import lombok.extern.slf4j.Slf4j;

@Factory
@Slf4j
public class ExecutorFactory {

  @Bean
  ScheduledExecutorService scheduledExecutorService() {
    return Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
  }
}
