#!/usr/bin/env bash

source ./local_env.sh

pushd ..

echo "INSIGHTEDGE_HOME=$INSIGHTEDGE_HOME"

baseDir="$(cd "`dirname "$0"`"/..; pwd)"

echo "baseDir=$baseDir"

streamJar="$baseDir/events-streaming/target/events-streaming.jar"

ieHost=localhost
zookeeper=localhost:2181
checkpointDir="magic-checkpoint"

$INSIGHTEDGE_HOME/bin/insightedge-submit \
    --class com.magic.insightedge.EventsStreamApp \
    --master spark://$ieHost:7077 \
    --executor-cores 2 \
    $streamJar \
    --master-url spark://$ieHost:7077 \
    --zookeeper $zookeeper \
    --group-id events-processing \
    --space-name insightedge-space \
    --lookup-groups insightedge \
    --lookup-locators $ieHost \
    --batch-duration 1 \
    --checkpoint-dir "C1"

popd

    # --deploy-mode cluster \


