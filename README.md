sample-service
==============
Simple CRUD service

## Tech Stack

+ Java
+ Spring Boot
+ In memory Db(H2)

## Endpoints

`GET /user/{userId}/interests` Fetch all interests for a user

`POST /user/{userId}/interest` Create an interest for a user

`PUT /user-interest/{interestId}` Update an interest

`DELETE /user-interest/{interestId}` Delete an interest

## Configuration

### Spring Boot Configuration

`src/test/resources/application.yaml` contains settings that can be overridden with environment specific values.

### To run this service locally

Execute maven command

    mvn exec:java@run-locally
Or run the class from IDE `com.sample.UserInterestApplication`

## Test data

- Three sample users are created with ids 1,2,3.
- Sample create request
```
curl -X POST 'http://localhost:8900/user/1/interest' \
-H 'Content-Type: application/json' \
--data-raw '{
"type": "CREDIT",
"name": "Interest 1",
"region": "Europe",
"language": "English"
}'
```