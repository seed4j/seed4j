# Usage

Start a local Sonar server with:

```bash
docker compose -f src/main/docker/sonar.yml up -d
```

You need to wait for Sonar to be up before getting the Sonar token:

```bash
docker logs -f sonar-token && SONAR_TOKEN=$(docker logs sonar-token)
```

Then, run a Sonar analysis:

For Maven

```bash
./mvnw clean verify sonar:sonar -Dsonar.token=$SONAR_TOKEN
```

You can use a single command:

```bash
docker compose -f src/main/docker/sonar.yml up -d && \
docker logs -f sonar-token && SONAR_TOKEN=$(docker logs sonar-token) && \
./mvnw clean verify sonar:sonar -Dsonar.token=$SONAR_TOKEN
```

or

For Gradle

```bash
./gradlew clean build sonar --info -Dsonar.token=$SONAR_TOKEN
```

You can use a single command:

```bash
docker compose -f src/main/docker/sonar.yml up -d && \
docker logs -f sonar-token && SONAR_TOKEN=$(docker logs sonar-token) && \
./gradlew clean build sonar --info -Dsonar.token=$SONAR_TOKEN
```

You can see the result at [http://localhost:9001/](http://localhost:9001/)

[Official documentation](https://www.sonarsource.com/products/sonarqube/)
