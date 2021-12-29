package tech.jhipster.lite.generator.server.springboot.cache.jcache.application;

import static tech.jhipster.lite.TestUtils.assertFileContent;
import static tech.jhipster.lite.TestUtils.assertFileExist;
import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.project.domain.Constants.MAIN_JAVA;
import static tech.jhipster.lite.generator.project.domain.Constants.TEST_JAVA;

import java.util.List;
import tech.jhipster.lite.generator.project.domain.DefaultConfig;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.springboot.cache.common.application.SpringBootCacheAssert;
import tech.jhipster.lite.generator.server.springboot.cache.common.domain.SpringBootCacheDomainService;

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

  public static void assertJavaConfig(Project project) {
    String basePackage = project.getPackageName().orElse("com.mycompany.myapp");
    String cachePackage = basePackage + ".technical.infrastructure.secondary.cache";

    String basePath = project.getPackageNamePath().orElse(getPath(DefaultConfig.PACKAGE_PATH));
    String cachePath = getPath(MAIN_JAVA, basePath, SpringBootCacheDomainService.DESTINATION);

    assertFileExist(project, getPath(cachePath, "JCacheConfiguration.java"));
    assertFileExist(project, getPath(cachePath, "JCacheConfigurer.java"));
    assertFileExist(project, getPath(cachePath, "JCacheCreator.java"));

    assertFileContent(project, getPath(cachePath, "JCacheConfiguration.java"), "package " + cachePackage);
    assertFileContent(project, getPath(cachePath, "JCacheConfigurer.java"), "package " + cachePackage);
    assertFileContent(project, getPath(cachePath, "JCacheCreator.java"), "package " + cachePackage);

    String cacheTestPath = getPath(TEST_JAVA, basePath, SpringBootCacheDomainService.DESTINATION);

    assertFileExist(project, getPath(cacheTestPath, "JCacheCreatorTest.java"));

    assertFileContent(project, getPath(cacheTestPath, "JCacheCreatorTest.java"), "package " + cachePackage);
  }
}
