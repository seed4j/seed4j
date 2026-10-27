package com.seed4j.module.infrastructure.secondary;

import com.seed4j.module.domain.*;
import com.seed4j.module.domain.landscape.Seed4JLandscape;
import com.seed4j.module.domain.postaction.Seed4JModuleExecutionContext;
import com.seed4j.module.domain.resource.Seed4JModulesResources;
import com.seed4j.module.infrastructure.secondary.javadependency.FileSystemJavaBuildCommandsHandler;
import com.seed4j.shared.error.domain.Assert;
import org.springframework.stereotype.Repository;

@Repository
class FileSystemSeed4JModulesRepository implements Seed4JModulesRepository {

  public static final String TEST_META_INF_FOLDER = "src/test/resources/META-INF/";

  private final AppliedModulesHistory history;
  private final Seed4JModulesResources resources;

  private final FileSystemSeed4JModuleFiles files;
  private final FileSystemJavaBuildCommandsHandler javaBuild;
  private final FileSystemApplicationPropertiesCommandsHandler applicationProperties;
  private final FileSystemYamlApplicationPropertiesCommandsHandler yamlApplicationProperties;
  private final FileSystemYamlApplicationPropertyCommentsCommandsHandler yamlApplicationPropertyComments;
  private final FileSystemApplicationPropertyCommentsCommandsHandler applicationPropertyComments;
  private final FileSystemSpringFactoriesCommandsHandler springFactories;
  private final FileSystemPackageJsonHandler packageJson;
  private final FileSystemGitIgnoreHandler gitIgnore;
  private final FileSystemReplacer replacer;
  private final FileSystemStartupCommandsReadmeCommandsHandler startupCommands;
  private final FileSystemDockerComposeFileHandler dockerComposeFile;
  private final Seed4JLandscape landscape;

  public FileSystemSeed4JModulesRepository(
    AppliedModulesHistory history,
    Seed4JModulesResources resources,
    FileSystemSeed4JModuleFiles files,
    FileSystemReplacer fileReplacer,
    FileSystemGitIgnoreHandler gitIgnore,
    FileSystemJavaBuildCommandsHandler javaBuild,
    FileSystemPackageJsonHandler packageJson,
    FileSystemStartupCommandsReadmeCommandsHandler startupCommands
  ) {
    this.history = history;
    this.resources = resources;
    this.landscape = Seed4JLandscape.from(resources);

    this.files = files;
    this.javaBuild = javaBuild;
    this.dockerComposeFile = new FileSystemDockerComposeFileHandler();
    this.applicationProperties = new FileSystemApplicationPropertiesCommandsHandler();
    this.applicationPropertyComments = new FileSystemApplicationPropertyCommentsCommandsHandler();
    this.yamlApplicationProperties = new FileSystemYamlApplicationPropertiesCommandsHandler();
    this.yamlApplicationPropertyComments = new FileSystemYamlApplicationPropertyCommentsCommandsHandler();
    this.springFactories = new FileSystemSpringFactoriesCommandsHandler();
    this.gitIgnore = gitIgnore;
    this.packageJson = packageJson;
    this.replacer = fileReplacer;
    this.startupCommands = startupCommands;
  }

  @Override
  public void apply(Seed4JModuleChanges changes) {
    Assert.notNull("changes", changes);

    changes.preActions().run();

    files.create(changes.projectFolder(), changes.filesToAdd());
    files.move(changes.projectFolder(), changes.filesToMove());
    files.delete(changes.projectFolder(), changes.filesToDelete());
    javaBuild.handle(changes.indentation(), changes.projectFolder(), changes.context(), changes.javaBuildCommands());
    applicationProperties.handle(changes.projectFolder(), changes.applicationProperties());
    applicationPropertyComments.handle(changes.projectFolder(), changes.applicationPropertyComments());
    yamlApplicationProperties.handle(changes.indentation(), changes.projectFolder(), changes.yamlApplicationProperties());
    yamlApplicationPropertyComments.handle(changes.indentation(), changes.projectFolder(), changes.yamlApplicationPropertyComments());
    springFactories.handle(changes.projectFolder(), changes.springFactories());
    gitIgnore.handle(changes.projectFolder(), changes.gitIgnore());
    packageJson.handle(
      changes.indentation(),
      changes.projectFolder(),
      changes.nodePackageManager(),
      changes.packageJson(),
      changes.context()
    );
    replacer.handle(changes.projectFolder(), changes.replacers(), changes.context());
    startupCommands.handle(changes.projectFolder(), changes.startupCommands(), changes.context());
    dockerComposeFile.handle(changes.indentation(), changes.projectFolder(), changes.dockerComposeFile());
    changes.postActions().run(new Seed4JModuleExecutionContext(changes.projectFolder()));
  }

  @Override
  public void applied(Seed4JModuleApplied moduleApplied) {
    history.append(moduleApplied);
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
