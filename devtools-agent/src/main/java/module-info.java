module org.javafx.devtools.agent {
  requires static lombok;
  requires org.slf4j;
  requires java.instrument;
  requires jcommander;
  requires static org.jetbrains.annotations;

  requires io.soabase.recordbuilder.core;
  requires java.rmi;
  exports org.javafx.devtools.agent;
}