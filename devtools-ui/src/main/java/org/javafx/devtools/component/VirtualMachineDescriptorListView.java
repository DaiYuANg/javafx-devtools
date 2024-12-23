package org.javafx.devtools.component;

import com.sun.tools.attach.VirtualMachineDescriptor;
import java.util.Optional;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.javafx.devtools.event.SelectVmEvent;

@Slf4j
public class VirtualMachineDescriptorListView extends ListView<VirtualMachineDescriptor> {
  public VirtualMachineDescriptorListView() {
    getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    setCellFactory(listView -> new VirtualMachineDescriptorCell());
    setOnMouseClicked(event -> {
      if (event.getClickCount() == 2) {
        Optional.ofNullable(getSelectionModel().getSelectedItem()).ifPresent(virtualMachineDescriptor -> {
          log.atInfo().log("Selected VM: {}", virtualMachineDescriptor);
          val e = new SelectVmEvent(SelectVmEvent.ANY, virtualMachineDescriptor);
          fireEvent(e);
        });
      }
    });
  }
}
