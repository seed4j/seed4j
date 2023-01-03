The changelog folder for cassandra/cql files is similar to Liquibase (for SQL databases), but with a minimal tooling.

- The script name should follow the pattern yyyyMMddHHmmss\_{script-name}.cql
  - eg: 20150805124838_added_table_BankAccount.cql
- The scripts will be applied sequentially in alphabetical order
- The scripts will be applied automatically only in two contexts:
  - Unit tests
  - Run of `docker compose -f src/main/docker/cassandra-migration.yml up -d` command

Changelog scripts MUST tell explicitly which keyspace is used :

- by using `USE myKeyspace;` directive at the beginning of a script
- or by prepending each table with the keyspace it belongs to : `CREATE TABLE IF NOT EXISTS myKeyspace.myTable`

Second approach is recommended : it allows to not switch keyspace at runtime if there's many of them.
See [warn-if-set-keyspace section](https://docs.datastax.com/en/developer/java-driver/latest/manual/core/configuration/reference/) for more info.
