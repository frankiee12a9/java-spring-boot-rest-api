<h1 align="center">Spring Boot API REST API </h1>
<p align="center">
  This is a simple REST API for a bookmark application (performing like Collections on Bing)
</p>

<br>
<br>

## Table of Contents

-   [Features](#features)
-   [Requirements](#requirements)
-   [Docker](#docker-deployment)
-   [Local installation](#local-installation)
-   [Entities](#entities)
-   [Running a specific test](#tests)
-   [Swagger](#swagger)
-   [Environment variables](#environment-variables)

# Features

<!-- <p align="center">
  <a href="https://throyer-crud-api.herokuapp.com" target="blank"><img src="./assets/images/features.jpeg"  alt="" /></a>
</p> -->

| #   | **Features**                                 | **Description**                                                                                          |
| --- | -------------------------------------------- | -------------------------------------------------------------------------------------------------------- |
| 1   | CRUD operations                              | Support `Create`, `Read`, `Update`, and `Delete` for each domain                                         |
| 2   | Lazy, Eager Loading on Entities Relationship | Entities can be loaded with its associated entities by Lazily loading and Eagerly loading configurations |
| 3   | JSON Web Token                               | JSON Web Token integration for user session Authorization and Authentication                             |
| 4   | Unit Tests & Integration Tests               | Unit test with JUnit, and Integration test with JDBC, Mocking Test and its coverage report with Jacoco   |
| 5   | Soft Delete                                  | Soft Delete with Spring data                                                                             |
| 6   | Password Recovery                            | Password recovery with code                                                                              |
| 7   | Audit Fields                                 | Entities inherited audit fields such `create_at`, `used_at`, `created_by`, etc from the base entity      |
| 8   | Docker                                       | Deployment with Docker Compose for the API and database                                                  |
| 9   | Swagger Documentation                        | Swagger documentation get generated based on controllers endpoints specification                         |

## Requirements

-   Postgres: 13+
-   Java: 17+
-   Maven: 3.8.0+
-   IntelliJ or VSCode

## Database Schema

<p>
  <img src="https://user-images.githubusercontent.com/123849429/227813000-eb6f01cb-fbc1-47ef-a2ec-ac8287a801a8.png" alt="database diagram" />
</p>

## Docker Deployment

> 🚨 create `environment` file and add permission to execute scripts
>
> ```shell
> cp .docker/.env.example .docker/.env && chmod -R +x .docker/scripts
> ```

-   docker-compose for development

    -   starting containers

    ```
    .docker/scripts/develop up -d --build
    ```

    -   removing contaiers

    ```
    .docker/scripts/develop down
    ```

    -   show backend logs

    ```
    .docker/scripts/develop logs -f api
    ```

-   docker-compose for production

    ```
    .docker/scripts/production up -d --build
    ```

    ```
    .docker/scripts/production down
    ```

## Local Installation

> 🚨 check [requirements](#requirements) or if you are using docker check [docker development instructions](#docker-examples)

-   clone the repository and redirect to the project directory.
    ```shell
    git clone git@github.com:frankiee12a9/java-spring-boot-rest-api.git && cd java-spring-boot-rest-api
    ```
-   download dependencies
    ```shell
    mvn -f api/pom.xml install -DskipTests
    ```
-   run the application (available at: [localhost:8081](http://localhost:8081))
    ```shell
    mvn clean spring-boot:run
    ```
-   running the tests
    ```shell
    mvn -f api/pom.xml test
    ```
-   to build for production
    ```shell
    mvn -f api/pom.xml clean package
    ```
-   to generate the coverage report after testing `(available at: api/target/site/jacoco/index.html)`
    ```shell
    mvn -f api/pom.xml jacoco:report
    ```

### Controller endpoints (quick reference)

| #   | Controller        | Method - URL                                | Description |
| --- | ----------------- | ------------------------------------------- | ----------- |
| 1   | Authentication    | `POST` - `/api/sessions`                    | Description |
|     |                   | `POST` - `/api/sessions/refresh`            | Description |
| 2   | User              | `GET` - `/api/users/{id}`                   | Description |
|     |                   | `GET` - `/api/users`                        | Description |
|     |                   | `PUT` - `/api/users/{id}`                   | Description |
|     |                   | `DELETE` - `/api/users/{id}`                | Description |
|     |                   | `POST` - `/api/users`                       | Description |
| 3   | Password Recovery | `POST` - `/api/recoveries`                  | Description |
|     |                   | `POST` - `/api/recoveries/update`           | Description |
|     |                   | `POST` - `/api/recoveries/confirm`          | Description |
| 4   | Role              | `GET` - `/api/roles`                        | Description |
| 5   | Collection        | `GET` - `/api/collections`                  | Description |
|     |                   | `GET` - `/api/collections`                  | Description |
|     |                   | `GET` - `/api/collections/{id}/notes`       | Description |
|     |                   | `GET` - `/api/collections/{id}/comments`    | Description |
|     |                   | `GET` - `/api/collections/sort?filter=?`    | Description |
|     |                   | `GET` - `/api/collections/search/keyword=?` | Description |
|     |                   | `PUT` - `/api/collections/{id}`             | Description |
|     |                   | `PUT` - `/api/collections/{id}/addImage`    | Description |
|     |                   | `PUT` - `/api/collections/{id}/addComment`  | Description |
|     |                   | `POST` - `/api/collections`                 | Description |
| 6   | Note              | `GET` - `/api/notes/{id}`                   | Description |
|     |                   | `GET` - `/api/notes`                        | Description |
|     |                   | `GET` - `/api/notes/{id}/images`            | Description |
|     |                   | `GET` - `/api/notes/sort?filter=?`          | Description |
|     |                   | `GET` - `/api/notes/search?keyword=?`       | Description |
|     |                   | `PUT` - `/api/notes/{id}`                   | Description |
|     |                   | `PUT` - `/api/notes/{id}/addImage`          | Description |
|     |                   | `POST` - `/api/notes`                       | Description |

**For the detailed references on the controllers endpoints, and their corresponding request params, request body, etc.. You should refer to the [Swagger documentation](#swagger) section.**

## Tests

<!--
[![Coverage Status](https://coveralls.io/repos/github/Throyer/springboot-api-crud/badge.svg?branch=master)](https://coveralls.io/repos/github/Throyer/springboot-api-crud/badge.svg?branch=master) -->

![Coverage Status](https://coveralls.io/repos/github/Throyer/springboot-api-crud/badge.svg?branch=master)

### Running a specific test

use the parameter `-Dtest=<class>#<method>`

-   for example the integration test. creating a user:
    ```shell
    mvn -f api/pom.xml test -Dtest=UsersControllerTests#should_save_a_new_user
    ```

## Swagger

Once the application is fired up, it is available at: [localhost:8080/docs](http://localhost:8081/docs)

> 🚨 if you set `SWAGGER_USERNAME` and `SWAGGER_PASSWORD` on [application.properties](https://github.com/Throyer/springboot-api-rest-example/blob/master/api/src/main/resources/application.properties#L35) file this route require authentication

---

## Environment variables

| **Description**                          | **Parameter**                      | **Default values** |
| ---------------------------------------- | ---------------------------------- | ------------------ |
| server port                              | `SERVER_PORT`                      | 8080               |
| database host                            | `DB_HOST`                          | localhost          |
| database port                            | `DB_PORT`                          | 5432               |
| database name                            | `DB_NAME`                          | example            |
| database username                        | `DB_USERNAME`                      | root               |
| database user password                   | `DB_PASSWORD`                      | root               |
| displays the generated sql in the logger | `DB_SHOW_SQL`                      | false              |
| set maximum database connections         | `DB_MAX_CONNECTIONS`               | 5                  |
| secret value in token generation         | `TOKEN_SECRET`                     | secret             |
| secret hash ids                          | `HASHID_SECRET`                    | secret             |
| token expiration time in hours           | `TOKEN_EXPIRATION_IN_HOURS`        | 24                 |
| refresh token expiry time in days        | `REFRESH_TOKEN_EXPIRATION_IN_DAYS` | 7                  |
| SMTP server address                      | `SMTP_HOST`                        | smtp.gmail.com     |
| SMTP server port                         | `SMTP_PORT`                        | 587                |
| SMTP username                            | `SMTP_USERNAME`                    | user               |
| SMTP server password                     | `SMTP_PASSWORD`                    | secret             |
| time for recovery email to expire        | `MINUTES_TO_EXPIRE_RECOVERY_CODE`  | 20                 |
| max requests per minute                  | `MAX_REQUESTS_PER_MINUTE`          | 50                 |
| swagger url                              | `SWAGGER_URL`                      | /docs              |
| swagger username                         | `SWAGGER_USERNAME`                 | `null`             |
| swagger password                         | `SWAGGER_PASSWORD`                 | `null`             |

> these variables are defined in: [**application.properties**](./src/main/resources/application.properties)
>
> ```shell
> # to change the value of some environment variable at runtime
> # on execution, just pass it as a parameter. (like --SERVER_PORT=80).
> $ java -jar api-5.0.0.jar --SERVER_PORT=80
> ```

<!-- -   Referenced auth project (appear when complete): https://github.com/Throyer/springboot-api-rest-example -->
