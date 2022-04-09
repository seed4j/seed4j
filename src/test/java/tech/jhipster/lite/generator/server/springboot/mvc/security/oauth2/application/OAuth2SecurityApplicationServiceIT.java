package tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.application;

import static tech.jhipster.lite.TestUtils.tmpProject;
import static tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.application.OAuth2SecurityAssert.assertAccountFiles;
import static tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.application.OAuth2SecurityAssert.assertDockerKeycloak;
import static tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.application.OAuth2SecurityAssert.assertExceptionTranslatorWithSecurity;
import static tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.application.OAuth2SecurityAssert.assertIntegrationTestWithSecurity;
import static tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.application.OAuth2SecurityAssert.assertJavaFiles;
import static tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.application.OAuth2SecurityAssert.assertProperties;
import static tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.application.OAuth2SecurityAssert.assertSecurityDependencies;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.buildtool.maven.domain.MavenService;
import tech.jhipster.lite.generator.server.javatool.base.application.JavaBaseApplicationService;
import tech.jhipster.lite.generator.server.springboot.core.domain.SpringBootService;
import tech.jhipster.lite.generator.server.springboot.mvc.web.domain.SpringBootMvcService;
import tech.jhipster.lite.generator.tools.domain.Project;

@IntegrationTest
class OAuth2SecurityApplicationServiceIT {

  @Autowired
  MavenService mavenService;

  @Autowired
  JavaBaseApplicationService javaBaseApplicationService;

  @Autowired
  SpringBootService springBootService;

  @Autowired
  SpringBootMvcService springBootMvcService;

  @Autowired
  OAuth2SecurityApplicationService oAuth2SecurityApplicationService;

  @Test
  @DisplayName("should add OAuth2")
  void shouldAddOAuth2() {
    Project project = tmpProject();

    mavenService.addJavaPomXml(project);
    springBootService.init(project);
    springBootMvcService.init(project);

    oAuth2SecurityApplicationService.addOAuth2(project);

    assertSecurityDependencies(project);
    assertDockerKeycloak(project);
    assertJavaFiles(project);
    assertProperties(project);

    assertExceptionTranslatorWithSecurity(project);
    assertIntegrationTestWithSecurity(project);
  }

  @Test
  void shouldAddAccountContext() {
    Project project = tmpProject();

    oAuth2SecurityApplicationService.addAccountContext(project);

    assertAccountFiles(project);
  }
}
