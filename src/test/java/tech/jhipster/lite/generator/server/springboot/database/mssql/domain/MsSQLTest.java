package tech.jhipster.lite.generator.server.springboot.database.mssql.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.Map;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;

@UnitTest
class MsSQLTest {

  @Test
  void shouldGetDockerImageName() {
    assertThat(MsSQL.getDockerImageName()).isEqualTo(MsSQL.MSSQL_DOCKER_IMAGE_NAME);
  }

  @Test
  void shouldGetSpringProperties() {
    Map<String, Object> springProperties = MsSQL.springProperties("baseName");

    assertThat(springProperties)
      .containsEntry("spring.datasource.url", "jdbc:sqlserver://localhost:1433;database=baseName;trustServerCertificate=true")
      .containsEntry("spring.datasource.driver-class-name", "com.microsoft.sqlserver.jdbc.SQLServerDriver")
      .containsEntry("spring.datasource.username", "SA")
      .containsEntry("spring.datasource.password", "yourStrong(!)Password")
      .containsEntry("spring.jpa.hibernate.ddl-auto", "update")
      .containsEntry("spring.jpa.properties.hibernate.dialect", "org.hibernate.dialect.SQLServer2012Dialect")
      .containsEntry("spring.jpa.properties.hibernate.format_sql", true);
  }
}
