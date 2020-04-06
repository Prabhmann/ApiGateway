#!/bin/bash
sudo docker run --rm --name pg_gateway_db -d -p 5432:5432 -e POSTGRES_USER=gateway -e POSTGRES_PASSWORD=gateway -e POSTGRES_DB=gateway_db postgres:9.6
./gradlew clean build -x test
sudo docker build -t api-gateway .
sudo docker run api-gateway
