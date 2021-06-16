#!/bin/bash

if [[ -z $1 && -z $2 ]]; then
  echo "No IP address passed"
  exit 1
fi

export IP_ADDRESS=$1

echo "The IP address is: $1"

docker-compose down -v
docker-compose up
