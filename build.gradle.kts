plugins {
  application
  java
  alias(libs.plugins.jlink)
  alias(libs.plugins.javafx)
  alias(libs.plugins.lombok)
  alias(libs.plugins.semver)
  alias(libs.plugins.versionCheck)
  alias(libs.plugins.shadow)
  alias(libs.plugins.graalvm)
  alias(libs.plugins.spotless)
  alias(libs.plugins.maniftest)
  alias(libs.plugins.dotenv)
}

application {
  mainModule = "org.scenicview.scenicview"
  mainClass = "org.scenicview.ScenicView"
}
group = "org.scenic-view"
version = "21.0.1"
//
//defaultTasks 'install'
//
java {
  sourceCompatibility = JavaVersion.VERSION_21
  targetCompatibility = JavaVersion.VERSION_21
}

repositories {
  mavenLocal()
  mavenCentral()
  gradlePluginPortal()
  google()
}

javafx {
  version = "21"
  modules = listOf(
    "javafx.base",
    "javafx.controls",
    "javafx.fxml",
    "javafx.graphics",
    "javafx.swing",
    "javafx.media",
    "javafx.web"
  )
  configuration = "implementation"
}

tasks.jar {
  manifest {
    attributes(
      "Main-Class" to "org.scenicview.ScenicView",
      "Agent-Class" to "org.fxconnector.remote.RuntimeAttach",
      "Premain-Class" to "org.scenicview.ScenicView",
      "Automatic-Module-Name" to "org.scenicview.scenicview"
    )
  }
}

dependencies {
  implementation(libs.slf4j)
  implementation(libs.slf4jJulBridage)
  implementation(libs.slf4jJdkPlatform)
  compileOnly(libs.jetbrains.annotation)

  implementation(libs.avaje.inject)
  annotationProcessor(libs.avaje.inject.generator)

  implementation(libs.logback)

  implementation(libs.atlantafx)
}
//
//run {
//	dependsOn jar
//}
//
//artifacts {
//    archives(jar)
//}
//
//ext.platform = osdetector.os == 'osx' ? 'mac' : osdetector.os == 'windows' ? 'win' : osdetector.os
//
jlink {
  options = listOf("--strip-debug", "--no-header-files", "--no-man-pages")
  launcher {
    name = "scenicView"
  }
//    imageDir = layout.buildDirectory.dir("scenicview")
//    imageZip = layout.buildDirectory.file("dist/scenicview-${JavaVersion.current()}-${platform}.zip")
}

