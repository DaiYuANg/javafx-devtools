package org.javafx.devtools.context;

import io.avaje.inject.BeanScope;
import org.jetbrains.annotations.NotNull;

public enum DIContext {
  INSTANCE;

  private final BeanScope beanScope = BeanScope.builder().build();

  public <T> @NotNull T get(Class<T> clazz) {
    return beanScope.get(clazz);
  }
}
