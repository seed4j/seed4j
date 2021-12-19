package tech.jhipster.lite.generator.buildtool.maven.domain;

import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.common.domain.WordUtils.indent;
import static tech.jhipster.lite.generator.buildtool.maven.domain.Maven.*;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.*;

import java.util.List;
import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.common.domain.WordUtils;
import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.lite.generator.buildtool.generic.domain.Parent;
import tech.jhipster.lite.generator.buildtool.generic.domain.Plugin;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

public class MavenDomainService implements MavenService {

  public static final String SOURCE = "buildtool/maven";
  public static final String POM_XML = "pom.xml";

  private final ProjectRepository projectRepository;

  public MavenDomainService(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @Override
  public void addParent(Project project, Parent parent) {
    project.addDefaultConfig(PRETTIER_DEFAULT_INDENT);
    int indent = (Integer) project.getConfig(PRETTIER_DEFAULT_INDENT).orElse(2);

    String parentNode = Maven.getParent(parent, indent);
    String parentHeaderNode = Maven.getParentHeader(parent, indent);
    String parentHeaderRegexp = FileUtils.REGEXP_PREFIX_MULTILINE + parentHeaderNode;

    if (!projectRepository.containsRegexp(project, "", POM_XML, parentHeaderRegexp)) {
      projectRepository.replaceText(project, "", POM_XML, NEEDLE_PARENT, parentNode);
    }
  }

  @Override
  public void addDependency(Project project, Dependency dependency) {
    addDependency(project, dependency, List.of());
  }

  @Override
  public void addDependency(Project project, Dependency dependency, List<Dependency> exclusions) {
    int indent = (Integer) project.getConfig(PRETTIER_DEFAULT_INDENT).orElse(2);

    String dependencyNodeNode = Maven.getDependencyHeader(dependency, indent);
    String dependencyRegexp = FileUtils.REGEXP_PREFIX_MULTILINE + dependencyNodeNode;

    if (!projectRepository.containsRegexp(project, "", POM_XML, dependencyRegexp)) {
      project.addDefaultConfig(PRETTIER_DEFAULT_INDENT);

      String needle = dependency.getScope().orElse("").equals("test") ? NEEDLE_DEPENDENCY_TEST : NEEDLE_DEPENDENCY;
      String dependencyWithNeedle =
        Maven.getDependency(dependency, indent, exclusions) + System.lineSeparator() + indent(2, indent) + needle;

      projectRepository.replaceText(project, "", POM_XML, needle, dependencyWithNeedle);
    }
  }

  @Override
  public void deleteDependency(Project project, Dependency dependency) {
    project.addDefaultConfig(PRETTIER_DEFAULT_INDENT);
    int indent = (Integer) project.getConfig(PRETTIER_DEFAULT_INDENT).orElse(2);
    Dependency dependencyToDelete = Dependency.builder().groupId(dependency.getGroupId()).artifactId(dependency.getArtifactId()).build();

    String dependencyNode = Maven.getDependency(dependencyToDelete, indent) + System.lineSeparator();
    String endNode = indent(2, indent) + "</dependency>";
    String dependencyNodeRegExp = "(?s)" + indent(2, indent) + dependencyNode.replace(endNode, ".*" + endNode);

    projectRepository.replaceRegexp(project, "", POM_XML, dependencyNodeRegExp, "");
  }

  @Override
  public void addPlugin(Project project, Plugin plugin) {
    project.addDefaultConfig(PRETTIER_DEFAULT_INDENT);
    int indent = (Integer) project.getConfig(PRETTIER_DEFAULT_INDENT).orElse(2);

    String pluginNodeNode = Maven.getPluginHeader(plugin, indent);
    String pluginRegexp = FileUtils.REGEXP_PREFIX_MULTILINE + pluginNodeNode;

    if (!projectRepository.containsRegexp(project, "", POM_XML, pluginRegexp)) {
      String pluginWithNeedle = Maven.getPlugin(plugin, indent) + System.lineSeparator() + indent(3, indent) + NEEDLE_PLUGIN;

      projectRepository.replaceText(project, "", POM_XML, NEEDLE_PLUGIN, pluginWithNeedle);
    }
  }

  @Override
  public void addPluginManagement(Project project, Plugin plugin) {
    project.addDefaultConfig(PRETTIER_DEFAULT_INDENT);
    int indent = (Integer) project.getConfig(PRETTIER_DEFAULT_INDENT).orElse(2);

    String pluginNodeNode = Maven.getPluginHeader(plugin, indent);
    String pluginRegexp = FileUtils.REGEXP_PREFIX_MULTILINE + pluginNodeNode;

    if (!projectRepository.containsRegexp(project, "", POM_XML, pluginRegexp)) {
      String pluginWithNeedle = Maven.getPlugin(plugin, indent) + System.lineSeparator() + indent(3, indent) + NEEDLE_PLUGIN_MANAGEMENT;

      projectRepository.replaceText(project, "", POM_XML, NEEDLE_PLUGIN_MANAGEMENT, pluginWithNeedle);
    }
  }

  @Override
  public void addProperty(Project project, String key, String version) {
    project.addDefaultConfig(PRETTIER_DEFAULT_INDENT);
    int indent = (Integer) project.getConfig(PRETTIER_DEFAULT_INDENT).orElse(2);

    String pluginRegexp = Maven.getProperty(key, ".*");

    if (!projectRepository.containsRegexp(project, "", POM_XML, pluginRegexp)) {
      String propertyWithNeedle = Maven.getProperty(key, version) + System.lineSeparator() + indent(2, indent) + NEEDLE_PROPERTIES;

      projectRepository.replaceText(project, "", POM_XML, NEEDLE_PROPERTIES, propertyWithNeedle);
    }
  }

  @Override
  public void deleteProperty(Project project, String key) {
    project.addDefaultConfig(PRETTIER_DEFAULT_INDENT);

    String propertyNode = Maven.getProperty(key, ".*") + System.lineSeparator();

    projectRepository.replaceText(project, "", POM_XML, propertyNode, "");
  }

  @Override
  public void init(Project project) {
    addPomXml(project);
    addMavenWrapper(project);
  }

  @Override
  public void addPomXml(Project project) {
    project.addDefaultConfig(PACKAGE_NAME);
    project.addDefaultConfig(PROJECT_NAME);
    project.addDefaultConfig(BASE_NAME);

    String baseName = project.getBaseName().orElse("");
    project.addConfig("dasherizedBaseName", WordUtils.kebabCase(baseName));

    projectRepository.template(project, SOURCE, POM_XML);
  }

  @Override
  public void addMavenWrapper(Project project) {
    projectRepository.add(project, SOURCE, "mvnw");
    projectRepository.setExecutable(project, "", "mvnw");

    projectRepository.add(project, SOURCE, "mvnw.cmd");
    projectRepository.setExecutable(project, "", "mvnw.cmd");

    String sourceWrapper = getPath(SOURCE, ".mvn", "wrapper");
    String destinationWrapper = getPath(".mvn", "wrapper");

    projectRepository.add(
      project,
      sourceWrapper,
      "MavenWrapperDownloader.java.mustache",
      destinationWrapper,
      "MavenWrapperDownloader.java"
    );
    projectRepository.add(project, sourceWrapper, "maven-wrapper.jar", destinationWrapper);
    projectRepository.add(project, sourceWrapper, "maven-wrapper.properties", destinationWrapper);
  }
}
