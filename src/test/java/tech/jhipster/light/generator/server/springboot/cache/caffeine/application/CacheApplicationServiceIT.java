package tech.jhipster.light.generator.server.springboot.cache.caffeine.application;

import static tech.jhipster.light.TestUtils.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.light.IntegrationTest;
import tech.jhipster.light.generator.project.domain.Project;

@IntegrationTest
class CacheApplicationServiceIT {

  @Autowired
  CaffeineApplicationService caffeineApplicationService;

  @Test
  void shouldInitCache() {
    Project project = tmpProjectWithPomXml();
    caffeineApplicationService.initCache(project);

    assertFileContent(
      project,
      "pom.xml",
      List.of(
        "<dependency>",
        "<groupId>org.springframework.boot</groupId>",
        "<artifactId>spring-boot-starter-cache</artifactId>",
        "</dependency>"
      )
    );
  }
}
