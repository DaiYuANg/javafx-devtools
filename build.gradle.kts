import name.remal.gradle_plugins.lombok.LombokPlugin

plugins {
  alias(libs.plugins.lombok)
  alias(libs.plugins.semver)
  alias(libs.plugins.versionCheck)
  alias(libs.plugins.spotless)
  alias(libs.plugins.dotenv)
  `java-library`
}

group = "org.javafx.devtools"
version = "21.0.1"

allprojects {
  repositories {
    mavenCentral()
    mavenLocal()
    maven { setUrl("https://jitpack.io") }
    gradlePluginPortal()
    google()
  }
}

subprojects {
  apply<JavaLibraryPlugin>()
  apply<LombokPlugin>()
  java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
  }
  tasks.test {
    useJUnitPlatform()
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
