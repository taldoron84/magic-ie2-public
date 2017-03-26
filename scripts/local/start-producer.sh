#!/usr/bin/env bash

source ./local_env.sh

pushd ..

echo "INSIGHTEDGE_HOME=$INSIGHTEDGE_HOME"

baseDir="$(cd "`dirname "$0"`"/..; pwd)"

echo "baseDir=$baseDir"

producerJar="$baseDir/events-producer/target/magic-events-producer.jar"

java -jar $producerJar
#java -jar $producerJar csv.location="/Users/tal/Downloads/temp2.csv"


popd


