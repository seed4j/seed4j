package tech.jhipster.lite.generator.server.springboot.devtools.domain;

import java.util.Map;
import java.util.TreeMap;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;

public record DevToolsDomainService(BuildToolService buildToolService, SpringBootCommonService springBootCommonService)
  implements DevToolsService {
  public static final String SOURCE = "server/springboot/devtools";

  @Override
  public void init(Project project) {
    addSpringBootDevTools(project);
    addProperties(project);
  }

  @Override
  public void addSpringBootDevTools(Project project) {
    Dependency dependency = Dependency.builder().groupId("org.springframework.boot").artifactId("spring-boot-devtools").optional().build();

    buildToolService.addDependency(project, dependency);
  }

  @Override
  public void addProperties(Project project) {
    springBootCommonService.addPropertiesComment(project, "Spring Boot Dev Tools");
    springPropertiesDevTools(false).forEach((k, v) -> springBootCommonService.addProperties(project, k, v));
    springBootCommonService.addPropertiesNewLine(project);

    springBootCommonService.addPropertiesLocalComment(project, "Spring Boot Dev Tools");
    springPropertiesDevTools(true).forEach((k, v) -> springBootCommonService.addPropertiesLocal(project, k, v));
    springBootCommonService.addPropertiesLocalNewLine(project);
  }

  private Map<String, Object> springPropertiesDevTools(boolean fast) {
    TreeMap<String, Object> result = new TreeMap<>();

    result.put("spring.devtools.livereload.enabled", fast);
    result.put("spring.devtools.restart.enabled", fast);

    return result;
  }
}
