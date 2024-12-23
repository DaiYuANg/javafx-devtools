package org.javafx.devtools.event;

import com.sun.tools.attach.VirtualMachineDescriptor;
import javafx.event.Event;
import javafx.event.EventType;
import lombok.Getter;

@Getter
public class SelectVmEvent extends Event {
  public static final EventType<SelectVmEvent> ANY = new EventType<>(Event.ANY, "SELECT_VM_EVENT");

  private final VirtualMachineDescriptor virtualMachineDescriptor;

  public SelectVmEvent(EventType<? extends Event> eventType, VirtualMachineDescriptor virtualMachineDescriptor) {
    super(eventType);
    this.virtualMachineDescriptor = virtualMachineDescriptor;
  }
}
