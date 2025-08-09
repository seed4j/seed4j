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
public final class JHipsterModuleChanges {

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
  private final JHipsterModulePreActions preActions;
  private final SeedModulePostActions postActions;
  private final SpringProperties springProperties;
  private final SpringComments springComments;
  private final SpringProperties springYamlProperties;
  private final SpringComments springYamlComments;
  private final SpringFactories springFactories;
  private final SeedModuleGitIgnore gitIgnore;
  private final SeedModuleDockerComposeFile dockerComposeFile;

  private JHipsterModuleChanges(JHipsterModuleChangesBuilder builder) {
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

  private void assertMandatoryFields(JHipsterModuleChangesBuilder builder) {
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

  public static JHipsterModuleChangesContextBuilder builder() {
    return new JHipsterModuleChangesBuilder();
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

  public JHipsterModulePreActions preActions() {
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

  private static final class JHipsterModuleChangesBuilder
    implements
      JHipsterModuleChangesContextBuilder,
      JHipsterModuleChangesProjectFolderBuilder,
      JHipsterModuleChangesIndentationBuilder,
      JHipsterModuleChangesFilesToAddBuilder,
      JHipsterModuleChangesFilesToMoveBuilder,
      JHipsterModuleChangesFilesToDeleteBuilder,
      JHipsterModuleChangesReplacersBuilder,
      JHipsterModuleChangesStartupCommandsBuilder,
      JHipsterModuleChangesJavaBuildCommandsBuilder,
      JHipsterModuleChangesPackageJsonBuilder,
      JHipsterModuleChangesPreActionsBuilder,
      JHipsterModuleChangesPostActionsBuilder,
      JHipsterModuleChangesSpringPropertiesBuilder,
      JHipsterModuleChangesSpringCommentsBuilder,
      JHipsterModuleChangesSpringFactoriesBuilder,
      JHipsterModuleChangesSpringYamlCommentsBuilder,
      JHipsterModuleChangesGitIgnorePatternsBuilder,
      JHipsterModuleChangesDockerComposeFileBuilder {

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
    private JHipsterModulePreActions preActions;
    private SeedModulePostActions postActions;
    private SpringProperties springProperties = SpringProperties.EMPTY;
    private SpringComments springComments = SpringComments.EMPTY;
    private SpringProperties springYamlProperties = SpringProperties.EMPTY;
    private SpringComments springYamlComments = SpringComments.EMPTY;
    private SpringFactories springFactories;
    private SeedModuleGitIgnore gitIgnore;
    private SeedModuleDockerComposeFile dockerComposeFile;

    @Override
    public JHipsterModuleChangesProjectFolderBuilder context(SeedModuleContext context) {
      this.context = context;

      return this;
    }

    @Override
    public JHipsterModuleChangesIndentationBuilder projectFolder(SeedProjectFolder projectFolder) {
      this.projectFolder = projectFolder;

      return this;
    }

    @Override
    public JHipsterModuleChangesFilesToAddBuilder indentation(Indentation indentation) {
      this.indentation = indentation;

      return this;
    }

    @Override
    public JHipsterModuleChangesFilesToMoveBuilder filesToAdd(SeedTemplatedFiles filesToAdd) {
      this.filesToAdd = filesToAdd;

      return this;
    }

    @Override
    public JHipsterModuleChangesFilesToDeleteBuilder filesToMove(SeedFilesToMove filesToMove) {
      this.filesToMove = filesToMove;

      return this;
    }

    @Override
    public JHipsterModuleChangesReplacersBuilder filesToDelete(SeedFilesToDelete filesToDelete) {
      this.filesToDelete = filesToDelete;

      return this;
    }

    @Override
    public JHipsterModuleChangesStartupCommandsBuilder replacers(ContentReplacers replacers) {
      this.replacers = replacers;

      return this;
    }

    @Override
    public JHipsterModuleChangesJavaBuildCommandsBuilder startupCommands(SeedStartupCommands startupCommands) {
      this.startupCommands = startupCommands;

      return this;
    }

    @Override
    public JHipsterModuleChangesPackageJsonBuilder javaBuildCommands(JavaBuildCommands javaDependencies) {
      this.javaBuildCommands = javaDependencies;

      return this;
    }

    @Override
    public JHipsterModuleChangesGitIgnorePatternsBuilder packageJson(SeedModulePackageJson packageJson) {
      this.packageJson = packageJson;

      return this;
    }

    @Override
    public JHipsterModuleChangesDockerComposeFileBuilder gitIgnore(SeedModuleGitIgnore gitIgnore) {
      this.gitIgnore = gitIgnore;

      return this;
    }

    @Override
    public JHipsterModuleChangesPreActionsBuilder dockerComposeFile(SeedModuleDockerComposeFile dockerComposeFile) {
      this.dockerComposeFile = dockerComposeFile;

      return this;
    }

    @Override
    public JHipsterModuleChangesPostActionsBuilder preActions(JHipsterModulePreActions preActions) {
      this.preActions = preActions;

      return this;
    }

    @Override
    public JHipsterModuleChangesSpringFactoriesBuilder postActions(SeedModulePostActions postActions) {
      this.postActions = postActions;

      return this;
    }

    @Override
    public JHipsterModuleChangesSpringYamlCommentsBuilder springYamlProperties(SpringProperties springProperties) {
      this.springYamlProperties = springProperties;

      return this;
    }

    @Override
    public JHipsterModuleChangesSpringCommentsBuilder springProperties(SpringProperties springProperties) {
      this.springProperties = springProperties;

      return this;
    }

    @Override
    public JHipsterModuleChanges springYamlComments(SpringComments springComments) {
      this.springYamlComments = springComments;

      return new JHipsterModuleChanges(this);
    }

    @Override
    public JHipsterModuleChanges springComments(SpringComments springComments) {
      this.springComments = springComments;

      return new JHipsterModuleChanges(this);
    }

    @Override
    public JHipsterModuleChangesSpringPropertiesBuilder springFactories(SpringFactories springFactories) {
      this.springFactories = springFactories;

      return this;
    }
  }

  public interface JHipsterModuleChangesContextBuilder {
    JHipsterModuleChangesProjectFolderBuilder context(SeedModuleContext context);
  }

  public interface JHipsterModuleChangesProjectFolderBuilder {
    JHipsterModuleChangesIndentationBuilder projectFolder(SeedProjectFolder projectFolder);
  }

  public interface JHipsterModuleChangesIndentationBuilder {
    JHipsterModuleChangesFilesToAddBuilder indentation(Indentation indentation);
  }

  public interface JHipsterModuleChangesFilesToAddBuilder {
    JHipsterModuleChangesFilesToMoveBuilder filesToAdd(SeedTemplatedFiles filesToAdd);
  }

  public interface JHipsterModuleChangesFilesToMoveBuilder {
    JHipsterModuleChangesFilesToDeleteBuilder filesToMove(SeedFilesToMove filesToMove);
  }

  public interface JHipsterModuleChangesFilesToDeleteBuilder {
    JHipsterModuleChangesReplacersBuilder filesToDelete(SeedFilesToDelete filesToDelete);
  }

  public interface JHipsterModuleChangesReplacersBuilder {
    JHipsterModuleChangesStartupCommandsBuilder replacers(ContentReplacers replacers);
  }

  public interface JHipsterModuleChangesStartupCommandsBuilder {
    JHipsterModuleChangesJavaBuildCommandsBuilder startupCommands(SeedStartupCommands startupCommands);
  }

  public interface JHipsterModuleChangesJavaBuildCommandsBuilder {
    JHipsterModuleChangesPackageJsonBuilder javaBuildCommands(JavaBuildCommands javaBuildCommands);
  }

  public interface JHipsterModuleChangesPackageJsonBuilder {
    JHipsterModuleChangesGitIgnorePatternsBuilder packageJson(SeedModulePackageJson packageJson);
  }

  public interface JHipsterModuleChangesGitIgnorePatternsBuilder {
    JHipsterModuleChangesDockerComposeFileBuilder gitIgnore(SeedModuleGitIgnore gitIgnore);
  }

  public interface JHipsterModuleChangesDockerComposeFileBuilder {
    JHipsterModuleChangesPreActionsBuilder dockerComposeFile(SeedModuleDockerComposeFile dockerComposeFile);
  }

  public interface JHipsterModuleChangesPreActionsBuilder {
    JHipsterModuleChangesPostActionsBuilder preActions(JHipsterModulePreActions preActions);
  }

  public interface JHipsterModuleChangesPostActionsBuilder {
    JHipsterModuleChangesSpringFactoriesBuilder postActions(SeedModulePostActions postActions);
  }

  public interface JHipsterModuleChangesSpringFactoriesBuilder {
    JHipsterModuleChangesSpringPropertiesBuilder springFactories(SpringFactories springFactories);
  }

  public interface JHipsterModuleChangesSpringPropertiesBuilder {
    JHipsterModuleChangesSpringYamlCommentsBuilder springYamlProperties(SpringProperties springProperties);
    JHipsterModuleChangesSpringCommentsBuilder springProperties(SpringProperties springProperties);
  }

  public interface JHipsterModuleChangesSpringYamlCommentsBuilder {
    JHipsterModuleChanges springYamlComments(SpringComments springComments);
  }

  public interface JHipsterModuleChangesSpringCommentsBuilder {
    JHipsterModuleChanges springComments(SpringComments springComments);
  }
}
