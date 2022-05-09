package tech.jhipster.lite.generator.server.springboot.database.mongodb.application;

import static tech.jhipster.lite.TestUtils.tmpProject;
import static tech.jhipster.lite.generator.server.springboot.database.mongodb.MongodbAssert.assertConfigFiles;
import static tech.jhipster.lite.generator.server.springboot.database.mongodb.MongodbAssert.assertDependencies;
import static tech.jhipster.lite.generator.server.springboot.database.mongodb.MongodbAssert.assertDockerMongodb;
import static tech.jhipster.lite.generator.server.springboot.database.mongodb.MongodbAssert.assertJavaFiles;
import static tech.jhipster.lite.generator.server.springboot.database.mongodb.MongodbAssert.assertLoggerInConfig;
import static tech.jhipster.lite.generator.server.springboot.database.mongodb.MongodbAssert.assertTestFiles;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.buildtool.maven.application.MavenApplicationService;
import tech.jhipster.lite.generator.init.application.InitApplicationService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.springboot.core.application.SpringBootApplicationService;
import tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.application.OAuth2SecurityApplicationService;
import tech.jhipster.lite.generator.server.springboot.mvc.web.application.SpringBootMvcApplicationService;

@IntegrationTest
class MongodbApplicationServiceIT {

  @Autowired
  MongodbApplicationService mongodbApplicationService;

  @Autowired
  InitApplicationService initApplicationService;

  @Autowired
  MavenApplicationService mavenApplicationService;

  @Autowired
  OAuth2SecurityApplicationService oAuth2SecurityApplicationService;

  @Autowired
  SpringBootApplicationService springBootApplicationService;

  @Autowired
  SpringBootMvcApplicationService springBootMvcApplicationService;

  @Test
  @DisplayName("Should init project with default values")
  void shouldInit() {
    Project project = tmpProject();
    initApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    mongodbApplicationService.init(project);

    assertDependencies(project);
    assertDockerMongodb(project);
    assertJavaFiles(project, false);
    assertTestFiles(project);
    assertConfigFiles(project);
    assertLoggerInConfig(project);
  }

  @Test
  @DisplayName("Should init project with default values when spring security is added")
  void shouldInitWithSpringSecurity() {
    Project project = tmpProject();
    initApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);
    springBootMvcApplicationService.addExceptionHandler(project);
    oAuth2SecurityApplicationService.addOAuth2(project);
    oAuth2SecurityApplicationService.addAccountContext(project);

    mongodbApplicationService.init(project);

    assertDependencies(project);
    assertDockerMongodb(project);
    assertJavaFiles(project, true);
    assertTestFiles(project);
    assertConfigFiles(project);
    assertLoggerInConfig(project);
  }
}
