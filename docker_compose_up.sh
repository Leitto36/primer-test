#!/usr/bin/env bash
./gradlew :bootJar
docker build -t primer-demo .
docker-compose -f src/test/resources/docker-compose.yml -p primer-demo up
