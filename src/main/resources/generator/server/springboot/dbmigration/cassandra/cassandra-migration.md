# Cassandra Migration tool

## How it works?

Similar to Liquibase, Seed4J provides a tool to apply your CQL migration scripts, with some restrictions:

- The tool is not run by the application itself when it is started but inside a Docker container or manually
- All CQL scripts must follow the pattern `{timestamp}_{description}.cql` and be placed in the changelog directory: `src/main/resources/config/cql/changelog/`
- All non already applied scripts located in the changelog directory are applied in alphabetical order (i.e.: following the timestamp)
- Because Cassandra is not a transactional database, if an error happens before inserting the metadata in the table used by the tool there is a risk to have your CQL migration script run multiple times

Some information on the tool:

- For running tests, all the CQL scripts in the `src/main/resources/config/cql/changelog/` directory are automatically applied to the testcontainer instance
  - Meaning you have nothing to do but to drop your script in the changelog directory to have it applied for the tests
- The tool uses its own cassandra table schema_version to store the metadata info

The tool will apply the migration scripts from `src/main/resources/config/cql/` in the following order:

- `create-migration-keyspace.cql`, create the dedicated keyspace and the schema_version table to store the migration metadata
- all `cql/changelog/*.cql` files in alphabetical order

## Log migration execution

Uncomment this line in `src/docker/cassandra-migration.yml`:

```yaml
#- DEBUG_LOG=1 # uncomment to show debug logs during the migration process
```

Run the migration container:

```bash
docker compose -f src/main/docker/cassandra-migration.yml up -d
docker logs cassandra-migration --follow
```

## Differences with JHipster

This tool is the same that is [used by jhipster project](https://www.jhipster.tech/using-cassandra/), with two differences:

- jhipster-lite doesn't have a CLI to generate entities, so there's no automatic generation of changelog in `resources/cql/changelog` folder
- a dedicated keyspace, `seed4jMigration` is used to store migration metadata. It allows to not mix jhipster data with your application data
  - it means that you must explicitly tell which keyspace to use in your `changelog/*.cql` scripts: either by using `USE` directive, or by prepending your table names with keyspace they belong to
