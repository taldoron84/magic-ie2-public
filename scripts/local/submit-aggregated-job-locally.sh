#!/usr/bin/env bash

source ./local_env.sh

pushd ..

echo "INSIGHTEDGE_HOME=$INSIGHTEDGE_HOME"

baseDir="$(cd "`dirname "$0"`"/..; pwd)"

echo "baseDir=$baseDir"

jobJar="$baseDir/events-aggregator-job/target/events-aggregator-job.jar"

ieHost=localhost


$INSIGHTEDGE_HOME/bin/insightedge-submit \
    --class com.magic.insightedge.AggregatorJob \
    --master spark://$ieHost:7077 \
    --executor-cores 2 \
    $jobJar \
    --master-url spark://$ieHost:7077 \
    --space-name insightedge-space \
    --lookup-groups insightedge \
    --lookup-locators $ieHost

popd

    # --deploy-mode cluster \


