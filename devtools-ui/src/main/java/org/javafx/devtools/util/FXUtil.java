package org.javafx.devtools.util;

import java.util.concurrent.CompletableFuture;
import javafx.application.Platform;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FXUtil {

  public void asyncUpdateUI(Runnable runnable) {
    CompletableFuture.runAsync(() -> Platform.runLater(runnable));
  }
}
