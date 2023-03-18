#!/bin/sh

set -e

# SERVER="localhost";
SERVER="local-psql"
USR="cudayanh"
PW="kien12a9";
DB="spring_security_db";
IMAGE="postgres"
PORT="5432"

# echo "echo stop & remove old docker [$SERVER] and starting new fresh instance of [$SERVER]"
# (docker restart $SERVER || :) && \
#   (docker rm $SERVER || :) && \
#   docker run --name $SERVER \
#   -e POSTGRES_USER=$USR \
#   -e POSTGRES_PASSWORD=$PW \
#   -p $PORT:$PORT \
#   -d $IMAGE

# wait for pg to start
echo "sleep wait for pg-server [$SERVER] to start";
sleep 3;

# create the db 
echo "CREATE DATABASE $DB ENCODING 'UTF-8';" | docker exec -i $SERVER psql -U $USR 
echo "\l" | docker exec -i $SERVER psql -U $USR

echo "Wait for restarting container $SERVER"
docker restart $SERVER

