package tech.jhipster.lite.generator.server.springboot.database.mssql.domain;

import java.util.Map;
import java.util.TreeMap;
import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;

public class MsSQL {

  public static final String MSSQL_DOCKER_IMAGE_NAME = "mcr.microsoft.com/mssql/server";
  public static final String MSSQL_TEST_CONTAINER_EXTENSION_FILE = "MsSQLTestContainerExtension.java";
  public static final String LICENSE_TEST_CONTAINER_FILE = "container-license-acceptance.txt";

  private MsSQL() {}

  public static String getDockerImageName() {
    return MSSQL_DOCKER_IMAGE_NAME;
  }

  public static Dependency driver() {
    return Dependency.builder().groupId("com.microsoft.sqlserver").artifactId("mssql-jdbc").build();
  }

  public static Map<String, Object> springProperties(String baseName) {
    TreeMap<String, Object> result = new TreeMap<>();
    result.put("spring.datasource.url", "jdbc:sqlserver://localhost:1433;database=" + baseName + ";trustServerCertificate=true");
    result.put("spring.datasource.driver-class-name", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
    result.put("spring.datasource.username", "SA");
    result.put("spring.datasource.password", "yourStrong(!)Password");

    result.put("spring.jpa.hibernate.ddl-auto", "update");
    result.put("spring.jpa.properties.hibernate.format_sql", true);
    result.put("spring.jpa.properties.hibernate.dialect", "org.hibernate.dialect.SQLServer2012Dialect");
    return result;
  }

  public static Map<String, Object> springPropertiesForTest(String baseName) {
    TreeMap<String, Object> result = new TreeMap<>();
    result.put("spring.datasource.driver-class-name", "org.testcontainers.jdbc.ContainerDatabaseDriver");
    result.put(
      "spring.datasource.url",
      "jdbc:tc:sqlserver://;database=" + baseName + ";trustServerCertificate=true?TC_TMPFS=/testtmpfs:rw"
    );
    result.put("spring.datasource.username", "SA");
    result.put("spring.datasource.password", "yourStrong(!)Password");
    result.put("spring.datasource.hikari.maximum-pool-size", 2);
    return result;
  }
}
