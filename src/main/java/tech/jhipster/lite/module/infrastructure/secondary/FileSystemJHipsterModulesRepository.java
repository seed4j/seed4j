package tech.jhipster.lite.module.infrastructure.secondary;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import tech.jhipster.lite.module.domain.JHipsterModuleApplied;
import tech.jhipster.lite.module.domain.JHipsterModuleChanges;
import tech.jhipster.lite.module.domain.JHipsterModulesRepository;
import tech.jhipster.lite.module.domain.ProjectFiles;
import tech.jhipster.lite.module.domain.javaproperties.SpringPropertyType;
import tech.jhipster.lite.module.domain.landscape.JHipsterLandscape;
import tech.jhipster.lite.module.domain.npm.NpmVersions;
import tech.jhipster.lite.module.domain.postaction.JHipsterModuleExecutionContext;
import tech.jhipster.lite.module.domain.resource.JHipsterModulesResources;
import tech.jhipster.lite.module.infrastructure.secondary.javadependency.FileSystemJavaBuildCommandsHandler;
import tech.jhipster.lite.project.infrastructure.primary.JavaProjects;
import tech.jhipster.lite.shared.error.domain.Assert;

@Repository
class FileSystemJHipsterModulesRepository implements JHipsterModulesRepository {

  public static final String DEFAULT_MAIN_FOLDER = "src/main/resources/config/";
  public static final String DEFAULT_TEST_FOLDER = "src/test/resources/config/";
  public static final String TEST_META_INF_FOLDER = "src/test/resources/META-INF/";

  public static Map<SpringPropertyType, List<String>> buildPaths() {
    return Map.of(
      SpringPropertyType.MAIN_PROPERTIES,
      List.of(DEFAULT_MAIN_FOLDER, "src/main/resources/"),
      SpringPropertyType.MAIN_BOOTSTRAP_PROPERTIES,
      List.of(DEFAULT_MAIN_FOLDER, "src/main/resources/"),
      SpringPropertyType.TEST_PROPERTIES,
      List.of(DEFAULT_TEST_FOLDER, "src/test/resources/"),
      SpringPropertyType.TEST_BOOTSTRAP_PROPERTIES,
      List.of(DEFAULT_TEST_FOLDER, "src/test/resources/")
    );
  }

  private final JavaProjects projects;
  private final JHipsterModulesResources resources;

  private final FileSystemJHipsterModuleFiles files;
  private final FileSystemJavaBuildCommandsHandler javaBuild;
  private final FileSystemSpringPropertiesCommandsHandler springProperties;
  private final FileSystemYamlSpringPropertiesCommandsHandler yamlSpringProperties;
  private final FileSystemYamlSpringCommentsCommandsHandler yamlSpringComments;
  private final FileSystemSpringCommentsCommandsHandler springComments;
  private final FileSystemSpringFactoriesCommandsHandler springFactories;
  private final FileSystemPackageJsonHandler packageJson;
  private final FileSystemReplacer replacer;
  private final JHipsterLandscape landscape;

  public FileSystemJHipsterModulesRepository(
    ProjectFiles filesReader,
    NpmVersions npmVersions,
    JavaProjects projects,
    JHipsterModulesResources resources
  ) {
    this.projects = projects;
    this.resources = resources;
    landscape = JHipsterLandscape.from(resources);

    files = new FileSystemJHipsterModuleFiles(filesReader);
    javaBuild = new FileSystemJavaBuildCommandsHandler();
    springProperties = new FileSystemSpringPropertiesCommandsHandler();
    springComments = new FileSystemSpringCommentsCommandsHandler();
    yamlSpringProperties = new FileSystemYamlSpringPropertiesCommandsHandler();
    yamlSpringComments = new FileSystemYamlSpringCommentsCommandsHandler();
    springFactories = new FileSystemSpringFactoriesCommandsHandler();
    packageJson = new FileSystemPackageJsonHandler(npmVersions);
    replacer = new FileSystemReplacer();
  }

  @Override
  public void apply(JHipsterModuleChanges changes) {
    Assert.notNull("changes", changes);

    changes.preActions().run();

    files.create(changes.projectFolder(), changes.filesToAdd());
    files.move(changes.projectFolder(), changes.filesToMove());
    files.delete(changes.projectFolder(), changes.filesToDelete());
    javaBuild.handle(changes.indentation(), changes.projectFolder(), changes.javaBuildCommands());
    springProperties.handle(changes.projectFolder(), changes.springProperties());
    springComments.handle(changes.projectFolder(), changes.springComments());
    yamlSpringProperties.handle(changes.indentation(), changes.projectFolder(), changes.springYamlProperties());
    yamlSpringComments.handle(changes.indentation(), changes.projectFolder(), changes.springYamlComments());
    springFactories.handle(changes.projectFolder(), changes.springFactories());
    packageJson.handle(changes.indentation(), changes.projectFolder(), changes.packageJson());
    replacer.handle(changes.projectFolder(), changes.replacers());

    changes.postActions().run(new JHipsterModuleExecutionContext(changes.projectFolder()));
  }

  @Override
  public void applied(JHipsterModuleApplied moduleApplied) {
    projects.moduleApplied(moduleApplied);
  }

  @Override
  public JHipsterModulesResources resources() {
    return resources;
  }

  @Override
  public JHipsterLandscape landscape() {
    return landscape;
  }
}
