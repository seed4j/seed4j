package com.seed4j.module.domain;

import static com.seed4j.module.domain.replacement.ReplacementCondition.notContainingReplacement;

import com.seed4j.module.domain.Seed4JModuleContext.Seed4JModuleContextBuilder;
import com.seed4j.module.domain.Seed4JModulePreActions.Seed4JModulePreActionsBuilder;
import com.seed4j.module.domain.buildproperties.Seed4JModuleBuildProperties;
import com.seed4j.module.domain.buildproperties.Seed4JModuleBuildProperties.Seed4JModuleBuildPropertiesBuilder;
import com.seed4j.module.domain.file.Seed4JDestination;
import com.seed4j.module.domain.file.Seed4JFilesToDelete;
import com.seed4j.module.domain.file.Seed4JFilesToMove;
import com.seed4j.module.domain.file.Seed4JModuleFile;
import com.seed4j.module.domain.file.Seed4JModuleFiles;
import com.seed4j.module.domain.file.Seed4JModuleFiles.Seed4JModuleFilesBuilder;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.gitignore.Seed4JModuleGitIgnore;
import com.seed4j.module.domain.gitignore.Seed4JModuleGitIgnore.Seed4JModuleGitIgnoreBuilder;
import com.seed4j.module.domain.gradleconfiguration.Seed4JModuleGradleConfigurations;
import com.seed4j.module.domain.gradleconfiguration.Seed4JModuleGradleConfigurations.Seed4JModuleGradleConfigurationBuilder;
import com.seed4j.module.domain.gradleplugin.GradleCommunityPlugin;
import com.seed4j.module.domain.gradleplugin.GradleCommunityPlugin.GradleCommunityPluginIdBuilder;
import com.seed4j.module.domain.gradleplugin.GradleCommunityProfilePlugin;
import com.seed4j.module.domain.gradleplugin.GradleCommunityProfilePlugin.GradleCommunityProfilePluginIdBuilder;
import com.seed4j.module.domain.gradleplugin.GradleCorePlugin;
import com.seed4j.module.domain.gradleplugin.GradleCorePlugin.GradleCorePluginIdBuilder;
import com.seed4j.module.domain.gradleplugin.Seed4JModuleGradlePlugins;
import com.seed4j.module.domain.gradleplugin.Seed4JModuleGradlePlugins.Seed4JModuleGradlePluginBuilder;
import com.seed4j.module.domain.javabuild.ArtifactId;
import com.seed4j.module.domain.javabuild.GroupId;
import com.seed4j.module.domain.javabuild.MavenBuildExtension;
import com.seed4j.module.domain.javabuild.MavenBuildExtension.MavenBuildExtensionGroupIdBuilder;
import com.seed4j.module.domain.javabuild.Seed4JModuleMavenBuildExtensions;
import com.seed4j.module.domain.javabuild.Seed4JModuleMavenBuildExtensions.Seed4JModuleMavenBuildExtensionsBuilder;
import com.seed4j.module.domain.javabuild.VersionSlug;
import com.seed4j.module.domain.javabuildprofile.BuildProfileActivation;
import com.seed4j.module.domain.javabuildprofile.BuildProfileActivation.BuildProfileActivationBuilder;
import com.seed4j.module.domain.javabuildprofile.BuildProfileId;
import com.seed4j.module.domain.javabuildprofile.Seed4JModuleJavaBuildProfiles;
import com.seed4j.module.domain.javabuildprofile.Seed4JModuleJavaBuildProfiles.Seed4JModuleJavaBuildProfilesBuilder;
import com.seed4j.module.domain.javadependency.DependencyId;
import com.seed4j.module.domain.javadependency.JavaDependency;
import com.seed4j.module.domain.javadependency.JavaDependency.JavaDependencyGroupIdBuilder;
import com.seed4j.module.domain.javadependency.Seed4JModuleJavaDependencies;
import com.seed4j.module.domain.javadependency.Seed4JModuleJavaDependencies.Seed4JModuleJavaDependenciesBuilder;
import com.seed4j.module.domain.javaproperties.Comment;
import com.seed4j.module.domain.javaproperties.PropertyKey;
import com.seed4j.module.domain.javaproperties.PropertyValue;
import com.seed4j.module.domain.javaproperties.Seed4JModuleSpringFactories;
import com.seed4j.module.domain.javaproperties.Seed4JModuleSpringFactories.Seed4JModuleSpringFactoriesBuilder;
import com.seed4j.module.domain.javaproperties.Seed4JModuleSpringProperties;
import com.seed4j.module.domain.javaproperties.Seed4JModuleSpringProperties.Seed4JModuleSpringPropertiesBuilder;
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
import com.seed4j.module.domain.mavenplugin.Seed4JModuleMavenPlugins;
import com.seed4j.module.domain.mavenplugin.Seed4JModuleMavenPlugins.Seed4JModuleMavenPluginsBuilder;
import com.seed4j.module.domain.nodejs.NodePackageManager;
import com.seed4j.module.domain.packagejson.PackageName;
import com.seed4j.module.domain.packagejson.ScriptCommand;
import com.seed4j.module.domain.packagejson.ScriptKey;
import com.seed4j.module.domain.packagejson.Seed4JModulePackageJson;
import com.seed4j.module.domain.packagejson.Seed4JModulePackageJson.Seed4JModulePackageJsonBuilder;
import com.seed4j.module.domain.postaction.Seed4JModulePostActions;
import com.seed4j.module.domain.postaction.Seed4JModulePostActions.Seed4JModulePostActionsBuilder;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.module.domain.properties.Seed4JProjectFolder;
import com.seed4j.module.domain.replacement.EndOfFileReplacer;
import com.seed4j.module.domain.replacement.FileStartReplacer;
import com.seed4j.module.domain.replacement.RegexNeedleAfterReplacer;
import com.seed4j.module.domain.replacement.RegexNeedleBeforeReplacer;
import com.seed4j.module.domain.replacement.RegexReplacer;
import com.seed4j.module.domain.replacement.ReplacementCondition;
import com.seed4j.module.domain.replacement.Seed4JModuleMandatoryReplacementsFactory;
import com.seed4j.module.domain.replacement.Seed4JModuleMandatoryReplacementsFactory.Seed4JModuleMandatoryReplacementsFactoryBuilder;
import com.seed4j.module.domain.replacement.Seed4JModuleOptionalReplacementsFactory;
import com.seed4j.module.domain.replacement.Seed4JModuleOptionalReplacementsFactory.Seed4JModuleOptionalReplacementsFactoryBuilder;
import com.seed4j.module.domain.replacement.TextNeedleAfterReplacer;
import com.seed4j.module.domain.replacement.TextNeedleBeforeReplacer;
import com.seed4j.module.domain.replacement.TextReplacer;
import com.seed4j.module.domain.standalonedocker.Seed4JModuleDockerComposeFile;
import com.seed4j.module.domain.standalonedocker.Seed4JModuleDockerComposeFile.Seed4JModuleDockerComposeFileBuilder;
import com.seed4j.module.domain.startupcommand.DockerComposeFile;
import com.seed4j.module.domain.startupcommand.Seed4JModuleStartupCommands;
import com.seed4j.module.domain.startupcommand.Seed4JModuleStartupCommands.Seed4JModuleStartupCommandsBuilder;
import com.seed4j.module.domain.startupcommand.Seed4JStartupCommands;
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
import org.apache.commons.lang3.Strings;

@SuppressWarnings("java:S6539")
public final class Seed4JModule {

  public static final String LINE_BREAK = "\n";

  private final Seed4JModuleProperties properties;
  private final Seed4JModuleFiles files;
  private final Seed4JModuleMandatoryReplacementsFactory mandatoryReplacements;
  private final Seed4JModuleOptionalReplacementsFactory optionalReplacements;
  private final Seed4JModuleStartupCommands startupCommands;
  private final Seed4JModuleContext context;
  private final Seed4JModuleJavaDependencies javaDependencies;
  private final Seed4JModuleBuildProperties javaBuildProperties;
  private final Seed4JModuleJavaBuildProfiles javaBuildProfiles;
  private final Seed4JModuleMavenPlugins mavenPlugins;
  private final Seed4JModuleGradleConfigurations gradleConfigurations;
  private final Seed4JModuleGradlePlugins gradlePlugins;
  private final Seed4JModuleMavenBuildExtensions mavenBuildExtensions;
  private final Seed4JModulePackageJson packageJson;
  private final Seed4JModulePreActions preActions;
  private final Seed4JModulePostActions postActions;
  private final SpringProperties springProperties;
  private final SpringComments springComments;
  private final SpringFactories springFactories;
  private final Seed4JModuleGitIgnore gitIgnore;
  private final Seed4JModuleDockerComposeFile dockerComposeFile;

  private Seed4JModule(Seed4JModuleBuilder builder) {
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

  private Seed4JModule(Seed4JModule source, Seed4JModuleUpgrade upgrade) {
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

  private SpringProperties buildSpringProperties(Seed4JModuleBuilder builder) {
    return new SpringProperties(builder.springProperties.entrySet().stream().flatMap(toSpringProperties()).toList());
  }

  private Function<Entry<PropertiesKey, Seed4JModuleSpringPropertiesBuilder>, Stream<SpringProperty>> toSpringProperties() {
    return inputProperties -> inputProperties.getValue().build().properties().entrySet().stream().map(toSpringProperty(inputProperties));
  }

  private Function<Entry<PropertyKey, PropertyValue>, SpringProperty> toSpringProperty(
    Entry<PropertiesKey, Seed4JModuleSpringPropertiesBuilder> inputProperties
  ) {
    return property ->
      SpringProperty.builder(inputProperties.getKey().type())
        .key(property.getKey())
        .value(property.getValue())
        .profile(inputProperties.getKey().profile())
        .build();
  }

  private SpringComments buildSpringComments(Seed4JModuleBuilder builder) {
    return new SpringComments(builder.springProperties.entrySet().stream().flatMap(toSpringComments()).toList());
  }

  private Function<Entry<PropertiesKey, Seed4JModuleSpringPropertiesBuilder>, Stream<SpringComment>> toSpringComments() {
    return inputProperties -> inputProperties.getValue().build().comments().entrySet().stream().map(toSpringComment(inputProperties));
  }

  private Function<Entry<PropertyKey, Comment>, SpringComment> toSpringComment(
    Entry<PropertiesKey, Seed4JModuleSpringPropertiesBuilder> inputProperties
  ) {
    return propertyComment ->
      SpringComment.builder(inputProperties.getKey().type())
        .key(propertyComment.getKey())
        .comment(propertyComment.getValue())
        .profile(inputProperties.getKey().profile())
        .build();
  }

  private SpringFactories buildSpringFactories(Seed4JModuleBuilder builder) {
    return new SpringFactories(builder.springFactories.entrySet().stream().flatMap(toSpringFactories()).toList());
  }

  private Function<Entry<SpringFactoryType, Seed4JModuleSpringFactoriesBuilder>, Stream<SpringFactory>> toSpringFactories() {
    return inputFactories -> inputFactories.getValue().build().factories().entrySet().stream().map(toSpringFactory(inputFactories));
  }

  private Function<Entry<PropertyKey, PropertyValue>, SpringFactory> toSpringFactory(
    Entry<SpringFactoryType, Seed4JModuleSpringFactoriesBuilder> inputFactories
  ) {
    return property -> SpringFactory.builder(inputFactories.getKey()).key(property.getKey()).value(property.getValue());
  }

  public static Seed4JModuleBuilder moduleBuilder(Seed4JModuleProperties properties) {
    return new Seed4JModuleBuilder(properties);
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

  public static Seed4JSource from(String source) {
    Assert.notBlank("source", source);

    return new Seed4JSource(Path.of("/generator", source));
  }

  public static Seed4JProjectFilePath path(String path) {
    return new Seed4JProjectFilePath(path);
  }

  public static Seed4JDestination to(String destination) {
    return new Seed4JDestination(destination);
  }

  public static Seed4JDestination toSrcMainJava() {
    return Seed4JDestination.SRC_MAIN_JAVA;
  }

  public static Seed4JDestination toSrcMainDocker() {
    return Seed4JDestination.SRC_MAIN_DOCKER;
  }

  public static Seed4JDestination toSrcTestJava() {
    return Seed4JDestination.SRC_TEST_JAVA;
  }

  public static Seed4JDestination toSrcMainResources() {
    return Seed4JDestination.SRC_MAIN_RESOURCES;
  }

  public static Seed4JDestination toSrcTestResources() {
    return Seed4JDestination.SRC_TEST_RESOURCES;
  }

  public static Seed4JFileMatcher filesWithExtension(String extension) {
    return path -> Strings.CI.endsWith(path.get(), "." + extension);
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

  public Seed4JModule withUpgrade(Seed4JModuleUpgrade upgrade) {
    return new Seed4JModule(this, upgrade);
  }

  public Seed4JProjectFolder projectFolder() {
    return properties.projectFolder();
  }

  public Seed4JModuleProperties properties() {
    return properties;
  }

  public Indentation indentation() {
    return properties.indentation();
  }

  public Seed4JModuleFiles files() {
    return files;
  }

  public Seed4JModuleContext context() {
    return context;
  }

  public Collection<Seed4JModuleFile> filesToAdd() {
    return files.filesToAdd();
  }

  public Seed4JFilesToMove filesToMove() {
    return files.filesToMove();
  }

  public Seed4JFilesToDelete filesToDelete() {
    return files.filesToDelete();
  }

  public Seed4JModuleMandatoryReplacementsFactory mandatoryReplacements() {
    return mandatoryReplacements;
  }

  public Seed4JModuleOptionalReplacementsFactory optionalReplacements() {
    return optionalReplacements;
  }

  public Seed4JStartupCommands startupCommands() {
    return startupCommands.commands();
  }

  public Seed4JModuleJavaDependencies javaDependencies() {
    return javaDependencies;
  }

  public Seed4JModuleBuildProperties javaBuildProperties() {
    return javaBuildProperties;
  }

  public Seed4JModuleJavaBuildProfiles javaBuildProfiles() {
    return javaBuildProfiles;
  }

  public Seed4JModuleMavenPlugins mavenPlugins() {
    return mavenPlugins;
  }

  public Seed4JModuleGradleConfigurations gradleConfigurations() {
    return gradleConfigurations;
  }

  public Seed4JModuleGradlePlugins gradlePlugins() {
    return gradlePlugins;
  }

  public Seed4JModuleMavenBuildExtensions mavenBuildExtensions() {
    return mavenBuildExtensions;
  }

  public Seed4JModulePackageJson packageJson() {
    return packageJson;
  }

  public Seed4JModulePreActions preActions() {
    return preActions;
  }

  public Seed4JModulePostActions postActions() {
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

  public Seed4JModuleGitIgnore gitIgnore() {
    return gitIgnore;
  }

  public Seed4JModuleDockerComposeFile dockerComposeFile() {
    return dockerComposeFile;
  }

  public static final class Seed4JModuleBuilder {

    private static final String PROFILE = "profile";

    private final Seed4JModuleProperties properties;
    private final Seed4JModuleContextBuilder context;
    private final Seed4JModuleFilesBuilder files = Seed4JModuleFiles.builder(this);
    private final Seed4JModuleMandatoryReplacementsFactoryBuilder mandatoryReplacements = Seed4JModuleMandatoryReplacementsFactory.builder(
      this
    );
    private final Seed4JModuleOptionalReplacementsFactoryBuilder optionalReplacements = Seed4JModuleOptionalReplacementsFactory.builder(
      this
    );
    private final Seed4JModuleStartupCommandsBuilder startupCommands = Seed4JModuleStartupCommands.builder(this);
    private final Seed4JModuleJavaDependenciesBuilder<Seed4JModuleBuilder> javaDependencies = Seed4JModuleJavaDependencies.builder(this);
    private final Seed4JModuleBuildPropertiesBuilder<Seed4JModuleBuilder> javaBuildProperties = Seed4JModuleBuildProperties.builder(this);
    private final Seed4JModuleJavaBuildProfilesBuilder javaBuildProfiles = Seed4JModuleJavaBuildProfiles.builder(this);
    private final Seed4JModuleMavenPluginsBuilder<Seed4JModuleBuilder> mavenPlugins = Seed4JModuleMavenPlugins.builder(this);
    private final Seed4JModuleGradleConfigurationBuilder gradleConfigurations = Seed4JModuleGradleConfigurations.builder(this);
    private final Seed4JModuleGradlePluginBuilder gradlePlugins = Seed4JModuleGradlePlugins.builder(this);
    private final Seed4JModuleMavenBuildExtensionsBuilder mavenBuildExtensions = Seed4JModuleMavenBuildExtensions.builder(this);
    private final Seed4JModulePackageJsonBuilder packageJson = Seed4JModulePackageJson.builder(this);
    private final Seed4JModulePreActionsBuilder preActions = Seed4JModulePreActions.builder(this);
    private final Seed4JModulePostActionsBuilder postActions = Seed4JModulePostActions.builder(this);
    private final Map<PropertiesKey, Seed4JModuleSpringPropertiesBuilder> springProperties = new HashMap<>();
    private final Map<SpringFactoryType, Seed4JModuleSpringFactoriesBuilder> springFactories = new EnumMap<>(SpringFactoryType.class);
    private final Seed4JModuleGitIgnoreBuilder gitIgnore = Seed4JModuleGitIgnore.builder(this);
    private final Seed4JModuleDockerComposeFileBuilder dockerComposeFile = Seed4JModuleDockerComposeFile.builder(this);

    private Seed4JModuleBuilder(Seed4JModuleProperties properties) {
      Assert.notNull("properties", properties);

      this.properties = properties;
      context = Seed4JModuleContext.builder(this);
    }

    Seed4JModuleProperties properties() {
      return properties;
    }

    public Seed4JModuleBuilder apply(Consumer<Seed4JModuleBuilder> builderCustomizer) {
      Assert.notNull("builderCustomizer", builderCustomizer);
      builderCustomizer.accept(this);

      return this;
    }

    public Seed4JModuleBuilder documentation(DocumentationTitle title, Seed4JSource source) {
      apply(Seed4JModuleShortcuts.documentation(title, source));

      return this;
    }

    public Seed4JModuleBuilder localEnvironment(LocalEnvironment localEnvironment) {
      apply(Seed4JModuleShortcuts.localEnvironment(localEnvironment));

      return this;
    }

    public Seed4JModuleBuilder preCommitActions(StagedFilesFilter stagedFilesFilter, PreCommitCommands preCommitCommands) {
      apply(Seed4JModuleShortcuts.preCommitActions(stagedFilesFilter, preCommitCommands));

      return this;
    }

    public Seed4JModuleStartupCommandsBuilder startupCommands() {
      return startupCommands;
    }

    public Seed4JModuleBuilder prerequisites(String prerequisites) {
      apply(Seed4JModuleShortcuts.prerequisites(prerequisites));

      return this;
    }

    public Seed4JModuleContextBuilder context() {
      return context;
    }

    public Seed4JModuleFilesBuilder files() {
      return files;
    }

    public Seed4JModuleMandatoryReplacementsFactoryBuilder mandatoryReplacements() {
      return mandatoryReplacements;
    }

    public Seed4JModuleBuilder springTestLogger(String name, LogLevel level) {
      apply(Seed4JModuleShortcuts.springTestLogger(name, level));

      return this;
    }

    public Seed4JModuleBuilder springMainLogger(String name, LogLevel level) {
      apply(Seed4JModuleShortcuts.springMainLogger(name, level));

      return this;
    }

    public Seed4JModuleBuilder integrationTestExtension(String extensionClass) {
      apply(Seed4JModuleShortcuts.integrationTestExtension(extensionClass));

      return this;
    }

    public Seed4JModuleOptionalReplacementsFactoryBuilder optionalReplacements() {
      return optionalReplacements;
    }

    public Seed4JModuleJavaDependenciesBuilder<Seed4JModuleBuilder> javaDependencies() {
      return javaDependencies;
    }

    public Seed4JModuleBuildPropertiesBuilder<Seed4JModuleBuilder> javaBuildProperties() {
      return javaBuildProperties;
    }

    public Seed4JModuleJavaBuildProfilesBuilder javaBuildProfiles() {
      return javaBuildProfiles;
    }

    public Seed4JModuleMavenPluginsBuilder<Seed4JModuleBuilder> mavenPlugins() {
      return mavenPlugins;
    }

    public Seed4JModuleGradleConfigurationBuilder gradleConfigurations() {
      return gradleConfigurations;
    }

    public Seed4JModuleGradlePluginBuilder gradlePlugins() {
      return gradlePlugins;
    }

    public Seed4JModuleMavenBuildExtensionsBuilder mavenBuildExtensions() {
      return mavenBuildExtensions;
    }

    /**
     * Configure the {@code package.json} file for the Seed4J module.
     * <p>
     * This method allows you to add scripts, dependencies, and development dependencies
     * to the package.json file. It uses a builder pattern to provide a fluent interface for
     * configuring the {@code package.json} file.
     *
     * @return a {@link Seed4JModulePackageJsonBuilder} to continue configuring the package.json
     */
    public Seed4JModulePackageJsonBuilder packageJson() {
      return packageJson;
    }

    public Seed4JModulePreActionsBuilder preActions() {
      return preActions;
    }

    public Seed4JModulePostActionsBuilder postActions() {
      return postActions;
    }

    public Seed4JModuleSpringPropertiesBuilder springMainProperties() {
      return springMainProperties(SpringProfile.DEFAULT);
    }

    public Seed4JModuleSpringPropertiesBuilder springLocalProperties() {
      return springMainProperties(SpringProfile.LOCAL);
    }

    public Seed4JModuleSpringPropertiesBuilder springMainBootstrapProperties() {
      return springMainBootstrapProperties(SpringProfile.DEFAULT);
    }

    public Seed4JModuleSpringPropertiesBuilder springMainBootstrapProperties(SpringProfile profile) {
      Assert.notNull(PROFILE, profile);

      return springProperties.computeIfAbsent(new PropertiesKey(profile, SpringPropertyType.MAIN_BOOTSTRAP_PROPERTIES), key ->
        Seed4JModuleSpringProperties.builder(this)
      );
    }

    public Seed4JModuleSpringPropertiesBuilder springMainProperties(SpringProfile profile) {
      Assert.notNull(PROFILE, profile);

      return springProperties.computeIfAbsent(new PropertiesKey(profile, SpringPropertyType.MAIN_PROPERTIES), key ->
        Seed4JModuleSpringProperties.builder(this)
      );
    }

    public Seed4JModuleSpringPropertiesBuilder springTestProperties() {
      return springTestProperties(SpringProfile.TEST);
    }

    public Seed4JModuleSpringPropertiesBuilder springTestBootstrapProperties() {
      return springProperties.computeIfAbsent(new PropertiesKey(SpringProfile.DEFAULT, SpringPropertyType.TEST_BOOTSTRAP_PROPERTIES), key ->
        Seed4JModuleSpringProperties.builder(this)
      );
    }

    public Seed4JModuleSpringPropertiesBuilder springTestProperties(SpringProfile profile) {
      Assert.notNull(PROFILE, profile);

      return springProperties.computeIfAbsent(new PropertiesKey(profile, SpringPropertyType.TEST_PROPERTIES), key ->
        Seed4JModuleSpringProperties.builder(this)
      );
    }

    public Seed4JModuleSpringFactoriesBuilder springTestFactories() {
      return springFactories.computeIfAbsent(SpringFactoryType.TEST_FACTORIES, key -> Seed4JModuleSpringFactories.builder(this));
    }

    /**
     * Add new entries to the {@code .gitignore} file.
     */
    public Seed4JModuleGitIgnoreBuilder gitIgnore() {
      return gitIgnore;
    }

    public Seed4JModuleDockerComposeFileBuilder dockerComposeFile() {
      return dockerComposeFile;
    }

    String packagePath() {
      return properties.basePackage().path();
    }

    Indentation indentation() {
      return properties.indentation();
    }

    public Seed4JModule build() {
      return new Seed4JModule(this);
    }
  }

  private record PropertiesKey(SpringProfile profile, SpringPropertyType type) {}
}
