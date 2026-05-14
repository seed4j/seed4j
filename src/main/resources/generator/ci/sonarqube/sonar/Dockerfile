FROM alpine:3.21
RUN apk add --no-cache bash curl jq && \
    addgroup -S seed4j && \
    adduser -S -G seed4j -s /bin/bash seed4j
ENV \
  SEED4J_SONAR_URL=http://sonar:9000 \
  SEED4J_SONAR_PASSWORD=Seed4J-1339-Project \
  SEED4J_SONAR_TOKENNAME=seed4jtoken
COPY sonar/sonar_generate_token.sh /usr/local/bin/sonar_generate_token
RUN chmod 755 /usr/local/bin/sonar_generate_token
USER seed4j
CMD ["sonar_generate_token"]
