package org.javafx.devtools.util;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import io.vavr.control.Try;
import jakarta.inject.Singleton;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.util.Pair;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javafx.devtools.DevtoolsApplication;
import org.javafx.devtools.constant.ViewConstant;
import org.javafx.devtools.context.DIContext;
import org.jetbrains.annotations.NotNull;

@Singleton
@Slf4j
@RequiredArgsConstructor
public class FXMLHelper {

  private final String fxmlSuffix = ".fxml";

  private final FXMLLoadListener loadListener;

  private final Map<ViewConstant, Pair<Parent, Object>> fxmlCache = Maps.newHashMap();

  public <T extends Parent> T loadView(@NotNull ViewConstant prefix, @NotNull Class<T> type) {
    return Try.of(() -> fxmlCache.computeIfAbsent(prefix, this::loadFromDisk))
      .mapTry(Pair::getKey)
      .andThenTry(view -> Preconditions.checkArgument(type.isInstance(view), "FXML cache contains a mismatched type."))
      .map(type::cast)
      .onFailure(throwable -> log.atError().log(throwable.getMessage(), throwable))
      .get();
  }

  public <T extends Parent> T loadController(@NotNull ViewConstant prefix, @NotNull Class<T> type) {
    return Try.of(() -> fxmlCache.computeIfAbsent(prefix, this::loadFromDisk))
      .mapTry(Pair::getValue)
      .andThenTry(view -> Preconditions.checkArgument(type.isInstance(view), "Controller cache contains a mismatched type."))
      .map(type::cast)
      .get();
  }

  public <T extends Parent, C> Pair<T, C> load(@NotNull ViewConstant prefix, @NotNull Class<T> type, Class<C> clazz) {
    return Try.of(() -> fxmlCache.computeIfAbsent(prefix, this::loadFromDisk))
      .andThenTry(view -> {
        Preconditions.checkArgument(type.isInstance(view.getKey()), "FXML cache contains a mismatched type.");
        Preconditions.checkArgument(clazz.isInstance(view.getValue()), "Controller cache contains a mismatched type.");
      })
      .map(obj -> new Pair<>(type.cast(obj.getKey()), clazz.cast(obj.getValue())))
      .get();
  }

  private <T extends Parent, C> Pair<T, C> loadFromDisk(@NotNull ViewConstant prefix) {
    return Try.of(() -> DevtoolsApplication.class.getResource(prefix.getViewName() + fxmlSuffix))
      .mapTry(FXMLLoader::new)
      .andThenTry(this::loaderSetup)
      .<Pair<T, C>>mapTry(fxmlLoader -> new Pair<>(fxmlLoader.load(), fxmlLoader.getController()))
      .onFailure(t -> log.atError().log(t.getMessage(), t))
      .get();
  }

  private void loaderSetup(@NotNull FXMLLoader loader) {
    loader.setControllerFactory(DIContext.INSTANCE::get);
    loader.setLoadListener(loadListener);
    loader.setCharset(StandardCharsets.UTF_8);
  }
}