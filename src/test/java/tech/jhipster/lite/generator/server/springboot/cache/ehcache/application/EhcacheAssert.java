package tech.jhipster.lite.generator.server.springboot.cache.ehcache.application;

import static tech.jhipster.lite.TestUtils.assertFileContent;
import static tech.jhipster.lite.TestUtils.assertFileExist;
import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.tools.domain.Constants.MAIN_JAVA;
import static tech.jhipster.lite.generator.tools.domain.Constants.MAIN_RESOURCES;
import static tech.jhipster.lite.generator.tools.domain.Constants.POM_XML;
import static tech.jhipster.lite.generator.tools.domain.Constants.TEST_JAVA;

import java.util.List;
import tech.jhipster.lite.generator.server.springboot.cache.common.application.SpringBootCacheAssert;
import tech.jhipster.lite.generator.server.springboot.cache.ehcache.domain.EhcacheDomainService;
import tech.jhipster.lite.generator.tools.domain.DefaultConfig;
import tech.jhipster.lite.generator.tools.domain.Project;

public class EhcacheAssert {

  public static void assertInitJavaConfiguration(Project project) {
    assertDependencies(project);
    assertEnableCaching(project);
    assertJavaFiles(project);
    assertProperties(project);
  }

  public static void assertInitXmlConfiguration(Project project) {
    assertDependencies(project);
    assertXmlDependencies(project);
    assertEhcacheXml(project);
    assertXmlProperty(project);
  }

  public static void assertDependencies(Project project) {
    SpringBootCacheAssert.assertDependencies(project);

    assertFileContent(
      project,
      POM_XML,
      List.of("<dependency>", "<groupId>javax.cache</groupId>", "<artifactId>cache-api</artifactId>", "</dependency>")
    );

    assertFileContent(
      project,
      POM_XML,
      List.of("<dependency>", "<groupId>org.ehcache</groupId>", "<artifactId>ehcache</artifactId>", "</dependency>")
    );
  }

  public static void assertXmlDependencies(Project project) {
    assertFileContent(
      project,
      POM_XML,
      List.of("<dependency>", "<groupId>jakarta.xml.bind</groupId>", "<artifactId>jakarta.xml.bind-api</artifactId>", "</dependency>")
    );
    assertFileContent(
      project,
      POM_XML,
      List.of(
        "<dependency>",
        "<groupId>org.glassfish.jaxb</groupId>",
        "<artifactId>jaxb-runtime</artifactId>",
        "<scope>runtime</scope>",
        "</dependency>"
      )
    );
  }

  public static void assertEnableCaching(Project project) {
    SpringBootCacheAssert.assertEnableCaching(project);
  }

  public static void assertJavaFiles(Project project) {
    String basePackage = project.getPackageName().orElse(DefaultConfig.DEFAULT_PACKAGE_NAME);
    String cachePackage = basePackage + ".technical.infrastructure.secondary.cache";

    String basePath = project.getPackageNamePath().orElse(getPath(DefaultConfig.PACKAGE_PATH));
    String ehcachePath = getPath(MAIN_JAVA, basePath, EhcacheDomainService.DESTINATION);

    assertFileExist(project, getPath(ehcachePath, "CacheConfiguration.java"));
    assertFileExist(project, getPath(ehcachePath, "EhcacheProperties.java"));

    assertFileContent(project, getPath(ehcachePath, "CacheConfiguration.java"), "package " + cachePackage);
    assertFileContent(project, getPath(ehcachePath, "EhcacheProperties.java"), "package " + cachePackage);

    String ehcacheTestPath = getPath(TEST_JAVA, basePath, EhcacheDomainService.DESTINATION);

    assertFileExist(project, getPath(ehcacheTestPath, "CacheConfigurationIT.java"));
    assertFileExist(project, getPath(ehcacheTestPath, "CacheConfigurationTest.java"));

    assertFileContent(project, getPath(ehcacheTestPath, "CacheConfigurationIT.java"), "package " + cachePackage);
    assertFileContent(project, getPath(ehcacheTestPath, "CacheConfigurationTest.java"), "package " + cachePackage);
  }

  public static void assertProperties(Project project) {
    assertFileContent(
      project,
      getPath(MAIN_RESOURCES, "config/application.properties"),
      List.of("application.cache.ehcache.max-entries=100", "application.cache.ehcache.time-to-live-seconds=3600")
    );
  }

  public static void assertEhcacheXml(Project project) {
    assertFileExist(project, getPath(MAIN_RESOURCES, "config/ehcache/ehcache.xml"));
  }

  public static void assertXmlProperty(Project project) {
    assertFileContent(
      project,
      getPath(MAIN_RESOURCES, "config/application.properties"),
      List.of("spring.cache.jcache.config=classpath:config/ehcache/ehcache.xml")
    );
  }
}
