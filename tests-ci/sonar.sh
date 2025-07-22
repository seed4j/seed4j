#!/usr/bin/env bash

application=$1
if [[ $application == '' ]]; then
  echo "using jhlite by default"
  application='jhlite'
fi

retry_count=0
max_retries=30
success=false

echo $SONAR_TOKEN
echo $application

while [[ $retry_count -lt $max_retries ]]; do
  sonar=$(curl -u "$SONAR_TOKEN:" -s 'http://localhost:9001/api/measures/component?component='"$application"'&metricKeys=bugs%2Ccoverage%2Cvulnerabilities%2Cduplicated_lines_density%2Ccode_smells%2Csecurity_hotspots')
  echo "sonar analysis response: $sonar"

  error=$(echo $sonar | jq -r .errors)
  measures_length=$(echo "$sonar" | jq '.component.measures | length')

  if [[ $error == null && $measures_length -gt 0 ]]; then
    success=true
    break
  else
    echo "Attempt $((retry_count + 1))/$max_retries failed: $error"
    ((retry_count++))
    sleep 1
  fi
done

if [[ $success == false ]]; then
  echo "Failed to get Sonar analysis after $max_retries attempts."
  exit 1
fi

measure() {
  echo "$sonar" | jq -r .component | jq -r .measures | jq '[.[]|select(.metric=="'$1'")][0]' | jq -r .value
}

vul=$(measure "vulnerabilities")
cov=$(measure "coverage")
bug=$(measure "bugs")
dup=$(measure "duplicated_lines_density")
csm=$(measure "code_smells")
sec=$(measure "security_hotspots")

echo "----- Local Sonar Analysis -----"
echo "  Coverage:          $cov"
echo "  Vulnerabilities:   $vul"
echo "  Bugs:              $bug"
echo "  Duplication:       $dup"
echo "  Code smells:       $csm"
echo "  Security Hotspots: $sec"
echo "--------------------------------"

fail() {
  echo "fail application="$application
  local app=$1
  echo
  echo 'List of all errors:'

  sonar=$(curl -u "$SONAR_TOKEN:" -s 'http://localhost:9001/api/measures/component?component='"$application"'&metricKeys=bugs%2Ccoverage%2Cvulnerabilities%2Cduplicated_lines_density%2Ccode_smells%2Csecurity_hotspots')
  echo "sonar analysis response: $sonar"

  sonar_fail=$(curl -u "$SONAR_TOKEN:" -s 'http://localhost:9001/api/issues/search?componentKeys='"$app"'&resolved=false')
  echo "sonar analysis response: $sonar_fail"
  echo $sonar_fail | jq
  echo $sonar_fail | jq '.issues[] | {file: "\(.component)#\(.line)", error: "[\(.rule)] \(.message)"}'

  sonar_fail=$(curl -u "$SONAR_TOKEN:" -s 'http://localhost:9001/api/issues/search?component='"$app"'&resolved=false')
  echo "sonar analysis response: $sonar_fail"
  echo $sonar_fail | jq
  echo $sonar_fail | jq '.issues[] | {file: "\(.component)#\(.line)", error: "[\(.rule)] \(.message)"}'

  sleep 10

  docker logs sonar

  exit 1
}

if [[ $vul != "0" ]]; then
  echo "Sonar Analysis failed -> Vulnerabilities"
  fail "$application"
fi

if [[ $bug != "0" ]]; then
  echo "Sonar Analysis failed -> Bugs"
  fail "$application"
fi

if [[ $dup != "0.0" ]]; then
  echo "Sonar Analysis failed -> Duplication"
  fail "$application"
fi

if [[ $csm != "0" ]]; then
  echo "Sonar Analysis failed -> Code smells"
  fail "$application"
fi

if [[ $sec != "0" ]]; then
  echo "Sonar Analysis failed -> Security Hotspots"
  fail "$application"
fi

echo "Sonar Analysis is passed"
