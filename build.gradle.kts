plugins {
    id("base")
    id("eclipse")
    id("idea")
    id("java")

    // the ignition module plugin: https://github.com/inductiveautomation/ignition-module-tools
    id("io.ia.sdk.modl") version ("0.1.1")
    id("org.barfuin.gradle.taskinfo") version "2.1.0"
}

allprojects {
    group = "com.macautoinc.widget"
    version = "1.0.0"
}

subprojects {
    apply(plugin = "java")

    dependencies {
        "testImplementation"("org.junit.jupiter:junit-jupiter-api:5.8.1")
        "testRuntimeOnly"("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}
