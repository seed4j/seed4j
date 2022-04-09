package tech.jhipster.lite.generator.server.springboot.mvc.springdoc.domain;

import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.server.springboot.mvc.springdoc.domain.SpringdocConstants.API_DESCRIPTION_CONFIG_KEY;
import static tech.jhipster.lite.generator.server.springboot.mvc.springdoc.domain.SpringdocConstants.API_EXT_DOC_DESCRIPTION_CONFIG_KEY;
import static tech.jhipster.lite.generator.server.springboot.mvc.springdoc.domain.SpringdocConstants.API_EXT_DOC_URL_CONFIG_KEY;
import static tech.jhipster.lite.generator.server.springboot.mvc.springdoc.domain.SpringdocConstants.API_LICENSE_NAME_CONFIG_KEY;
import static tech.jhipster.lite.generator.server.springboot.mvc.springdoc.domain.SpringdocConstants.API_LICENSE_URL_CONFIG_KEY;
import static tech.jhipster.lite.generator.server.springboot.mvc.springdoc.domain.SpringdocConstants.API_TITLE_CONFIG_KEY;
import static tech.jhipster.lite.generator.server.springboot.mvc.springdoc.domain.SpringdocConstants.DEFAULT_API_DESCRIPTION;
import static tech.jhipster.lite.generator.server.springboot.mvc.springdoc.domain.SpringdocConstants.DEFAULT_API_TITLE;
import static tech.jhipster.lite.generator.server.springboot.mvc.springdoc.domain.SpringdocConstants.DEFAULT_EXT_DOC_DESCRIPTION;
import static tech.jhipster.lite.generator.server.springboot.mvc.springdoc.domain.SpringdocConstants.DEFAULT_EXT_DOC_URL;
import static tech.jhipster.lite.generator.server.springboot.mvc.springdoc.domain.SpringdocConstants.DEFAULT_LICENSE_NAME;
import static tech.jhipster.lite.generator.server.springboot.mvc.springdoc.domain.SpringdocConstants.DEFAULT_LICENSE_URL;
import static tech.jhipster.lite.generator.server.springboot.mvc.springdoc.domain.SpringdocConstants.DEFAULT_SWAGGER_UI_SORT_VALUE;
import static tech.jhipster.lite.generator.server.springboot.mvc.springdoc.domain.SpringdocConstants.DEFAULT_TRY_OUT_ENABLED;
import static tech.jhipster.lite.generator.tools.domain.Constants.MAIN_JAVA;
import static tech.jhipster.lite.generator.tools.domain.DefaultConfig.BASE_NAME;
import static tech.jhipster.lite.generator.tools.domain.DefaultConfig.PACKAGE_NAME;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import tech.jhipster.lite.common.domain.WordUtils;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.domain.ProjectRepository;

public class SpringdocDomainService implements SpringdocService {

  private static final String SOURCE = "server/springboot/mvc/springdoc";
  private static final String DESTINATION = "technical/infrastructure/primary/springdoc";

  private final BuildToolService buildToolService;
  private final ProjectRepository projectRepository;
  private final SpringBootCommonService springBootCommonService;

  public SpringdocDomainService(
    BuildToolService buildToolService,
    ProjectRepository projectRepository,
    SpringBootCommonService springBootCommonService
  ) {
    this.buildToolService = buildToolService;
    this.projectRepository = projectRepository;
    this.springBootCommonService = springBootCommonService;
  }

  @Override
  public void init(Project project) {
    addSpringdocDependency(project);
    addJavaFiles(project);
    addProperties(project);
  }

  @Override
  public void initWithSecurityJWT(Project project) {
    addSpringdocDependency(project);
    addJavaFilesWithSecurityJWT(project);
    addProperties(project);
  }

  @Override
  public void addSpringdocDependency(Project project) {
    this.buildToolService.getVersion(project, "springdoc-openapi")
      .ifPresentOrElse(
        version -> {
          buildToolService.addProperty(project, "springdoc-openapi-ui.version", version);
          buildToolService.addDependency(project, Springdoc.springdocDependency());
        },
        () -> {
          throw new GeneratorException("Springdoc Openapi version not found");
        }
      );
  }

  @Override
  public void addJavaFiles(Project project) {
    addDefaultParameters(project);
    String packageNamePath = project.getPackageNamePath().orElse(getPath("com/mycompany/myapp"));
    projectRepository.template(
      project,
      getPath(SOURCE, "src"),
      "SpringdocConfiguration.java",
      getPath(MAIN_JAVA, packageNamePath, DESTINATION)
    );
  }

  private void addJavaFilesWithSecurityJWT(Project project) {
    addDefaultParameters(project);
    String packageNamePath = project.getPackageNamePath().orElse(getPath("com/mycompany/myapp"));
    projectRepository.template(
      project,
      getPath(SOURCE, "src"),
      "SpringdocConfigurationSecurityJWT.java",
      getPath(MAIN_JAVA, packageNamePath, DESTINATION),
      "SpringdocConfiguration.java"
    );
  }

  @Override
  public void addProperties(Project project) {
    springBootCommonService.addPropertiesComment(project, "Springdoc Configuration");
    getSpringdocProperties().forEach((k, v) -> springBootCommonService.addProperties(project, k, v));
    springBootCommonService.addPropertiesNewLine(project);
  }

  private void addDefaultParameters(Project project) {
    project.addDefaultConfig(PACKAGE_NAME);
    project.addDefaultConfig(BASE_NAME);
    String baseName = project.getBaseName().orElseThrow();
    project.addConfig("baseNameLowercase", WordUtils.lowerFirst(baseName));
    getDefaultSpringdocConfig().forEach((key, defaultValue) -> updateEmptyConfig(project, key, defaultValue));
  }

  private TreeMap<String, Object> getSpringdocProperties() {
    TreeMap<String, Object> result = new TreeMap<>();

    result.put("springdoc.swagger-ui.operationsSorter", DEFAULT_SWAGGER_UI_SORT_VALUE);
    result.put("springdoc.swagger-ui.tagsSorter", DEFAULT_SWAGGER_UI_SORT_VALUE);
    result.put("springdoc.swagger-ui.tryItOutEnabled", DEFAULT_TRY_OUT_ENABLED);

    return result;
  }

  private Map<String, String> getDefaultSpringdocConfig() {
    Map<String, String> result = new HashMap<>();
    result.put(API_TITLE_CONFIG_KEY, DEFAULT_API_TITLE);
    result.put(API_DESCRIPTION_CONFIG_KEY, DEFAULT_API_DESCRIPTION);
    result.put(API_LICENSE_NAME_CONFIG_KEY, DEFAULT_LICENSE_NAME);
    result.put(API_LICENSE_URL_CONFIG_KEY, DEFAULT_LICENSE_URL);
    result.put(API_EXT_DOC_DESCRIPTION_CONFIG_KEY, DEFAULT_EXT_DOC_DESCRIPTION);
    result.put(API_EXT_DOC_URL_CONFIG_KEY, DEFAULT_EXT_DOC_URL);

    return result;
  }

  private void updateEmptyConfig(Project project, String configKey, String defaultValue) {
    project.addConfig(configKey, project.getConfig(configKey).orElse(defaultValue));
  }
}
