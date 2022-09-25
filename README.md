# bug-tracking-system
Online bug tracking system for projects.

## How to run?

1. Open the `application` folder in an IDE like VSCode / IntelliJ IDEA Ultimate / STS etc. or in a terminal
1. Run the Maven build task `compile war:exploded` or `compile war:war`, depending on whether a `war` or `exploded war` is to be built. This can also be achieved by running the VSCode build task from the command menu, or by running `.\mvnw compile war:exploded` in the terminal.
1. Add the folder/WAR inside `target` folder deployment to a server like Apache Tomcat, Pivotal TC, Jetty etc.
1. Visit the deployment's home page.
