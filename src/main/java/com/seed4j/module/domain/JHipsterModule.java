package com.seed4j.module.domain;

import static com.seed4j.module.domain.replacement.ReplacementCondition.notContainingReplacement;

import com.seed4j.module.domain.JHipsterModulePreActions.JHipsterModulePreActionsBuilder;
import com.seed4j.module.domain.SeedModuleContext.JHipsterModuleContextBuilder;
import com.seed4j.module.domain.buildproperties.SeedModuleBuildProperties;
import com.seed4j.module.domain.buildproperties.SeedModuleBuildProperties.JHipsterModuleBuildPropertiesBuilder;
import com.seed4j.module.domain.file.SeedDestination;
import com.seed4j.module.domain.file.SeedFilesToDelete;
import com.seed4j.module.domain.file.SeedFilesToMove;
import com.seed4j.module.domain.file.SeedModuleFile;
import com.seed4j.module.domain.file.SeedModuleFiles;
import com.seed4j.module.domain.file.SeedModuleFiles.JHipsterModuleFilesBuilder;
import com.seed4j.module.domain.file.SeedSource;
import com.seed4j.module.domain.gitignore.SeedModuleGitIgnore;
import com.seed4j.module.domain.gitignore.SeedModuleGitIgnore.JHipsterModuleGitIgnoreBuilder;
import com.seed4j.module.domain.gradleconfiguration.SeedModuleGradleConfigurations;
import com.seed4j.module.domain.gradleconfiguration.SeedModuleGradleConfigurations.JHipsterModuleGradleConfigurationBuilder;
import com.seed4j.module.domain.gradleplugin.GradleCommunityPlugin;
import com.seed4j.module.domain.gradleplugin.GradleCommunityPlugin.GradleCommunityPluginIdBuilder;
import com.seed4j.module.domain.gradleplugin.GradleCommunityProfilePlugin;
import com.seed4j.module.domain.gradleplugin.GradleCommunityProfilePlugin.GradleCommunityProfilePluginIdBuilder;
import com.seed4j.module.domain.gradleplugin.GradleCorePlugin;
import com.seed4j.module.domain.gradleplugin.GradleCorePlugin.GradleCorePluginIdBuilder;
import com.seed4j.module.domain.gradleplugin.SeedModuleGradlePlugins;
import com.seed4j.module.domain.gradleplugin.SeedModuleGradlePlugins.JHipsterModuleGradlePluginBuilder;
import com.seed4j.module.domain.javabuild.ArtifactId;
import com.seed4j.module.domain.javabuild.GroupId;
import com.seed4j.module.domain.javabuild.MavenBuildExtension;
import com.seed4j.module.domain.javabuild.MavenBuildExtension.MavenBuildExtensionGroupIdBuilder;
import com.seed4j.module.domain.javabuild.SeedModuleMavenBuildExtensions;
import com.seed4j.module.domain.javabuild.SeedModuleMavenBuildExtensions.JHipsterModuleMavenBuildExtensionsBuilder;
import com.seed4j.module.domain.javabuild.VersionSlug;
import com.seed4j.module.domain.javabuildprofile.BuildProfileActivation;
import com.seed4j.module.domain.javabuildprofile.BuildProfileActivation.BuildProfileActivationBuilder;
import com.seed4j.module.domain.javabuildprofile.BuildProfileId;
import com.seed4j.module.domain.javabuildprofile.SeedModuleJavaBuildProfiles;
import com.seed4j.module.domain.javabuildprofile.SeedModuleJavaBuildProfiles.SeedModuleJavaBuildProfilesBuilder;
import com.seed4j.module.domain.javadependency.DependencyId;
import com.seed4j.module.domain.javadependency.JavaDependency;
import com.seed4j.module.domain.javadependency.JavaDependency.JavaDependencyGroupIdBuilder;
import com.seed4j.module.domain.javadependency.SeedModuleJavaDependencies;
import com.seed4j.module.domain.javadependency.SeedModuleJavaDependencies.JHipsterModuleJavaDependenciesBuilder;
import com.seed4j.module.domain.javaproperties.Comment;
import com.seed4j.module.domain.javaproperties.PropertyKey;
import com.seed4j.module.domain.javaproperties.PropertyValue;
import com.seed4j.module.domain.javaproperties.SeedModuleSpringFactories;
import com.seed4j.module.domain.javaproperties.SeedModuleSpringFactories.JHipsterModuleSpringFactoriesBuilder;
import com.seed4j.module.domain.javaproperties.SeedModuleSpringProperties;
import com.seed4j.module.domain.javaproperties.SeedModuleSpringProperties.JHipsterModuleSpringPropertiesBuilder;
import com.seed4j.module.domain.javaproperties.SpringComment;
import com.seed4j.module.domain.javaproperties.SpringComments;
import com.seed4j.module.domain.javaproperties.SpringFactories;
import com.seed4j.module.domain.javaproperties.SpringFactory;
import com.seed4j.module.domain.javaproperties.SpringFactoryType;
import com.seed4j.module.domain.javaproperties.SpringProfile;
import com.seed4j.module.domain.javaproperties.SpringProperties;
import com.seed4j.module.domain.javaproperties.SpringProperty;
import com.seed4j.module.domain.javaproperties.SpringPropertyType;
import com.seed4j.module.domain.mavenplugin.MavenPlugin;
import com.seed4j.module.domain.mavenplugin.MavenPlugin.MavenPluginGroupIdBuilder;
import com.seed4j.module.domain.mavenplugin.MavenPluginExecution;
import com.seed4j.module.domain.mavenplugin.MavenPluginExecution.MavenPluginExecutionGoalsBuilder;
import com.seed4j.module.domain.mavenplugin.SeedModuleMavenPlugins;
import com.seed4j.module.domain.mavenplugin.SeedModuleMavenPlugins.SeedModuleMavenPluginsBuilder;
import com.seed4j.module.domain.nodejs.NodePackageManager;
import com.seed4j.module.domain.packagejson.PackageName;
import com.seed4j.module.domain.packagejson.ScriptCommand;
import com.seed4j.module.domain.packagejson.ScriptKey;
import com.seed4j.module.domain.packagejson.SeedModulePackageJson;
import com.seed4j.module.domain.packagejson.SeedModulePackageJson.JHipsterModulePackageJsonBuilder;
import com.seed4j.module.domain.postaction.SeedModulePostActions;
import com.seed4j.module.domain.postaction.SeedModulePostActions.SeedModulePostActionsBuilder;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.module.domain.properties.SeedProjectFolder;
import com.seed4j.module.domain.replacement.EndOfFileReplacer;
import com.seed4j.module.domain.replacement.FileStartReplacer;
import com.seed4j.module.domain.replacement.RegexNeedleAfterReplacer;
import com.seed4j.module.domain.replacement.RegexNeedleBeforeReplacer;
import com.seed4j.module.domain.replacement.RegexReplacer;
import com.seed4j.module.domain.replacement.ReplacementCondition;
import com.seed4j.module.domain.replacement.SeedModuleMandatoryReplacementsFactory;
import com.seed4j.module.domain.replacement.SeedModuleMandatoryReplacementsFactory.SeedModuleMandatoryReplacementsFactoryBuilder;
import com.seed4j.module.domain.replacement.SeedModuleOptionalReplacementsFactory;
import com.seed4j.module.domain.replacement.SeedModuleOptionalReplacementsFactory.JHipsterModuleOptionalReplacementsFactoryBuilder;
import com.seed4j.module.domain.replacement.TextNeedleAfterReplacer;
import com.seed4j.module.domain.replacement.TextNeedleBeforeReplacer;
import com.seed4j.module.domain.replacement.TextReplacer;
import com.seed4j.module.domain.standalonedocker.JHipsterModuleDockerComposeFile;
import com.seed4j.module.domain.standalonedocker.JHipsterModuleDockerComposeFile.JHipsterModuleDockerComposeFileBuilder;
import com.seed4j.module.domain.startupcommand.DockerComposeFile;
import com.seed4j.module.domain.startupcommand.JHipsterModuleStartupCommands;
import com.seed4j.module.domain.startupcommand.JHipsterModuleStartupCommands.JHipsterModuleStartupCommandsBuilder;
import com.seed4j.module.domain.startupcommand.JHipsterStartupCommands;
import com.seed4j.shared.error.domain.Assert;
import java.nio.file.Path;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;

@SuppressWarnings("java:S6539")
public final class JHipsterModule {

  public static final String LINE_BREAK = "\n";

  private final SeedModuleProperties properties;
  private final SeedModuleFiles files;
  private final SeedModuleMandatoryReplacementsFactory mandatoryReplacements;
  private final SeedModuleOptionalReplacementsFactory optionalReplacements;
  private final JHipsterModuleStartupCommands startupCommands;
  private final SeedModuleContext context;
  private final SeedModuleJavaDependencies javaDependencies;
  private final SeedModuleBuildProperties javaBuildProperties;
  private final SeedModuleJavaBuildProfiles javaBuildProfiles;
  private final SeedModuleMavenPlugins mavenPlugins;
  private final SeedModuleGradleConfigurations gradleConfigurations;
  private final SeedModuleGradlePlugins gradlePlugins;
  private final SeedModuleMavenBuildExtensions mavenBuildExtensions;
  private final SeedModulePackageJson packageJson;
  private final JHipsterModulePreActions preActions;
  private final SeedModulePostActions postActions;
  private final SpringProperties springProperties;
  private final SpringComments springComments;
  private final SpringFactories springFactories;
  private final SeedModuleGitIgnore gitIgnore;
  private final JHipsterModuleDockerComposeFile dockerComposeFile;

  private JHipsterModule(SeedModuleBuilder builder) {
    properties = builder.properties;

    files = builder.files.build();
    mandatoryReplacements = builder.mandatoryReplacements.build();
    optionalReplacements = builder.optionalReplacements.build();
    startupCommands = builder.startupCommands.build();
    context = builder.context.build();
    javaDependencies = builder.javaDependencies.build();
    javaBuildProperties = builder.javaBuildProperties.build();
    javaBuildProfiles = builder.javaBuildProfiles.build();
    mavenPlugins = builder.mavenPlugins.build();
    gradleConfigurations = builder.gradleConfigurations.build();
    gradlePlugins = builder.gradlePlugins.build();
    mavenBuildExtensions = builder.mavenBuildExtensions.build();
    packageJson = builder.packageJson.build();
    preActions = builder.preActions.build();
    postActions = builder.postActions.build();
    springProperties = buildSpringProperties(builder);
    springComments = buildSpringComments(builder);
    springFactories = buildSpringFactories(builder);
    gitIgnore = builder.gitIgnore.build();
    dockerComposeFile = builder.dockerComposeFile.build();
  }

  private JHipsterModule(JHipsterModule source, JHipsterModuleUpgrade upgrade) {
    Assert.notNull("upgrade", upgrade);

    properties = source.properties;
    files = source.files.forUpgrade(upgrade);
    mandatoryReplacements = source.mandatoryReplacements;
    optionalReplacements = source.optionalReplacements.add(upgrade.replacements());
    startupCommands = source.startupCommands;
    context = source.context;
    javaDependencies = source.javaDependencies;
    javaBuildProperties = source.javaBuildProperties;
    javaBuildProfiles = source.javaBuildProfiles;
    mavenPlugins = source.mavenPlugins;
    gradleConfigurations = source.gradleConfigurations;
    gradlePlugins = source.gradlePlugins;
    mavenBuildExtensions = source.mavenBuildExtensions;
    packageJson = source.packageJson;
    preActions = source.preActions;
    postActions = source.postActions;
    springProperties = source.springProperties;
    springComments = source.springComments;
    springFactories = source.springFactories;
    gitIgnore = source.gitIgnore;
    dockerComposeFile = source.dockerComposeFile;
  }

  private SpringProperties buildSpringProperties(SeedModuleBuilder builder) {
    return new SpringProperties(builder.springProperties.entrySet().stream().flatMap(toSpringProperties()).toList());
  }

  private Function<Entry<PropertiesKey, JHipsterModuleSpringPropertiesBuilder>, Stream<SpringProperty>> toSpringProperties() {
    return inputProperties -> inputProperties.getValue().build().properties().entrySet().stream().map(toSpringProperty(inputProperties));
  }

  private Function<Entry<PropertyKey, PropertyValue>, SpringProperty> toSpringProperty(
    Entry<PropertiesKey, JHipsterModuleSpringPropertiesBuilder> inputProperties
  ) {
    return property ->
      SpringProperty.builder(inputProperties.getKey().type())
        .key(property.getKey())
        .value(property.getValue())
        .profile(inputProperties.getKey().profile())
        .build();
  }

  private SpringComments buildSpringComments(SeedModuleBuilder builder) {
    return new SpringComments(builder.springProperties.entrySet().stream().flatMap(toSpringComments()).toList());
  }

  private Function<Entry<PropertiesKey, JHipsterModuleSpringPropertiesBuilder>, Stream<SpringComment>> toSpringComments() {
    return inputProperties -> inputProperties.getValue().build().comments().entrySet().stream().map(toSpringComment(inputProperties));
  }

  private Function<Entry<PropertyKey, Comment>, SpringComment> toSpringComment(
    Entry<PropertiesKey, JHipsterModuleSpringPropertiesBuilder> inputProperties
  ) {
    return propertyComment ->
      SpringComment.builder(inputProperties.getKey().type())
        .key(propertyComment.getKey())
        .comment(propertyComment.getValue())
        .profile(inputProperties.getKey().profile())
        .build();
  }

  private SpringFactories buildSpringFactories(SeedModuleBuilder builder) {
    return new SpringFactories(builder.springFactories.entrySet().stream().flatMap(toSpringFactories()).toList());
  }

  private Function<Entry<SpringFactoryType, JHipsterModuleSpringFactoriesBuilder>, Stream<SpringFactory>> toSpringFactories() {
    return inputFactories -> inputFactories.getValue().build().factories().entrySet().stream().map(toSpringFactory(inputFactories));
  }

  private Function<Entry<PropertyKey, PropertyValue>, SpringFactory> toSpringFactory(
    Entry<SpringFactoryType, JHipsterModuleSpringFactoriesBuilder> inputFactories
  ) {
    return property -> SpringFactory.builder(inputFactories.getKey()).key(property.getKey()).value(property.getValue());
  }

  public static SeedModuleBuilder moduleBuilder(SeedModuleProperties properties) {
    return new SeedModuleBuilder(properties);
  }

  public static JavaDependencyGroupIdBuilder javaDependency() {
    return JavaDependency.builder();
  }

  public static MavenBuildExtensionGroupIdBuilder mavenBuildExtension() {
    return MavenBuildExtension.builder();
  }

  public static DependencyId dependencyId(String groupId, String artifactId) {
    return DependencyId.of(groupId(groupId), artifactId(artifactId));
  }

  public static MavenPluginGroupIdBuilder mavenPlugin() {
    return MavenPlugin.builder();
  }

  public static SeedSource from(String source) {
    Assert.notBlank("source", source);

    return new SeedSource(Path.of("/generator", source));
  }

  public static SeedProjectFilePath path(String path) {
    return new SeedProjectFilePath(path);
  }

  public static SeedDestination to(String destination) {
    return new SeedDestination(destination);
  }

  public static SeedDestination toSrcMainJava() {
    return SeedDestination.SRC_MAIN_JAVA;
  }

  public static SeedDestination toSrcMainDocker() {
    return SeedDestination.SRC_MAIN_DOCKER;
  }

  public static SeedDestination toSrcTestJava() {
    return SeedDestination.SRC_TEST_JAVA;
  }

  public static SeedDestination toSrcMainResources() {
    return SeedDestination.SRC_MAIN_RESOURCES;
  }

  public static SeedDestination toSrcTestResources() {
    return SeedDestination.SRC_TEST_RESOURCES;
  }

  public static SeedFileMatcher filesWithExtension(String extension) {
    return path -> StringUtils.endsWithIgnoreCase(path.get(), "." + extension);
  }

  public static GroupId groupId(String groupId) {
    return new GroupId(groupId);
  }

  public static ArtifactId artifactId(String artifactId) {
    return new ArtifactId(artifactId);
  }

  public static VersionSlug versionSlug(String versionSlug) {
    return new VersionSlug(versionSlug);
  }

  public static TextReplacer text(String text) {
    return new TextReplacer(notContainingReplacement(), text);
  }

  public static RegexReplacer regex(String regex) {
    return regex(notContainingReplacement(), regex);
  }

  public static RegexReplacer regex(ReplacementCondition condition, String regex) {
    return new RegexReplacer(condition, regex);
  }

  public static RegexReplacer regex(ReplacementCondition condition, Pattern pattern) {
    return new RegexReplacer(condition, pattern);
  }

  public static FileStartReplacer fileStart() {
    return new FileStartReplacer(notContainingReplacement());
  }

  public static TextNeedleBeforeReplacer lineBeforeText(String needle) {
    return new TextNeedleBeforeReplacer(notContainingReplacement(), needle);
  }

  public static TextNeedleAfterReplacer lineAfterText(String needle) {
    return new TextNeedleAfterReplacer(notContainingReplacement(), needle);
  }

  public static RegexNeedleBeforeReplacer lineBeforeRegex(String regex) {
    return new RegexNeedleBeforeReplacer(notContainingReplacement(), Pattern.compile(regex, Pattern.MULTILINE));
  }

  public static RegexNeedleAfterReplacer lineAfterRegex(String regex) {
    return new RegexNeedleAfterReplacer(notContainingReplacement(), Pattern.compile(regex, Pattern.MULTILINE));
  }

  public static EndOfFileReplacer append() {
    return new EndOfFileReplacer(ReplacementCondition.always());
  }

  public static BuildProfileId buildProfileId(String id) {
    return new BuildProfileId(id);
  }

  public static com.seed4j.module.domain.buildproperties.PropertyKey buildPropertyKey(String key) {
    return new com.seed4j.module.domain.buildproperties.PropertyKey(key);
  }

  public static com.seed4j.module.domain.buildproperties.PropertyValue buildPropertyValue(String value) {
    return new com.seed4j.module.domain.buildproperties.PropertyValue(value);
  }

  public static BuildProfileActivationBuilder buildProfileActivation() {
    return BuildProfileActivation.builder();
  }

  public static MavenPluginExecutionGoalsBuilder pluginExecution() {
    return MavenPluginExecution.builder();
  }

  public static PropertyKey propertyKey(String key) {
    return new PropertyKey(key);
  }

  public static PropertyValue propertyValue(String... values) {
    return PropertyValue.of(values);
  }

  public static PropertyValue propertyValue(Boolean... values) {
    return PropertyValue.of(values);
  }

  public static PropertyValue propertyValue(Number... values) {
    return PropertyValue.of(values);
  }

  public static SpringProfile springProfile(String profile) {
    return new SpringProfile(profile);
  }

  public static DockerComposeFile dockerComposeFile(String file) {
    return new DockerComposeFile(file);
  }

  public static Comment comment(String value) {
    return new Comment(value);
  }

  public static DocumentationTitle documentationTitle(String title) {
    return new DocumentationTitle(title);
  }

  public static LocalEnvironment localEnvironment(String localEnvironment) {
    return new LocalEnvironment(localEnvironment);
  }

  public static ScriptKey scriptKey(String key) {
    return new ScriptKey(key);
  }

  public static ScriptCommand scriptCommand(String command) {
    return new ScriptCommand(command);
  }

  public static ScriptCommand runScriptCommandWith(NodePackageManager nodePackageManager, String otherScript) {
    return scriptCommand("%s run %s".formatted(nodePackageManager.command(), otherScript));
  }

  public static PackageName packageName(String packageName) {
    return new PackageName(packageName);
  }

  public static StagedFilesFilter stagedFilesFilter(String filesFilter) {
    return new StagedFilesFilter(filesFilter);
  }

  public static PreCommitCommands preCommitCommands(String... commands) {
    return PreCommitCommands.of(commands);
  }

  public static GradleCorePluginIdBuilder gradleCorePlugin() {
    return GradleCorePlugin.builder();
  }

  public static GradleCommunityPluginIdBuilder gradleCommunityPlugin() {
    return GradleCommunityPlugin.builder();
  }

  public static GradleCommunityProfilePluginIdBuilder gradleProfilePlugin() {
    return GradleCommunityProfilePlugin.builder();
  }

  public JHipsterModule withUpgrade(JHipsterModuleUpgrade upgrade) {
    return new JHipsterModule(this, upgrade);
  }

  public SeedProjectFolder projectFolder() {
    return properties.projectFolder();
  }

  public SeedModuleProperties properties() {
    return properties;
  }

  public Indentation indentation() {
    return properties.indentation();
  }

  public SeedModuleFiles files() {
    return files;
  }

  public SeedModuleContext context() {
    return context;
  }

  public Collection<SeedModuleFile> filesToAdd() {
    return files.filesToAdd();
  }

  public SeedFilesToMove filesToMove() {
    return files.filesToMove();
  }

  public SeedFilesToDelete filesToDelete() {
    return files.filesToDelete();
  }

  public SeedModuleMandatoryReplacementsFactory mandatoryReplacements() {
    return mandatoryReplacements;
  }

  public SeedModuleOptionalReplacementsFactory optionalReplacements() {
    return optionalReplacements;
  }

  public JHipsterStartupCommands startupCommands() {
    return startupCommands.commands();
  }

  public SeedModuleJavaDependencies javaDependencies() {
    return javaDependencies;
  }

  public SeedModuleBuildProperties javaBuildProperties() {
    return javaBuildProperties;
  }

  public SeedModuleJavaBuildProfiles javaBuildProfiles() {
    return javaBuildProfiles;
  }

  public SeedModuleMavenPlugins mavenPlugins() {
    return mavenPlugins;
  }

  public SeedModuleGradleConfigurations gradleConfigurations() {
    return gradleConfigurations;
  }

  public SeedModuleGradlePlugins gradlePlugins() {
    return gradlePlugins;
  }

  public SeedModuleMavenBuildExtensions mavenBuildExtensions() {
    return mavenBuildExtensions;
  }

  public SeedModulePackageJson packageJson() {
    return packageJson;
  }

  public JHipsterModulePreActions preActions() {
    return preActions;
  }

  public SeedModulePostActions postActions() {
    return postActions;
  }

  public SpringComments springComments() {
    return springComments;
  }

  public SpringProperties springProperties() {
    return springProperties;
  }

  public SpringFactories springFactories() {
    return springFactories;
  }

  public SeedModuleGitIgnore gitIgnore() {
    return gitIgnore;
  }

  public JHipsterModuleDockerComposeFile dockerComposeFile() {
    return dockerComposeFile;
  }

  public static final class SeedModuleBuilder {

    private static final String PROFILE = "profile";

    private final SeedModuleProperties properties;
    private final JHipsterModuleContextBuilder context;
    private final JHipsterModuleFilesBuilder files = SeedModuleFiles.builder(this);
    private final SeedModuleMandatoryReplacementsFactoryBuilder mandatoryReplacements = SeedModuleMandatoryReplacementsFactory.builder(
      this
    );
    private final JHipsterModuleOptionalReplacementsFactoryBuilder optionalReplacements = SeedModuleOptionalReplacementsFactory.builder(
      this
    );
    private final JHipsterModuleStartupCommandsBuilder startupCommands = JHipsterModuleStartupCommands.builder(this);
    private final JHipsterModuleJavaDependenciesBuilder<SeedModuleBuilder> javaDependencies = SeedModuleJavaDependencies.builder(this);
    private final JHipsterModuleBuildPropertiesBuilder<SeedModuleBuilder> javaBuildProperties = SeedModuleBuildProperties.builder(this);
    private final SeedModuleJavaBuildProfilesBuilder javaBuildProfiles = SeedModuleJavaBuildProfiles.builder(this);
    private final SeedModuleMavenPluginsBuilder<SeedModuleBuilder> mavenPlugins = SeedModuleMavenPlugins.builder(this);
    private final JHipsterModuleGradleConfigurationBuilder gradleConfigurations = SeedModuleGradleConfigurations.builder(this);
    private final JHipsterModuleGradlePluginBuilder gradlePlugins = SeedModuleGradlePlugins.builder(this);
    private final JHipsterModuleMavenBuildExtensionsBuilder mavenBuildExtensions = SeedModuleMavenBuildExtensions.builder(this);
    private final JHipsterModulePackageJsonBuilder packageJson = SeedModulePackageJson.builder(this);
    private final JHipsterModulePreActionsBuilder preActions = JHipsterModulePreActions.builder(this);
    private final SeedModulePostActionsBuilder postActions = SeedModulePostActions.builder(this);
    private final Map<PropertiesKey, JHipsterModuleSpringPropertiesBuilder> springProperties = new HashMap<>();
    private final Map<SpringFactoryType, JHipsterModuleSpringFactoriesBuilder> springFactories = new EnumMap<>(SpringFactoryType.class);
    private final JHipsterModuleGitIgnoreBuilder gitIgnore = SeedModuleGitIgnore.builder(this);
    private final JHipsterModuleDockerComposeFileBuilder dockerComposeFile = JHipsterModuleDockerComposeFile.builder(this);

    private SeedModuleBuilder(SeedModuleProperties properties) {
      Assert.notNull("properties", properties);

      this.properties = properties;
      context = SeedModuleContext.builder(this);
    }

    SeedModuleProperties properties() {
      return properties;
    }

    public SeedModuleBuilder apply(Consumer<SeedModuleBuilder> builderCustomizer) {
      Assert.notNull("builderCustomizer", builderCustomizer);
      builderCustomizer.accept(this);

      return this;
    }

    public SeedModuleBuilder documentation(DocumentationTitle title, SeedSource source) {
      apply(JHipsterModuleShortcuts.documentation(title, source));

      return this;
    }

    public SeedModuleBuilder localEnvironment(LocalEnvironment localEnvironment) {
      apply(JHipsterModuleShortcuts.localEnvironment(localEnvironment));

      return this;
    }

    public SeedModuleBuilder preCommitActions(StagedFilesFilter stagedFilesFilter, PreCommitCommands preCommitCommands) {
      apply(JHipsterModuleShortcuts.preCommitActions(stagedFilesFilter, preCommitCommands));

      return this;
    }

    public JHipsterModuleStartupCommandsBuilder startupCommands() {
      return startupCommands;
    }

    public SeedModuleBuilder prerequisites(String prerequisites) {
      apply(JHipsterModuleShortcuts.prerequisites(prerequisites));

      return this;
    }

    public JHipsterModuleContextBuilder context() {
      return context;
    }

    public JHipsterModuleFilesBuilder files() {
      return files;
    }

    public SeedModuleMandatoryReplacementsFactoryBuilder mandatoryReplacements() {
      return mandatoryReplacements;
    }

    public SeedModuleBuilder springTestLogger(String name, LogLevel level) {
      apply(JHipsterModuleShortcuts.springTestLogger(name, level));

      return this;
    }

    public SeedModuleBuilder springMainLogger(String name, LogLevel level) {
      apply(JHipsterModuleShortcuts.springMainLogger(name, level));

      return this;
    }

    public SeedModuleBuilder integrationTestExtension(String extensionClass) {
      apply(JHipsterModuleShortcuts.integrationTestExtension(extensionClass));

      return this;
    }

    public JHipsterModuleOptionalReplacementsFactoryBuilder optionalReplacements() {
      return optionalReplacements;
    }

    public JHipsterModuleJavaDependenciesBuilder<SeedModuleBuilder> javaDependencies() {
      return javaDependencies;
    }

    public JHipsterModuleBuildPropertiesBuilder<SeedModuleBuilder> javaBuildProperties() {
      return javaBuildProperties;
    }

    public SeedModuleJavaBuildProfilesBuilder javaBuildProfiles() {
      return javaBuildProfiles;
    }

    public SeedModuleMavenPluginsBuilder<SeedModuleBuilder> mavenPlugins() {
      return mavenPlugins;
    }

    public JHipsterModuleGradleConfigurationBuilder gradleConfigurations() {
      return gradleConfigurations;
    }

    public JHipsterModuleGradlePluginBuilder gradlePlugins() {
      return gradlePlugins;
    }

    public JHipsterModuleMavenBuildExtensionsBuilder mavenBuildExtensions() {
      return mavenBuildExtensions;
    }

    /**
     * Configure the {@code package.json} file for the JHipster module.
     * <p>
     * This method allows you to add scripts, dependencies, and development dependencies
     * to the package.json file. It uses a builder pattern to provide a fluent interface for
     * configuring the {@code package.json} file.
     *
     * @return a {@link JHipsterModulePackageJsonBuilder} to continue configuring the package.json
     */
    public JHipsterModulePackageJsonBuilder packageJson() {
      return packageJson;
    }

    public JHipsterModulePreActionsBuilder preActions() {
      return preActions;
    }

    public SeedModulePostActionsBuilder postActions() {
      return postActions;
    }

    public JHipsterModuleSpringPropertiesBuilder springMainProperties() {
      return springMainProperties(SpringProfile.DEFAULT);
    }

    public JHipsterModuleSpringPropertiesBuilder springLocalProperties() {
      return springMainProperties(SpringProfile.LOCAL);
    }

    public JHipsterModuleSpringPropertiesBuilder springMainBootstrapProperties() {
      return springMainBootstrapProperties(SpringProfile.DEFAULT);
    }

    public JHipsterModuleSpringPropertiesBuilder springMainBootstrapProperties(SpringProfile profile) {
      Assert.notNull(PROFILE, profile);

      return springProperties.computeIfAbsent(new PropertiesKey(profile, SpringPropertyType.MAIN_BOOTSTRAP_PROPERTIES), key ->
        SeedModuleSpringProperties.builder(this)
      );
    }

    public JHipsterModuleSpringPropertiesBuilder springMainProperties(SpringProfile profile) {
      Assert.notNull(PROFILE, profile);

      return springProperties.computeIfAbsent(new PropertiesKey(profile, SpringPropertyType.MAIN_PROPERTIES), key ->
        SeedModuleSpringProperties.builder(this)
      );
    }

    public JHipsterModuleSpringPropertiesBuilder springTestProperties() {
      return springTestProperties(SpringProfile.TEST);
    }

    public JHipsterModuleSpringPropertiesBuilder springTestBootstrapProperties() {
      return springProperties.computeIfAbsent(new PropertiesKey(SpringProfile.DEFAULT, SpringPropertyType.TEST_BOOTSTRAP_PROPERTIES), key ->
        SeedModuleSpringProperties.builder(this)
      );
    }

    public JHipsterModuleSpringPropertiesBuilder springTestProperties(SpringProfile profile) {
      Assert.notNull(PROFILE, profile);

      return springProperties.computeIfAbsent(new PropertiesKey(profile, SpringPropertyType.TEST_PROPERTIES), key ->
        SeedModuleSpringProperties.builder(this)
      );
    }

    public JHipsterModuleSpringFactoriesBuilder springTestFactories() {
      return springFactories.computeIfAbsent(SpringFactoryType.TEST_FACTORIES, key -> SeedModuleSpringFactories.builder(this));
    }

    /**
     * Add new entries to the {@code .gitignore} file.
     */
    public JHipsterModuleGitIgnoreBuilder gitIgnore() {
      return gitIgnore;
    }

    public JHipsterModuleDockerComposeFileBuilder dockerComposeFile() {
      return dockerComposeFile;
    }

    String packagePath() {
      return properties.basePackage().path();
    }

    Indentation indentation() {
      return properties.indentation();
    }

    public JHipsterModule build() {
      return new JHipsterModule(this);
    }
  }

  private record PropertiesKey(SpringProfile profile, SpringPropertyType type) {}
}
