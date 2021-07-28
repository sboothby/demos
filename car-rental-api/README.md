# Car Rental API
An example Spring Boot API for renting vehicles.  The following are demonstrated:
* Spring MVC Rest endpoints (Controller)
* Configuration beans (DI)
* Service business logic beans
* Unit tests with mocking techniques
* Container deployment (docker)

## Deploying - Running Locally
Build docker image locally:

```
docker build -t car-rental-api .
```

Start docker container. Service is available on port 8090:

```
docker run -p 8090:8080 car-rental-api
```
