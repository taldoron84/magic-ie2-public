#!/usr/bin/env bash

#pushd ..
#pushd ..
#mvn xap:deploy -Dlocators=127.0.0.1
#popd
#popd



source ./local_env.sh

echo "INSIGHTEDGE_HOME=$INSIGHTEDGE_HOME"

$INSIGHTEDGE_HOME/sbin/deploy-datagrid.sh --master 127.0.0.1

