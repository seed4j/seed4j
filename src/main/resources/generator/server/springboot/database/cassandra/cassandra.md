# Setting up your Keyspace

In `src/main/resources/config/application.properties`, uncomment `spring.cassandra.keyspace-name` and set the right keyspace.

# Usage

You can start a Cassandra instance by running

```bash
docker compose -f src/main/docker/cassandra.yml up -d
```

You can interact with your database by running

```bash
docker exec -it cassandra cqlsh
```
