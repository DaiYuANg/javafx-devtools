package org.javafx.devtools.agent.model;

import com.beust.jcommander.Parameter;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class Argument {

  @Parameter
  private List<String> parameters = new ArrayList<>();
  @Parameter(names = {"-log", "-verbose"}, description = "Level of verbosity")
  private Integer verbose = 1;

  @Parameter(names = "-debug", description = "Debug mode")
  private boolean debug = false;
}
