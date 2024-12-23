package org.javafx.devtools.util;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;
import io.vavr.control.Try;
import java.util.List;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.javafx.devtools.constant.Define;
import org.jetbrains.annotations.NotNull;

@UtilityClass
@Slf4j
public class ProcessUtil {

  public List<VirtualMachineDescriptor> listJfxProcesses() {
    val current = String.valueOf(ProcessHandle.current().pid());
    return VirtualMachine.list().stream()
      .filter(descriptor -> !StringUtils.equals(current, descriptor.id()))
      .filter(ProcessUtil::isJavaFXProcess).toList();
  }

  private boolean isJavaFXProcess(@NotNull VirtualMachineDescriptor descriptor) {
    val pid = descriptor.id();

    return Try.of(() -> {
        val vm = VirtualMachine.attach(pid);
        val isJavaFxApplication = vm.getSystemProperties().containsKey(Define.JAVAFX_SYSTEM_PROPERTIES_KEY);
        vm.detach();
        return isJavaFxApplication;
      })
      .onFailure(t -> log.atError().log(t.getMessage(), t))
      .getOrElse(false); // 如果出现异常，返回 false
  }
}
