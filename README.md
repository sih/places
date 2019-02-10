# Places sample app

## Overview
This is a "Hello, World!" toy microservices project. Currently it uses:
* Spring Boot
* Docker


## Running on localhost 
* Build with the localhost `spring.jpa.datasource.url` in `application.yml`
* Ensure you have a MySQL datastore running on 3306
  * There should be a places database set up
    * `CREATE database places;`
  * You will need to set up a non-root user ...
    * `CREATE user 'janedoe'@'localhost' IDENTIFIED BY '<password>';`
  * ... and grant them access to the places database
    * `GRANT ALL PRIVILEGES ON places.* to 'janedoe'@'localhost';`
* Connect to the MySQL server and run in some sample data
  * There is some test data at `.\src\test\resources\test-data-london.sql`
* Build the file `mvn clean install` ...
* ... and then run, passing the db credentials on the command line
  * `java -jar ./target/places-0.1.0.jar --spring.datasource.username=janedoe --spring.datasource.password=<password>`
* Access the application on [localhost:8097](http://localhost:8097/place/1))

## Docker commands
* Use the placesMySql `spring.jpa.datasource.url` in `application.yml`
* Use the Spotify Maven plugin to build the application
  * `mvn clean install dockerfile:build`
* Start a MySql Docker service, passing the root and application credentials
  ```
    docker run --rm -t --name placesServer \
        --link placesMySql:mysql \
        -p 8097:8097 \
        dsoc/places \
        --spring.datasource.url=jdbc:mysql://docker.for.mac.host.internal:8306/places \
        --spring.datasource.username=<usr> \
        --spring.datasource.password=<psw>
      ``` 
     
* Some things to note:
   * The MySql Docker image also has a schema and some sample data
     * This is done by adding an sql script to the init dir - [see Dockerfile](https://github.com/sih/places-mysql/blob/master/Dockerfile)
 * You can connect to this database via
   * `mysql places -u <usr> -p --host=127.0.0.0 --port=8306`
 * Run the Spring Boot application 
   ```
    docker run --rm -t --name placesServer \
        --link placesMySql:mysql \
        -p 8097:8097 \
        dsoc/places \
        --spring.datasource.url=jdbc:mysql://docker.for.mac.host.internal:8306/places \
        --spring.datasource.username=<usr> \
        --spring.datasource.password=<psw>
   ```
 
 * Note that this is set up to cope with Docker / Mac network issues
   * Run this on other OS by replacing the `docker.for.mac.host.internal` address with the IP of the docker machine