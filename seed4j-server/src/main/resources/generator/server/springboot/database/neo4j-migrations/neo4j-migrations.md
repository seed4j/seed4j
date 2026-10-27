# Neo4j Migrations

[Neo4j-Migrations](https://michael-simons.github.io/neo4j-migrations) are a set of tools to make your schema migrations as easy as possible.
The project is inspired to a large extent by FlywayDB, which is an awesome tool for migration of relational databases. Most things evolve around Cypher scripts, however the Core API of Neo4j-Migrations allows defining Java classes as migrations as well.

To do that you'll need to define migrations in `/src/main/resources/neo4j/migrations`.

Details how to [write migrations can be found in the official documentation](https://michael-simons.github.io/neo4j-migrations/current/#concepts_migrations).
