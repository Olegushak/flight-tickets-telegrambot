#!/bin/bash

# Pull new changes
git pull

# Prepare Jar
mvn clean
mvn package

# Ensure, that docker-compose stopped
docker-compose stop

export TELEGRAM_NAME=$1
export TELEGRAM_TOKEN=$2

# Start new deployment
docker-compose up --build -d