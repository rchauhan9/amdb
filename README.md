# Alternative Movie Database

This service is the core to AMDb (Alternative Movie Database) application. 
It provides a GraphQL API to interact with in order to access movie information
from a Neo4j database. Below are some basic instructions for setting up the 
service to run locally.

## AMDb without Docker (IntelliJ)
You may not want to package the app into a container for local development, so you can opt
to run AMDb locally by selecting the [LOCAL](./.run/LOCAL.run.xml) configuration file 
in Intellij's run box and clicking Run (you will still need a docker instance of Neo4j running).

## Docker Compose
The [docker-compose.yml](./docker-compose.yml) file can be used for starting up a Neo4j
 database and the GraphQL API this repo creates each in separate containers. One container
 is based off the image provided by Neo4j and the other based on the Dockerfile found 
 within this repository.
### A Neo4j container
The Neo4j database server has mapped ports 7474 for HTTP and 7687 for Bolt
(a convention for Neo4j). 

You may access the Neo4j browser via http://localhost:7474.If this is your first time 
running Neo4j locally you will be prompted for a username and password both usually
set to default 'neo4j'. You can change these as you wish, but be sure to update the
[application.properties](./src/main/resources/application.properties) file with the
appropriate credentials.

### An AMDb Spring App container
To create the .jar necessary for packaging into the Docker container, first run 
```
mvn clean install
```
Ensure the .jar name is represented appropriately in the [Dockerfile](./Dockerfile) and 
change if necessary.

### Lift off
Ensure you have Docker and docker-compose installed on your computer. From the root 
directory of this project simply run the command...
```
docker-compose up -d
```

## Other Information
### Accessing the API.
The API is accessible using a tool like GraphiQL which specialises in hitting GraphQL
endpoints. Of course, you can build your own GraphQL client to interact with the API
as well. The schema are located [here](./src/main/resources/graphql).
For a quick verification that the api is running and accessible, you can send the below 
query as a healthcheck as this does not rely on Neo4j.
```
query {
  healthcheck(info: "Some information")
}
```
A healthy response should look like the below.
```
{
  "data": {
    "healthcheck": "Healthcheck: Some information"
  }
}
```
### Running the tests.
The codebase also contains a suite of unit and GraphQL integration tests. These can
be run from inside the files themselves or run as a whole from the 
[folder](./src/main/test/java).