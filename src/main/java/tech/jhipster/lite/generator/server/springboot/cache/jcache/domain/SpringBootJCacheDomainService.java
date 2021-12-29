package tech.jhipster.lite.generator.server.springboot.cache.jcache.domain;

import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.project.domain.Constants.MAIN_JAVA;
import static tech.jhipster.lite.generator.project.domain.Constants.TEST_JAVA;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.BASE_NAME;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.PACKAGE_NAME;

import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;
import tech.jhipster.lite.generator.server.springboot.cache.common.domain.SpringBootCacheService;

public class SpringBootJCacheDomainService implements SpringBootJCacheService {

  public static final String SOURCE = "server/springboot/cache/jcache";
  public static final String DESTINATION = "technical/infrastructure/secondary/cache";

  private final BuildToolService buildToolService;
  private final ProjectRepository projectRepository;
  private final SpringBootCacheService springBootCacheService;

  public SpringBootJCacheDomainService(
    BuildToolService buildToolService,
    ProjectRepository projectRepository,
    SpringBootCacheService springBootCacheService
  ) {
    this.buildToolService = buildToolService;
    this.projectRepository = projectRepository;
    this.springBootCacheService = springBootCacheService;
  }

  @Override
  public void addDependencies(Project project) {
    springBootCacheService.addDependencies(project);
    buildToolService.addDependency(project, SpringBootJCache.cacheApiDependency());
  }

  @Override
  public void addEnableCaching(Project project) {
    springBootCacheService.addEnableCaching(project);
  }

  @Override
  public void addJavaConfig(Project project) {
    project.addDefaultConfig(PACKAGE_NAME);
    project.addDefaultConfig(BASE_NAME);
    String packageNamePath = project.getPackageNamePath().orElse(getPath("com/mycompany/myapp"));

    templateToCache(project, packageNamePath, "src", "JCacheConfiguration.java", MAIN_JAVA);
    templateToCache(project, packageNamePath, "src", "JCacheConfigurer.java", MAIN_JAVA);
    templateToCache(project, packageNamePath, "src", "JCacheCreator.java", MAIN_JAVA);

    templateToCache(project, packageNamePath, "test", "JCacheCreatorTest.java", TEST_JAVA);
  }

  private void templateToCache(Project project, String source, String type, String sourceFilename, String destination) {
    projectRepository.template(project, getPath(SOURCE, type), sourceFilename, getPath(destination, source, DESTINATION));
  }
}
