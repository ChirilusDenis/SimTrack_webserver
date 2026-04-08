# SimTrack

This is the backend for an application to organize and keep track of sim-racing championships. 

To start the backend install docker and docker compose from https://docs.docker.com/engine/install/ and enter the command below to launch the Postgresql database while in the Deployment folder:

```shell showLineNumbers
docker-compose -f docker-compose.yml up -d
```

You can use PGAdmin (https://www.pgadmin.org/) or DBeaver (https://dbeaver.io/download/) to access the database on localhost:5432 with database/user/password "postgres".

In order to run the application you need to have maven installed (https://maven.apache.org/install.html) and run the following commands:

```shell showLineNumbers
mvn clean install
mvn spring-boot:run
http://localhost:8090/swagger-ui/index.html
```

# End points

POST /auth/register

POST /auth/login

## Championships

POST /championships          (organizer)

GET /championships

POST /championships/{id}/apply

GET /championships/{id}/participants

POST /championships/{id}/close          (organizer)

POST /championships/{id}/end          (organizer)

## ChampionshipEntries

GET /championshipentries/pending

GET /championshipentries/mine

POST /championshipentries/{id}/accept          (organizer)

POST /championshipentries/{id}/reject          (organizer)

## Races

POST /championships/{id}/race          (organizer)

GET /championships/{id}/races

## Submissions

POST /submissions/race/{id}/submit

GET /submissions

GET /submissions/mine

POST /submissions/{id}/validate          (organizer)

POST /submissions/{id}/reject          (organizer)