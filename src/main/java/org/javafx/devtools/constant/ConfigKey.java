package org.javafx.devtools.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ConfigKey {

  AUTO_REFRESH_STYLESHEETS("autoRefreshStyleSheets"),

  STAGE_WIDTH("stageWidth"),
  STAGE_HEIGHT("stageHeight");

  private final String key;
}
