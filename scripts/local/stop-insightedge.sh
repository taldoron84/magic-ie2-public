#!/usr/bin/env bash

source ./local_env.sh

echo "INSIGHTEDGE_HOME=$INSIGHTEDGE_HOME"

$INSIGHTEDGE_HOME/sbin/stop-datagrid-master.sh --master 127.0.0.1
$INSIGHTEDGE_HOME/sbin/stop-datagrid-slave.sh --master 127.0.0.1
$INSIGHTEDGE_HOME/sbin/insightedge.sh --mode shutdown
$INSIGHTEDGE_HOME/sbin/stop-zeppelin.sh

rm -rf $INSIGHTEDGE_HOME/datagrid/deploy/insightedge-space

