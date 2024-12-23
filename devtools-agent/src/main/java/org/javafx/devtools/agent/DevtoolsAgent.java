package org.javafx.devtools.agent;

import com.beust.jcommander.JCommander;
import java.lang.instrument.Instrumentation;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.javafx.devtools.agent.model.Argument;

@Slf4j
public class DevtoolsAgent {

  //  TODO add args
  public static void agentmain(String agentArgs, Instrumentation instrumentation) {
    val args = new Argument();
    JCommander.newBuilder()
      .addObject(args)
      .build()
      .parse(agentArgs);
    System.err.println("Agent arg" + args);
    System.err.println("test");
    System.err.println("agent started");
    System.out.println("Agent loaded via agentmain.");

  }
}
