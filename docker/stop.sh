#!/bin/bash
#export KAFKA_ADVERTISED_HOST_NAME=$(ifconfig -a | sed -En 's/127.0.0.1//;s/172.*.0.1//;s/.*inet (addr:)?(([0-9]*\.){3}[0-9]*).*/\2/p')
export KAFKA_ADVERTISED_HOST_NAME=$(docker-machine ip kafka-machine)
echo "Host IP ${KAFKA_ADVERTISED_HOST_NAME}"
echo "Stop kafka"
docker-compose stop
