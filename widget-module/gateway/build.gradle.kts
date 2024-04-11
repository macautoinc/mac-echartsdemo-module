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
    eclipse // Applies the Eclipse plugin for integration with Eclipse IDE
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
    // Include the 'common' project as an implementation dependency. This is a project within the same build that this project depends on.
    implementation(projects.common)

    // Include the 'web' project as a module implementation dependency. This is another project within the same build that this project depends on, specifically for module implementations.
    modlImplementation(projects.web)

    // Declare dependencies on Ignition SDK elements. These dependencies are defined in the gradle/libs.versions.toml file of the root project for this module.
    // 'compileOnly' dependencies are required for compiling the project but are not included in the runtime classpath.
    compileOnly(libs.ignition.common) // Common libraries for Ignition
    compileOnly(libs.ignition.gateway.api) // Gateway API libraries for Ignition

    // 'implementation' dependencies are included in the runtime classpath and are necessary for both compiling and running the project.
    implementation(libs.ignition.perspective.gateway) // Perspective gateway libraries for Ignition
    implementation(libs.ignition.perspective.common) // Common libraries for Ignition Perspective
    compileOnly(libs.ia.gson) // Gson library for JSON processing, used only for compilation
}
