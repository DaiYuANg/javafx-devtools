/*
 * Scenic View,
 * Copyright (C) 2012 Jonathan Giles, Ander Ruiz, Amy Fowler
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.scenicview;

import java.lang.instrument.Instrumentation;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import atlantafx.base.theme.PrimerDark;
import atlantafx.base.theme.PrimerLight;
import com.jthemedetecor.OsThemeDetector;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.fxconnector.AppController;
import org.fxconnector.AppControllerImpl;
import org.fxconnector.StageControllerImpl;
import org.fxconnector.remote.FXConnectorFactory;
import org.javafx.devtools.context.DIContext;
import org.scenicview.model.attach.AttachHandlerFactory;
import org.scenicview.model.update.LocalUpdateStrategy;
import org.scenicview.model.update.RemoteVMsUpdateStrategy;
import org.scenicview.utils.ExceptionLogger;
import org.scenicview.view.ScenicViewGui;

/**
 * This is the entry point for all different versions of Scenic View.
 */
@Slf4j
public class ScenicView extends Application {

  private final OsThemeDetector osThemeDetector = DIContext.INSTANCE.get(OsThemeDetector.class);

  @Override
  public void init() throws Exception {
    val theme = osThemeDetector.isDark() ? new PrimerDark() : new PrimerLight();
    Application.setUserAgentStylesheet(theme.getUserAgentStylesheet());
  }

  /**************************************************************************
   *
   * fields
   *
   *************************************************************************/

  public static final String JDK_PATH_KEY = "jdkPath";

  private static boolean debug = true;

  /**************************************************************************
   *
   * general-purpose code
   *
   *************************************************************************/


  /**************************************************************************
   *
   * Scenic View 'hardcoded show(..)' start point
   *
   *************************************************************************/

  public static void show(final Scene target) {
    show(target.getRoot());
  }

  public static void show(final Parent target) {
    if (target == null) {
      return;
    }

//        startup();

    final Stage stage = new Stage();

    // workaround for RT-10714
    stage.setWidth(1024);
    stage.setHeight(768);
    stage.setTitle("Scenic View v" + ScenicViewGui.VERSION);

    final List<AppController> controllers = new ArrayList<>();
    final AppController aController = new AppControllerImpl();
    final boolean sceneRoot = target.getScene().getRoot() == target;
    final StageControllerImpl sController = new StageControllerImpl(target, aController, sceneRoot);

    System.out.println("aController = " + aController);
    aController.getStages().add(sController);
    controllers.add(aController);

    final LocalUpdateStrategy updateStrategy = new LocalUpdateStrategy(controllers);
    ScenicViewGui.show(new ScenicViewGui(updateStrategy, stage), stage);
  }


  /**************************************************************************
   *
   * runtime discovery start point
   * (Also refer to RuntimeAttach class)
   *
   *************************************************************************/
  public static void premain(final String agentArgs, final Instrumentation instrumentation) {
    // we start up a new thread to take care of initialising Scenic View
    // so that we don't block the loading of the actual application.
    @SuppressWarnings("unused")
    Thread scenicViewBootThread = new Thread(() -> {
      while (true) {
        try {
          Platform.runLater(() -> {
            try {
              new ScenicView().start(new Stage());
            } catch (Exception e) {
              e.printStackTrace();
            }
          });
          break;
        } catch (IllegalStateException e) {
          // FX runtime hasn't been initialized yet. Actual initialization occurs in method LauncherImpl.startup()
          // which must be called only once and under normal circumstances called as result of launching instrumented
          // application, so we have to wait while FX runtime will be initialized.
          try {
            Thread.sleep(500);
          } catch (InterruptedException ex) {
          }
        }
      }
    }, "scenic-view-boot");
    scenicViewBootThread.setDaemon(true);
    scenicViewBootThread.start();
  }

  @Override
  public void start(final Stage stage) throws Exception {
    // This mode is only available when we are in the commercial Scenic View,
    // so we must start up the license checker and validate

//        // Test if we can load a class from jfxrt.jar
//        try {
//            Class.forName("javafx.beans.property.SimpleBooleanProperty").newInstance();
//        } catch (final Exception e) {
//            // Fatal error - JavaFX should be on the classpath for all users
//            // of Java 8.0 and above (which is what Scenic View 8.0 and above
//            // targets.
//            Logger.print("Error: JavaFX not found");
//            System.exit(-1);
//        }

    AttachHandlerFactory.initAttachAPI(stage);
//        System.setProperty(FXConnector.SCENIC_VIEW_VM, "true");
//        startup();

//    setUserAgentStylesheet(STYLESHEET_MODENA);

    val strategy = new RemoteVMsUpdateStrategy();
//    val alternativeStage = new Stage();
//    val scene = DIContext.INSTANCE.get(Scene.class);
//    alternativeStage.setScene(scene);
//    alternativeStage.show();

    // workaround for RT-10714
    stage.setWidth(1024);
    stage.setHeight(768);
    stage.setTitle("Scenic View v" + ScenicViewGui.VERSION);
    log.atInfo().log("Platform running");
    log.atInfo().log("Launching ScenicView v" + ScenicViewGui.VERSION);
    ScenicViewGui view = new ScenicViewGui(strategy, stage);
    ScenicViewGui.show(view, stage);

    log.atInfo().log("Startup done");
    while (view == null) {
      try {
        Thread.sleep(500);
      } catch (final InterruptedException e) {
        ExceptionLogger.submitException(e);
      }
    }

    log.atInfo().log("Creating server");
    try {
      strategy.setFXConnector(FXConnectorFactory.getConnector());
    } catch (final RemoteException e1) {
      ExceptionLogger.submitException(e1);
    }
    log.atInfo().log("Server done");
  }
}
