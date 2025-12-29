package com.seed4j.module.domain;

import com.seed4j.module.domain.file.Seed4JFilesToDelete;
import com.seed4j.module.domain.file.Seed4JFilesToMove;
import com.seed4j.module.domain.file.Seed4JTemplatedFiles;
import com.seed4j.module.domain.gitignore.Seed4JModuleGitIgnore;
import com.seed4j.module.domain.javabuild.command.JavaBuildCommands;
import com.seed4j.module.domain.javaproperties.SpringComments;
import com.seed4j.module.domain.javaproperties.SpringFactories;
import com.seed4j.module.domain.javaproperties.SpringProperties;
import com.seed4j.module.domain.packagejson.Seed4JModulePackageJson;
import com.seed4j.module.domain.postaction.Seed4JModulePostActions;
import com.seed4j.module.domain.properties.Seed4JProjectFolder;
import com.seed4j.module.domain.replacement.ContentReplacers;
import com.seed4j.module.domain.standalonedocker.Seed4JModuleDockerComposeFile;
import com.seed4j.module.domain.startupcommand.Seed4JStartupCommands;
import com.seed4j.shared.error.domain.Assert;
import com.seed4j.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;
import org.jspecify.annotations.Nullable;

@SuppressWarnings("java:S6539")
public final class Seed4JModuleChanges {

  private final Seed4JModuleContext context;
  private final Seed4JProjectFolder projectFolder;
  private final Indentation indentation;
  private final Seed4JTemplatedFiles filesToAdd;
  private final Seed4JFilesToMove filesToMove;
  private final Seed4JFilesToDelete filesToDelete;
  private final ContentReplacers replacers;
  private final Seed4JStartupCommands startupCommands;
  private final JavaBuildCommands javaBuildCommands;
  private final Seed4JModulePackageJson packageJson;
  private final Seed4JModulePreActions preActions;
  private final Seed4JModulePostActions postActions;
  private final SpringProperties springProperties;
  private final SpringComments springComments;
  private final SpringProperties springYamlProperties;
  private final SpringComments springYamlComments;
  private final SpringFactories springFactories;
  private final Seed4JModuleGitIgnore gitIgnore;
  private final Seed4JModuleDockerComposeFile dockerComposeFile;

  private Seed4JModuleChanges(Seed4JModuleChangesBuilder builder) {
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
    Assert.notNull("startupCommands", builder.startupCommands);
    Assert.notNull("packageJson", builder.packageJson);
    Assert.notNull("springProperties", builder.springProperties);
    Assert.notNull("springComments", builder.springComments);
    Assert.notNull("springYamlProperties", builder.springYamlProperties);
    Assert.notNull("springYamlComments", builder.springYamlComments);
    Assert.notNull("gitIgnore", builder.gitIgnore);
    Assert.notNull("dockerComposeFile", builder.dockerComposeFile);

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

  public static Seed4JModuleChangesContextBuilder builder() {
    return new Seed4JModuleChangesBuilder();
  }

  public Seed4JModuleContext context() {
    return context;
  }

  public Seed4JProjectFolder projectFolder() {
    return projectFolder;
  }

  public Indentation indentation() {
    return indentation;
  }

  public Seed4JTemplatedFiles filesToAdd() {
    return filesToAdd;
  }

  public Seed4JFilesToMove filesToMove() {
    return filesToMove;
  }

  public Seed4JFilesToDelete filesToDelete() {
    return filesToDelete;
  }

  public ContentReplacers replacers() {
    return replacers;
  }

  public Seed4JStartupCommands startupCommands() {
    return startupCommands;
  }

  public JavaBuildCommands javaBuildCommands() {
    return javaBuildCommands;
  }

  public Seed4JModulePackageJson packageJson() {
    return packageJson;
  }

  public Seed4JModuleGitIgnore gitIgnore() {
    return gitIgnore;
  }

  public Seed4JModulePreActions preActions() {
    return preActions;
  }

  public Seed4JModulePostActions postActions() {
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

  public Seed4JModuleDockerComposeFile dockerComposeFile() {
    return dockerComposeFile;
  }

  private static final class Seed4JModuleChangesBuilder
    implements
      Seed4JModuleChangesContextBuilder,
      Seed4JModuleChangesProjectFolderBuilder,
      Seed4JModuleChangesIndentationBuilder,
      Seed4JModuleChangesFilesToAddBuilder,
      Seed4JModuleChangesFilesToMoveBuilder,
      Seed4JModuleChangesFilesToDeleteBuilder,
      Seed4JModuleChangesReplacersBuilder,
      Seed4JModuleChangesStartupCommandsBuilder,
      Seed4JModuleChangesJavaBuildCommandsBuilder,
      Seed4JModuleChangesPackageJsonBuilder,
      Seed4JModuleChangesPreActionsBuilder,
      Seed4JModuleChangesPostActionsBuilder,
      Seed4JModuleChangesSpringPropertiesBuilder,
      Seed4JModuleChangesSpringCommentsBuilder,
      Seed4JModuleChangesSpringFactoriesBuilder,
      Seed4JModuleChangesSpringYamlCommentsBuilder,
      Seed4JModuleChangesGitIgnorePatternsBuilder,
      Seed4JModuleChangesDockerComposeFileBuilder {

    private @Nullable Seed4JModuleContext context;
    private @Nullable Seed4JProjectFolder projectFolder;
    private @Nullable Seed4JTemplatedFiles filesToAdd;
    private @Nullable Seed4JFilesToMove filesToMove;
    private @Nullable Seed4JFilesToDelete filesToDelete;
    private @Nullable ContentReplacers replacers;
    private @Nullable Seed4JStartupCommands startupCommands;
    private @Nullable JavaBuildCommands javaBuildCommands;
    private @Nullable Seed4JModulePackageJson packageJson;
    private @Nullable Indentation indentation;
    private @Nullable Seed4JModulePreActions preActions;
    private @Nullable Seed4JModulePostActions postActions;
    private @Nullable SpringProperties springProperties = SpringProperties.EMPTY;
    private @Nullable SpringComments springComments = SpringComments.EMPTY;
    private @Nullable SpringProperties springYamlProperties = SpringProperties.EMPTY;
    private @Nullable SpringComments springYamlComments = SpringComments.EMPTY;
    private @Nullable SpringFactories springFactories;
    private @Nullable Seed4JModuleGitIgnore gitIgnore;
    private @Nullable Seed4JModuleDockerComposeFile dockerComposeFile;

    @Override
    public Seed4JModuleChangesProjectFolderBuilder context(Seed4JModuleContext context) {
      this.context = context;

      return this;
    }

    @Override
    public Seed4JModuleChangesIndentationBuilder projectFolder(Seed4JProjectFolder projectFolder) {
      this.projectFolder = projectFolder;

      return this;
    }

    @Override
    public Seed4JModuleChangesFilesToAddBuilder indentation(Indentation indentation) {
      this.indentation = indentation;

      return this;
    }

    @Override
    public Seed4JModuleChangesFilesToMoveBuilder filesToAdd(Seed4JTemplatedFiles filesToAdd) {
      this.filesToAdd = filesToAdd;

      return this;
    }

    @Override
    public Seed4JModuleChangesFilesToDeleteBuilder filesToMove(Seed4JFilesToMove filesToMove) {
      this.filesToMove = filesToMove;

      return this;
    }

    @Override
    public Seed4JModuleChangesReplacersBuilder filesToDelete(Seed4JFilesToDelete filesToDelete) {
      this.filesToDelete = filesToDelete;

      return this;
    }

    @Override
    public Seed4JModuleChangesStartupCommandsBuilder replacers(ContentReplacers replacers) {
      this.replacers = replacers;

      return this;
    }

    @Override
    public Seed4JModuleChangesJavaBuildCommandsBuilder startupCommands(Seed4JStartupCommands startupCommands) {
      this.startupCommands = startupCommands;

      return this;
    }

    @Override
    public Seed4JModuleChangesPackageJsonBuilder javaBuildCommands(JavaBuildCommands javaDependencies) {
      this.javaBuildCommands = javaDependencies;

      return this;
    }

    @Override
    public Seed4JModuleChangesGitIgnorePatternsBuilder packageJson(Seed4JModulePackageJson packageJson) {
      this.packageJson = packageJson;

      return this;
    }

    @Override
    public Seed4JModuleChangesDockerComposeFileBuilder gitIgnore(Seed4JModuleGitIgnore gitIgnore) {
      this.gitIgnore = gitIgnore;

      return this;
    }

    @Override
    public Seed4JModuleChangesPreActionsBuilder dockerComposeFile(Seed4JModuleDockerComposeFile dockerComposeFile) {
      this.dockerComposeFile = dockerComposeFile;

      return this;
    }

    @Override
    public Seed4JModuleChangesPostActionsBuilder preActions(Seed4JModulePreActions preActions) {
      this.preActions = preActions;

      return this;
    }

    @Override
    public Seed4JModuleChangesSpringFactoriesBuilder postActions(Seed4JModulePostActions postActions) {
      this.postActions = postActions;

      return this;
    }

    @Override
    public Seed4JModuleChangesSpringYamlCommentsBuilder springYamlProperties(SpringProperties springProperties) {
      this.springYamlProperties = springProperties;

      return this;
    }

    @Override
    public Seed4JModuleChangesSpringCommentsBuilder springProperties(SpringProperties springProperties) {
      this.springProperties = springProperties;

      return this;
    }

    @Override
    public Seed4JModuleChanges springYamlComments(SpringComments springComments) {
      this.springYamlComments = springComments;

      return new Seed4JModuleChanges(this);
    }

    @Override
    public Seed4JModuleChanges springComments(SpringComments springComments) {
      this.springComments = springComments;

      return new Seed4JModuleChanges(this);
    }

    @Override
    public Seed4JModuleChangesSpringPropertiesBuilder springFactories(SpringFactories springFactories) {
      this.springFactories = springFactories;

      return this;
    }
  }

  public interface Seed4JModuleChangesContextBuilder {
    Seed4JModuleChangesProjectFolderBuilder context(Seed4JModuleContext context);
  }

  public interface Seed4JModuleChangesProjectFolderBuilder {
    Seed4JModuleChangesIndentationBuilder projectFolder(Seed4JProjectFolder projectFolder);
  }

  public interface Seed4JModuleChangesIndentationBuilder {
    Seed4JModuleChangesFilesToAddBuilder indentation(Indentation indentation);
  }

  public interface Seed4JModuleChangesFilesToAddBuilder {
    Seed4JModuleChangesFilesToMoveBuilder filesToAdd(Seed4JTemplatedFiles filesToAdd);
  }

  public interface Seed4JModuleChangesFilesToMoveBuilder {
    Seed4JModuleChangesFilesToDeleteBuilder filesToMove(Seed4JFilesToMove filesToMove);
  }

  public interface Seed4JModuleChangesFilesToDeleteBuilder {
    Seed4JModuleChangesReplacersBuilder filesToDelete(Seed4JFilesToDelete filesToDelete);
  }

  public interface Seed4JModuleChangesReplacersBuilder {
    Seed4JModuleChangesStartupCommandsBuilder replacers(ContentReplacers replacers);
  }

  public interface Seed4JModuleChangesStartupCommandsBuilder {
    Seed4JModuleChangesJavaBuildCommandsBuilder startupCommands(Seed4JStartupCommands startupCommands);
  }

  public interface Seed4JModuleChangesJavaBuildCommandsBuilder {
    Seed4JModuleChangesPackageJsonBuilder javaBuildCommands(JavaBuildCommands javaBuildCommands);
  }

  public interface Seed4JModuleChangesPackageJsonBuilder {
    Seed4JModuleChangesGitIgnorePatternsBuilder packageJson(Seed4JModulePackageJson packageJson);
  }

  public interface Seed4JModuleChangesGitIgnorePatternsBuilder {
    Seed4JModuleChangesDockerComposeFileBuilder gitIgnore(Seed4JModuleGitIgnore gitIgnore);
  }

  public interface Seed4JModuleChangesDockerComposeFileBuilder {
    Seed4JModuleChangesPreActionsBuilder dockerComposeFile(Seed4JModuleDockerComposeFile dockerComposeFile);
  }

  public interface Seed4JModuleChangesPreActionsBuilder {
    Seed4JModuleChangesPostActionsBuilder preActions(Seed4JModulePreActions preActions);
  }

  public interface Seed4JModuleChangesPostActionsBuilder {
    Seed4JModuleChangesSpringFactoriesBuilder postActions(Seed4JModulePostActions postActions);
  }

  public interface Seed4JModuleChangesSpringFactoriesBuilder {
    Seed4JModuleChangesSpringPropertiesBuilder springFactories(SpringFactories springFactories);
  }

  public interface Seed4JModuleChangesSpringPropertiesBuilder {
    Seed4JModuleChangesSpringYamlCommentsBuilder springYamlProperties(SpringProperties springProperties);
    Seed4JModuleChangesSpringCommentsBuilder springProperties(SpringProperties springProperties);
  }

  public interface Seed4JModuleChangesSpringYamlCommentsBuilder {
    Seed4JModuleChanges springYamlComments(SpringComments springComments);
  }

  public interface Seed4JModuleChangesSpringCommentsBuilder {
    Seed4JModuleChanges springComments(SpringComments springComments);
  }
}
