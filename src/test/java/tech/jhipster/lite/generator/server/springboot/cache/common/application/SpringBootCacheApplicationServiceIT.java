package tech.jhipster.lite.generator.server.springboot.cache.common.application;

import static tech.jhipster.lite.TestUtils.tmpProject;
import static tech.jhipster.lite.generator.server.springboot.cache.common.application.SpringBootCacheAssert.assertDependencies;
import static tech.jhipster.lite.generator.server.springboot.cache.common.application.SpringBootCacheAssert.assertEnableCaching;
import static tech.jhipster.lite.generator.tools.domain.DefaultConfig.BASE_NAME;
import static tech.jhipster.lite.generator.tools.domain.DefaultConfig.PACKAGE_NAME;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.buildtool.maven.application.MavenApplicationService;
import tech.jhipster.lite.generator.project.application.ProjectApplicationService;
import tech.jhipster.lite.generator.server.springboot.core.application.SpringBootApplicationService;
import tech.jhipster.lite.generator.tools.domain.Project;

@IntegrationTest
class SpringBootCacheApplicationServiceIT {

  @Autowired
  SpringBootCacheApplicationService springBootCacheApplicationService;

  @Autowired
  ProjectApplicationService projectApplicationService;

  @Autowired
  MavenApplicationService mavenApplicationService;

  @Autowired
  SpringBootApplicationService springBootApplicationService;

  @Test
  void shouldAddDependencies() {
    Project project = tmpProject();
    project.addConfig(BASE_NAME, "bar");
    project.addConfig(PACKAGE_NAME, "tech.jhipster.baz");
    projectApplicationService.init(project);
    mavenApplicationService.addPomXml(project);

    springBootCacheApplicationService.addDependencies(project);

    assertDependencies(project);
  }

  @Test
  void shouldEnableCaching() {
    Project project = tmpProject();
    project.addConfig(BASE_NAME, "bar");
    project.addConfig(PACKAGE_NAME, "tech.jhipster.baz");
    projectApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    springBootCacheApplicationService.addEnableCaching(project);

    assertEnableCaching(project);
  }
}
