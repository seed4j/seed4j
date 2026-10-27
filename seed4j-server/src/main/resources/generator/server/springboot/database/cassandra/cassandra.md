# Usage

You can start a Cassandra instance by running

```bash
docker compose -f src/main/docker/cassandra.yml up -d
```

You can interact with your database by running

```bash
docker exec -it cassandra cqlsh
```

# Setting up your Keyspace

Current version of cassandra module doesn't create a default keyspace.

If your project doesn't have one, create a keyspace with

```bash
docker exec cassandra cqlsh -e "CREATE KEYSPACE projectKeyspace WITH replication={'class' : 'SimpleStrategy', 'replication_factor':1}"
```

Then, in `src/main/resources/config/application.properties`, uncomment `spring.cassandra.keyspace-name` and set its value to `projectKeyspace`
