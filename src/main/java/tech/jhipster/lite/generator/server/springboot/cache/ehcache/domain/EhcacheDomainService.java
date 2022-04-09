package tech.jhipster.lite.generator.server.springboot.cache.ehcache.domain;

import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.tools.domain.Constants.MAIN_JAVA;
import static tech.jhipster.lite.generator.tools.domain.Constants.MAIN_RESOURCES;
import static tech.jhipster.lite.generator.tools.domain.Constants.TEST_JAVA;
import static tech.jhipster.lite.generator.tools.domain.DefaultConfig.BASE_NAME;
import static tech.jhipster.lite.generator.tools.domain.DefaultConfig.PACKAGE_NAME;

import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.server.springboot.cache.common.domain.SpringBootCacheService;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.domain.ProjectRepository;

public class EhcacheDomainService implements EhcacheService {

  public static final String SOURCE = "server/springboot/cache/ehcache";
  public static final String DESTINATION = "technical/infrastructure/secondary/cache";

  private final BuildToolService buildToolService;
  private final ProjectRepository projectRepository;
  private final SpringBootCacheService springBootCacheService;
  private final SpringBootCommonService springBootCommonService;

  public EhcacheDomainService(
    BuildToolService buildToolService,
    ProjectRepository projectRepository,
    SpringBootCacheService springBootCacheService,
    SpringBootCommonService springBootCommonService
  ) {
    this.buildToolService = buildToolService;
    this.projectRepository = projectRepository;
    this.springBootCacheService = springBootCacheService;
    this.springBootCommonService = springBootCommonService;
  }

  @Override
  public void initJavaConfiguration(Project project) {
    addDependencies(project);
    addJavaConfig(project);
    addJavaProperties(project);
  }

  @Override
  public void initXmlConfiguration(Project project) {
    addDependencies(project);
    addXmlDependencies(project);
    addEnableCaching(project);
    addEhcacheXml(project);
    addXmlProperty(project);
  }

  @Override
  public void addDependencies(Project project) {
    springBootCacheService.addDependencies(project);
    buildToolService.addDependency(project, Ehcache.cacheApiDependency());
    buildToolService.addDependency(project, Ehcache.ehcacheDependency());
  }

  @Override
  public void addXmlDependencies(Project project) {
    buildToolService.addDependency(project, Ehcache.jakartaXmlBindApi());
    buildToolService.addDependency(project, Ehcache.glassfishJaxbRuntime());
  }

  @Override
  public void addEnableCaching(Project project) {
    springBootCacheService.addEnableCaching(project);
  }

  @Override
  public void addEhcacheXml(Project project) {
    projectRepository.add(project, getPath(SOURCE, "resources"), "ehcache.xml", getPath(MAIN_RESOURCES, "config/ehcache"));
  }

  @Override
  public void addXmlProperty(Project project) {
    springBootCommonService.addProperties(project, "spring.cache.jcache.config", "classpath:config/ehcache/ehcache.xml");
  }

  @Override
  public void addJavaConfig(Project project) {
    project.addDefaultConfig(PACKAGE_NAME);
    project.addDefaultConfig(BASE_NAME);
    String packageNamePath = project.getPackageNamePath().orElse(getPath("com/mycompany/myapp"));

    templateToEhcache(project, packageNamePath, "src", "CacheConfiguration.java", MAIN_JAVA);
    templateToEhcache(project, packageNamePath, "src", "EhcacheProperties.java", MAIN_JAVA);

    templateToEhcache(project, packageNamePath, "test", "CacheConfigurationIT.java", TEST_JAVA);
    templateToEhcache(project, packageNamePath, "test", "CacheConfigurationTest.java", TEST_JAVA);
  }

  @Override
  public void addJavaProperties(Project project) {
    springBootCommonService.addPropertiesComment(project, "Ehcache Configuration");
    springBootCommonService.addProperties(project, "application.cache.ehcache.max-entries", 100);
    springBootCommonService.addProperties(project, "application.cache.ehcache.time-to-live-seconds", 3600);
    springBootCommonService.addPropertiesNewLine(project);
  }

  private void templateToEhcache(Project project, String source, String type, String sourceFilename, String destination) {
    projectRepository.template(project, getPath(SOURCE, type), sourceFilename, getPath(destination, source, DESTINATION));
  }
}
