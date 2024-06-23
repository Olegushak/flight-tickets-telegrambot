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
export BOT_DB_USERNAME='ftt_db_user'
export BOT_DB_PASSWORD='ftt_db_password'
export RAPID_API_KEY='8091030ab3msh4d1b5bf2b0f195dp1235cejsndf87ab9629d2'

# Start new deployment
docker-compose up --build -d