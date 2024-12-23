package org.javafx.devtools;

import javafx.application.Application;
import lombok.extern.slf4j.Slf4j;
import org.scenicview.ScenicView;


@Slf4j
public class DevtoolsApplication {
  public static void main(String[] args) {
    Application.launch(ScenicView.class, args);
  }
}
