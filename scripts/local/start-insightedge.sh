#!/usr/bin/env bash

source ./local_env.sh

echo "INSIGHTEDGE_HOME=$INSIGHTEDGE_HOME"

#
$INSIGHTEDGE_HOME/sbin/insightedge.sh --mode master --master 127.0.0.1
$INSIGHTEDGE_HOME/sbin/insightedge.sh --mode slave --master 127.0.0.1
$INSIGHTEDGE_HOME/sbin/start-zeppelin.sh

$INSIGHTEDGE_HOME/datagrid/bin/gs-webui.sh > /dev/null 2>&1 &