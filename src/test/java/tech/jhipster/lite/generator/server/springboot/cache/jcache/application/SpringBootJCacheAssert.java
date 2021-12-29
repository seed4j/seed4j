package tech.jhipster.lite.generator.server.springboot.cache.jcache.application;

import static tech.jhipster.lite.TestUtils.assertFileContent;

import java.util.List;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.springboot.cache.common.application.SpringBootCacheAssert;

public class SpringBootJCacheAssert {

  public static void assertDependencies(Project project) {
    SpringBootCacheAssert.assertDependencies(project);

    assertFileContent(
      project,
      "pom.xml",
      List.of("<dependency>", "<groupId>javax.cache</groupId>", "<artifactId>cache-api</artifactId>", "</dependency>")
    );
  }

  public static void assertEnableCaching(Project project) {
    SpringBootCacheAssert.assertEnableCaching(project);
  }
}
