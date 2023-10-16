package tech.jhipster.lite.module.domain;

import static tech.jhipster.lite.module.domain.replacement.ReplacementCondition.*;

import java.nio.file.Paths;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;
import tech.jhipster.lite.module.domain.JHipsterModuleContext.JHipsterModuleContextBuilder;
import tech.jhipster.lite.module.domain.JHipsterModulePreActions.JHipsterModulePreActionsBuilder;
import tech.jhipster.lite.module.domain.file.*;
import tech.jhipster.lite.module.domain.file.JHipsterModuleFiles.JHipsterModuleFilesBuilder;
import tech.jhipster.lite.module.domain.javabuild.ArtifactId;
import tech.jhipster.lite.module.domain.javabuild.GroupId;
import tech.jhipster.lite.module.domain.javabuild.VersionSlug;
import tech.jhipster.lite.module.domain.javabuildplugin.JHipsterModuleJavaBuildPlugin;
import tech.jhipster.lite.module.domain.javabuildplugin.JHipsterModuleJavaBuildPlugin.JHipsterModuleJavaBuildPluginBuilder;
import tech.jhipster.lite.module.domain.javabuildplugin.JavaBuildPlugin;
import tech.jhipster.lite.module.domain.javabuildplugin.JavaBuildPlugin.JavaBuildPluginGroupIdBuilder;
import tech.jhipster.lite.module.domain.javadependency.DependencyId;
import tech.jhipster.lite.module.domain.javadependency.JHipsterModuleJavaDependencies;
import tech.jhipster.lite.module.domain.javadependency.JHipsterModuleJavaDependencies.JHipsterModuleJavaDependenciesBuilder;
import tech.jhipster.lite.module.domain.javadependency.JavaDependency;
import tech.jhipster.lite.module.domain.javadependency.JavaDependency.JavaDependencyGroupIdBuilder;
import tech.jhipster.lite.module.domain.javadependency.JavaDependencyVersion;
import tech.jhipster.lite.module.domain.javaproperties.*;
import tech.jhipster.lite.module.domain.javaproperties.JHipsterModuleSpringFactories.JHipsterModuleSpringFactoriesBuilder;
import tech.jhipster.lite.module.domain.javaproperties.JHipsterModuleSpringProperties.JHipsterModuleSpringPropertiesBuilder;
import tech.jhipster.lite.module.domain.javaproperties.PropertiesBlockComment.PropertiesBlockCommentPropertiesBuilder;
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
import tech.jhipster.lite.shared.error.domain.Assert;

@SuppressWarnings("java:S6539")
public class JHipsterModule {

  public static final String LINE_BREAK = "\n";

  private final JHipsterModuleProperties properties;
  private final JHipsterModuleFiles files;
  private final JHipsterModuleMandatoryReplacementsFactory mandatoryReplacements;
  private final JHipsterModuleOptionalReplacementsFactory optionalReplacements;
  private final JHipsterModuleContext context;
  private final JHipsterModuleJavaDependencies javaDependencies;
  private final JHipsterModuleJavaBuildPlugin javaBuildPlugins;
  private final JHipsterModulePackageJson packageJson;
  private final JHipsterModulePreActions preActions;
  private final JHipsterModulePostActions postActions;
  private final SpringProperties springProperties;
  private final SpringComments springComments;
  private final SpringPropertiesBlockComments springPropertiesBlockComments;
  private final SpringFactories springFactories;

  private JHipsterModule(JHipsterModuleBuilder builder) {
    properties = builder.properties;

    files = builder.files.build();
    mandatoryReplacements = builder.mandatoryReplacements.build();
    optionalReplacements = builder.optionalReplacements.build();
    context = builder.context.build();
    javaDependencies = builder.javaDependencies.build();
    javaBuildPlugins = builder.javaBuildPlugins.build();
    packageJson = builder.packageJson.build();
    preActions = builder.preActions.build();
    postActions = builder.postActions.build();
    springProperties = buildSpringProperties(builder);
    springComments = buildSpringComments(builder);
    springPropertiesBlockComments = buildSpringPropertiesBlockComments(builder);
    springFactories = buildSpringFactories(builder);
  }

  private JHipsterModule(JHipsterModule source, JHipsterModuleUpgrade upgrade) {
    Assert.notNull("upgrade", upgrade);

    properties = source.properties;
    files = source.files.forUpgrade(upgrade);
    mandatoryReplacements = source.mandatoryReplacements;
    optionalReplacements = source.optionalReplacements.add(upgrade.replacements());
    context = source.context;
    javaDependencies = source.javaDependencies;
    javaBuildPlugins = source.javaBuildPlugins;
    packageJson = source.packageJson;
    preActions = source.preActions;
    postActions = source.postActions;
    springProperties = source.springProperties;
    springComments = source.springComments;
    springPropertiesBlockComments = source.springPropertiesBlockComments;
    springFactories = source.springFactories;
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
      SpringProperty
        .builder(inputProperties.getKey().type())
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
      SpringComment
        .builder(inputProperties.getKey().type())
        .key(propertyComment.getKey())
        .comment(propertyComment.getValue())
        .profile(inputProperties.getKey().profile())
        .build();
  }

  private SpringPropertiesBlockComments buildSpringPropertiesBlockComments(JHipsterModuleBuilder builder) {
    return new SpringPropertiesBlockComments(
      builder.springProperties.entrySet().stream().flatMap(toSpringPropertiesBlockComments()).toList()
    );
  }

  private Function<
    Entry<PropertiesKey, JHipsterModuleSpringPropertiesBuilder>,
    Stream<SpringPropertiesBlockComment>
  > toSpringPropertiesBlockComments() {
    return inputProperties ->
      inputProperties.getValue().build().propertiesBlockComments().stream().map(toSpringPropertiesBlockComment(inputProperties));
  }

  private Function<PropertiesBlockComment, SpringPropertiesBlockComment> toSpringPropertiesBlockComment(
    Entry<PropertiesKey, JHipsterModuleSpringPropertiesBuilder> inputProperties
  ) {
    return propertiesBlockComment ->
      SpringPropertiesBlockComment
        .builder(inputProperties.getKey().type())
        .comment(propertiesBlockComment.comment())
        .properties(propertiesBlockComment.properties())
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

  public static JavaDependencyVersion javaDependencyVersion(String slug, String version) {
    return new JavaDependencyVersion(slug, version);
  }

  public static DependencyId dependencyId(String groupId, String artifactId) {
    return DependencyId.of(groupId(groupId), artifactId(artifactId));
  }

  public static JavaBuildPluginGroupIdBuilder javaBuildPlugin() {
    return JavaBuildPlugin.builder();
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

  public static RegexNeedleBeforeReplacer lineBeforeRegex(String regex) {
    return new RegexNeedleBeforeReplacer(notContainingReplacement(), Pattern.compile(regex, Pattern.MULTILINE));
  }

  public static RegexNeedleAfterReplacer lineAfterRegex(String regex) {
    return new RegexNeedleAfterReplacer(notContainingReplacement(), Pattern.compile(regex, Pattern.MULTILINE));
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

  public static PropertiesBlockCommentPropertiesBuilder properties(String comment) {
    return PropertiesBlockComment.builder().comment(comment(comment));
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

  public JHipsterTemplatedFiles templatedFiles() {
    List<JHipsterTemplatedFile> templatedFiles = files
      .filesToAdd()
      .stream()
      .map(file -> JHipsterTemplatedFile.builder().file(file).context(context).build())
      .toList();

    return new JHipsterTemplatedFiles(templatedFiles);
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

  public JHipsterModuleJavaDependencies javaDependencies() {
    return javaDependencies;
  }

  public JHipsterModuleJavaBuildPlugin javaBuildPlugins() {
    return javaBuildPlugins;
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

  public SpringPropertiesBlockComments springPropertiesBlockComments() {
    return springPropertiesBlockComments;
  }

  public static class JHipsterModuleBuilder {

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
    private final JHipsterModuleJavaDependenciesBuilder javaDependencies = JHipsterModuleJavaDependencies.builder(this);
    private final JHipsterModuleJavaBuildPluginBuilder javaBuildPlugins = JHipsterModuleJavaBuildPlugin.builder(this);
    private final JHipsterModulePackageJsonBuilder packageJson = JHipsterModulePackageJson.builder(this);
    private final JHipsterModulePreActionsBuilder preActions = JHipsterModulePreActions.builder(this);
    private final JHipsterModulePostActionsBuilder postActions = JHipsterModulePostActions.builder(this);
    private final Map<PropertiesKey, JHipsterModuleSpringPropertiesBuilder> springProperties = new HashMap<>();
    private final Map<SpringFactoryType, JHipsterModuleSpringFactoriesBuilder> springFactories = new EnumMap<>(SpringFactoryType.class);

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

    public JHipsterModuleBuilder startupCommand(String startupCommand) {
      shortcuts.startupCommand(startupCommand);

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

    public JHipsterModuleJavaDependenciesBuilder javaDependencies() {
      return javaDependencies;
    }

    public JHipsterModuleJavaBuildPluginBuilder javaBuildPlugins() {
      return javaBuildPlugins;
    }

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

    public JHipsterModuleSpringPropertiesBuilder springMainBootstrapProperties() {
      return springMainBootstrapProperties(SpringProfile.DEFAULT);
    }

    public JHipsterModuleSpringPropertiesBuilder springMainBootstrapProperties(SpringProfile profile) {
      Assert.notNull(PROFILE, profile);

      return springProperties.computeIfAbsent(
        new PropertiesKey(profile, SpringPropertyType.MAIN_BOOTSTRAP_PROPERTIES),
        key -> JHipsterModuleSpringProperties.builder(this)
      );
    }

    public JHipsterModuleSpringPropertiesBuilder springMainProperties(SpringProfile profile) {
      Assert.notNull(PROFILE, profile);

      return springProperties.computeIfAbsent(
        new PropertiesKey(profile, SpringPropertyType.MAIN_PROPERTIES),
        key -> JHipsterModuleSpringProperties.builder(this)
      );
    }

    public JHipsterModuleSpringPropertiesBuilder springTestProperties() {
      return springTestProperties(SpringProfile.TEST);
    }

    public JHipsterModuleSpringPropertiesBuilder springTestBootstrapProperties() {
      return springProperties.computeIfAbsent(
        new PropertiesKey(SpringProfile.DEFAULT, SpringPropertyType.TEST_BOOTSTRAP_PROPERTIES),
        key -> JHipsterModuleSpringProperties.builder(this)
      );
    }

    public JHipsterModuleSpringPropertiesBuilder springTestProperties(SpringProfile profile) {
      Assert.notNull(PROFILE, profile);

      return springProperties.computeIfAbsent(
        new PropertiesKey(profile, SpringPropertyType.TEST_PROPERTIES),
        key -> JHipsterModuleSpringProperties.builder(this)
      );
    }

    public JHipsterModuleSpringFactoriesBuilder springTestFactories() {
      return springFactories.computeIfAbsent(SpringFactoryType.TEST_FACTORIES, key -> JHipsterModuleSpringFactories.builder(this));
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
