import org.javamodularity.moduleplugin.extensions.CompileModuleOptions

plugins {
  alias(libs.plugins.maniftest)
  alias(libs.plugins.javamodularity)
}

group = "org.javafx.devtools.agent"
version = "21.0.1"

val agentMainPath = "org.javafx.devtools.agent.DevtoolsAgent"

tasks.jar {
  manifest {
    attributes(
      "Agent-Class" to agentMainPath,
    )
  }
}

dependencies {
  implementation(libs.slf4j)
  implementation(libs.record.builder.core)
  compileOnly(libs.jetbrains.annotation)
  implementation("org.jcommander:jcommander:2.0")
  annotationProcessor(libs.record.builder.processor)
  testImplementation(platform("org.junit:junit-bom:5.10.0"))
  testImplementation("org.junit.jupiter:junit-jupiter")
}


tasks.compileJava {
  extensions.configure<CompileModuleOptions> {
    addModules = listOf("jcommander")
  }
}