import org.apache.commons.lang3.SystemUtils

plugins {
  application
  java
  alias(libs.plugins.jlink)
  alias(libs.plugins.javafx)
  alias(libs.plugins.shadow)
  alias(libs.plugins.graalvm)
  alias(libs.plugins.maniftest)
  alias(libs.plugins.javamodularity)
}

group = "org.scenic-view"
version = "21.0.1"

val mainClassPath = "org.javafx.devtools.DevtoolsApplication"
val mainModulePath = "org.scenicview.scenicview"
application {
  mainModule = mainModulePath
  mainClass = mainClassPath
}
repositories {
  mavenCentral()
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

  implementation(libs.apache.common.lang3)

  implementation(libs.guava)
  implementation(libs.vavr)

  implementation(libs.jna)

  implementation(libs.theme.detector) {
    exclude(group = "net.java.dev.jna", module = "jna")
  }

  implementation(libs.record.builder.core)
  annotationProcessor(libs.record.builder.processor)
  implementation(libs.ikonliJavafx)
  implementation(libs.simpleicon)
  implementation(libs.devicons)
  implementation(libs.materialIcons)
  implementation(libs.fontawesome5)
  implementation(libs.controlfx)

  implementation(projects.devtoolsAgent)

  testImplementation(libs.testfx.junit5)
  testImplementation(platform("org.junit:junit-bom:5.10.0"))
  testImplementation("org.junit.jupiter:junit-jupiter")
}


javafx {
  version = "23"
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
  dependsOn(tasks.collectReachabilityMetadata)
  from(tasks.collectReachabilityMetadata)
  manifest {
    attributes(
      "Main-Class" to mainClassPath,
      "Agent-Class" to "org.fxconnector.remote.RuntimeAttach",
      "Premain-Class" to "org.scenicview.ScenicView",
      "Automatic-Module-Name" to "org.scenicview.scenicview"
    )
  }
}

val platform = when {
  SystemUtils.IS_OS_MAC -> "mac"
  SystemUtils.IS_OS_WINDOWS -> "windows"
  SystemUtils.IS_OS_LINUX -> "linux"
  else -> {
    throw IllegalStateException("Unsupported platform")
  }
}
jlink {
  options = listOf("--strip-debug", "--compress", "2", "--no-header-files", "--no-man-pages")
  enableCds()
  mainClass.set(mainClassPath)
  moduleName.set(mainModulePath)
  addExtraDependencies("javafx", "jakarta", "avaje")
  launcher {
    name = "scenicView"
  }
  mergedModule {
    addExtraDependencies("javafx")
    mergedModule {
      requires("javafx.web")
      requires("com.google.j2objc.annotations");
      requires("com.google.errorprone.annotations");
      requires("java.logging");
      requires("org.checkerframework.checker.qual");
      requires("java.desktop");
      requires("java.datatransfer");
      requires("org.slf4j");
      requires("jdk.unsupported");
    }
  }
  jpackage {
    imageName = "javafx-devtools"
    icon = "icon/logo.icns"
    jvmArgs = JvmArguments
  }
}

graalvmNative {
  metadataRepository {
    enabled = true
  }
  binaries {
    named("main") {
      sharedLibrary.set(false)
    }
  }
}


java {
  modularity.inferModulePath.set(true)
}