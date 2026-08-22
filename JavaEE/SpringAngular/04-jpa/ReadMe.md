# Introduction

This document details the step to create a spring initialiser project for full stack development.

# Install

## Download Spring Initialiser archive

- Go to [Spring Initializr](https://start.spring.io/)
- Spring Initalizr form
  - Project: Maven
  - SpringBoot: 3.5.6 (Latest non snapshot version)
  - Project Metadata
    - Group: io.github.cafeduke
    - Artifact: dukecart
    - Java: 25
  - Add dependencies
    - Spring Data JPA -- Persist data using Java Persistence API (JPA)
    - Rest Repositories -- Expose spring data using REST
    - MySQL Driver -- JDBC driver
    - Lombak -- Java annotation library which helps to reduce boilerplate code.

## Setup Eclipse Enterprise Edition

- File > Import maven project
- Project (right click) > Properties 
  - Project Facets > Configuration (dropdown) > Select 'Basic JPA configuration'
  - JPA > Add connection...
    - Connection Profile Types: MySQL
      - Only for the first time click on new driver definition
      - NameTab: Select latest driver (even if the driver does not match the installed DB version)
      - Jar List > Add Jar/Zip > Locate the `mysql*connector*jar` in `~/.m2/repository/`
    - Name: Duke MySQL
    - Add connection details
    - Test connection
- Project (right-click) > JPA Tools > Generate Entities from Tables...



## Setup Eclipse with Lombok

```bash
# Locate lombok
> get  ~/.m2/repository "lombok*jar"

# Run lombok
java -jar <path-to-lombok-jar-in-maven-repo>

# Use the LombokUI to locate eclipse binary and select install
```



# Accessing Data using JPA

https://spring.io/guides/gs/accessing-data-jpa

# Project: dukecart

See [dukecart](dukecart) to learn about

- Generating Entities from Tables
- Remove getters and setters and instead use `@Getters` and `@Setters` from Lombok
- Use DAO (Data Access Object) design patter to create Repository interfaces extending the `org.springframework.data.repository.CrudRepository` interface
- Create config classes overriding methods in `org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer` to restrict access to certain certain HTTP methods.
