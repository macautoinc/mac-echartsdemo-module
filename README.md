# Perspective Component Module Example

This document serves as a guide for adding a custom Gauge widget to the Perspective module's palette. It aims to be a foundational resource for developers looking to expand their projects with more widgets. Through this example, we'll explore how to integrate a collection of widgets into the Perspective palette, focusing on various approaches to data handling and component configuration within the gateway designer.

This instance represents just one of many strategies a developer might employ to craft a module for Perspective. The choice of tools and methodologies ultimately rests with the developer.

## Overview of Tools Used

The construction of this project leverages several tools, each playing a crucial role in different stages of the build process. It's important to recognize that these tools are merely suggestions; developers are free to choose their preferred tools or opt to work without them. This project includes:

* [Gradle](https://gradle.org/) serves as the main build tool, facilitating most of the routine tasks in a typical development workflow.
* [lerna.js](https://lerna.js.org/) is a tool for managing JavaScript projects with multiple packages, simplifying the process of linking and publishing changes across projects within the same repository. It's particularly useful for command-line operations outside of Gradle.
* [yarn](https://yarnpkg.com/) is a package manager for JavaScript, offering several advantages over npm. It's used for dependency resolution and downloading packages from remote repositories. To resolve Inductive Automation's node packages, an **.npmrc** file must be added to the project's front-end directories, directing yarn/npm to the correct package locations within the **@inductiveautomation** namespace.
* [Typescript](https://www.typescriptlang.org/) is the chosen language for the project's front-end development. While not mandatory, Typescript is highly recommended for its support of types, which enhances the development experience by improving tooling support for maintenance, refactoring, code navigation, and bug detection. Typescript compiles to JavaScript, with other tools typically handling asset bundling and dependency management.
* [Webpack](https://webpack.js.org/) is used to bundle the JavaScript output from Typescript, along with any necessary assets, dependencies, and sourcemaps, into a final package ready for deployment.

Further documentation is forthcoming, and the **web/README.md** file contains detailed information about the Typescript build process.

### Initial Setup

Here's a streamlined guide to get this project up and running.

Ideally, the module can be built without the need for additional tool installations. By utilizing the [Gradle Wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html), all necessary tools are automatically downloaded and utilized to execute the build process.

##### Note: Module-specific tasks are defined by the module plugin. For more details on these tasks and configuration options, refer to the documentation at the [Ignition Module Tool](https://github.com/inductiveautomation/ignition-module-tools) repository.

To initiate the build, clone this repository, navigate to the **perspective-component** directory in a command line interface, and execute the **build** gradle task:

If executed on Windows:

**gradlew build**

If executed on Linux/OSX

**./gradlew build**


For those interested in running parts of the build independently of Gradle, familiarity with the JavaScript and TypeScript ecosystems is necessary, including tools like NodeJs, NPM/Yarn, TypeScript, Webpack, Babel, etc.

A rough guide to setting up these tools is as follows:

1. Node and npm installation is the first step, which then allows for the installation of yarn, typescript, webpack, etc. NodeJS can be installed from the NodeJS Website, with LTS versions recommended. The specific versions used in the build are listed in the **./web/build.gradle** file within the **node** configuration block.
2. Once npm is installed, proceed to install the global dev-dependency tools. These should match the versions specified in your package.json files as closely as possible.

    * **npm install -g typescript**
    * **npm install -g webpack@3.10.1** // Adjust the version as needed
    * **npm install -g tslint**
    * **npm install -g lerna**
    * **npm install -g yarn**

3. Gradle does not require installation if commands are executed through the gradle wrapper, as detailed in the [Gradle Wrapper Docs](https://docs.gradle.org/current/userguide/gradle_wrapper.html).

A quick note: This example utilizes a custom gradle plugin developed by IA for building Ignition modules. Originally intended for internal use, the plugin assumes certain project structures and dependencies. While Maven can be used for building perspective modules, integration and support for Perspective module development with the **ignition-maven-plugin** are not planned.

### Project Structure & Overview

This section outlines the project's layout, offering a broad view of its components. For more detailed information about the **web** subproject, refer to its readme file.

The module's structure follows a typical Ignition Module layout with a notable exception. Like many cross-scope projects, it includes a **common** subproject shared between the gateway and designer scopes. However, it lacks a **client** scope, featuring a **web** subproject instead. This subproject houses the source code, assets, and build configurations for the module's HTML/JS/CSS components.

The **web** directory contains a _lerna workspace_, akin to a multi-module project in Maven or a multi-project build in Gradle. This setup allows for more than one build configuration, with our project targeting both a runtime perspective client in a browser and a perspective in the designer. The output from both packages within the **web/** directory ultimately becomes part of the **gateway** scoped Java project, from where it is served. The naming (**client** or **designer**) is less critical than ensuring the files are correctly registered in the appropriate registries.

#### Building the Module

Building the module with the gradle wrapper is straightforward. Execute `./gradlew build` in a bash or similar terminal (Linux/OSX) or `gradle.bat build` on Windows. This process ensures the download of the correct gradle binaries, as specified by our **wrapper** task and the committed **gradle**/ directory, compiles and assembles all jars, runs tests/checks, and ultimately produces the signed modl file. Note that module signing requires appropriate certificates and a **gradle.properties** file configured as described in the plugin readme at the [Tools Repository](https://github.com/inductiveautomation/ignition-module-tools).

#### Customization and Configuration

The scope of this example does not extend to detailed build customization. We encourage developers to explore the documentation of the tools mentioned above to tailor the build process to their project's needs.

#### SDK Insights

Given the complexity and ongoing development of Perspective, it's advisable to test modules against every intended version of Ignition/Perspective. While the APIs are considered relatively stable, future updates may introduce changes or enhancements.

##### Conventions

From the 8.1.4 release onwards, components receive a **ref** via **emit** props, essential for internal use by the Perspective module to reference the root element of each component. Consequently, the root element in custom components should not include a **ref** property to avoid overriding the emitted ref, which could lead to display or error issues. The component's store can still access the ref if necessary. Additionally, maintaining a consistent root element throughout the component's lifecycle is recommended. For an implementation example, see the **MessengerComponent** in the example components.
