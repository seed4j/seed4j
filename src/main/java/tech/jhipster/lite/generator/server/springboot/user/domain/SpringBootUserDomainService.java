package tech.jhipster.lite.generator.server.springboot.user.domain;

import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.project.domain.Constants.DOMAIN;
import static tech.jhipster.lite.generator.project.domain.Constants.INFRASTRUCTURE_SECONDARY;
import static tech.jhipster.lite.generator.project.domain.Constants.MAIN_JAVA;
import static tech.jhipster.lite.generator.project.domain.Constants.TEST_JAVA;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.PACKAGE_NAME;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.PACKAGE_PATH;

import tech.jhipster.lite.generator.project.domain.DatabaseType;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

public class SpringBootUserDomainService implements SpringBootUserService {

  private static final String SOURCE = "server/springboot/user/sqldatabase";
  private static final String NO_SQL_SOURCE = "server/springboot/user/nosqldatabase";
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
    String dynamicSource = getSource(sqlDatabase);
    if (isDatabaseWhichNoNeedsSequenceStrategy(sqlDatabase)) {
      projectRepository.template(
        project,
        SOURCE,
        "UserEntity_without_sequence.java",
        getSqlJavaPath(packageNamePath, sqlDatabase),
        "UserEntity.java"
      );
    } else {
      projectRepository.template(project, dynamicSource, "UserEntity.java", getSqlJavaPath(packageNamePath, sqlDatabase));
    }
    projectRepository.template(project, dynamicSource, "UserJpaRepository.java", getSqlJavaPath(packageNamePath, sqlDatabase));
    projectRepository.template(project, SOURCE, "UserConstants.java", getSqlDomainJavaPath(packageNamePath));

    projectRepository.template(project, dynamicSource, "UserEntityTest.java", getSqlJavaTestPath(packageNamePath, sqlDatabase));
    projectRepository.template(project, dynamicSource, "UserJpaRepositoryIT.java", getSqlJavaTestPath(packageNamePath, sqlDatabase));
  }

  private void addAuthorityEntity(Project project, DatabaseType sqlDatabase) {
    project.addDefaultConfig(PACKAGE_NAME);
    String packageNamePath = project.getPackageNamePath().orElse(getPath(PACKAGE_PATH));
    project.addConfig(USER_DATABASE_KEY, sqlDatabase.id());
    String dynamicSource = getSource(sqlDatabase);

    projectRepository.template(project, dynamicSource, "AuthorityEntity.java", getSqlJavaPath(packageNamePath, sqlDatabase));
    projectRepository.template(project, dynamicSource, "AuthorityJpaRepository.java", getSqlJavaPath(packageNamePath, sqlDatabase));

    projectRepository.template(project, dynamicSource, "AuthorityEntityTest.java", getSqlJavaTestPath(packageNamePath, sqlDatabase));
  }

  private void addAbstractAuditingEntity(Project project, DatabaseType sqlDatabase) {
    project.addDefaultConfig(PACKAGE_NAME);
    String packageNamePath = project.getPackageNamePath().orElse(getPath(PACKAGE_PATH));
    project.addConfig(USER_DATABASE_KEY, sqlDatabase.id());
    String dynamicSource = getSource(sqlDatabase);

    projectRepository.template(project, dynamicSource, "AbstractAuditingEntity.java", getSqlJavaPath(packageNamePath, sqlDatabase));
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

  private boolean isMongoDBDatabase(DatabaseType databaseType) {
    return DatabaseType.MONGODB.equals(databaseType);
  }

  private boolean isDatabaseWhichNoNeedsSequenceStrategy(DatabaseType databaseType) {
    return isMySQLDatabase(databaseType) || isMariaDBDatabase(databaseType);
  }

  private boolean isNoSQLDatabase(DatabaseType databaseType) {
    return isMongoDBDatabase(databaseType);
  }

  private String getSource(DatabaseType databaseType) {
    return isNoSQLDatabase(databaseType) ? NO_SQL_SOURCE + "/" + databaseType.id() : SOURCE;
  }
}
