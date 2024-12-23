package org.javafx.devtools.controller;

import jakarta.inject.Singleton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.fxconnector.node.NodeType;
import org.fxconnector.node.SVDummyNode;
import org.fxconnector.node.SVNode;
import org.javafx.devtools.component.NodeTreeView;

@Singleton
@Slf4j
public class NodeTreeController implements Initializable {
  @FXML
  private NodeTreeView nodeTreeView;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    val root = new TreeItem<SVNode>(new SVDummyNode("Apps", "Java", 0, NodeType.VMS_ROOT));
    root.setExpanded(true);
    nodeTreeView.setRoot(root);
  }
}
