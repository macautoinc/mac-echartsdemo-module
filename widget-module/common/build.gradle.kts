import java.text.SimpleDateFormat
import java.util.Date

val dateFormat = SimpleDateFormat("yyyyMMddHHmm")
val date = Date()
val buildDate = dateFormat.format(date)

// Set the build date as an extra property
extra["buildDate"] = buildDate

plugins {
    idea // Applies the IntelliJ IDEA plugin for project integration with IntelliJ IDEA IDE
    eclipse // Applies the Eclipse plugin for project integration with Eclipse IDE
    `java-library` // Applies the Java Library plugin to support Java library development
}

// Configure the Java toolchain
java {
    toolchain {
        // Set the Java language version to 11 for compilation and execution
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

tasks.jar {
    manifest {
        attributes(
            "Implementation-Title" to "MAC Auto Inc. Widget",
            "Implementation-Version" to "${project.version}",
            "Build-Date" to date
        )
    }
}

dependencies {
    // compileOnly is the gradle equivalent to "provided" scope.  Here we resolve the dependencies via the
    // declarations in the gradle/libs.versions.toml file
    compileOnly(libs.ignition.common)
    compileOnly(libs.ignition.perspective.common)
    compileOnly(libs.google.guava)
    compileOnly(libs.ia.gson)
}
