import java.text.SimpleDateFormat
import java.util.Date

val dateFormat = SimpleDateFormat("yyyyMMddHH")
val date = Date()
val buildDate = dateFormat.format(date)

// Set the build date as an extra property
extra["buildDate"] = buildDate

// Apply necessary plugins for the project
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

// Define project dependencies
dependencies {
    // Include the 'common' project as an API dependency
    api(projects.common)

    // Include various libraries as compile-only dependencies. These are required for compilation but not at runtime.
    compileOnly(libs.ignition.common) // Common libraries for Ignition
    compileOnly(libs.google.jsr305) // Annotations for software defect detection in Java
    compileOnly(libs.ignition.designer.api) // API libraries for Ignition Designer
    compileOnly(libs.ignition.perspective.common) // Common libraries for Ignition Perspective
    compileOnly(libs.ignition.perspective.designer) // Designer libraries for Ignition Perspective
}
