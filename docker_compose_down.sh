#!/usr/bin/env bash
docker-compose -f src/test/resources/docker-compose.yml -p primer-demo down --remove-orphans
