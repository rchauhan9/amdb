# Alternative Movie Database

This application is the core to an alternative movie database service. 
It provides a GraphQL API to interact with in order to access movie information
from a Neo4j database. Below are some basic instructions for setting up the 
service to run locally.

### 1. Creating a local Neo4j DB Server using Docker.
Ensure you have Docker and docker-compose installed on your computer. From the root
directory of this project simply run the command...
```
docker-compose up -d
```
The [docker-compose.yml](./docker-compose.yml) file has instructions to create a
container running a Neo4j database server with mapped ports 7474 for HTTP and 7687 for Bolt
(a convention for Neo4j). 

You may access the Neo4j brower via http://localhost:7474.If this is your first time 
running Neo4j locally you will be prompted for a username and password both usually
set to default 'neo4j'. You can change these as you wish, but be sure to update the
[application.properties](./src/main/resources/application.properties) file with the
appropriate credentials.

### 2. Running the Application.
The application can be run from the 
[AmdbApplication](./src/main/java/com/rchauhan/amdb/AmdbApplication.java) file.
No additional setup is required.

### 3. Accessing the API.
The API is accessible using a tool like GraphiQL which specialises in hitting GraphQL
endpoints. Of course, you can build your own GraphQL client to interact with the API
as well. The schema are located [here](./src/main/resources/graphql).

### 4. Running the tests.
The codebase also contains a suite of unit and graphql integration tests. These can
be run from inside the files themselves or run as a whole from the 
[folder](./src/main/test/java).