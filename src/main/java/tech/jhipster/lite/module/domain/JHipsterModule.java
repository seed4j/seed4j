package tech.jhipster.lite.module.domain;

import static tech.jhipster.lite.module.domain.replacement.ReplacementCondition.*;

import java.nio.file.Paths;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;
import tech.jhipster.lite.module.domain.JHipsterModuleContext.JHipsterModuleContextBuilder;
import tech.jhipster.lite.module.domain.JHipsterModulePreActions.JHipsterModulePreActionsBuilder;
import tech.jhipster.lite.module.domain.buildproperties.JHipsterModuleBuildProperties;
import tech.jhipster.lite.module.domain.buildproperties.JHipsterModuleBuildProperties.JHipsterModuleBuildPropertiesBuilder;
import tech.jhipster.lite.module.domain.file.*;
import tech.jhipster.lite.module.domain.file.JHipsterModuleFiles.JHipsterModuleFilesBuilder;
import tech.jhipster.lite.module.domain.gitignore.JHipsterModuleGitIgnore;
import tech.jhipster.lite.module.domain.gitignore.JHipsterModuleGitIgnore.JHipsterModuleGitIgnoreBuilder;
import tech.jhipster.lite.module.domain.gradleconfiguration.JHipsterModuleGradleConfigurations;
import tech.jhipster.lite.module.domain.gradleconfiguration.JHipsterModuleGradleConfigurations.JHipsterModuleGradleConfigurationBuilder;
import tech.jhipster.lite.module.domain.gradleplugin.GradleCommunityPlugin;
import tech.jhipster.lite.module.domain.gradleplugin.GradleCommunityPlugin.GradleCommunityPluginIdBuilder;
import tech.jhipster.lite.module.domain.gradleplugin.GradleCommunityProfilePlugin;
import tech.jhipster.lite.module.domain.gradleplugin.GradleCommunityProfilePlugin.GradleCommunityProfilePluginIdBuilder;
import tech.jhipster.lite.module.domain.gradleplugin.GradleCorePlugin;
import tech.jhipster.lite.module.domain.gradleplugin.GradleCorePlugin.GradleCorePluginIdBuilder;
import tech.jhipster.lite.module.domain.gradleplugin.JHipsterModuleGradlePlugins;
import tech.jhipster.lite.module.domain.gradleplugin.JHipsterModuleGradlePlugins.JHipsterModuleGradlePluginBuilder;
import tech.jhipster.lite.module.domain.javabuild.*;
import tech.jhipster.lite.module.domain.javabuild.JHipsterModuleMavenBuildExtensions.JHipsterModuleMavenBuildExtensionsBuilder;
import tech.jhipster.lite.module.domain.javabuild.MavenBuildExtension.MavenBuildExtensionGroupIdBuilder;
import tech.jhipster.lite.module.domain.javabuildprofile.BuildProfileActivation;
import tech.jhipster.lite.module.domain.javabuildprofile.BuildProfileActivation.BuildProfileActivationBuilder;
import tech.jhipster.lite.module.domain.javabuildprofile.BuildProfileId;
import tech.jhipster.lite.module.domain.javabuildprofile.JHipsterModuleJavaBuildProfiles;
import tech.jhipster.lite.module.domain.javabuildprofile.JHipsterModuleJavaBuildProfiles.JHipsterModuleJavaBuildProfilesBuilder;
import tech.jhipster.lite.module.domain.javadependency.DependencyId;
import tech.jhipster.lite.module.domain.javadependency.JHipsterModuleJavaDependencies;
import tech.jhipster.lite.module.domain.javadependency.JHipsterModuleJavaDependencies.JHipsterModuleJavaDependenciesBuilder;
import tech.jhipster.lite.module.domain.javadependency.JavaDependency;
import tech.jhipster.lite.module.domain.javadependency.JavaDependency.JavaDependencyGroupIdBuilder;
import tech.jhipster.lite.module.domain.javaproperties.*;
import tech.jhipster.lite.module.domain.javaproperties.JHipsterModuleSpringFactories.JHipsterModuleSpringFactoriesBuilder;
import tech.jhipster.lite.module.domain.javaproperties.JHipsterModuleSpringProperties.JHipsterModuleSpringPropertiesBuilder;
import tech.jhipster.lite.module.domain.mavenplugin.JHipsterModuleMavenPlugins;
import tech.jhipster.lite.module.domain.mavenplugin.JHipsterModuleMavenPlugins.JHipsterModuleMavenPluginsBuilder;
import tech.jhipster.lite.module.domain.mavenplugin.MavenPlugin;
import tech.jhipster.lite.module.domain.mavenplugin.MavenPlugin.MavenPluginGroupIdBuilder;
import tech.jhipster.lite.module.domain.mavenplugin.MavenPluginExecution;
import tech.jhipster.lite.module.domain.mavenplugin.MavenPluginExecution.MavenPluginExecutionGoalsBuilder;
import tech.jhipster.lite.module.domain.packagejson.JHipsterModulePackageJson;
import tech.jhipster.lite.module.domain.packagejson.JHipsterModulePackageJson.JHipsterModulePackageJsonBuilder;
import tech.jhipster.lite.module.domain.packagejson.PackageName;
import tech.jhipster.lite.module.domain.packagejson.ScriptCommand;
import tech.jhipster.lite.module.domain.packagejson.ScriptKey;
import tech.jhipster.lite.module.domain.postaction.JHipsterModulePostActions;
import tech.jhipster.lite.module.domain.postaction.JHipsterModulePostActions.JHipsterModulePostActionsBuilder;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;
import tech.jhipster.lite.module.domain.replacement.*;
import tech.jhipster.lite.module.domain.replacement.JHipsterModuleMandatoryReplacementsFactory.JHipsterModuleMandatoryReplacementsFactoryBuilder;
import tech.jhipster.lite.module.domain.replacement.JHipsterModuleOptionalReplacementsFactory.JHipsterModuleOptionalReplacementsFactoryBuilder;
import tech.jhipster.lite.module.domain.startupcommand.JHipsterModuleStartupCommands;
import tech.jhipster.lite.module.domain.startupcommand.JHipsterModuleStartupCommands.JHipsterModuleStartupCommandsBuilder;
import tech.jhipster.lite.module.domain.startupcommand.JHipsterStartupCommands;
import tech.jhipster.lite.shared.error.domain.Assert;

@SuppressWarnings("java:S6539")
public final class JHipsterModule {

  public static final String LINE_BREAK = "\n";

  private final JHipsterModuleProperties properties;
  private final JHipsterModuleFiles files;
  private final JHipsterModuleMandatoryReplacementsFactory mandatoryReplacements;
  private final JHipsterModuleOptionalReplacementsFactory optionalReplacements;
  private final JHipsterModuleStartupCommands startupCommands;
  private final JHipsterModuleStartupCommands sonarAnalysisCommands;
  private final JHipsterModuleContext context;
  private final JHipsterModuleJavaDependencies javaDependencies;
  private final JHipsterModuleBuildProperties javaBuildProperties;
  private final JHipsterModuleJavaBuildProfiles javaBuildProfiles;
  private final JHipsterModuleMavenPlugins mavenPlugins;
  private final JHipsterModuleGradleConfigurations gradleConfigurations;
  private final JHipsterModuleGradlePlugins gradlePlugins;
  private final JHipsterModuleMavenBuildExtensions mavenBuildExtensions;
  private final JHipsterModulePackageJson packageJson;
  private final JHipsterModulePreActions preActions;
  private final JHipsterModulePostActions postActions;
  private final SpringProperties springProperties;
  private final SpringComments springComments;
  private final SpringFactories springFactories;
  private final JHipsterModuleGitIgnore gitIgnore;

  private JHipsterModule(JHipsterModuleBuilder builder) {
    properties = builder.properties;

    files = builder.files.build();
    mandatoryReplacements = builder.mandatoryReplacements.build();
    optionalReplacements = builder.optionalReplacements.build();
    startupCommands = builder.startupCommands.build();
    sonarAnalysisCommands = builder.sonarAnalysisCommands.build();
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
  }

  private JHipsterModule(JHipsterModule source, JHipsterModuleUpgrade upgrade) {
    Assert.notNull("upgrade", upgrade);

    properties = source.properties;
    files = source.files.forUpgrade(upgrade);
    mandatoryReplacements = source.mandatoryReplacements;
    optionalReplacements = source.optionalReplacements.add(upgrade.replacements());
    startupCommands = source.startupCommands;
    sonarAnalysisCommands = source.sonarAnalysisCommands;
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
  }

  private SpringProperties buildSpringProperties(JHipsterModuleBuilder builder) {
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

  private SpringComments buildSpringComments(JHipsterModuleBuilder builder) {
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

  private SpringFactories buildSpringFactories(JHipsterModuleBuilder builder) {
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

  public static JHipsterModuleBuilder moduleBuilder(JHipsterModuleProperties properties) {
    return new JHipsterModuleBuilder(properties);
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

  public static JHipsterSource from(String source) {
    Assert.notBlank("source", source);

    return new JHipsterSource(Paths.get("/generator", source));
  }

  public static JHipsterProjectFilePath path(String path) {
    return new JHipsterProjectFilePath(path);
  }

  public static JHipsterDestination to(String destination) {
    return new JHipsterDestination(destination);
  }

  public static JHipsterDestination toSrcMainJava() {
    return JHipsterDestination.SRC_MAIN_JAVA;
  }

  public static JHipsterDestination toSrcMainDocker() {
    return JHipsterDestination.SRC_MAIN_DOCKER;
  }

  public static JHipsterDestination toSrcTestJava() {
    return JHipsterDestination.SRC_TEST_JAVA;
  }

  public static JHipsterDestination toSrcMainResources() {
    return JHipsterDestination.SRC_MAIN_RESOURCES;
  }

  public static JHipsterFileMatcher filesWithExtension(String extension) {
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
    return new TextReplacer(always(), text);
  }

  public static RegexReplacer regex(String regex) {
    return new RegexReplacer(always(), regex);
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

  public static BuildProfileId buildProfileId(String id) {
    return new BuildProfileId(id);
  }

  public static tech.jhipster.lite.module.domain.buildproperties.PropertyKey buildPropertyKey(String key) {
    return new tech.jhipster.lite.module.domain.buildproperties.PropertyKey(key);
  }

  public static tech.jhipster.lite.module.domain.buildproperties.PropertyValue buildPropertyValue(String value) {
    return new tech.jhipster.lite.module.domain.buildproperties.PropertyValue(value);
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

  public static PackageName packageName(String packageName) {
    return new PackageName(packageName);
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

  public JHipsterProjectFolder projectFolder() {
    return properties.projectFolder();
  }

  public JHipsterModuleProperties properties() {
    return properties;
  }

  public Indentation indentation() {
    return properties.indentation();
  }

  public JHipsterModuleFiles files() {
    return files;
  }

  public JHipsterModuleContext context() {
    return context;
  }

  public Collection<JHipsterModuleFile> filesToAdd() {
    return files.filesToAdd();
  }

  public JHipsterFilesToMove filesToMove() {
    return files.filesToMove();
  }

  public JHipsterFilesToDelete filesToDelete() {
    return files.filesToDelete();
  }

  public JHipsterModuleMandatoryReplacementsFactory mandatoryReplacements() {
    return mandatoryReplacements;
  }

  public JHipsterModuleOptionalReplacementsFactory optionalReplacements() {
    return optionalReplacements;
  }

  public JHipsterStartupCommands startupCommands() {
    return startupCommands.commands();
  }

  public JHipsterStartupCommands sonarAnalysisCommands() {
    return sonarAnalysisCommands.commands();
  }

  public JHipsterModuleJavaDependencies javaDependencies() {
    return javaDependencies;
  }

  public JHipsterModuleBuildProperties javaBuildProperties() {
    return javaBuildProperties;
  }

  public JHipsterModuleJavaBuildProfiles javaBuildProfiles() {
    return javaBuildProfiles;
  }

  public JHipsterModuleMavenPlugins mavenPlugins() {
    return mavenPlugins;
  }

  public JHipsterModuleGradleConfigurations gradleConfigurations() {
    return gradleConfigurations;
  }

  public JHipsterModuleGradlePlugins gradlePlugins() {
    return gradlePlugins;
  }

  public JHipsterModuleMavenBuildExtensions mavenBuildExtensions() {
    return mavenBuildExtensions;
  }

  public JHipsterModulePackageJson packageJson() {
    return packageJson;
  }

  public JHipsterModulePreActions preActions() {
    return preActions;
  }

  public JHipsterModulePostActions postActions() {
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

  public JHipsterModuleGitIgnore gitIgnore() {
    return gitIgnore;
  }

  public static final class JHipsterModuleBuilder {

    private static final String PROFILE = "profile";

    private final JHipsterModuleShortcuts shortcuts;
    private final JHipsterModuleProperties properties;
    private final JHipsterModuleContextBuilder context;
    private final JHipsterModuleFilesBuilder files = JHipsterModuleFiles.builder(this);
    private final JHipsterModuleMandatoryReplacementsFactoryBuilder mandatoryReplacements =
      JHipsterModuleMandatoryReplacementsFactory.builder(this);
    private final JHipsterModuleOptionalReplacementsFactoryBuilder optionalReplacements = JHipsterModuleOptionalReplacementsFactory.builder(
      this
    );
    private final JHipsterModuleStartupCommandsBuilder startupCommands = JHipsterModuleStartupCommands.builder(this);
    private final JHipsterModuleStartupCommandsBuilder sonarAnalysisCommands = JHipsterModuleStartupCommands.builder(this);
    private final JHipsterModuleJavaDependenciesBuilder<JHipsterModuleBuilder> javaDependencies = JHipsterModuleJavaDependencies.builder(
      this
    );
    private final JHipsterModuleBuildPropertiesBuilder<JHipsterModuleBuilder> javaBuildProperties = JHipsterModuleBuildProperties.builder(
      this
    );
    private final JHipsterModuleJavaBuildProfilesBuilder javaBuildProfiles = JHipsterModuleJavaBuildProfiles.builder(this);
    private final JHipsterModuleMavenPluginsBuilder<JHipsterModuleBuilder> mavenPlugins = JHipsterModuleMavenPlugins.builder(this);
    private final JHipsterModuleGradleConfigurationBuilder gradleConfigurations = JHipsterModuleGradleConfigurations.builder(this);
    private final JHipsterModuleGradlePluginBuilder gradlePlugins = JHipsterModuleGradlePlugins.builder(this);
    private final JHipsterModuleMavenBuildExtensionsBuilder mavenBuildExtensions = JHipsterModuleMavenBuildExtensions.builder(this);
    private final JHipsterModulePackageJsonBuilder packageJson = JHipsterModulePackageJson.builder(this);
    private final JHipsterModulePreActionsBuilder preActions = JHipsterModulePreActions.builder(this);
    private final JHipsterModulePostActionsBuilder postActions = JHipsterModulePostActions.builder(this);
    private final Map<PropertiesKey, JHipsterModuleSpringPropertiesBuilder> springProperties = new HashMap<>();
    private final Map<SpringFactoryType, JHipsterModuleSpringFactoriesBuilder> springFactories = new EnumMap<>(SpringFactoryType.class);
    private final JHipsterModuleGitIgnoreBuilder gitIgnore = JHipsterModuleGitIgnore.builder(this);

    private JHipsterModuleBuilder(JHipsterModuleProperties properties) {
      Assert.notNull("properties", properties);

      this.properties = properties;
      context = JHipsterModuleContext.builder(this);
      shortcuts = new JHipsterModuleShortcuts(this);
    }

    JHipsterModuleProperties properties() {
      return properties;
    }

    public JHipsterModuleBuilder documentation(DocumentationTitle title, JHipsterSource source) {
      shortcuts.documentation(title, source);

      return this;
    }

    public JHipsterModuleBuilder localEnvironment(LocalEnvironment localEnvironment) {
      shortcuts.localEnvironment(localEnvironment);

      return this;
    }

    public JHipsterModuleStartupCommandsBuilder startupCommands() {
      return startupCommands;
    }

    public JHipsterModuleStartupCommandsBuilder sonarAnalysisCommands() {
      return sonarAnalysisCommands;
    }

    public JHipsterModuleBuilder prerequisites(String prerequisites) {
      shortcuts.prerequisites(prerequisites);

      return this;
    }

    public JHipsterModuleContextBuilder context() {
      return context;
    }

    public JHipsterModuleFilesBuilder files() {
      return files;
    }

    public JHipsterModuleMandatoryReplacementsFactoryBuilder mandatoryReplacements() {
      return mandatoryReplacements;
    }

    public JHipsterModuleBuilder springTestLogger(String name, LogLevel level) {
      shortcuts.springTestLogger(name, level);

      return this;
    }

    public JHipsterModuleBuilder springMainLogger(String name, LogLevel level) {
      shortcuts.springMainLogger(name, level);

      return this;
    }

    public JHipsterModuleBuilder integrationTestExtension(String extensionClass) {
      shortcuts.integrationTestExtension(extensionClass);

      return this;
    }

    public JHipsterModuleOptionalReplacementsFactoryBuilder optionalReplacements() {
      return optionalReplacements;
    }

    public JHipsterModuleJavaDependenciesBuilder<JHipsterModuleBuilder> javaDependencies() {
      return javaDependencies;
    }

    public JHipsterModuleBuildPropertiesBuilder<JHipsterModuleBuilder> javaBuildProperties() {
      return javaBuildProperties;
    }

    public JHipsterModuleJavaBuildProfilesBuilder javaBuildProfiles() {
      return javaBuildProfiles;
    }

    public JHipsterModuleMavenPluginsBuilder<JHipsterModuleBuilder> mavenPlugins() {
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

    public JHipsterModulePostActionsBuilder postActions() {
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
        JHipsterModuleSpringProperties.builder(this)
      );
    }

    public JHipsterModuleSpringPropertiesBuilder springMainProperties(SpringProfile profile) {
      Assert.notNull(PROFILE, profile);

      return springProperties.computeIfAbsent(new PropertiesKey(profile, SpringPropertyType.MAIN_PROPERTIES), key ->
        JHipsterModuleSpringProperties.builder(this)
      );
    }

    public JHipsterModuleSpringPropertiesBuilder springTestProperties() {
      return springTestProperties(SpringProfile.TEST);
    }

    public JHipsterModuleSpringPropertiesBuilder springTestBootstrapProperties() {
      return springProperties.computeIfAbsent(new PropertiesKey(SpringProfile.DEFAULT, SpringPropertyType.TEST_BOOTSTRAP_PROPERTIES), key ->
        JHipsterModuleSpringProperties.builder(this)
      );
    }

    public JHipsterModuleSpringPropertiesBuilder springTestProperties(SpringProfile profile) {
      Assert.notNull(PROFILE, profile);

      return springProperties.computeIfAbsent(new PropertiesKey(profile, SpringPropertyType.TEST_PROPERTIES), key ->
        JHipsterModuleSpringProperties.builder(this)
      );
    }

    public JHipsterModuleSpringFactoriesBuilder springTestFactories() {
      return springFactories.computeIfAbsent(SpringFactoryType.TEST_FACTORIES, key -> JHipsterModuleSpringFactories.builder(this));
    }

    /**
     * Add new entries to the {@code .gitignore} file.
     */
    public JHipsterModuleGitIgnoreBuilder gitIgnore() {
      return gitIgnore;
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
