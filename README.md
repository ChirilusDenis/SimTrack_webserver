# MobyLabWebProgramming

This is an example application to teach students the basics of web programming.

To start working with the backend install docker and docker compose from https://docs.docker.com/engine/install/ and enter the command below to launch the Postgresql database while in the Deployment folder:

```shell showLineNumbers
docker-compose -f docker-compose.yml up -d
```

You can use PGAdmin (https://www.pgadmin.org/) or DBeaver (https://dbeaver.io/download/) to access the database on localhost:5432 with database/userOld/password "postgres".

In order to run the application you need to have maven installed (https://maven.apache.org/install.html) and run the following commands:

```shell showLineNumbers
mvn clean install
mvn spring-boot:run
firefox http://localhost:8090/swagger-ui/index.html
```
# TODO
 CHECK CONFIG PACKAGE

# End points

POST /auth/register

POST /auth/login

## Championships

~~POST /championships          (organizer)~~

~~GET /championships~~

~~POST /championships/{id}/apply~~

~~GET /championships/{id}/participants~~

~~POST /championships/{id}/close~~

## ChampionshipEntries

~~GET /championshipentries/pending~~

~~POST /championshipentries/{id}/accept~~

~~POST /championshipentries/{id}/reject~~

## Races

~~POST /championships/{id}/race~~

~~GET /championships/{id}/races~~

## Submissions

POST /submissions/race/{id}/submit

GET /submissions

POST /submissions/{id}/validate

POST /submissions/{id}/reject