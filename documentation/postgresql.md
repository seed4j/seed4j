# Usage

Seed4J runs without PostgreSQL by default. The PostgreSQL configuration is only activated when the `psql` Spring profile is enabled.

If you are using the PostgreSQL profile, start the database first:

```bash
docker compose -f src/main/docker/postgresql.yml up -d
```

Then start the app with the profile enabled:

```bash
SPRING_PROFILES_ACTIVE=psql ./mvnw
```

or:

```bash
./mvnw -Dspring-boot.run.profiles=psql
```
