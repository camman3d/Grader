# About

Grading framework by Prasun Dewan and Joshua Monson.

# Installation

## Requirements

This project is built by Maven, so make sure that's on your system.

## Object editor jar

You need to make sure that Object Editor is in your local Maven repository.

```
mvn install:install-file -Dfile=oeall-22.jar -DgroupId=edu.unc -DartifactId=oeall -Dversion=22 -Dpackaging=jar
```

## Building

To compile:

```
mvn compile
```

To build the .jar:

```
mvn package
```

## Executing

The name of the jar depends on the `version` defined in `pom.xml`. Run the jar:

```
java -jar target/comp401-grader-Assignment-X-jar-with-dependencies.jar
```

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