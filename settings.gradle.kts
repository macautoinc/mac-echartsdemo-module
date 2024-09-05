// this file configures settings for the gradle build tools, as well as the project structure.
// Generally this doesn't need to be altered unless you are adding/removing sub-projects.
pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        mavenLocal()
        // add the IA repo to pull in the module-signer artifact.  Can be removed if the module-signer is maven
        // published locally from its source-code and loaded via mavenLocal.
        maven {
            url = uri("https://nexus.inductiveautomation.com/repository/public/")
        }
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)

    repositories {
        mavenLocal()
        maven {
            url = uri("https://nexus.inductiveautomation.com/repository/public/")
        }

        // Declare the Node.js download repository.  We do this here so that we can continue to have repositoryMode set
        // to 'PREFER SETTINGS', as the node plugin will respect that and not set the node repo, meaning we can't
        // resolve the node runtime we need for building the web packages.
        ivy {
            name = "Node.js"
            setUrl("https://nodejs.org/dist/")
            patternLayout {
                artifact("v[revision]/[artifact](-v[revision]-[classifier]).[ext]")
            }
            metadataSources {
                artifact()
            }
            content {
                includeModule("org.nodejs", "node")
            }
        }
    }
}

// settings.gradle.kts
rootProject.name = "mac-echartsdemo-module"

include(":widget-module")

