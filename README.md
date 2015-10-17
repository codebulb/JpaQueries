# JpaQueries
A comparison of writing persistence queries using SQL or JPQL dynamic or named queries, the Criteria API or Querydsl, with example queries running in a unit test.

## Setup
In order to run the JUnit tests locally, you need to setup a database according to the [persistence.xml](https://github.com/codebulb/JpaQueries/blob/master/src/main/resources/META-INF/persistence.xml) configuration.

For example, when using **NetBeans IDE 8.0**:
- **Create DB (once):** In the Services window, right click on Databases > new Connection… to create a new DB connection based on the persistence.xml configuration, e.g. using the Java DB driver for an in-memory database.
- **Start DB server (after every IDE restart):** In the Services window, right click on Databases / Java DB > Start Server.
