# Grader

Grading framework by Prasun Dewan and Joshua Monson.

## Installation

### Requirements

This project is built by Maven, so make sure that's on your system.

### Object editor jar

You need to make sure that Object Editor is installed in Maven.

```
mvn install:install-file -Dfile=oeall-22.jar -DgroupId=edu.unc -DartifactId=oeall -Dversion=22 -Dpackaging=jar
```

### Building

To compile:

```
mvn compile
```

To build the .jar:

```
mvn package
```

### Executing

The name of the jar depends on the `version` defined in `pom.xml`. Run the jar:

```
java -jar target/comp401-grader-Assignment-X-jar-with-dependencies.jar
```

## Examples

There are a number of examples on how to use the system under the `examples` package.
There is, somewhat, an order to them.

* GraderExample.java
* FrameworkExample.java
* GraderWithProjectRequirementsExample.java
* GraderWithNewGUIExample.java