/*
 * Scenic View,
 * Copyright (C) 2012, 2018 Jonathan Giles, Ander Ruiz, Amy Fowler
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
module org.scenicview.scenicview {
  requires transitive javafx.fxml;
  requires transitive javafx.web;
  requires transitive javafx.swing;
  requires transitive javafx.base;
  requires static lombok;
  requires org.slf4j;
  requires static org.jetbrains.annotations;
  requires io.avaje.inject;
  requires com.google.common;
  requires atlantafx.base;

  requires com.sun.jna;
  requires java.instrument;
  requires java.rmi;
  requires java.logging;
  requires jdk.attach;
  requires io.vavr;
  requires com.jthemedetector;
  requires org.checkerframework.checker.qual;
  requires io.avaje.inject.events;
  requires org.apache.commons.lang3;
  requires java.prefs;
  requires org.kordamp.ikonli.javafx;

  opens org.scenicview.view.cssfx to javafx.fxml;
  opens org.scenicview.view.threedom to javafx.fxml;
  opens org.fxconnector.remote to java.instrument, java.rmi;

  exports org.scenicview.view.cssfx to javafx.fxml;
  exports org.scenicview.view.threedom to javafx.fxml;
  exports org.fxconnector.remote to java.instrument;

  exports org.fxconnector;
  exports org.scenicview;
  exports org.javafx.devtools;

  provides io.avaje.inject.spi.InjectExtension with org.javafx.devtools.DevtoolsModule;
}