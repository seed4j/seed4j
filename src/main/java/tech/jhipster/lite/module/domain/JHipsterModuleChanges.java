package tech.jhipster.lite.module.domain;

import tech.jhipster.lite.module.domain.file.JHipsterFilesToDelete;
import tech.jhipster.lite.module.domain.file.JHipsterFilesToMove;
import tech.jhipster.lite.module.domain.file.JHipsterTemplatedFiles;
import tech.jhipster.lite.module.domain.gitignore.JHipsterModuleGitIgnore;
import tech.jhipster.lite.module.domain.javabuild.command.JavaBuildCommands;
import tech.jhipster.lite.module.domain.javaproperties.SpringComments;
import tech.jhipster.lite.module.domain.javaproperties.SpringFactories;
import tech.jhipster.lite.module.domain.javaproperties.SpringProperties;
import tech.jhipster.lite.module.domain.packagejson.JHipsterModulePackageJson;
import tech.jhipster.lite.module.domain.postaction.JHipsterModulePostActions;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;
import tech.jhipster.lite.module.domain.replacement.ContentReplacers;
import tech.jhipster.lite.module.domain.startupcommand.JHipsterStartupCommands;
import tech.jhipster.lite.shared.error.domain.Assert;
import tech.jhipster.lite.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;

@SuppressWarnings("java:S6539")
public final class JHipsterModuleChanges {

  private final JHipsterModuleContext context;
  private final JHipsterProjectFolder projectFolder;
  private final Indentation indentation;
  private final JHipsterTemplatedFiles filesToAdd;
  private final JHipsterFilesToMove filesToMove;
  private final JHipsterFilesToDelete filesToDelete;
  private final ContentReplacers replacers;
  private final JHipsterStartupCommands startupCommands;
  private final JHipsterStartupCommands sonarAnalysisCommands;
  private final JavaBuildCommands javaBuildCommands;
  private final JHipsterModulePackageJson packageJson;
  private final JHipsterModulePreActions preActions;
  private final JHipsterModulePostActions postActions;
  private final SpringProperties springProperties;
  private final SpringComments springComments;
  private final SpringProperties springYamlProperties;
  private final SpringComments springYamlComments;
  private final SpringFactories springFactories;
  private final JHipsterModuleGitIgnore gitIgnore;

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
    sonarAnalysisCommands = builder.sonarAnalysisCommands;
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

  public JHipsterModuleContext context() {
    return context;
  }

  public JHipsterProjectFolder projectFolder() {
    return projectFolder;
  }

  public Indentation indentation() {
    return indentation;
  }

  public JHipsterTemplatedFiles filesToAdd() {
    return filesToAdd;
  }

  public JHipsterFilesToMove filesToMove() {
    return filesToMove;
  }

  public JHipsterFilesToDelete filesToDelete() {
    return filesToDelete;
  }

  public ContentReplacers replacers() {
    return replacers;
  }

  public JHipsterStartupCommands startupCommands() {
    return startupCommands;
  }

  public JHipsterStartupCommands sonarAnalysisCommands() {
    return sonarAnalysisCommands;
  }

  public JavaBuildCommands javaBuildCommands() {
    return javaBuildCommands;
  }

  public JHipsterModulePackageJson packageJson() {
    return packageJson;
  }

  public JHipsterModuleGitIgnore gitIgnore() {
    return gitIgnore;
  }

  public JHipsterModulePreActions preActions() {
    return preActions;
  }

  public JHipsterModulePostActions postActions() {
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
      JHipsterModuleChangesSonarAnalysisCommandsBuilder,
      JHipsterModuleChangesJavaBuildCommandsBuilder,
      JHipsterModuleChangesPackageJsonBuilder,
      JHipsterModuleChangesPreActionsBuilder,
      JHipsterModuleChangesPostActionsBuilder,
      JHipsterModuleChangesSpringPropertiesBuilder,
      JHipsterModuleChangesSpringCommentsBuilder,
      JHipsterModuleChangesSpringFactoriesBuilder,
      JHipsterModuleChangesSpringYamlCommentsBuilder,
      JHipsterModuleChangesGitIgnorePatternsBuilder {

    private JHipsterModuleContext context;
    private JHipsterProjectFolder projectFolder;
    private JHipsterTemplatedFiles filesToAdd;
    private JHipsterFilesToMove filesToMove;
    private JHipsterFilesToDelete filesToDelete;
    private ContentReplacers replacers;
    private JHipsterStartupCommands startupCommands;
    private JHipsterStartupCommands sonarAnalysisCommands;
    private JavaBuildCommands javaBuildCommands;
    private JHipsterModulePackageJson packageJson;
    private Indentation indentation;
    private JHipsterModulePreActions preActions;
    private JHipsterModulePostActions postActions;
    private SpringProperties springProperties = SpringProperties.EMPTY;
    private SpringComments springComments = SpringComments.EMPTY;
    private SpringProperties springYamlProperties = SpringProperties.EMPTY;
    private SpringComments springYamlComments = SpringComments.EMPTY;
    private SpringFactories springFactories;
    private JHipsterModuleGitIgnore gitIgnore;

    @Override
    public JHipsterModuleChangesProjectFolderBuilder context(JHipsterModuleContext context) {
      this.context = context;

      return this;
    }

    @Override
    public JHipsterModuleChangesIndentationBuilder projectFolder(JHipsterProjectFolder projectFolder) {
      this.projectFolder = projectFolder;

      return this;
    }

    @Override
    public JHipsterModuleChangesFilesToAddBuilder indentation(Indentation indentation) {
      this.indentation = indentation;

      return this;
    }

    @Override
    public JHipsterModuleChangesFilesToMoveBuilder filesToAdd(JHipsterTemplatedFiles filesToAdd) {
      this.filesToAdd = filesToAdd;

      return this;
    }

    @Override
    public JHipsterModuleChangesFilesToDeleteBuilder filesToMove(JHipsterFilesToMove filesToMove) {
      this.filesToMove = filesToMove;

      return this;
    }

    @Override
    public JHipsterModuleChangesReplacersBuilder filesToDelete(JHipsterFilesToDelete filesToDelete) {
      this.filesToDelete = filesToDelete;

      return this;
    }

    @Override
    public JHipsterModuleChangesStartupCommandsBuilder replacers(ContentReplacers replacers) {
      this.replacers = replacers;

      return this;
    }

    @Override
    public JHipsterModuleChangesSonarAnalysisCommandsBuilder startupCommands(JHipsterStartupCommands startupCommands) {
      this.startupCommands = startupCommands;

      return this;
    }

    @Override
    public JHipsterModuleChangesJavaBuildCommandsBuilder sonarAnalysisCommands(JHipsterStartupCommands sonarAnalysisCommands) {
      this.sonarAnalysisCommands = sonarAnalysisCommands;

      return this;
    }

    @Override
    public JHipsterModuleChangesPackageJsonBuilder javaBuildCommands(JavaBuildCommands javaDependencies) {
      this.javaBuildCommands = javaDependencies;

      return this;
    }

    @Override
    public JHipsterModuleChangesGitIgnorePatternsBuilder packageJson(JHipsterModulePackageJson packageJson) {
      this.packageJson = packageJson;

      return this;
    }

    @Override
    public JHipsterModuleChangesPreActionsBuilder gitIgnore(JHipsterModuleGitIgnore gitIgnore) {
      this.gitIgnore = gitIgnore;

      return this;
    }

    @Override
    public JHipsterModuleChangesPostActionsBuilder preActions(JHipsterModulePreActions preActions) {
      this.preActions = preActions;

      return this;
    }

    @Override
    public JHipsterModuleChangesSpringFactoriesBuilder postActions(JHipsterModulePostActions postActions) {
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
    JHipsterModuleChangesProjectFolderBuilder context(JHipsterModuleContext context);
  }

  public interface JHipsterModuleChangesProjectFolderBuilder {
    JHipsterModuleChangesIndentationBuilder projectFolder(JHipsterProjectFolder projectFolder);
  }

  public interface JHipsterModuleChangesIndentationBuilder {
    JHipsterModuleChangesFilesToAddBuilder indentation(Indentation indentation);
  }

  public interface JHipsterModuleChangesFilesToAddBuilder {
    JHipsterModuleChangesFilesToMoveBuilder filesToAdd(JHipsterTemplatedFiles filesToAdd);
  }

  public interface JHipsterModuleChangesFilesToMoveBuilder {
    JHipsterModuleChangesFilesToDeleteBuilder filesToMove(JHipsterFilesToMove filesToMove);
  }

  public interface JHipsterModuleChangesFilesToDeleteBuilder {
    JHipsterModuleChangesReplacersBuilder filesToDelete(JHipsterFilesToDelete filesToDelete);
  }

  public interface JHipsterModuleChangesReplacersBuilder {
    JHipsterModuleChangesStartupCommandsBuilder replacers(ContentReplacers replacers);
  }

  public interface JHipsterModuleChangesStartupCommandsBuilder {
    JHipsterModuleChangesSonarAnalysisCommandsBuilder startupCommands(JHipsterStartupCommands startupCommands);
  }

  public interface JHipsterModuleChangesSonarAnalysisCommandsBuilder {
    JHipsterModuleChangesJavaBuildCommandsBuilder sonarAnalysisCommands(JHipsterStartupCommands sonarAnalysisCommands);
  }

  public interface JHipsterModuleChangesJavaBuildCommandsBuilder {
    JHipsterModuleChangesPackageJsonBuilder javaBuildCommands(JavaBuildCommands javaBuildCommands);
  }

  public interface JHipsterModuleChangesPackageJsonBuilder {
    JHipsterModuleChangesGitIgnorePatternsBuilder packageJson(JHipsterModulePackageJson packageJson);
  }

  public interface JHipsterModuleChangesGitIgnorePatternsBuilder {
    JHipsterModuleChangesPreActionsBuilder gitIgnore(JHipsterModuleGitIgnore gitIgnore);
  }

  public interface JHipsterModuleChangesPreActionsBuilder {
    JHipsterModuleChangesPostActionsBuilder preActions(JHipsterModulePreActions preActions);
  }

  public interface JHipsterModuleChangesPostActionsBuilder {
    JHipsterModuleChangesSpringFactoriesBuilder postActions(JHipsterModulePostActions postActions);
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
