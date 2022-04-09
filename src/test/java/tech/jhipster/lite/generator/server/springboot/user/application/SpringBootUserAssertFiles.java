package tech.jhipster.lite.generator.server.springboot.user.application;

import static tech.jhipster.lite.TestUtils.assertFileContent;
import static tech.jhipster.lite.TestUtils.assertFileExist;
import static tech.jhipster.lite.TestUtils.assertFileNoContent;
import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.tools.domain.Constants.MAIN_JAVA;
import static tech.jhipster.lite.generator.tools.domain.Constants.TEST_JAVA;
import static tech.jhipster.lite.generator.tools.domain.DatabaseType.MARIADB;
import static tech.jhipster.lite.generator.tools.domain.DatabaseType.MYSQL;

import java.util.List;
import tech.jhipster.lite.generator.tools.domain.DatabaseType;
import tech.jhipster.lite.generator.tools.domain.DefaultConfig;
import tech.jhipster.lite.generator.tools.domain.Project;

public class SpringBootUserAssertFiles {

  public static void assertFilesSqlJavaUser(Project project, DatabaseType databaseType) {
    checkJavaFile(project, "UserEntity.java", databaseType);
    checkJavaFile(project, "UserJpaRepository.java", databaseType);
    checkJavaDomainFile(project, "UserConstants.java");

    checkJavaTestFile(project, "UserEntityTest.java", databaseType);
    checkJavaTestFile(project, "UserJpaRepositoryIT.java", databaseType);
  }

  public static void assertFilesSqlJavaUserAuthority(Project project, DatabaseType databaseType) {
    checkJavaFile(project, "AuthorityEntity.java", databaseType);
    checkJavaFile(project, "AuthorityJpaRepository.java", databaseType);

    checkJavaTestFile(project, "AuthorityEntityTest.java", databaseType);
  }

  public static void assertFilesSqlJavaAuditEntity(Project project, DatabaseType databaseType) {
    checkJavaFile(project, "AbstractAuditingEntity.java", databaseType);
  }

  public static void checkSequence(Project project, DatabaseType databaseType) {
    String userPath = getUserPath(project, databaseType);

    if (List.of(MYSQL, MARIADB).contains(databaseType)) {
      assertFileContent(project, getPath(userPath, "UserEntity.java"), "@GeneratedValue(strategy = GenerationType.IDENTITY)");
      assertFileNoContent(project, getPath(userPath, "UserEntity.java"), "@GeneratedValue(strategy = GenerationType.SEQUENCE");
    } else {
      assertFileNoContent(project, getPath(userPath, "UserEntity.java"), "@GeneratedValue(strategy = GenerationType.IDENTITY)");
      assertFileContent(project, getPath(userPath, "UserEntity.java"), "@GeneratedValue(strategy = GenerationType.SEQUENCE");
    }
  }

  private static void checkJavaFile(Project project, String javaFileName, DatabaseType databaseType) {
    String userPackage =
      project.getPackageName().orElse(DefaultConfig.DEFAULT_PACKAGE_NAME) + ".user.infrastructure.secondary." + databaseType.id();
    String userPath = getUserPath(project, databaseType);

    assertFileExist(project, getPath(userPath, javaFileName));
    assertFileContent(project, getPath(userPath, javaFileName), "package " + userPackage);
  }

  private static String getUserPath(Project project, DatabaseType databaseType) {
    return getPath(
      MAIN_JAVA,
      project.getPackageNamePath().orElse("com/mycompany/myapp"),
      "user/infrastructure/secondary",
      databaseType.id()
    );
  }

  private static void checkJavaDomainFile(Project project, String javaFileName) {
    String userPackage = project.getPackageName().orElse(DefaultConfig.DEFAULT_PACKAGE_NAME) + ".user.domain";
    String userPath = getPath(MAIN_JAVA, project.getPackageNamePath().orElse("com/mycompany/myapp"), "user/domain");

    assertFileExist(project, getPath(userPath, javaFileName));
    assertFileContent(project, getPath(userPath, javaFileName), "package " + userPackage);
  }

  private static void checkJavaTestFile(Project project, String javaFileName, DatabaseType databaseType) {
    String userPackage =
      project.getPackageName().orElse(DefaultConfig.DEFAULT_PACKAGE_NAME) + ".user.infrastructure.secondary." + databaseType.id();
    String userPath = getPath(
      TEST_JAVA,
      project.getPackageNamePath().orElse("com/mycompany/myapp"),
      "user/infrastructure/secondary",
      databaseType.id()
    );

    assertFileExist(project, getPath(userPath, javaFileName));
    assertFileContent(project, getPath(userPath, javaFileName), "package " + userPackage);
  }
}
