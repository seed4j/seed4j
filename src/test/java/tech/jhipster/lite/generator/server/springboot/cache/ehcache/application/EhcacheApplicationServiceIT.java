package tech.jhipster.lite.generator.server.springboot.cache.ehcache.application;

import static tech.jhipster.lite.TestUtils.tmpProject;
import static tech.jhipster.lite.generator.server.springboot.cache.ehcache.application.EhcacheAssert.assertDependencies;
import static tech.jhipster.lite.generator.server.springboot.cache.ehcache.application.EhcacheAssert.assertEhcacheXml;
import static tech.jhipster.lite.generator.server.springboot.cache.ehcache.application.EhcacheAssert.assertEnableCaching;
import static tech.jhipster.lite.generator.server.springboot.cache.ehcache.application.EhcacheAssert.assertInitJavaConfiguration;
import static tech.jhipster.lite.generator.server.springboot.cache.ehcache.application.EhcacheAssert.assertInitXmlConfiguration;
import static tech.jhipster.lite.generator.server.springboot.cache.ehcache.application.EhcacheAssert.assertJavaFiles;
import static tech.jhipster.lite.generator.server.springboot.cache.ehcache.application.EhcacheAssert.assertProperties;
import static tech.jhipster.lite.generator.server.springboot.cache.ehcache.application.EhcacheAssert.assertXmlDependencies;
import static tech.jhipster.lite.generator.server.springboot.cache.ehcache.application.EhcacheAssert.assertXmlProperty;
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
class EhcacheApplicationServiceIT {

  @Autowired
  EhcacheApplicationService ehcacheApplicationService;

  @Autowired
  ProjectApplicationService projectApplicationService;

  @Autowired
  MavenApplicationService mavenApplicationService;

  @Autowired
  SpringBootApplicationService springBootApplicationService;

  @Test
  void shouldInitJavaConfiguration() {
    Project project = tmpProject();
    project.addConfig(BASE_NAME, "foo");

    projectApplicationService.init(project);

    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    ehcacheApplicationService.initJavaConfiguration(project);

    assertInitJavaConfiguration(project);
  }

  @Test
  void shouldInitXmlConfiguration() {
    Project project = tmpProject();
    project.addConfig(BASE_NAME, "foo");

    projectApplicationService.init(project);

    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    ehcacheApplicationService.initXmlConfiguration(project);

    assertInitXmlConfiguration(project);
  }

  @Test
  void shouldAddDependencies() {
    Project project = tmpProject();
    project.addConfig(BASE_NAME, "bar");
    project.addConfig(PACKAGE_NAME, "tech.jhipster.baz");
    projectApplicationService.init(project);
    mavenApplicationService.addPomXml(project);

    ehcacheApplicationService.addDependencies(project);

    assertDependencies(project);
  }

  @Test
  void shouldAddXmlDependencies() {
    Project project = tmpProject();
    project.addConfig(BASE_NAME, "bar");
    project.addConfig(PACKAGE_NAME, "tech.jhipster.baz");
    projectApplicationService.init(project);
    mavenApplicationService.addPomXml(project);

    ehcacheApplicationService.addXmlDependencies(project);

    assertXmlDependencies(project);
  }

  @Test
  void shouldEnableCaching() {
    Project project = tmpProject();
    project.addConfig(BASE_NAME, "bar");
    project.addConfig(PACKAGE_NAME, "tech.jhipster.baz");
    projectApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    ehcacheApplicationService.addEnableCaching(project);

    assertEnableCaching(project);
  }

  @Test
  void shouldAddJavaConfig() {
    Project project = tmpProject();
    project.addConfig(BASE_NAME, "bar");
    project.addConfig(PACKAGE_NAME, "tech.jhipster.baz");
    projectApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    ehcacheApplicationService.addJavaConfig(project);

    assertJavaFiles(project);
  }

  @Test
  void shouldAddJavaProperties() {
    Project project = tmpProject();
    project.addConfig(BASE_NAME, "bar");
    projectApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    ehcacheApplicationService.addJavaProperties(project);

    assertProperties(project);
  }

  @Test
  void shouldAddEhcacheXml() {
    Project project = tmpProject();
    project.addConfig(BASE_NAME, "bar");
    project.addConfig(PACKAGE_NAME, "tech.jhipster.baz");
    projectApplicationService.init(project);

    ehcacheApplicationService.addEhcacheXml(project);

    assertEhcacheXml(project);
  }

  @Test
  void shouldAddXmlProperty() {
    Project project = tmpProject();
    project.addConfig(BASE_NAME, "bar");
    projectApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    ehcacheApplicationService.addXmlProperty(project);

    assertXmlProperty(project);
  }
}
