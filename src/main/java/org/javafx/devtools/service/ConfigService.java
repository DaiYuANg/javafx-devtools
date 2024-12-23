package org.javafx.devtools.service;

import jakarta.inject.Singleton;
import java.io.*;
import java.util.Optional;
import java.util.prefs.Preferences;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.SerializationUtils;
import org.javafx.devtools.constant.ConfigKey;
import org.jetbrains.annotations.NotNull;

@Singleton
@Slf4j
@RequiredArgsConstructor
public class ConfigService {

  private final Preferences preferences;

  @SneakyThrows
  public <T extends Serializable> void set(@NotNull ConfigKey key, T value) {
    val serialized = SerializationUtils.serialize(value);
    preferences.putByteArray(key.getKey(), serialized);
  }

  @SneakyThrows
  public <T> Optional<T> get(@NotNull ConfigKey key, @NotNull Class<T> clazz) {
    val data = preferences.getByteArray(key.getKey(), new byte[0]);
    val result = SerializationUtils.deserialize(data);
    if (clazz.isInstance(result)) {
      return Optional.of(clazz.cast(result));
    }
    return Optional.empty();
  }
}
