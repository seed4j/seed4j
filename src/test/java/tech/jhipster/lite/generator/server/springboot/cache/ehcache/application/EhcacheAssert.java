package tech.jhipster.lite.generator.server.springboot.cache.ehcache.application;

import static tech.jhipster.lite.TestUtils.assertFileContent;
import static tech.jhipster.lite.TestUtils.assertFileExist;
import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.project.domain.Constants.*;

import java.util.List;
import tech.jhipster.lite.generator.project.domain.DefaultConfig;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.springboot.cache.ehcache.domain.EhcacheDomainService;
import tech.jhipster.lite.generator.server.springboot.cache.jcache.application.SpringBootJCacheAssert;

public class EhcacheAssert {

  public static void assertInitJavaConfiguration(Project project) {
    assertDependencies(project);
    assertEnableCaching(project);
    assertJavaFiles(project);
    assertProperties(project);
  }

  public static void assertDependencies(Project project) {
    SpringBootJCacheAssert.assertDependencies(project);

    assertFileContent(
      project,
      "pom.xml",
      List.of("<dependency>", "<groupId>org.ehcache</groupId>", "<artifactId>ehcache</artifactId>", "</dependency>")
    );
  }

  public static void assertEnableCaching(Project project) {
    SpringBootJCacheAssert.assertEnableCaching(project);
  }

  public static void assertJavaFiles(Project project) {
    SpringBootJCacheAssert.assertJavaConfig(project);

    String basePackage = project.getPackageName().orElse("com.mycompany.myapp");
    String cachePackage = basePackage + ".technical.infrastructure.secondary.cache";

    String basePath = project.getPackageNamePath().orElse(getPath(DefaultConfig.PACKAGE_PATH));
    String ehcachePath = getPath(MAIN_JAVA, basePath, EhcacheDomainService.DESTINATION);

    assertFileExist(project, getPath(ehcachePath, "EhcacheConfiguration.java"));
    assertFileExist(project, getPath(ehcachePath, "EhcacheConfigurer.java"));
    assertFileExist(project, getPath(ehcachePath, "EhcacheProperties.java"));

    assertFileContent(project, getPath(ehcachePath, "EhcacheConfiguration.java"), "package " + cachePackage);
    assertFileContent(project, getPath(ehcachePath, "EhcacheConfigurer.java"), "package " + cachePackage);
    assertFileContent(project, getPath(ehcachePath, "EhcacheProperties.java"), "package " + cachePackage);

    String ehcacheTestPath = getPath(TEST_JAVA, basePath, EhcacheDomainService.DESTINATION);

    assertFileExist(project, getPath(ehcacheTestPath, "EhcacheConfigurationIT.java"));

    assertFileContent(project, getPath(ehcacheTestPath, "EhcacheConfigurationIT.java"), "package " + cachePackage);
  }

  public static void assertProperties(Project project) {
    assertFileContent(
      project,
      getPath(MAIN_RESOURCES, "config/application.properties"),
      List.of("application.cache.ehcache.max-entries=100", "application.cache.ehcache.time-to-live-seconds=3600")
    );
  }
}
