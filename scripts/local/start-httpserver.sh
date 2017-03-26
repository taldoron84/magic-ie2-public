#!/usr/bin/env bash

source ./local_env.sh

echo "KAFKA_HOME=$KAFKA_HOME"

java -cp ../../../word-count-0.1-SNAPSHOT-jar-with-dependencies.jar scala.HttpServer
