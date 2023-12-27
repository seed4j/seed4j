package tech.jhipster.lite.module.infrastructure.secondary.javadependency.maven;

import static org.joox.JOOX.$;
import static tech.jhipster.lite.module.domain.JHipsterModule.LINE_BREAK;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.joox.JOOX;
import org.joox.Match;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import tech.jhipster.lite.module.domain.Indentation;
import tech.jhipster.lite.module.domain.buildproperties.BuildProperty;
import tech.jhipster.lite.module.domain.buildproperties.PropertyKey;
import tech.jhipster.lite.module.domain.buildproperties.PropertyValue;
import tech.jhipster.lite.module.domain.javabuild.VersionSlug;
import tech.jhipster.lite.module.domain.javabuild.command.AddBuildPluginManagement;
import tech.jhipster.lite.module.domain.javabuild.command.AddDirectJavaBuildPlugin;
import tech.jhipster.lite.module.domain.javabuild.command.AddDirectJavaDependency;
import tech.jhipster.lite.module.domain.javabuild.command.AddJavaBuildPlugin;
import tech.jhipster.lite.module.domain.javabuild.command.AddJavaBuildProfile;
import tech.jhipster.lite.module.domain.javabuild.command.AddJavaDependency;
import tech.jhipster.lite.module.domain.javabuild.command.AddJavaDependencyManagement;
import tech.jhipster.lite.module.domain.javabuild.command.AddMavenBuildExtension;
import tech.jhipster.lite.module.domain.javabuild.command.RemoveDirectJavaDependency;
import tech.jhipster.lite.module.domain.javabuild.command.RemoveJavaDependencyManagement;
import tech.jhipster.lite.module.domain.javabuild.command.SetBuildProperty;
import tech.jhipster.lite.module.domain.javabuild.command.SetVersion;
import tech.jhipster.lite.module.domain.javabuildplugin.JavaBuildPluginAdditionalElements;
import tech.jhipster.lite.module.domain.javabuildprofile.BuildProfileActivation;
import tech.jhipster.lite.module.domain.javabuildprofile.BuildProfileId;
import tech.jhipster.lite.module.domain.javadependency.DependencyId;
import tech.jhipster.lite.module.domain.javadependency.JavaDependencyClassifier;
import tech.jhipster.lite.module.domain.javadependency.JavaDependencyScope;
import tech.jhipster.lite.module.infrastructure.secondary.javadependency.JavaDependenciesCommandHandler;
import tech.jhipster.lite.shared.enumeration.domain.Enums;
import tech.jhipster.lite.shared.error.domain.Assert;
import tech.jhipster.lite.shared.error.domain.GeneratorException;
import tech.jhipster.lite.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;

public class MavenCommandHandler implements JavaDependenciesCommandHandler {

  private static final String FORMATTED_LINE_END = "> *" + LINE_BREAK;
  private static final String RESULTING_LINE_END = ">" + LINE_BREAK;
  private static final Pattern SPACES_ONLY_LINE = Pattern.compile("^\\s+$", Pattern.MULTILINE);
  private static final String HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" + LINE_BREAK;
  private static final String COMMAND = "command";
  private static final String GROUP_ID = "groupId";
  private static final String ARTIFACT_ID = "artifactId";
  private static final String VERSION = "version";
  private static final String CLASSIFIER = "classifier";

  private static final String PARENT = "parent";
  private static final String PACKAGING = "packaging";
  private static final String DESCRIPTION = "description";
  private static final String NAME = "name";
  private static final String PROPERTIES = "properties";
  private static final String BUILD = "build";
  private static final String DEPENDENCY_MANAGEMENT = "dependencyManagement";
  private static final String DEPENDENCIES = "dependencies";
  private static final String PLUGINS = "plugins";

  private static final String[] PROPERTIES_ANCHORS = new String[] { PARENT, PACKAGING, DESCRIPTION, NAME, VERSION, ARTIFACT_ID };

  private static final String[] DEPENDENCIES_ANCHORS = new String[] {
    DEPENDENCY_MANAGEMENT,
    PROPERTIES,
    PARENT,
    PACKAGING,
    DESCRIPTION,
    NAME,
    VERSION,
    ARTIFACT_ID,
  };

  private static final String[] BUILD_ANCHORS = new String[] {
    DEPENDENCIES,
    DEPENDENCY_MANAGEMENT,
    PROPERTIES,
    PARENT,
    PACKAGING,
    DESCRIPTION,
    NAME,
    VERSION,
    ARTIFACT_ID,
  };

  private static final String[] PROFILES_ANCHORS = new String[] {
    BUILD,
    DEPENDENCIES,
    DEPENDENCY_MANAGEMENT,
    PROPERTIES,
    PARENT,
    PACKAGING,
    DESCRIPTION,
    NAME,
    VERSION,
    ARTIFACT_ID,
  };

  private final Indentation indentation;
  private final Path pomPath;
  private final Match document;

  public MavenCommandHandler(Indentation indentation, Path pomPath) {
    Assert.notNull("indentation", indentation);
    Assert.notNull("pomPath", pomPath);

    this.indentation = indentation;
    this.pomPath = pomPath;
    document = readDocument(pomPath);
  }

  private Match readDocument(Path pomPath) {
    try (InputStream input = Files.newInputStream(pomPath)) {
      return $(input);
    } catch (IOException | SAXException e) {
      throw GeneratorException.technicalError("Error reading pom content: " + e.getMessage(), e);
    }
  }

  @Override
  public void handle(SetVersion command) {
    Assert.notNull(COMMAND, command);

    BuildProperty property = new BuildProperty(new PropertyKey(command.property()), new PropertyValue(command.dependencyVersion()));
    handle(new SetBuildProperty(property));
  }

  @Override
  public void handle(SetBuildProperty command) {
    Assert.notNull(COMMAND, command);

    Match properties;
    int indentationLevel;
    if (command.buildProfile().isPresent()) {
      BuildProfileId buildProfile = command.buildProfile().orElseThrow();
      properties = findBuildProfile(buildProfile).orElseThrow(() -> new MissingMavenProfileException(buildProfile)).child(PROPERTIES);
      if (properties.isEmpty()) {
        properties = appendPropertiesToBuildProfile(buildProfile);
      }
      indentationLevel = 3;
    } else {
      properties = document.find("project > properties");
      if (properties.isEmpty()) {
        properties = appendProperties();
      }
      indentationLevel = 1;
    }

    appendPropertyLine(properties, command.property(), indentationLevel);

    writePom();
  }

  @Override
  public void handle(AddJavaBuildProfile command) {
    if (profiles().isEmpty()) {
      appendProfiles();
    }

    if (findBuildProfile(command.buildProfileId()).isEmpty()) {
      appendProfile(command);
    }

    writePom();
  }

  private Match profiles() {
    return document.find("project > profiles");
  }

  private void appendProfile(AddJavaBuildProfile command) {
    Match profile = format($("profile"), indentation.times(3), $("id", command.buildProfileId().value()), indentation.times(2), null, true);
    if (command.activation().isPresent()) {
      Match activationNode = $("activation");
      BuildProfileActivation buildProfileActivation = command.activation().orElseThrow();
      if (buildProfileActivation.activeByDefault().isPresent()) {
        activationNode =
          format(
            activationNode,
            indentation.times(4),
            $("activeByDefault", buildProfileActivation.activeByDefault().orElseThrow().toString()),
            indentation.times(3),
            null,
            true
          );
      }

      shortFormat(profile.append(indentation.times(1)).append(activationNode), indentation.times(2), null);
    }

    shortFormat(profiles().append(indentation.times(1)).append(profile), indentation.times(1), null);
  }

  private Match format(
    Match match,
    String firstIndentation,
    Match matchAppend,
    String secondIndentation,
    String spaces,
    boolean hasLineBreak
  ) {
    return match
      .append(hasLineBreak ? LINE_BREAK : "")
      .append(firstIndentation)
      .append(matchAppend)
      .append(LINE_BREAK)
      .append(secondIndentation != null ? secondIndentation : "")
      .append(spaces != null ? spaces : "");
  }

  private Match shortFormat(Match match, String content, Match matchAppend) {
    if (matchAppend != null) {
      return match.append(LINE_BREAK).append(content).append(matchAppend);
    }
    return match.append(LINE_BREAK).append(content);
  }

  private void appendProfiles() {
    Match profiles = shortFormat($("profiles"), indentation.spaces(), null);
    findFirst(PROFILES_ANCHORS).after(profiles);
    profiles().before(LINE_BREAK).before(LINE_BREAK).before(indentation.spaces());
  }

  private Optional<Match> findBuildProfile(BuildProfileId buildProfile) {
    return document.find("project > profiles > profile").each().stream().filter(buildProfileMatch(buildProfile)).findFirst();
  }

  private Predicate<Match> buildProfileMatch(BuildProfileId buildProfile) {
    return profile -> profile.child("id").text().equals(buildProfile.value());
  }

  private Match appendProperties() {
    Match properties = shortFormat($(PROPERTIES), indentation.spaces(), null);
    findFirst(PROPERTIES_ANCHORS).after(properties);
    return document.find("project > properties").before(LINE_BREAK).before(LINE_BREAK).before(indentation.spaces());
  }

  private Match appendPropertiesToBuildProfile(BuildProfileId buildProfile) {
    Match properties = shortFormat($(PROPERTIES), indentation.times(3), null);
    Match buildProfileNode = findBuildProfile(buildProfile).orElseThrow();
    shortFormat(buildProfileNode.append(indentation.times(1)).append(properties), indentation.times(2), null);
    return buildProfileNode.child(PROPERTIES);
  }

  private void appendPropertyLine(Match properties, BuildProperty buildProperty, int level) {
    Match propertyNode = properties.children().filter(buildProperty.key().get());

    if (propertyNode.isNotEmpty()) {
      propertyNode.text(buildProperty.value().get());
    } else {
      format(
        properties,
        indentation.times(1),
        $(buildProperty.key().get(), buildProperty.value().get()),
        null,
        indentation.times(level),
        false
      );
    }
  }

  @Override
  public void handle(RemoveJavaDependencyManagement command) {
    Assert.notNull(COMMAND, command);

    removeDependency("project > dependencyManagement > dependencies > dependency", command.dependency());
  }

  @Override
  public void handle(RemoveDirectJavaDependency command) {
    Assert.notNull(COMMAND, command);

    removeDependency("project > dependencies > dependency", command.dependency());
  }

  private void removeDependency(String rootPath, DependencyId dependency) {
    document.find(rootPath).each().stream().filter(dependencyMatch(dependency)).forEach(Match::remove);

    writePom();
  }

  @Override
  public void handle(AddMavenBuildExtension command) {
    Assert.notNull(COMMAND, command);

    Match extensionNode = extensionNode(command, 3);

    Match buildNode = findBuildNode();
    if (buildNode.isEmpty()) {
      appendBuildNode(extensionsNode(extensionNode));
    } else {
      appendExtensionInBuildNode(extensionNode, buildNode);
    }

    writePom();
  }

  private Match extensionNode(AddMavenBuildExtension command, int level) {
    Match extensionNode = format(
      $("extension"),
      indent(level),
      $(GROUP_ID, command.buildExtension().groupId().get()),
      indent(level),
      null,
      true
    )
      .append($(ARTIFACT_ID, command.buildExtension().artifactId().get()));

    appendVersion(command.buildExtension().versionSlug(), extensionNode, level);

    return extensionNode.append(LINE_BREAK);
  }

  private String indent(int level) {
    return indentation.times(level + 1);
  }

  private Match extensionsNode(Match extensionNode) {
    return format($("extensions"), indentation.times(3), extensionNode.append(indentation.times(3)), indentation.times(2), null, true);
  }

  private void appendExtensionInBuildNode(Match extensionNode, Match buildNode) {
    Match extensionsNode = buildNode.child("extensions");

    if (extensionsNode.isEmpty()) {
      prependExtensions(extensionNode, buildNode);
    } else {
      appendInExtensions(extensionNode, extensionsNode);
    }
  }

  private Match prependExtensions(Match extensionNode, Match buildNode) {
    return buildNode.prepend(extensionsNode(extensionNode)).prepend(indentation.times(2)).prepend(LINE_BREAK);
  }

  private Match appendInExtensions(Match extensionNode, Match extensionsNode) {
    return format(extensionsNode, indentation.times(1), extensionNode.append(indentation.times(3)), indentation.times(2), null, false);
  }

  @Override
  public void handle(AddJavaDependencyManagement command) {
    Assert.notNull(COMMAND, command);

    Match dependencies = document.find("project > dependencyManagement > dependencies");
    if (dependencies.isEmpty()) {
      appendDependenciesManagement(command);
    } else {
      appendDependencyManagement(command, dependencies);
    }

    writePom();
  }

  private void appendDependenciesManagement(AddJavaDependencyManagement command) {
    Match dependencies = format(
      $(DEPENDENCY_MANAGEMENT),
      indentation.times(2),
      format($(DEPENDENCIES), indentation.times(3), dependencyNode(command, 3), indentation.times(2), null, true),
      null,
      indentation.spaces(),
      true
    );

    findFirst(DEPENDENCIES_ANCHORS).after(dependencies);

    document.find("project > dependencyManagement").before(LINE_BREAK).before(LINE_BREAK).before(indentation.spaces());
  }

  private void appendDependencyManagement(AddJavaDependency command, Match dependencies) {
    appendNotTestDependency(command, dependencies, 3);
  }

  @Override
  public void handle(AddDirectJavaDependency command) {
    Assert.notNull(COMMAND, command);

    Match dependencies = document.find("project > dependencies");
    if (dependencies.isEmpty()) {
      appendDependencies(command);
    } else {
      appendDependency(command, dependencies);
    }

    writePom();
  }

  private void appendDependencies(AddDirectJavaDependency command) {
    Match dependencies = format($(DEPENDENCIES), indentation.times(2), dependencyNode(command, 2), null, indentation.spaces(), true);

    findFirst(DEPENDENCIES_ANCHORS).after(dependencies);

    document.find("project > dependencies").before(LINE_BREAK).before(LINE_BREAK).before(indentation.spaces());
  }

  private void appendDependency(AddDirectJavaDependency command, Match dependencies) {
    if (command.scope() == JavaDependencyScope.TEST) {
      appendDependencyInLastPosition(command, dependencies, 2);
    } else {
      appendNotTestDependency(command, dependencies, 2);
    }
  }

  private void appendNotTestDependency(AddJavaDependency command, Match dependencies, int level) {
    dependencies
      .children()
      .each()
      .stream()
      .filter(testDependency())
      .findFirst()
      .ifPresentOrElse(appendBeforeFirstTestDependency(command, level), () -> appendDependencyInLastPosition(command, dependencies, level));
  }

  private Consumer<Match> appendBeforeFirstTestDependency(AddJavaDependency command, int level) {
    return firstTestDependency -> {
      Match dependencyNode = dependencyNode(command, level);
      firstTestDependency.before(dependencyNode);

      document
        .find("project > dependencies > dependency")
        .each()
        .stream()
        .filter(dependencyMatch(command.dependencyId()))
        .findFirst()
        .ifPresent(node -> node.after(indentation.times(level)).after(LINE_BREAK).after(LINE_BREAK));
    };
  }

  private Predicate<Match> dependencyMatch(DependencyId dependency) {
    return dependencyNode -> {
      boolean sameGroupId = dependencyNode.child(GROUP_ID).text().equals(dependency.groupId().get());
      boolean sameArtifactId = dependencyNode.child(ARTIFACT_ID).text().equals(dependency.artifactId().get());

      return sameGroupId && sameArtifactId;
    };
  }

  private Predicate<Match> testDependency() {
    return dependency -> "test".equals(dependency.child("scope").text());
  }

  private void appendDependencyInLastPosition(AddJavaDependency command, Match dependencies, int level) {
    format(dependencies, indentation.times(level), dependencyNode(command, level), indentation.times(level - 1), null, true);
  }

  private Match dependencyNode(AddJavaDependency command, int level) {
    Match dependency = buildDependencyNode(command, level);

    appendVersion(command.version(), dependency, level);
    appendClassifier(command.classifier(), dependency, level);
    appendScope(command, dependency, level);
    appendOptional(command, dependency, level);
    appendType(command, dependency, level);
    appendExclusions(command, dependency, level);

    shortFormat(dependency, indentation.times(level), null);

    return dependency;
  }

  private Match buildDependencyNode(AddJavaDependency command, int level) {
    return appendDependencyId($("dependency"), command.dependencyId(), level);
  }

  private void appendScope(AddJavaDependency command, Match dependency, int level) {
    if (command.scope() != JavaDependencyScope.COMPILE) {
      shortFormat(dependency, indent(level), $("scope", Enums.map(command.scope(), MavenScope.class).key()));
    }
  }

  private void appendOptional(AddJavaDependency command, Match dependency, int level) {
    if (command.optional()) {
      shortFormat(dependency, indent(level), $("optional", "true"));
    }
  }

  private void appendType(AddJavaDependency command, Match dependency, int level) {
    command.dependencyType().ifPresent(type -> shortFormat(dependency, indent(level), $("type", Enums.map(type, MavenType.class).key())));
  }

  private void appendExclusions(AddJavaDependency command, Match dependency, int level) {
    Collection<DependencyId> exclusions = command.exclusions();
    if (exclusions.isEmpty()) {
      return;
    }

    shortFormat(dependency, indent(level), buildExclusionsNode(level, exclusions));
  }

  private Match buildExclusionsNode(int level, Collection<DependencyId> exclusions) {
    Match exclusionsNode = $("exclusions");

    exclusions.stream().map(toExclusionNode(level)).forEach(appendExclusionNode(level, exclusionsNode));

    shortFormat(exclusionsNode, indent(level), null);

    return exclusionsNode;
  }

  private Function<DependencyId, Match> toExclusionNode(int level) {
    return exclusion -> shortFormat(appendDependencyId($("exclusion"), exclusion, level + 2), indentation.times(level + 2), null);
  }

  private Consumer<Match> appendExclusionNode(int level, Match exclusionsNode) {
    return exclusionNode -> shortFormat(exclusionsNode, indentation.times(level + 2), exclusionNode);
  }

  @Override
  public void handle(AddBuildPluginManagement command) {
    Assert.notNull(COMMAND, command);

    Match pluginNode = pluginNode(command, 4);

    Match buildNode = findBuildNode();
    if (buildNode.isEmpty()) {
      appendBuildNode(pluginManagementNode(pluginNode));
    } else {
      appendPluginManagementInBuildNode(pluginNode, buildNode);
    }

    writePom();
  }

  private void appendPluginManagementInBuildNode(Match pluginNode, Match buildNode) {
    Match pluginManagementNode = buildNode.child("pluginManagement");

    if (pluginManagementNode.isEmpty()) {
      appendPluginManagement(pluginNode, buildNode);
    } else {
      appendInPluginManagement(pluginNode, pluginManagementNode);
    }
  }

  private Match appendPluginManagement(Match pluginNode, Match buildNode) {
    return shortFormat(buildNode.append(indentation.times(1)).append(pluginManagementNode(pluginNode)), indentation.times(1), null);
  }

  private void appendInPluginManagement(Match pluginNode, Match pluginManagementNode) {
    Match pluginsNode = pluginManagementNode.child(PLUGINS);

    if (pluginsNode.isEmpty()) {
      appendPluginsNode(pluginManagementNode, pluginNode, 4);
    } else {
      appendPluginNode(pluginsNode, pluginNode, 4);
    }
  }

  private Match pluginManagementNode(Match pluginNode) {
    return format($("pluginManagement"), indentation.times(3), pluginsNode(pluginNode, 4), indentation.times(2), null, true);
  }

  @Override
  public void handle(AddDirectJavaBuildPlugin command) {
    Assert.notNull(COMMAND, command);

    Match pluginNode = pluginNode(command, 3);

    Match buildNode = findBuildNode();
    if (buildNode.isEmpty()) {
      appendBuildNode(pluginsNode(pluginNode, 3));
    } else {
      appendPluginInBuildNode(pluginNode, buildNode);
    }

    writePom();
  }

  private void appendPluginInBuildNode(Match pluginNode, Match buildNode) {
    Match pluginsNode = buildNode.child(PLUGINS);

    if (pluginsNode.isEmpty()) {
      appendPluginsNode(buildNode, pluginNode, 3);
    } else {
      appendPluginNode(pluginsNode, pluginNode, 3);
    }
  }

  private Match appendPluginsNode(Match parent, Match pluginNode, int level) {
    return format(parent, indentation.times(1), pluginsNode(pluginNode, level), indentation.times(level - 2), null, false);
  }

  private Match pluginsNode(Match pluginNode, int level) {
    return format(
      $(PLUGINS),
      indentation.times(level),
      pluginNode.append(indentation.times(level)),
      indentation.times(level - 1),
      null,
      true
    );
  }

  private void appendBuildNode(Match innerNode) {
    Match build = format($(BUILD), indentation.times(2), innerNode, indentation.times(1), null, true);

    findFirst(BUILD_ANCHORS).after(build);

    findBuildNode().before(LINE_BREAK).before(LINE_BREAK).before(indentation.spaces());
  }

  private Match findBuildNode() {
    return document.find("project > build");
  }

  private Match appendPluginNode(Match pluginsNode, Match pluginNode, int level) {
    return format(
      pluginsNode,
      indentation.times(1),
      pluginNode.append(indentation.times(level)),
      indentation.times(level - 1),
      null,
      false
    );
  }

  private Match pluginNode(AddJavaBuildPlugin command, int level) {
    Match pluginNode = appendDependencyId($("plugin"), command.dependencyId(), level);

    appendVersion(command.versionSlug(), pluginNode, level);

    command.additionalElements().ifPresent(appendAdditionalElements(pluginNode, level));

    return shortFormat(pluginNode, "", null);
  }

  private Match appendDependencyId(Match node, DependencyId dependency, int level) {
    return format(node, indent(level), $(GROUP_ID, dependency.groupId().get()), indent(level), null, true)
      .append($(ARTIFACT_ID, dependency.artifactId().get()));
  }

  private void appendVersion(Optional<VersionSlug> versionSlug, Match node, int level) {
    versionSlug.ifPresent(version -> shortFormat(node, indent(level), $(VERSION, version.mavenVariable())));
  }

  private void appendClassifier(Optional<JavaDependencyClassifier> classifier, Match node, int level) {
    classifier.ifPresent(depClassifier -> shortFormat(node, indent(level), $(CLASSIFIER, depClassifier.get())));
  }

  private Consumer<JavaBuildPluginAdditionalElements> appendAdditionalElements(Match pluginNode, int level) {
    return additionalElements ->
      shortFormat(pluginNode, indent(level), null).append(formatAdditionalElements(additionalElements, level + 1));
  }

  private String formatAdditionalElements(JavaBuildPluginAdditionalElements additionalElements, int level) {
    try (StringWriter stringWriter = new StringWriter()) {
      org.dom4j.Document additionalElementsDocument = DocumentHelper.parseText("<root>" + additionalElements.get() + "</root>");
      XMLWriter writer = new XMLWriter(stringWriter, buildFormat());
      writer.write(additionalElementsDocument);

      String result = stringWriter.toString();

      return result
        .substring(10, result.length() - 10)
        .replaceAll(FORMATTED_LINE_END, RESULTING_LINE_END)
        .indent((level - 1) * indentation.spacesCount());
    } catch (DocumentException | IOException e) {
      throw new MalformedAdditionalInformationException(e);
    }
  }

  private OutputFormat buildFormat() {
    OutputFormat format = OutputFormat.createPrettyPrint();

    format.setIndentSize(indentation.spacesCount());
    format.setSuppressDeclaration(true);
    format.setEncoding("UTF-8");

    return format;
  }

  @ExcludeFromGeneratedCodeCoverage(reason = "The exception handling is hard to test and an implementation detail")
  private void writePom() {
    try (Writer writer = Files.newBufferedWriter(pomPath, StandardCharsets.UTF_8)) {
      writer.write(HEADER);

      for (Element e : document) {
        String element = JOOX.$(e).toString().replace("\r\n", LINE_BREAK).replace(" xmlns=\"\"", "");

        element = SPACES_ONLY_LINE.matcher(element).replaceAll("");

        writer.write(element);
      }
    } catch (IOException e) {
      throw GeneratorException.technicalError("Error writing pom: " + e.getMessage(), e);
    }
  }

  private Match findFirst(String... elements) {
    return Stream
      .of(elements)
      .map(element -> "project > " + element)
      .map(document::find)
      .filter(Match::isNotEmpty)
      .findFirst()
      .orElseThrow(InvalidPomException::new);
  }
}
