package org.javafx.devtools.lifecycle;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;
import jakarta.inject.Singleton;
import java.io.File;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.javafx.devtools.agent.DevtoolsAgent;
import org.javafx.devtools.constant.ViewConstant;
import org.javafx.devtools.event.SelectVmEvent;
import org.javafx.devtools.util.FXMLHelper;
import org.javafx.devtools.util.FXUtil;


@Singleton
@Slf4j
@RequiredArgsConstructor
public class DetectJvmApplicationLifecycle implements StageLifecycle {

  private final FXMLHelper fxmlHelper;

  @Override
  public void beforeShown(Stage stage) {
    val dialog = new Stage();
    dialog.setResizable(true);
    dialog.initStyle(StageStyle.UTILITY);
    val scene = new Scene(fxmlHelper.loadView(ViewConstant.ATTACH_DIALOG, VBox.class));
    scene.addEventHandler(SelectVmEvent.ANY, event -> {
      log.atDebug().log("Received Custom Event: {}", event.getVirtualMachineDescriptor());
      attach(event.getVirtualMachineDescriptor());
      FXUtil.asyncUpdateUI(dialog::close);
    });
    dialog.setScene(scene);
    dialog.showAndWait();
  }

  @SneakyThrows
  private void attach(VirtualMachineDescriptor vmd) {
    val vm = VirtualMachine.attach(vmd);
    val agent = DevtoolsAgent.class.getProtectionDomain().getCodeSource().getLocation();
    log.atDebug().log("Agent at:{}", agent);
    val agentFile = new File(agent.toURI());
    vm.loadAgent(agentFile.getAbsolutePath(), "-log -debug");
  }
}
