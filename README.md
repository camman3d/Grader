# About

Grading framework by Prasun Dewan and Joshua Monson.

The purpose of this project is to provide a framework and tools to allow for the automatic and assisted grading of
Java-based programs. It employs several methods to check code validity including reflection-based invocation,
abstract syntax tree inspection, output parsing, source code validation, and JVM modification.

# Quick Start with Eclipse

The following is a guide to getting up an running as quickly as possible. The only assumption this quick start guide
makes is that you have Eclipse installed.

## Step 1: Get Maven

Download Maven from http://maven.apache.org, extract it, and add the binaries folder to your system path. Make sure the
`JAVA_HOME` environment variable is set.

## Step 2: Setup Eclipse with Maven

From the **Help** menu select **Eclipse Marketplace**.

Install the plugin called *Maven Integration for Eclipse*. This will require Eclipse to restart.

## Step 3: Get and Initialize the Repository

Clone the repository.

```
git clone https://github.com/camman3d/Grader.git
```

From a command line (if using Windows, use command prompt not powershell), navigate to the folder you just cloned and
run the following command.

```
mvn install:install-file -Dfile=oeall-22.jar -DgroupId=edu.unc -DartifactId=oeall -Dversion=22 -Dpackaging=jar
```

This adds the Object Editor jar file to your local Maven repository so that the dependency can be resolved.

## Step 4: Add the Project to Eclipse

In Eclipse, create a new Java project. Don't use the default location but browse to and select the cloned git repo.
Remove the "Test Data" folder from the sources.

*Note:* The compliance level may be set to **1.5** so be sure to change this to **1.7**.

## Step 5: Run the Program

That's it, you're all set up. The default entry point is `graderTools.Driver`. You can run this file to run the grading
tool.

# Project Setup

## Testing, Building, and Executing

Because this is a Maven project, you can run the tests and build it using Maven commands.

### Testing

To run all the tests in the `test` folder, just run the following command:

```
mvn test
```

### Building

To compile:

```
mvn compile
```

To build the .jar (which does the testing and compilation as well):

```
mvn package
```

### Executing

The name of the jar depends on the `version` defined in `pom.xml`. Run the jar:

```
java -jar target/comp401-grader-Assignment-X-jar-with-dependencies.jar
```

If you have the project set up in an IDE you can run it there as well.

# Configuration

The entry point in the program (the one which Maven is configured to use) looks at the configuration file to determine
what and how to run. There are the following settings that you can set:

* `project.requirements`: This is the canonical name of a class which extends `ProjectRequirements` to use as the grading
criteria.
* `project.name`: The name of the project. Something like "Assignment4".
* `grader.controller`: This specifies which controller is used to load projects and dictate the grading process.
* `grader.logger`: This setting allows you to set how results will be saved. You can choose which loggers are used by selecting any of the following concatenated with '+':
* * `feedback-txt`: This saves a text file in the students' feedback folder
* * `feedback-json`: This saves a json file in the students' feedback folder
* * `feedback`: Equivalent to "feedback-txt + feedback-json"
* * `local-txt`: This saves a text file in a local log folder
* * `local-json`: This saves a json file in the local log folder
* * `local`: Equivalent to "local-txt + local-json"
* * `spreadsheet`: This saves all the scores in a spreadsheet

# Examples

There are a number of examples on how to use the system under the `examples` package.
There is, somewhat, an order to them.

* GraderExample.java
* FrameworkExample.java
* GraderWithProjectRequirementsExample.java
* GraderWithNewGUIExample.java