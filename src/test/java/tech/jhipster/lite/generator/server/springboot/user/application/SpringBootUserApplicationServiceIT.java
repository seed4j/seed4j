package tech.jhipster.lite.generator.server.springboot.user.application;

import static tech.jhipster.lite.TestUtils.tmpProject;
import static tech.jhipster.lite.TestUtils.tmpProjectBuilder;
import static tech.jhipster.lite.generator.server.springboot.user.application.SpringBootUserAssertFiles.assertFilesSqlJavaAuditEntity;
import static tech.jhipster.lite.generator.server.springboot.user.application.SpringBootUserAssertFiles.assertFilesSqlJavaUser;
import static tech.jhipster.lite.generator.server.springboot.user.application.SpringBootUserAssertFiles.assertFilesSqlJavaUserAuthority;
import static tech.jhipster.lite.generator.server.springboot.user.application.SpringBootUserAssertFiles.checkSequence;
import static tech.jhipster.lite.generator.tools.domain.DatabaseType.MYSQL;
import static tech.jhipster.lite.generator.tools.domain.DatabaseType.POSTGRESQL;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.buildtool.maven.application.MavenApplicationService;
import tech.jhipster.lite.generator.server.springboot.core.application.SpringBootApplicationService;
import tech.jhipster.lite.generator.server.springboot.database.mariadb.application.MariaDBApplicationService;
import tech.jhipster.lite.generator.server.springboot.database.mysql.application.MySQLApplicationService;
import tech.jhipster.lite.generator.tools.domain.DatabaseType;
import tech.jhipster.lite.generator.tools.domain.Project;

@IntegrationTest
class SpringBootUserApplicationServiceIT {

  @Autowired
  MavenApplicationService mavenApplicationService;

  @Autowired
  SpringBootApplicationService springBootApplicationService;

  @Autowired
  MySQLApplicationService mySQLApplicationService;

  @Autowired
  MariaDBApplicationService mariaDBApplicationService;

  @Autowired
  SpringBootUserApplicationService springBootUserApplicationService;

  @Test
  void shouldAddUserAndAuthorityEntitiesForPostgresql() {
    Project project = tmpProjectBuilder().build();

    springBootUserApplicationService.addUserAndAuthorityEntities(project, POSTGRESQL);

    assertFilesSqlJavaUser(project, POSTGRESQL);
    assertFilesSqlJavaUserAuthority(project, POSTGRESQL);
    assertFilesSqlJavaAuditEntity(project, POSTGRESQL);

    checkSequence(project, POSTGRESQL);
  }

  @ParameterizedTest
  @EnumSource(value = DatabaseType.class, names = { "MYSQL", "MARIADB" })
  void shouldAddUserAndAuthorityEntitiesForMysqlOrMariaDB(DatabaseType databaseType) {
    Project project = tmpProject();
    mavenApplicationService.init(project);
    springBootApplicationService.init(project);
    if (databaseType.equals(MYSQL)) {
      mySQLApplicationService.init(project);
    } else {
      mariaDBApplicationService.init(project);
    }

    springBootUserApplicationService.addUserAndAuthorityEntities(project, databaseType);

    assertFilesSqlJavaUser(project, databaseType);
    assertFilesSqlJavaUserAuthority(project, databaseType);
    assertFilesSqlJavaAuditEntity(project, databaseType);

    checkSequence(project, databaseType);
  }
}
