FROM openjdk:21-slim AS build
COPY . /code/app/
WORKDIR /code/app/
RUN chmod +x mvnw \
    && ./mvnw package -B \
    -DskipTests \
    -Dmaven.javadoc.skip=true \
    -Dmaven.source.skip \
    -Ddevelocity.cache.remote.enabled=false \
    && mv /code/app/target/*-exec.jar /code/seed4j.jar

FROM openjdk:21-slim
COPY --from=build /code/*.jar /code/
RUN \
    # configure the "seed4j" user
    groupadd seed4j && \
    useradd seed4j -s /bin/bash -m -g seed4j -G sudo && \
    echo 'seed4j:seed4j'|chpasswd
ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS \
    JAVA_OPTS=""
USER seed4j
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/code/seed4j.jar"]
EXPOSE 7471
