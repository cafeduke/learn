- [Introduction](#introduction)
- [Technologies](#technologies)
- [VSCode IDE setup](#vscode-ide-setup)
- [Project creation](#project-creation)
  - [Spring Initializr](#spring-initializr)
- [Maven Setup](#maven-setup)
  - [Maven dependency configuration](#maven-dependency-configuration)
  - [Standard Maven plugin configuraiton](#standard-maven-plugin-configuraiton)
    - [maven-compiler-plugin](#maven-compiler-plugin)
  - [SpringBoot plugin for maven](#springboot-plugin-for-maven)
    - [spring-boot-maven-plugin](#spring-boot-maven-plugin)
  - [Custom plugins for maven](#custom-plugins-for-maven)
    - [tailwind-maven-plugin](#tailwind-maven-plugin)
- [SQLite database setup](#sqlite-database-setup)
- [Tailwind Setup](#tailwind-setup)
- [DiasyUI Setup](#diasyui-setup)
  - [Install daisy files](#install-daisy-files)
  - [Choose daisy theme](#choose-daisy-theme)
  - [Install custom fonts](#install-custom-fonts)
  - [Configure tailwind for daisy](#configure-tailwind-for-daisy)
- [HTMX setup](#htmx-setup)
- [Setup Project](#setup-project)
  - [Setup application.properties](#setup-applicationproperties)
  - [Project directory structure](#project-directory-structure)
- [Putting it all together](#putting-it-all-together)

# Introduction
This document details the setup required for SpringBoot-4, SQLite, Thymeleaf and HTMX (using DaisyUI and Tailwind) setup on VSCode IDE.

# Technologies

The following technologies were used

| Tech       | Version | Comment                                          |
| ---------- | ------- | ------------------------------------------------ |
| Java       | 26.0.1  |                                                  |
| SpringBoot | 4.1.0   |                                                  |
| Thymeleaf  | 3.1.5   | A server-side Java template engine               |
| HTMX       | 2.0.10  | Ajax library for HTML developed using javascript |
| Tailwind   | 4.3     | CSS class library                                |
| DaisyUI    | 5.0     | A component framework that uses Tailwind         |

# VSCode IDE setup
The following VS code extensions needs to be installed
  - Language Support for Java(TM) by Red Hat (redhat.java)
  - Maven for Java (vscjava.vscode-maven)
  - Spring Boot Extension Pack (vmware.vscode-boot-dev-pack)
    - Spring Boot Tools
    - Spring Initializr Java Support
    - Spring Boot Dashboard
  - thymeleaf (juhahinkula.thymeleaf)

# Project creation

## Spring Initializr
Go to [Spring Initializr](https://start.spring.io/) and select the following dependencies
- SQLite driver
- Spring boot data JPA
- Spring boot WebMVC
- Spring boot thymeleaf
- Spring boot dev tools
- Spring boot valiation
- Spring security
- Lombok

# Maven Setup

## Maven dependency configuration
Add the following maven dependencies

| Group                 | Artifact                     | Version | Comment                                            |
| --------------------- | ---------------------------- | ------- | -------------------------------------------------- |
| org.mapstruct         | mapstruct                    | 1.6.3   | Generate classes to convert DTO to Record and back |
| io.github.wimdeblauwe | htmx-spring-boot-thymeleaf   | 5.1.0   | Utility classes and annotations for HTMX           |
| org.hibernate.orm     | hibernate-community-dialects | LATEST  | SQLite dialects for Hibernate                      |

## Standard Maven plugin configuraiton

### maven-compiler-plugin
This is maven's standard plugin that compiles project source. This is configured by Lomok and MapStruct.

```xml
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-compiler-plugin</artifactId>
  <configuration>
    <annotationProcessorPaths>
      <path>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
      </path>
      <!-- Binding tool ensures Lombok runs before MapStruct -->
      <path>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok-mapstruct-binding</artifactId>
        <version>0.2.0</version>
      </path>
      <path>
        <groupId>org.mapstruct</groupId>
        <artifactId>mapstruct-processor</artifactId>
        <version>${org.mapstruct.version}</version>
      </path>
    </annotationProcessorPaths>
  </configuration>
</plugin>

```
## SpringBoot plugin for maven

### spring-boot-maven-plugin
This is spring boot plugin for maven. If we are using maven to run the spring boot application using `mvn spring-boot:start` as opposed to using IDE,
the following plugin ensures HOT reload of changes made to code.

```xml
<configuration>
  <!-- HotReload: Allows Spring Boot to fetch HTML templates directly from src/main/resources -->
  <addResources>true</addResources>
</configuration>
```

## Custom plugins for maven

### tailwind-maven-plugin
The tailwind plugin for maven takes the input CSS file and generate output CSS file that shall contain CSS classes
only for the subset of styles currently used. See [4ndreiDev/maven-tailwind-plugin](https://github.com/4ndreiDev/maven-tailwind-plugin) for detils

```xml
<plugin>
  <groupId>io.github.4ndreidev</groupId>
  <artifactId>tailwind-maven-plugin</artifactId>
  <version>1.2.0</version>
  <executions>
    <execution>
      <goals>
        <goal>compile</goal>
      </goals>
    </execution>
  </executions>
  <configuration>
    <inputFile>${project.basedir}/src/main/resources/static/css/tailwind-input.css</inputFile>
    <outputFile>${project.basedir}/src/main/resources/static/css/tailwind.css</outputFile>
  </configuration>
</plugin>
```
# SQLite database setup
To create a SQLite database from SQL commands that create a schema and populates tables,
execute the following commands

```bash
mkdir -p src/main/resources/database
cd src/main/resources/database
# Here,
#  - todo.sqlite is the database file
#  - tdo.sql is the file having SQL commands
sqlite3 todo.sqlite < todo.sql
```

# Tailwind Setup
Tailwind is a utility-first CSS framework packed with classes. In the current setup,
we shall download Tailwind binary and place it at a standard location. The binary shall
generate CSS only for the styles required. See [Tailwind CSS](https://tailwindcss.com/)

```bash
cd ~/.tailwind-maven-plugin/v4.3.3
wget https://github.com/tailwindlabs/tailwindcss/releases/download/v4.3.3/tailwindcss-linux-x64
chmod 755 tailwindcss-linux-x64
```

# DiasyUI Setup

## Install daisy files

```bash
mkdir -p src/main/resources/static/css
cd src/main/resources/static/css
wget https://github.com/saadeghi/daisyui/releases/latest/download/daisyui.mjs
wget https://github.com/saadeghi/daisyui/releases/latest/download/daisyui-theme.mjs
```
## Choose daisy theme
Select a theme name from [Daisy UI](https://daisyui.com/?lang=en)

## Install custom fonts
  - Download custom fonts from [1001fonts.com](https://www.1001fonts.com/cursive+elegant-fonts.html)
  - Extract the .ttf into `src/main/resources/static/fonts`

## Configure tailwind for daisy
Add the following to `src/main/resources/static/css/tailwind-input.css`
```css
@import "tailwindcss";

/* Exclude the downloaded script from content scanning to prevent build loops */
@source not "./daisyui.mjs";

/* Inject daisyUI using Tailwind v4 native local plugin mapping */
/* Note: Custom theme 'emerald' is selected */
@plugin "./daisyui.mjs" {
  themes: light --default, dark --prefersdark, emerald;
}

/* Configure custom fonts*/
@font-face {
    font-family: 'Magnolia';
    src: url('/fonts/Magnolia.otf') format('truetype');
    font-weight: normal;
    font-style: normal;
}

@font-face {
    font-family: 'Italianno';
    src: url('/fonts/Italianno.ttf') format('truetype');
    font-weight: normal;
    font-style: normal;
}
/* Register custom font in Tailwind */
@theme {
    --font-magnolia: 'Magnolia', sans-serif;
    --font-italianno: 'Italianno', sans-serif;
}
```
# HTMX setup

Download the latest verion of [HTMX](https://htmx.org/docs/#installing)

```bash
cd src/main/resources/static/js
wget https://cdn.jsdelivr.net/npm/htmx.org@2.0.10/dist/htmx.min.js
```

# Setup Project

## Setup application.properties

Here,
  - `todo` is the name of the applicaiton
  - `resource:database/todo.sqlite` provides relative path to DB file
  - `target/logs/app.log` log is redirect to this directory instead of console

```properties
spring.application.name=todo

# SQLite Datasource Configuration
spring.datasource.url=jdbc:sqlite::resource:database/todo.sqlite
spring.datasource.driver-class-name=org.sqlite.JDBC

# JPA / Hibernate Configuration
spring.jpa.database-platform=org.hibernate.community.dialect.SQLiteDialect
spring.jpa.hibernate.ddl-auto=none

# Disable template caching for development
# ----------------------------------------
spring.thymeleaf.cache=false
spring.web.resources.chain.cache=false
spring.web.resources.cache.cachecontrol.no-store=true

# Logging
# -------

#  Direct all application file logs to your targeted location
logging.file.name=target/logs/app.log

# Silence the console logs entirely so everything goes only to the file
logging.threshold.console=OFF

# Explicitly enable Hibernate logging levels
# This logs all SQL DML statements (SELECT, INSERT, UPDATE, DELETE)
logging.level.org.hibernate.SQL=DEBUG

# Pretty-print the SQL statements cleanly inside the log file
spring.jpa.properties.hibernate.format_sql=true

# This logs the actual prepared statement binding parameter values (e.g. replacing '?' with actual data)
logging.level.org.hibernate.orm.jdbc.bind=TRACE

# CRITICAL CRUX: Ensure this is set to false or removed entirely
# If true, Hibernate hard-bypasses the logging framework and forces text directly to STDOUT/Console!
spring.jpa.show-sql=false
```

## Project directory structure

```bash
todo
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.github.cafeduke.todo
│   │   │           ├── config
│   │   │           ├── controller
│   │   │           ├── dto
│   │   │           ├── entity
│   │   │           ├── mapper
│   │   │           ├── repository
│   │   │           └── service
│   │   └── resources
│   │       ├── database
│   │       ├── static
│   │       │   ├── css
│   │       │   ├── fonts
│   │       │   └── js
│   │       └── templates
│   │           └── fragments
│   └── test
├── target
│   └── logs
└── pom.xml
```

# Putting it all together

- Copy the [sample DaisyUI HTML file](https://github.com/4ndreiDev/maven-tailwind-plugin#step-3-create-your-html-template-with-tailwind-classes)
from [Github: maven-tailwind-plugin](https://github.com/4ndreiDev/maven-tailwind-plugin) into `src/main/resources/templates/index.html`
- Start spring boot application using `mvn spring-boot:start`
- Access `http://localhost:8080`
