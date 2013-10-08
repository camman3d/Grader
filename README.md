# Grader

Grading framework for COMP 401

## Requirements

Maven

## Installation

You need to make sure that Object Editor is installed.

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

Run the jar:

```
java -jar target/comp401-grader-Assignment-6-jar-with-dependencies.jar
```
