package com.seed4j.module.infrastructure.secondary;

import com.seed4j.module.domain.*;
import com.seed4j.module.domain.javaproperties.SpringPropertyType;
import com.seed4j.module.domain.landscape.Seed4JLandscape;
import com.seed4j.module.domain.postaction.Seed4JModuleExecutionContext;
import com.seed4j.module.domain.resource.Seed4JModulesResources;
import com.seed4j.module.infrastructure.secondary.javadependency.FileSystemJavaBuildCommandsHandler;
import com.seed4j.project.infrastructure.primary.JavaProjects;
import com.seed4j.shared.error.domain.Assert;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
class FileSystemSeed4JModulesRepository implements Seed4JModulesRepository {

  public static final String DEFAULT_MAIN_FOLDER = "src/main/resources/config/";
  public static final String DEFAULT_TEST_FOLDER = "src/test/resources/config/";
  public static final String TEST_META_INF_FOLDER = "src/test/resources/META-INF/";

  private final JavaProjects projects;
  private final Seed4JModulesResources resources;

  private final FileSystemSeed4JModuleFiles files;
  private final FileSystemJavaBuildCommandsHandler javaBuild;
  private final FileSystemSpringPropertiesCommandsHandler springProperties;
  private final FileSystemYamlSpringPropertiesCommandsHandler yamlSpringProperties;
  private final FileSystemYamlSpringCommentsCommandsHandler yamlSpringComments;
  private final FileSystemSpringCommentsCommandsHandler springComments;
  private final FileSystemSpringFactoriesCommandsHandler springFactories;
  private final FileSystemPackageJsonHandler packageJson;
  private final FileSystemGitIgnoreHandler gitIgnore;
  private final FileSystemReplacer replacer;
  private final FileSystemStartupCommandsReadmeCommandsHandler startupCommands;
  private final FileSystemDockerComposeFileHandler dockerComposeFile;
  private final Seed4JLandscape landscape;

  public FileSystemSeed4JModulesRepository(
    JavaProjects projects,
    Seed4JModulesResources resources,
    FileSystemSeed4JModuleFiles files,
    FileSystemReplacer fileReplacer,
    FileSystemGitIgnoreHandler gitIgnore,
    FileSystemJavaBuildCommandsHandler javaBuild,
    FileSystemPackageJsonHandler packageJson,
    FileSystemStartupCommandsReadmeCommandsHandler startupCommands
  ) {
    this.projects = projects;
    this.resources = resources;
    this.landscape = Seed4JLandscape.from(resources);

    this.files = files;
    this.javaBuild = javaBuild;
    this.dockerComposeFile = new FileSystemDockerComposeFileHandler();
    this.springProperties = new FileSystemSpringPropertiesCommandsHandler();
    this.springComments = new FileSystemSpringCommentsCommandsHandler();
    this.yamlSpringProperties = new FileSystemYamlSpringPropertiesCommandsHandler();
    this.yamlSpringComments = new FileSystemYamlSpringCommentsCommandsHandler();
    this.springFactories = new FileSystemSpringFactoriesCommandsHandler();
    this.gitIgnore = gitIgnore;
    this.packageJson = packageJson;
    this.replacer = fileReplacer;
    this.startupCommands = startupCommands;
  }

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

  @Override
  public void apply(Seed4JModuleChanges changes) {
    Assert.notNull("changes", changes);

    changes.preActions().run();

    files.create(changes.projectFolder(), changes.filesToAdd());
    files.move(changes.projectFolder(), changes.filesToMove());
    files.delete(changes.projectFolder(), changes.filesToDelete());
    javaBuild.handle(changes.indentation(), changes.projectFolder(), changes.context(), changes.javaBuildCommands());
    springProperties.handle(changes.projectFolder(), changes.springProperties());
    springComments.handle(changes.projectFolder(), changes.springComments());
    yamlSpringProperties.handle(changes.indentation(), changes.projectFolder(), changes.springYamlProperties());
    yamlSpringComments.handle(changes.indentation(), changes.projectFolder(), changes.springYamlComments());
    springFactories.handle(changes.projectFolder(), changes.springFactories());
    gitIgnore.handle(changes.projectFolder(), changes.gitIgnore());
    packageJson.handle(changes.indentation(), changes.projectFolder(), changes.packageJson(), changes.context());
    replacer.handle(changes.projectFolder(), changes.replacers(), changes.context());
    startupCommands.handle(changes.projectFolder(), changes.startupCommands(), changes.context());
    dockerComposeFile.handle(changes.indentation(), changes.projectFolder(), changes.dockerComposeFile());
    changes.postActions().run(new Seed4JModuleExecutionContext(changes.projectFolder()));
  }

  @Override
  public void applied(Seed4JModuleApplied moduleApplied) {
    projects.moduleApplied(moduleApplied);
  }

  @Override
  public Seed4JModulesResources resources() {
    return resources;
  }

  @Override
  public Seed4JLandscape landscape() {
    return landscape;
  }
}
