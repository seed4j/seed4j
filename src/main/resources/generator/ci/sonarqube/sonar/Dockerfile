FROM ubuntu:24.04
RUN \
  apt update && \
  apt --no-install-recommends install -y curl jq && \
  groupadd seed4j && \
  useradd seed4j -s /bin/bash -m -g seed4j -G sudo && \
  echo 'seed4j:seed4j'|chpasswd && \
  apt clean
ENV \
  SEED4J_SONAR_URL=http://sonar:9000 \
  SEED4J_SONAR_PASSWORD=Seed4J-7471-Project \
  SEED4J_SONAR_TOKENNAME=seed4jtoken
COPY sonar/sonar_generate_token.sh /usr/local/bin/sonar_generate_token
RUN chmod 755 /usr/local/bin/sonar_generate_token
USER seed4j
CMD ["sonar_generate_token"]
