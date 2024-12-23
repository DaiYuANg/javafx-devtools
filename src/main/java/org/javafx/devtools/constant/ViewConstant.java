package org.javafx.devtools.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ViewConstant {

  MAIN_LAYOUT("MainLayout"),

  ABOUT_DIALOG("dialog/About");

  private final String viewName;
}
