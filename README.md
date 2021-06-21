# Crude Email - Final Project for OOP Semester 2
Crude Email is an email client made in Java with GUI from JavaFX. Ya that's pretty much it.

## Configuration
1. Git clone the project
```PowerShell
git clone https://github.com/digaji/CrudeEmail.git
```
2. CD into the project
```PowerShell
cd CrudeEmail
```

Java version for this project == 15.0.2

How you run this code is up to you, but usually you would start by using an IDE like [NetBeans](https://netbeans.org/), [Intellij IDEA](https://www.jetbrains.com/idea/), or [Eclipse](https://eclipse.org/ide/).

To build the program, you will need to download and unpack the latest (or recent) version of [Maven](https://maven.apache.org/download.cgi)
and put the `mvn` command on your path.

Now you can run `mvn clean install` and Maven will compile your project,
and put the results in a shaded `jar` file in the `target` directory.

To run the file:
```PowerShell
java -jar CrudeEmail-1.0-shaded.jar
```

Most IDEs also have support for Maven and can run Maven commands from within the project.

# Notice for users who can run but don't get anything displayed
In certain cases, the program will launch but no components will be displayed. This may be due to the graphics accelerator that Java chooses to use when running the program. To circumvent this, run the program with additional arguments / VM options:
```PowerShell
-Dprism.order=sw
```