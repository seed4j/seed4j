FROM alpine:3.22.4
RUN \
  apk update && \
  apk add --no-cache curl jq && \
  addgroup -S seed4j && \
  adduser -S -s /bin/ash -D -H -G seed4j seed4j && \
  echo 'seed4j:seed4j'|chpasswd
ENV \
  SEED4J_SONAR_URL=http://sonar:9000 \
  SEED4J_SONAR_PASSWORD=Seed4J-1339-Project \
  SEED4J_SONAR_TOKENNAME=seed4jtoken
COPY sonar/sonar_generate_token.sh /usr/local/bin/sonar_generate_token
RUN chmod 755 /usr/local/bin/sonar_generate_token
USER seed4j
CMD ["sonar_generate_token"]
