import org.apache.commons.lang3.SystemUtils

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
  alias(libs.plugins.javamodularity)
}

val mainClassPath = "org.javafx.devtools.DevtoolsApplication"
val mainModulePath = "org.scenicview.scenicview"
application {
  mainModule = mainModulePath
  mainClass = mainClassPath
}
group = "org.scenic-view"
version = "21.0.1"

java {
  sourceCompatibility = JavaVersion.VERSION_21
  targetCompatibility = JavaVersion.VERSION_21
}

repositories {
  mavenCentral()
  mavenLocal()
  maven { setUrl("https://jitpack.io") }
  gradlePluginPortal()
  google()
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

dependencies {
  implementation(libs.slf4j)
  implementation(libs.slf4jJulBridage)
  implementation(libs.slf4jJdkPlatform)
  compileOnly(libs.jetbrains.annotation)

  implementation(libs.avaje.inject)
  annotationProcessor(libs.avaje.inject.generator)

  implementation(libs.logback)

  implementation(libs.atlantafx)

  implementation(libs.guava)
  implementation(libs.vavr)

  implementation(libs.eclipse.collections.api)
  implementation(libs.eclipse.collections)

  implementation(libs.jna)

  implementation(libs.theme.detector) {
    exclude(group = "net.java.dev.jna", module = "jna")
  }

  implementation(libs.ikonliJavafx)
  implementation(libs.simpleicon)
  implementation(libs.devicons)
  implementation(libs.materialIcons)
  implementation(libs.fontawesome5)
  implementation(libs.controlfx)

  testImplementation(libs.testfx.junit5)
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
  options = listOf("--strip-debug", "--no-header-files", "--no-man-pages")
  enableCds()
  mainClass.set(mainClassPath)
  moduleName.set(mainModulePath)
  addExtraDependencies("javafx", "jakarta", "eclipse.collections")
  launcher {
    name = "scenicView"
  }
  mergedModule {
    addExtraDependencies("javafx")
  }
//    imageDir = layout.buildDirectory.dir("scenicview")
//    imageZip = layout.buildDirectory.file("dist/scenicview-${JavaVersion.current()}-${platform}.zip")
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

spotless {
  java {
    target("**/*.java")
    removeUnusedImports()
    importOrder()
    indentWithSpaces(2)
  }
}