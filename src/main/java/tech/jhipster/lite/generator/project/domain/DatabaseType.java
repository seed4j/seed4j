package tech.jhipster.lite.generator.project.domain;

public enum DatabaseType {
  POSTGRESQL("postgresql"),
  MYSQL("mysql"),
  MARIADB("mariadb"),
  ORACLE("oracle"),
  MSSQL("mssql"),
  CASSANDRA("cassandra"),
  COUCHBASE("couchbase"),
  NEO4J("neo4j");

  private final String id;

  DatabaseType(String id) {
    this.id = id;
  }

  public String id() {
    return id;
  }
}
