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
    - 8082:8081
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
b0714f8522e2   maven:3.8.5-openjdk-17   "mvn clean spring-bo…"   50 minutes ago      Up 50 minutes      0.0.0.0:8082->8081/tcp                       bookmark-api-dev
03499e065a8e   postgres:13              "docker-entrypoint.s…"   About an hour ago   Up About an hour   0.0.0.0:5433->5432/tcp                       bookmark-api-db
```

Listing the currently created Docker compose

```
$ docker compose ls
NAME                STATUS              CONFIG FILES
bookmark-app        running(2)          C:\Users\<name.\<path>\<path>\<project>\.docker\docker-compose.dev.yml
```

After the application starts, navigate to `http://localhost:8082/api` in your web browse OR just run:

```
$ curl localhost:8082/api
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100    50    0    50    0     0   6930      0 --:--:-- --:--:-- --:--:--  8333
{
  "message": "Hasta la vista, baby. - Terminator 2"
}
```

If the application was fired up but is not running. check it logs:

```
$ docker logs <container_name>
```

Also, check the network defined in the docker.compose file
```
$ docker network inspect bookmark-app_bookmark-api-net
[
    {
        "Name": "bookmark-app_bookmark-api-net",
        "Id": "ca6c3a94b62c1c8e0c2a55897d7c893f56383e6d4bae3eb2900d1ffa83d24649",
        "Created": "2023-03-29T04:02:29.563626808Z",
        "Scope": "local",
        "Driver": "bridge",
        "EnableIPv6": false,
        "IPAM": {
            "Driver": "default",
            "Options": null,
            "Config": [
                {
                    "Subnet": "172.20.0.0/16",
                    "Gateway": "172.20.0.1"
                }
            ]
        },
        "Internal": false,
        "Attachable": false,
        "Ingress": false,
        "ConfigFrom": {
            "Network": ""
        },
        "ConfigOnly": false,
        "Containers": {
            "2f8c7493479d386b64108008f833bf2ccc2df1d66b0175e98fa1de10804a6159": {
                "Name": "bookmark-api-dev",
                "EndpointID": "13e1ab0baca0520b0eb4e6fb5505daa8b9b64f2f1de8ce7328de2d294c582e28",
                "MacAddress": "02:42:ac:14:00:02",
                "IPv4Address": "172.20.0.2/16",
                "IPv6Address": ""
            },
            "bd7277d736e607b5f5ac4be681c437042ba930b2d1fbbabd93cb13db3cc8b949": {
                "Name": "bookmark-api-db",
                "EndpointID": "692133c5a4307dd7f23ce65196344af1fcb5d7dfbe38274ce227cb23ad8db87c",
                "MacAddress": "02:42:ac:14:00:03",
                "IPv4Address": "172.20.0.3/16",
                "IPv6Address": ""
            }
        },
        "Options": {},
        "Labels": {
            "com.docker.compose.network": "bookmark-api-net",
            "com.docker.compose.project": "bookmark-app",
            "com.docker.compose.version": "2.15.1"
        }
    }
]
```
You should verify that there are two containers `bookmark-api-dev` and `bookmark-api-db` are appeared in the Containers field. 
