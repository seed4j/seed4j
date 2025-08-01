#!/usr/bin/env bash

PORT=$1
if [[ $PORT == '' ]]; then
  echo "*** Using default port 8081"
  PORT='8081'
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

echo "*** Identifying application executable..."
export EXEC_JAR=$(
  find . -maxdepth 1 -name "*-exec.jar" | grep . \
    || find . -maxdepth 1 -name "*.jar" | grep -v "\-javadoc" | grep -v "\-sources" | grep -v "\-tests" | grep -v "\-plain"
)

echo "*** Starting application using ${EXEC_JAR}..."
java \
  -jar ${EXEC_JAR} \
  --logging.level.ROOT=OFF &
> /dev/null
echo $! > .pid-seed4j

retryCount=1
maxRetry=30
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
  exit 1
fi
