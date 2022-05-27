package tech.jhipster.lite.generator.server.springboot.mvc.security.jwt.application;

import static tech.jhipster.lite.TestUtils.assertFileContent;
import static tech.jhipster.lite.TestUtils.tmpProject;
import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.project.domain.Constants.TEST_JAVA;
import static tech.jhipster.lite.generator.server.springboot.mvc.security.jwt.application.JwtSecurityAssertFiles.assertBasicAuthJavaFiles;
import static tech.jhipster.lite.generator.server.springboot.mvc.security.jwt.application.JwtSecurityAssertFiles.assertBasicAuthProperties;
import static tech.jhipster.lite.generator.server.springboot.mvc.security.jwt.application.JwtSecurityAssertFiles.assertExceptionTranslatorWithSecurity;
import static tech.jhipster.lite.generator.server.springboot.mvc.security.jwt.application.JwtSecurityAssertFiles.assertJwtSecurityFilesExists;
import static tech.jhipster.lite.generator.server.springboot.mvc.security.jwt.application.JwtSecurityAssertFiles.assertJwtSecurityProperties;
import static tech.jhipster.lite.generator.server.springboot.mvc.security.jwt.application.JwtSecurityAssertFiles.assertLoggerInConfig;
import static tech.jhipster.lite.generator.server.springboot.mvc.security.jwt.application.JwtSecurityAssertFiles.assertPomXmlProperties;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.buildtool.maven.domain.MavenService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.infrastructure.secondary.GitUtils;
import tech.jhipster.lite.generator.server.javatool.base.application.JavaBaseApplicationService;
import tech.jhipster.lite.generator.server.javatool.base.domain.JavaBaseModuleProperties;
import tech.jhipster.lite.generator.server.springboot.core.domain.SpringBootService;
import tech.jhipster.lite.generator.server.springboot.mvc.web.domain.SpringBootMvcService;

@IntegrationTest
class JwtSecurityApplicationServiceIT {

  @Autowired
  MavenService mavenService;

  @Autowired
  JavaBaseApplicationService javaBaseApplicationService;

  @Autowired
  SpringBootService springBootService;

  @Autowired
  SpringBootMvcService springBootMvcService;

  @Autowired
  JwtSecurityApplicationService jwtSecurityApplicationService;

  @Test
  void shouldInit() throws Exception {
    Project project = tmpProject();
    GitUtils.init(project.getFolder());
    mavenService.addJavaPomXml(project);
    javaBaseApplicationService.addJavaBase(JavaBaseModuleProperties.from(project));
    springBootService.init(project);
    springBootMvcService.init(project);

    jwtSecurityApplicationService.init(project);

    assertPomXmlProperties(project);
    assertJwtSecurityFilesExists(project);
    assertExceptionTranslatorWithSecurity(project);
    assertJwtSecurityProperties(project);
    assertLoggerInConfig(project);

    String integrationTest = "com/mycompany/myapp/IntegrationTest.java";
    assertFileContent(
      project,
      getPath(TEST_JAVA, integrationTest),
      List.of(
        "import org.springframework.boot.test.context.SpringBootTest;",
        "import org.springframework.security.test.context.support.WithMockUser;"
      )
    );
    assertFileContent(project, getPath(TEST_JAVA, integrationTest), List.of("@WithMockUser", "public @interface"));
  }

  @Test
  void shouldAddBasicAuth() throws Exception {
    Project project = tmpProject();
    GitUtils.init(project.getFolder());
    mavenService.addJavaPomXml(project);
    javaBaseApplicationService.addJavaBase(JavaBaseModuleProperties.from(project));
    springBootService.init(project);
    springBootMvcService.init(project);
    jwtSecurityApplicationService.init(project);

    jwtSecurityApplicationService.addBasicAuth(project);

    assertBasicAuthJavaFiles(project);
    assertBasicAuthProperties(project);
  }
}
