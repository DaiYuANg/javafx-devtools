package org.javafx.devtools.component;

import com.sun.tools.attach.VirtualMachineDescriptor;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.kordamp.ikonli.javafx.FontIcon;

@Slf4j
public class VirtualMachineDescriptorItem extends HBox {

  public VirtualMachineDescriptorItem(@NotNull VirtualMachineDescriptor descriptor) {
    setPadding(new Insets(10, 0, 10, 0));
    getChildren().addAll(
      new FontIcon("si-java"),
      new Separator(Orientation.VERTICAL),
      displayName(descriptor),
      placeholder(),
      idLabel(descriptor)
    );
  }

  @Contract("_ -> new")
  private @NotNull Label displayName(@NotNull VirtualMachineDescriptor descriptor) {
    return new Label(descriptor.displayName());
  }

  @Contract("_ -> new")
  private @NotNull Label idLabel(@NotNull VirtualMachineDescriptor descriptor) {
    return new Label(descriptor.id());
  }

  private @NotNull Region placeholder() {
    val region = new Region();
    HBox.setHgrow(region, Priority.ALWAYS);
    return region;
  }
}
