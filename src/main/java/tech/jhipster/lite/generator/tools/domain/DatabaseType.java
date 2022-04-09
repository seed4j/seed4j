package tech.jhipster.lite.generator.tools.domain;

public enum DatabaseType {
  POSTGRESQL("postgresql"),
  MYSQL("mysql"),
  MARIADB("mariadb"),
  ORACLE("oracle"),
  MSSQL("mssql"),
  MONGODB("mongodb"),
  CASSANDRA("cassandra"),
  COUCHBASE("couchbase"),
  NEO4J("neo4j");

  private final String id;

  private DatabaseType(String id) {
    this.id = id;
  }

  public String id() {
    return id;
  }
}
