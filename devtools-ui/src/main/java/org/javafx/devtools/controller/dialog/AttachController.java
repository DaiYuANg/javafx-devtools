package org.javafx.devtools.controller.dialog;

import jakarta.inject.Singleton;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.VBox;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.javafx.devtools.component.VirtualMachineDescriptorListView;
import org.javafx.devtools.util.ProcessUtil;

@Singleton
@Slf4j
@RequiredArgsConstructor
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
    vmList.setPlaceholder(new Label("No Javafx Application Founded"));
    refreshButton.setOnAction(event -> refreshVMList());
  }

  private void refreshVMList() {
    loadingIndicator.setVisible(true);
    refreshButton.setDisable(true);
    vmList.setVisible(false);

    // 异步加载虚拟机列表
    CompletableFuture.runAsync(() -> {
      val vms = ProcessUtil.listJfxProcesses();
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
