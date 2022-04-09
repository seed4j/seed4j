package tech.jhipster.lite.generator.server.javatool.base.application;

import static tech.jhipster.lite.TestUtils.assertFileContent;
import static tech.jhipster.lite.TestUtils.assertFileExist;
import static tech.jhipster.lite.TestUtils.tmpProject;
import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.server.javatool.base.domain.JavaBaseDomainService.ERROR_DOMAIN_PATH;
import static tech.jhipster.lite.generator.tools.domain.Constants.MAIN_JAVA;
import static tech.jhipster.lite.generator.tools.domain.Constants.TEST_JAVA;
import static tech.jhipster.lite.generator.tools.domain.DefaultConfig.PACKAGE_NAME;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.server.javatool.base.domain.JavaBase;
import tech.jhipster.lite.generator.tools.domain.DefaultConfig;
import tech.jhipster.lite.generator.tools.domain.Project;

@IntegrationTest
class JavaBaseApplicationServiceIT {

  @Autowired
  JavaBaseApplicationService javaBaseApplicationService;

  @Test
  void shouldAddJavaBase() {
    Project project = tmpProject();

    javaBaseApplicationService.addJavaBase(project);

    String pathMain = getPath(MAIN_JAVA, DefaultConfig.PACKAGE_PATH, ERROR_DOMAIN_PATH);
    JavaBase.errorDomainFiles().forEach(file -> assertFileExist(project, getPath(pathMain, file)));

    String pathTest = getPath(TEST_JAVA, DefaultConfig.PACKAGE_PATH, ERROR_DOMAIN_PATH);
    JavaBase.errorDomainTestFiles().forEach(file -> assertFileExist(project, getPath(pathTest, file)));

    JavaBase.annotationsFiles().forEach(file -> assertFileExist(project, getPath(TEST_JAVA, DefaultConfig.PACKAGE_PATH, file)));
  }

  @Test
  void shouldAddJavaBaseWithSpecificPackage() {
    Project project = tmpProject();
    project.addConfig(PACKAGE_NAME, "tech.jhipster.chips");

    javaBaseApplicationService.addJavaBase(project);

    String packageResult = "package tech.jhipster.chips.error.domain;";
    String specificPath = "tech/jhipster/chips";

    String pathMain = getPath(MAIN_JAVA, specificPath, ERROR_DOMAIN_PATH);
    JavaBase
      .errorDomainFiles()
      .forEach(file -> {
        assertFileExist(project, getPath(pathMain, file));
        assertFileContent(project, getPath(pathMain, file), packageResult);
      });

    String pathTest = getPath(TEST_JAVA, specificPath, ERROR_DOMAIN_PATH);
    JavaBase
      .errorDomainTestFiles()
      .forEach(file -> {
        assertFileExist(project, getPath(pathTest, file));
        assertFileContent(project, getPath(pathTest, file), packageResult);
      });

    JavaBase.annotationsFiles().forEach(file -> assertFileExist(project, getPath(TEST_JAVA, specificPath, file)));
  }
}
