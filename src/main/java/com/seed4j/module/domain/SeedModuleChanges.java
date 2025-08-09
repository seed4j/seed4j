package com.seed4j.module.domain;

import com.seed4j.module.domain.file.SeedFilesToDelete;
import com.seed4j.module.domain.file.SeedFilesToMove;
import com.seed4j.module.domain.file.SeedTemplatedFiles;
import com.seed4j.module.domain.gitignore.SeedModuleGitIgnore;
import com.seed4j.module.domain.javabuild.command.JavaBuildCommands;
import com.seed4j.module.domain.javaproperties.SpringComments;
import com.seed4j.module.domain.javaproperties.SpringFactories;
import com.seed4j.module.domain.javaproperties.SpringProperties;
import com.seed4j.module.domain.packagejson.SeedModulePackageJson;
import com.seed4j.module.domain.postaction.SeedModulePostActions;
import com.seed4j.module.domain.properties.SeedProjectFolder;
import com.seed4j.module.domain.replacement.ContentReplacers;
import com.seed4j.module.domain.standalonedocker.SeedModuleDockerComposeFile;
import com.seed4j.module.domain.startupcommand.SeedStartupCommands;
import com.seed4j.shared.error.domain.Assert;
import com.seed4j.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;

@SuppressWarnings("java:S6539")
public final class SeedModuleChanges {

  private final SeedModuleContext context;
  private final SeedProjectFolder projectFolder;
  private final Indentation indentation;
  private final SeedTemplatedFiles filesToAdd;
  private final SeedFilesToMove filesToMove;
  private final SeedFilesToDelete filesToDelete;
  private final ContentReplacers replacers;
  private final SeedStartupCommands startupCommands;
  private final JavaBuildCommands javaBuildCommands;
  private final SeedModulePackageJson packageJson;
  private final SeedModulePreActions preActions;
  private final SeedModulePostActions postActions;
  private final SpringProperties springProperties;
  private final SpringComments springComments;
  private final SpringProperties springYamlProperties;
  private final SpringComments springYamlComments;
  private final SpringFactories springFactories;
  private final SeedModuleGitIgnore gitIgnore;
  private final SeedModuleDockerComposeFile dockerComposeFile;

  private SeedModuleChanges(SeedModuleChangesBuilder builder) {
    assertMandatoryFields(builder);

    context = builder.context;
    projectFolder = builder.projectFolder;
    indentation = builder.indentation;
    filesToAdd = builder.filesToAdd;
    filesToMove = builder.filesToMove;
    filesToDelete = builder.filesToDelete;
    replacers = builder.replacers;
    startupCommands = builder.startupCommands;
    javaBuildCommands = builder.javaBuildCommands;
    packageJson = builder.packageJson;
    preActions = builder.preActions;
    postActions = builder.postActions;
    springProperties = builder.springProperties;
    springComments = builder.springComments;
    springYamlProperties = builder.springYamlProperties;
    springYamlComments = builder.springYamlComments;
    springFactories = builder.springFactories;
    gitIgnore = builder.gitIgnore;
    dockerComposeFile = builder.dockerComposeFile;
  }

  private void assertMandatoryFields(SeedModuleChangesBuilder builder) {
    Assert.notNull("context", builder.context);
    Assert.notNull("projectFolder", builder.projectFolder);
    Assert.notNull("indentation", builder.indentation);
    Assert.notNull("filesToAdd", builder.filesToAdd);
    Assert.notNull("filesToMove", builder.filesToMove);
    Assert.notNull("filesToDelete", builder.filesToDelete);
    Assert.notNull("replacers", builder.replacers);
    Assert.notNull("javaBuildCommands", builder.javaBuildCommands);
    Assert.notNull("preActions", builder.preActions);
    Assert.notNull("postActions", builder.postActions);
    Assert.notNull("springFactories", builder.springFactories);
  }

  public static SeedModuleChangesContextBuilder builder() {
    return new SeedModuleChangesBuilder();
  }

  public SeedModuleContext context() {
    return context;
  }

  public SeedProjectFolder projectFolder() {
    return projectFolder;
  }

  public Indentation indentation() {
    return indentation;
  }

  public SeedTemplatedFiles filesToAdd() {
    return filesToAdd;
  }

  public SeedFilesToMove filesToMove() {
    return filesToMove;
  }

  public SeedFilesToDelete filesToDelete() {
    return filesToDelete;
  }

  public ContentReplacers replacers() {
    return replacers;
  }

  public SeedStartupCommands startupCommands() {
    return startupCommands;
  }

  public JavaBuildCommands javaBuildCommands() {
    return javaBuildCommands;
  }

  public SeedModulePackageJson packageJson() {
    return packageJson;
  }

  public SeedModuleGitIgnore gitIgnore() {
    return gitIgnore;
  }

  public SeedModulePreActions preActions() {
    return preActions;
  }

  public SeedModulePostActions postActions() {
    return postActions;
  }

  public SpringProperties springYamlProperties() {
    return springYamlProperties;
  }

  @ExcludeFromGeneratedCodeCoverage(reason = "Handling YAML comments is not yet implemented")
  public SpringComments springYamlComments() {
    return springYamlComments;
  }

  public SpringProperties springProperties() {
    return springProperties;
  }

  public SpringComments springComments() {
    return springComments;
  }

  public SpringFactories springFactories() {
    return springFactories;
  }

  public SeedModuleDockerComposeFile dockerComposeFile() {
    return dockerComposeFile;
  }

  private static final class SeedModuleChangesBuilder
    implements
      SeedModuleChangesContextBuilder,
      SeedModuleChangesProjectFolderBuilder,
      SeedModuleChangesIndentationBuilder,
      SeedModuleChangesFilesToAddBuilder,
      SeedModuleChangesFilesToMoveBuilder,
      SeedModuleChangesFilesToDeleteBuilder,
      SeedModuleChangesReplacersBuilder,
      SeedModuleChangesStartupCommandsBuilder,
      SeedModuleChangesJavaBuildCommandsBuilder,
      SeedModuleChangesPackageJsonBuilder,
      SeedModuleChangesPreActionsBuilder,
      SeedModuleChangesPostActionsBuilder,
      SeedModuleChangesSpringPropertiesBuilder,
      SeedModuleChangesSpringCommentsBuilder,
      SeedModuleChangesSpringFactoriesBuilder,
      SeedModuleChangesSpringYamlCommentsBuilder,
      SeedModuleChangesGitIgnorePatternsBuilder,
      SeedModuleChangesDockerComposeFileBuilder {

    private SeedModuleContext context;
    private SeedProjectFolder projectFolder;
    private SeedTemplatedFiles filesToAdd;
    private SeedFilesToMove filesToMove;
    private SeedFilesToDelete filesToDelete;
    private ContentReplacers replacers;
    private SeedStartupCommands startupCommands;
    private JavaBuildCommands javaBuildCommands;
    private SeedModulePackageJson packageJson;
    private Indentation indentation;
    private SeedModulePreActions preActions;
    private SeedModulePostActions postActions;
    private SpringProperties springProperties = SpringProperties.EMPTY;
    private SpringComments springComments = SpringComments.EMPTY;
    private SpringProperties springYamlProperties = SpringProperties.EMPTY;
    private SpringComments springYamlComments = SpringComments.EMPTY;
    private SpringFactories springFactories;
    private SeedModuleGitIgnore gitIgnore;
    private SeedModuleDockerComposeFile dockerComposeFile;

    @Override
    public SeedModuleChangesProjectFolderBuilder context(SeedModuleContext context) {
      this.context = context;

      return this;
    }

    @Override
    public SeedModuleChangesIndentationBuilder projectFolder(SeedProjectFolder projectFolder) {
      this.projectFolder = projectFolder;

      return this;
    }

    @Override
    public SeedModuleChangesFilesToAddBuilder indentation(Indentation indentation) {
      this.indentation = indentation;

      return this;
    }

    @Override
    public SeedModuleChangesFilesToMoveBuilder filesToAdd(SeedTemplatedFiles filesToAdd) {
      this.filesToAdd = filesToAdd;

      return this;
    }

    @Override
    public SeedModuleChangesFilesToDeleteBuilder filesToMove(SeedFilesToMove filesToMove) {
      this.filesToMove = filesToMove;

      return this;
    }

    @Override
    public SeedModuleChangesReplacersBuilder filesToDelete(SeedFilesToDelete filesToDelete) {
      this.filesToDelete = filesToDelete;

      return this;
    }

    @Override
    public SeedModuleChangesStartupCommandsBuilder replacers(ContentReplacers replacers) {
      this.replacers = replacers;

      return this;
    }

    @Override
    public SeedModuleChangesJavaBuildCommandsBuilder startupCommands(SeedStartupCommands startupCommands) {
      this.startupCommands = startupCommands;

      return this;
    }

    @Override
    public SeedModuleChangesPackageJsonBuilder javaBuildCommands(JavaBuildCommands javaDependencies) {
      this.javaBuildCommands = javaDependencies;

      return this;
    }

    @Override
    public SeedModuleChangesGitIgnorePatternsBuilder packageJson(SeedModulePackageJson packageJson) {
      this.packageJson = packageJson;

      return this;
    }

    @Override
    public SeedModuleChangesDockerComposeFileBuilder gitIgnore(SeedModuleGitIgnore gitIgnore) {
      this.gitIgnore = gitIgnore;

      return this;
    }

    @Override
    public SeedModuleChangesPreActionsBuilder dockerComposeFile(SeedModuleDockerComposeFile dockerComposeFile) {
      this.dockerComposeFile = dockerComposeFile;

      return this;
    }

    @Override
    public SeedModuleChangesPostActionsBuilder preActions(SeedModulePreActions preActions) {
      this.preActions = preActions;

      return this;
    }

    @Override
    public SeedModuleChangesSpringFactoriesBuilder postActions(SeedModulePostActions postActions) {
      this.postActions = postActions;

      return this;
    }

    @Override
    public SeedModuleChangesSpringYamlCommentsBuilder springYamlProperties(SpringProperties springProperties) {
      this.springYamlProperties = springProperties;

      return this;
    }

    @Override
    public SeedModuleChangesSpringCommentsBuilder springProperties(SpringProperties springProperties) {
      this.springProperties = springProperties;

      return this;
    }

    @Override
    public SeedModuleChanges springYamlComments(SpringComments springComments) {
      this.springYamlComments = springComments;

      return new SeedModuleChanges(this);
    }

    @Override
    public SeedModuleChanges springComments(SpringComments springComments) {
      this.springComments = springComments;

      return new SeedModuleChanges(this);
    }

    @Override
    public SeedModuleChangesSpringPropertiesBuilder springFactories(SpringFactories springFactories) {
      this.springFactories = springFactories;

      return this;
    }
  }

  public interface SeedModuleChangesContextBuilder {
    SeedModuleChangesProjectFolderBuilder context(SeedModuleContext context);
  }

  public interface SeedModuleChangesProjectFolderBuilder {
    SeedModuleChangesIndentationBuilder projectFolder(SeedProjectFolder projectFolder);
  }

  public interface SeedModuleChangesIndentationBuilder {
    SeedModuleChangesFilesToAddBuilder indentation(Indentation indentation);
  }

  public interface SeedModuleChangesFilesToAddBuilder {
    SeedModuleChangesFilesToMoveBuilder filesToAdd(SeedTemplatedFiles filesToAdd);
  }

  public interface SeedModuleChangesFilesToMoveBuilder {
    SeedModuleChangesFilesToDeleteBuilder filesToMove(SeedFilesToMove filesToMove);
  }

  public interface SeedModuleChangesFilesToDeleteBuilder {
    SeedModuleChangesReplacersBuilder filesToDelete(SeedFilesToDelete filesToDelete);
  }

  public interface SeedModuleChangesReplacersBuilder {
    SeedModuleChangesStartupCommandsBuilder replacers(ContentReplacers replacers);
  }

  public interface SeedModuleChangesStartupCommandsBuilder {
    SeedModuleChangesJavaBuildCommandsBuilder startupCommands(SeedStartupCommands startupCommands);
  }

  public interface SeedModuleChangesJavaBuildCommandsBuilder {
    SeedModuleChangesPackageJsonBuilder javaBuildCommands(JavaBuildCommands javaBuildCommands);
  }

  public interface SeedModuleChangesPackageJsonBuilder {
    SeedModuleChangesGitIgnorePatternsBuilder packageJson(SeedModulePackageJson packageJson);
  }

  public interface SeedModuleChangesGitIgnorePatternsBuilder {
    SeedModuleChangesDockerComposeFileBuilder gitIgnore(SeedModuleGitIgnore gitIgnore);
  }

  public interface SeedModuleChangesDockerComposeFileBuilder {
    SeedModuleChangesPreActionsBuilder dockerComposeFile(SeedModuleDockerComposeFile dockerComposeFile);
  }

  public interface SeedModuleChangesPreActionsBuilder {
    SeedModuleChangesPostActionsBuilder preActions(SeedModulePreActions preActions);
  }

  public interface SeedModuleChangesPostActionsBuilder {
    SeedModuleChangesSpringFactoriesBuilder postActions(SeedModulePostActions postActions);
  }

  public interface SeedModuleChangesSpringFactoriesBuilder {
    SeedModuleChangesSpringPropertiesBuilder springFactories(SpringFactories springFactories);
  }

  public interface SeedModuleChangesSpringPropertiesBuilder {
    SeedModuleChangesSpringYamlCommentsBuilder springYamlProperties(SpringProperties springProperties);
    SeedModuleChangesSpringCommentsBuilder springProperties(SpringProperties springProperties);
  }

  public interface SeedModuleChangesSpringYamlCommentsBuilder {
    SeedModuleChanges springYamlComments(SpringComments springComments);
  }

  public interface SeedModuleChangesSpringCommentsBuilder {
    SeedModuleChanges springComments(SpringComments springComments);
  }
}
