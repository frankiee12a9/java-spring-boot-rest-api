## Docker Compose Specification

### Use with Docker Development Environments

You can open this sample in the Dev Environments feature of Docker Desktop version 4.12 or later.

[Open in Docker Dev Environments <img src="../open_in_new.svg" alt="Open in Docker Dev Environments" align="top"/>](https://open.docker.com/dashboard/dev-envs?url=https://github.com/docker/awesome-compose/tree/master/spring-postgres)

### Java Spring Boot and a PostgreSQL database Application

Project structure:

```
|___.docker
|   |__scripts
|   |  |__...
|   |__ docker-compose.dev.yml
|   |__ docker-compose.prod.yml
├── backend
│   ├── docker/Dockerfile
│   └── ...
└── README.md

```

[docker-compose.dev.yml](./docker/docker-compose.dev.yaml)

Database service:

```
database:
  image: postgres:13
  restart: unless-stopped
  container_name: bookmark-api-db
  ports:
      - '5433:5432'
...
```

API service:

```
services:
  backend:
    build: backend
    ports:
    - 8081:8081
  db:
    image: postgres
...
```

The compose file defines an application with two services `api` and `database`.
When deploying the application, docker compose maps port 8081 of the backend service container to port 8081 of the host machine as specified in the file.
Make sure port 8081 on the host is not already being in use.

## Test and Deploy

🚨 create `environment` file and add permission to execute scripts

```shell
$ cp .docker/.env.example .docker/.env && chmod -R +x .docker/scripts
```

docker-compose for development

-   starting containers

```
.docker/scripts/develop up -d --build
```

-   removing containers

```
.docker/scripts/develop down
```

-   show backend logs

```
.docker/scripts/develop logs -f api
```

docker-compose for production

-   starting containers

```
.docker/scripts/production up -d --build
```

-   removing containers

```
.docker/scripts/production down
```

## Expected result

Listing containers must show two containers running and the port mapping as below:

```
$ docker ps
CONTAINER ID        IMAGE                     COMMAND                  CREATED             STATUS              PORTS                  NAMES
b0714f8522e2   maven:3.8.5-openjdk-17   "mvn clean spring-bo…"   50 minutes ago      Up 50 minutes      0.0.0.0:8081->8081/tcp                       bookmark-api-dev
03499e065a8e   postgres:13              "docker-entrypoint.s…"   About an hour ago   Up About an hour   0.0.0.0:5433->5432/tcp                       bookmark-api-db
```

Listing the currently created Docker compose

```
$ docker compose ls
NAME                STATUS              CONFIG FILES
bookmark-app        running(2)          C:\Users\<name.\<path>\<path>\<project>\.docker\docker-compose.dev.yml
```

After the application starts, navigate to `http://localhost:8081` in your web browse OR just run:

```
$ curl localhost:8081
```

If the application was fired up but is not running. check it logs:

```
$ docker logs <container_name>
```
