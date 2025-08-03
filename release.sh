#!/usr/bin/env bash

GIT_MAIN_BRANCH='main'
GIT_REMOTE='upstream'

show_syntax() {
  echo "You want to release a new version"
  echo "  - current version: ${currentVersion}"
  echo "  - release version: ${releaseVersion} (which is a patch)"
  echo " "
  echo "Usage: $0 <patch|minor|major>" >&2
  exit 1
}

currentVersion=$(./mvnw help:evaluate -Dexpression=project.version -q -DforceStdout)
releaseVersion=${currentVersion//-SNAPSHOT/}

checkGit=$(git status --porcelain | wc -l)
if [[ $checkGit != 0 ]]; then
  echo "*** check: there are uncommitted changes..."
  echo " "
  show_syntax
fi

if [ "$#" -ne 1 ]; then
  show_syntax
fi

echo "*** git: update project..."
git switch $GIT_MAIN_BRANCH
git fetch $GIT_REMOTE
git rebase $GIT_REMOTE/$GIT_MAIN_BRANCH

echo "*** use specific settings.xml"
mv ~/.m2/settings.xml ~/.m2/settings.xml.save 2> /dev/null
cp ~/.m2/personal.settings.xml ~/.m2/settings.xml

if [[ "$1" == "patch" ]]; then
  echo "*** version: remove SNAPSHOT and keep the version"
  ./mvnw versions:set -DremoveSnapshot versions:commit -q

elif [[ "$1" == "minor" ]]; then
  echo "*** version: remove SNAPSHOT and change to minor version"
  ./mvnw versions:set -DremoveSnapshot versions:commit -q
  ./mvnw build-helper:parse-version versions:set \
    -DnewVersion=\${parsedVersion.majorVersion}.\${parsedVersion.nextMinorVersion}.0 \
    versions:commit -q
elif [[ "$1" == "major" ]]; then
  echo "*** version: remove SNAPSHOT and change to major version"
  ./mvnw versions:set -DremoveSnapshot versions:commit -q
  ./mvnw build-helper:parse-version versions:set \
    -DnewVersion=\${parsedVersion.nextMajorVersion}.0.0 \
    versions:commit -q
else
  show_syntax
fi

echo "*** calculate release version..."
releaseVersion=$(./mvnw help:evaluate -Dexpression=project.version -q -DforceStdout)

echo "*** update version in package.json"
npm version "${releaseVersion}" --no-git-tag-version

echo "*** git: commit, tag and push tag..."
git add . && git commit -m "Release v${releaseVersion}"
git tag -a v"${releaseVersion}" -m "Release v${releaseVersion}"
git push $GIT_REMOTE v"${releaseVersion}"

echo "*** build and publish to maven central"
./mvnw deploy -DskipTests -Drelease

echo "*** version: add SNAPSHOT"
./mvnw build-helper:parse-version versions:set \
  -DnewVersion=\${parsedVersion.majorVersion}.\${parsedVersion.minorVersion}.\${parsedVersion.nextIncrementalVersion}-SNAPSHOT \
  versions:commit -q

echo "*** put back old settings.xml"
rm ~/.m2/settings.xml
mv ~/.m2/settings.xml.save ~/.m2/settings.xml 2> /dev/null

echo "*** git: commit, push to $GIT_MAIN_BRANCH..."
nextVersion=$(./mvnw help:evaluate -Dexpression=project.version -q -DforceStdout)
npm version "${nextVersion}" --no-git-tag-version
git add . && git commit -m "Update to next version v${nextVersion}"
git push $GIT_REMOTE $GIT_MAIN_BRANCH
