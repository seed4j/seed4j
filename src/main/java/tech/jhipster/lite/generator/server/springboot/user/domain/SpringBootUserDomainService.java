package tech.jhipster.lite.generator.server.springboot.user.domain;

import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.tools.domain.Constants.DOMAIN;
import static tech.jhipster.lite.generator.tools.domain.Constants.INFRASTRUCTURE_SECONDARY;
import static tech.jhipster.lite.generator.tools.domain.Constants.MAIN_JAVA;
import static tech.jhipster.lite.generator.tools.domain.Constants.TEST_JAVA;
import static tech.jhipster.lite.generator.tools.domain.DefaultConfig.PACKAGE_NAME;
import static tech.jhipster.lite.generator.tools.domain.DefaultConfig.PACKAGE_PATH;

import tech.jhipster.lite.generator.tools.domain.DatabaseType;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.domain.ProjectRepository;

public class SpringBootUserDomainService implements SpringBootUserService {

  private static final String SOURCE = "server/springboot/user/sqldatabase";
  private static final String USER_CONTEXT = "user";
  private static final String USER_INFRASTRUCTURE_SECONDARY = getPath(USER_CONTEXT, INFRASTRUCTURE_SECONDARY);
  private static final String USER_DOMAIN = getPath(USER_CONTEXT, DOMAIN);
  private static final String USER_DATABASE_KEY = "sqlDatabaseName";

  private final ProjectRepository projectRepository;

  public SpringBootUserDomainService(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @Override
  public void addUserAndAuthorityEntities(Project project, DatabaseType sqlDatabase) {
    addUserEntity(project, sqlDatabase);
    addAuthorityEntity(project, sqlDatabase);
    addAbstractAuditingEntity(project, sqlDatabase);
  }

  private void addUserEntity(Project project, DatabaseType sqlDatabase) {
    project.addDefaultConfig(PACKAGE_NAME);
    String packageNamePath = project.getPackageNamePath().orElse(getPath(PACKAGE_PATH));
    project.addConfig(USER_DATABASE_KEY, sqlDatabase.id());

    if (isDatabaseWhichNoNeedsSequenceStrategy(sqlDatabase)) {
      projectRepository.template(
        project,
        SOURCE,
        "UserEntity_without_sequence.java",
        getSqlJavaPath(packageNamePath, sqlDatabase),
        "UserEntity.java"
      );
    } else {
      projectRepository.template(project, SOURCE, "UserEntity.java", getSqlJavaPath(packageNamePath, sqlDatabase));
    }
    projectRepository.template(project, SOURCE, "UserJpaRepository.java", getSqlJavaPath(packageNamePath, sqlDatabase));
    projectRepository.template(project, SOURCE, "UserConstants.java", getSqlDomainJavaPath(packageNamePath));

    projectRepository.template(project, SOURCE, "UserEntityTest.java", getSqlJavaTestPath(packageNamePath, sqlDatabase));
    projectRepository.template(project, SOURCE, "UserJpaRepositoryIT.java", getSqlJavaTestPath(packageNamePath, sqlDatabase));
  }

  private void addAuthorityEntity(Project project, DatabaseType sqlDatabase) {
    project.addDefaultConfig(PACKAGE_NAME);
    String packageNamePath = project.getPackageNamePath().orElse(getPath(PACKAGE_PATH));
    project.addConfig(USER_DATABASE_KEY, sqlDatabase.id());

    projectRepository.template(project, SOURCE, "AuthorityEntity.java", getSqlJavaPath(packageNamePath, sqlDatabase));
    projectRepository.template(project, SOURCE, "AuthorityJpaRepository.java", getSqlJavaPath(packageNamePath, sqlDatabase));

    projectRepository.template(project, SOURCE, "AuthorityEntityTest.java", getSqlJavaTestPath(packageNamePath, sqlDatabase));
  }

  private void addAbstractAuditingEntity(Project project, DatabaseType sqlDatabase) {
    project.addDefaultConfig(PACKAGE_NAME);
    String packageNamePath = project.getPackageNamePath().orElse(getPath(PACKAGE_PATH));
    project.addConfig(USER_DATABASE_KEY, sqlDatabase.id());

    projectRepository.template(project, SOURCE, "AbstractAuditingEntity.java", getSqlJavaPath(packageNamePath, sqlDatabase));
  }

  private String getSqlJavaPath(String packageNamePath, DatabaseType sqlDatabase) {
    return getPath(MAIN_JAVA, packageNamePath, USER_INFRASTRUCTURE_SECONDARY + "/" + sqlDatabase.id());
  }

  private String getSqlDomainJavaPath(String packageNamePath) {
    return getPath(MAIN_JAVA, packageNamePath, USER_DOMAIN);
  }

  private String getSqlJavaTestPath(String packageNamePath, DatabaseType sqlDatabase) {
    return getPath(TEST_JAVA, packageNamePath, USER_INFRASTRUCTURE_SECONDARY + "/" + sqlDatabase.id());
  }

  private boolean isMySQLDatabase(DatabaseType databaseType) {
    return databaseType.equals(DatabaseType.MYSQL);
  }

  private boolean isMariaDBDatabase(DatabaseType databaseType) {
    return databaseType.equals(DatabaseType.MARIADB);
  }

  private boolean isDatabaseWhichNoNeedsSequenceStrategy(DatabaseType databaseType) {
    return isMySQLDatabase(databaseType) || isMariaDBDatabase(databaseType);
  }
}
