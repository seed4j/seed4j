#!/usr/bin/env bash

show_syntax() {
  echo "Usage: $0 <docker-image>" >&2
  exit 1
}

if [ "$#" -ne 1 ]; then
  show_syntax
fi

DOCKER_IMAGE=$1
PORT=7471

# Start docker container
echo "*** Starting docker container using image ${DOCKER_IMAGE}..."
docker run -p $PORT:7471 -d ${DOCKER_IMAGE} > .container_seed4j

retryCount=1
maxRetry=10
httpUrl="http://localhost:$PORT/management/health"

rep=$(curl -v "$httpUrl")
status=$?
while [ "$status" -ne 0 ] && [ "$retryCount" -le "$maxRetry" ]; do
  echo "*** [$(date)] Application not reachable yet. Sleep and retry - retryCount =" $retryCount "/" $maxRetry
  retryCount=$((retryCount + 1))
  sleep 5
  rep=$(curl -v "$httpUrl")
  status=$?
done

if [ "$status" -ne 0 ]; then
  echo "*** [$(date)] Not connected after" $retryCount " retries."
  echo "*** Container logs:"
  docker logs "$(cat .container_seed4j)"
  exit 1
fi
