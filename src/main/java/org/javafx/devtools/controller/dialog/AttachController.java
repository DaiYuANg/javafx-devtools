package org.javafx.devtools.controller.dialog;

import com.sun.tools.attach.VirtualMachine;
import jakarta.inject.Singleton;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.VBox;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.javafx.devtools.component.VirtualMachineDescriptorListView;

@Singleton
@Slf4j
public class AttachController implements Initializable {
  @FXML
  private VBox root;
  @FXML
  private ProgressIndicator loadingIndicator;
  @FXML
  private Button refreshButton;
  @FXML
  private VirtualMachineDescriptorListView vmList;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    refreshVMList();
    refreshButton.setOnAction(event -> refreshVMList());
  }

  private void refreshVMList() {
    // 显示加载指示器，禁用刷新按钮
    loadingIndicator.setVisible(true);
    refreshButton.setDisable(true);
    vmList.setVisible(false);

    // 异步加载虚拟机列表
    CompletableFuture.runAsync(() -> {
      val vms = VirtualMachine.list();
      log.atDebug().log("Vm list: {}", vms);
      try {
        TimeUnit.SECONDS.sleep(1);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
      Platform.runLater(() -> {
        vmList.getItems().setAll(vms);
        loadingIndicator.setVisible(false);
        refreshButton.setDisable(false);
        vmList.setVisible(true);
      });
    });
  }
}
