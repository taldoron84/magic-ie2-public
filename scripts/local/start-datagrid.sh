#!/usr/bin/env bash

source ./local_env.sh

echo "INSIGHTEDGE_HOME=$INSIGHTEDGE_HOME"

$INSIGHTEDGE_HOME/sbin/start-datagrid-master.sh --master 127.0.0.1
$INSIGHTEDGE_HOME/sbin/start-datagrid-slave.sh --master 127.0.0.1