package tech.jhipster.lite.generator.server.springboot.cache.simple.application;

import static tech.jhipster.lite.TestUtils.tmpProject;
import static tech.jhipster.lite.generator.server.springboot.cache.simple.application.SpringBootCacheSimpleAssert.assertInit;
import static tech.jhipster.lite.generator.tools.domain.DefaultConfig.BASE_NAME;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.buildtool.maven.application.MavenApplicationService;
import tech.jhipster.lite.generator.project.application.ProjectApplicationService;
import tech.jhipster.lite.generator.server.springboot.core.application.SpringBootApplicationService;
import tech.jhipster.lite.generator.tools.domain.Project;

@IntegrationTest
class SpringBootCacheSimpleApplicationServiceIT {

  @Autowired
  SpringBootCacheSimpleApplicationService springBootCacheSimpleApplicationService;

  @Autowired
  ProjectApplicationService projectApplicationService;

  @Autowired
  MavenApplicationService mavenApplicationService;

  @Autowired
  SpringBootApplicationService springBootApplicationService;

  @Test
  void shouldInit() {
    Project project = tmpProject();
    project.addConfig(BASE_NAME, "foo");

    projectApplicationService.init(project);

    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    springBootCacheSimpleApplicationService.init(project);

    assertInit(project);
  }
}
