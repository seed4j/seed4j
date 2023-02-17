#!/bin/bash

PORT=$1
if [[ $PORT == '' ]]; then
  echo "*** Using default port 8080"
  PORT='8080'
fi

echo "*** Waiting 5sec to be sure the Jar is here"
sleep 5

echo "*** List folder"
ls -al

if test -f "mvnw"; then
  cd target/
elif test -f "gradlew"; then
  cd build/libs/
fi

echo "*** Removing other jar files..."
rm *-javadoc.jar *-sources.jar *-tests.jar

echo "*** Starting application..."
java \
  -jar *.jar \
  --logging.level.ROOT=OFF & > /dev/null
echo $! > .pid-jhlite

retryCount=1
maxRetry=30
httpUrl="http://localhost:"$PORT"/management/health"

rep=$(curl -v "$httpUrl")
status=$?
while [ "$status" -ne 0 ] && [ "$retryCount" -le "$maxRetry" ]; do
  echo "*** [$(date)] Application not reachable yet. Sleep and retry - retryCount =" $retryCount "/" $maxRetry
  retryCount=$((retryCount+1))
  sleep 5
  rep=$(curl -v "$httpUrl")
  status=$?
done

if [ "$status" -ne 0 ]; then
  echo "*** [$(date)] Not connected after" $retryCount " retries."
  return 1
fi
