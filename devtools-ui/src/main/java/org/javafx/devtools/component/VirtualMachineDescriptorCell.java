package org.javafx.devtools.component;

import com.sun.tools.attach.VirtualMachineDescriptor;
import javafx.scene.control.ListCell;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

@Slf4j
public class VirtualMachineDescriptorCell extends ListCell<VirtualMachineDescriptor> {

  @Override
  protected void updateItem(VirtualMachineDescriptor item, boolean empty) {
    super.updateItem(item, empty);
    if (empty || item == null) {
      setText(null);
    } else {
      val displayItem = new VirtualMachineDescriptorItem(item);
      setGraphic(displayItem);
//      setText(String.format("Name: %s (ID: %s)", item.displayName(), item.id()));
    }
  }
}
