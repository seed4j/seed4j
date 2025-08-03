#!/usr/bin/env bash

until [ "$(curl -s $SEED4J_SONAR_URL/api/system/status | jq -r .status)" = "UP" ]; do
  sleep 5
done

curl -sS -u admin:admin -X POST \
  "$SEED4J_SONAR_URL/api/users/change_password" \
  -d "login=admin&previousPassword=admin&password=$SEED4J_SONAR_PASSWORD"

SONAR_TOKEN=$(curl -sS -u admin:$SEED4J_SONAR_PASSWORD -X POST "$SEED4J_SONAR_URL/api/user_tokens/generate" -d "name=$SEED4J_SONAR_TOKENNAME" | jq -r .token)
echo $SONAR_TOKEN
