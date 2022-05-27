package tech.jhipster.lite.generator.buildtool.maven.domain;

import static tech.jhipster.lite.common.domain.FileUtils.REGEXP_PREFIX_MULTILINE;
import static tech.jhipster.lite.common.domain.FileUtils.REGEXP_SPACE_STAR;
import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.common.domain.WordUtils.DEFAULT_INDENTATION;
import static tech.jhipster.lite.common.domain.WordUtils.LF;
import static tech.jhipster.lite.common.domain.WordUtils.indent;
import static tech.jhipster.lite.generator.buildtool.maven.domain.Maven.*;
import static tech.jhipster.lite.generator.buildtool.maven.domain.Maven.GROUP_ID_END;
import static tech.jhipster.lite.generator.project.domain.Constants.DEPENDENCIES_FOLDER;
import static tech.jhipster.lite.generator.project.domain.Constants.POM_XML;
import static tech.jhipster.lite.generator.project.domain.Constants.TEMPLATE_FOLDER;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.BASE_NAME;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.PACKAGE_NAME;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.PRETTIER_DEFAULT_INDENT;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.PROJECT_NAME;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.common.domain.WordUtils;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.lite.generator.buildtool.generic.domain.Parent;
import tech.jhipster.lite.generator.buildtool.generic.domain.Plugin;
import tech.jhipster.lite.generator.buildtool.generic.domain.Repository;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectFile;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

public class MavenDomainService implements MavenService {

  public static final String SOURCE = "buildtool/maven";

  private final ProjectRepository projectRepository;

  public MavenDomainService(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @Override
  public void addParent(Project project, Parent parent) {
    project.addDefaultConfig(PRETTIER_DEFAULT_INDENT);

    int indent = (Integer) project.getConfig(PRETTIER_DEFAULT_INDENT).orElse(DEFAULT_INDENTATION);

    String parentHeaderNode = Maven.getParentHeader(parent, indent).indent(indent);
    String parentHeaderRegexp = (REGEXP_PREFIX_MULTILINE + parentHeaderNode);

    if (!projectRepository.containsRegexp(project, "", POM_XML, parentHeaderRegexp)) {
      String newParentNode = Maven.getParent(parent, indent).indent(indent);
      projectRepository.replaceText(project, "", POM_XML, REGEXP_SPACE_STAR + NEEDLE_PARENT + LF, newParentNode);
    }
  }

  @Override
  public void addDependency(Project project, Dependency dependency) {
    addDependency(project, dependency, null);
  }

  @Override
  public void addDependency(Project project, Dependency dependency, List<Dependency> exclusions) {
    addDependency(
      project,
      dependency,
      exclusions,
      dependency.getScope().filter(s -> s.equals("test")).map(s -> NEEDLE_DEPENDENCY_TEST).orElse(NEEDLE_DEPENDENCY)
    );
  }

  @Override
  public void addDependencyManagement(Project project, Dependency dependency) {
    addDependency(project, dependency, null, NEEDLE_DEPENDENCY_MANAGEMENT);
  }

  private void addDependency(Project project, Dependency dependency, List<Dependency> exclusions, String needle) {
    project.addDefaultConfig(PRETTIER_DEFAULT_INDENT);

    int indent = (Integer) project.getConfig(PRETTIER_DEFAULT_INDENT).orElse(DEFAULT_INDENTATION);

    int level = NEEDLE_DEPENDENCY_MANAGEMENT.equals(needle) ? 3 : 2;

    String dependencyNode = Maven.getDependencyHeader(dependency, indent).indent(level * indent);
    String dependencyRegexp = (REGEXP_PREFIX_MULTILINE + dependencyNode);

    if (!projectRepository.containsRegexp(project, "", POM_XML, dependencyRegexp)) {
      String newDependencyNode = Maven.getDependency(dependency, indent, exclusions).indent(level * indent);
      String dependencyWithNeedle = (newDependencyNode + indent(level, indent) + needle);

      projectRepository.replaceText(project, "", POM_XML, REGEXP_SPACE_STAR + needle, dependencyWithNeedle);
    }
  }

  @Override
  public void deleteDependency(Project project, Dependency dependency) {
    project.addDefaultConfig(PRETTIER_DEFAULT_INDENT);

    int indent = (Integer) project.getConfig(PRETTIER_DEFAULT_INDENT).orElse(DEFAULT_INDENTATION);

    Dependency dependencyToDelete = Dependency.builder().groupId(dependency.getGroupId()).artifactId(dependency.getArtifactId()).build();

    String dependencyNode = Maven.getDependency(dependencyToDelete, indent).indent(2 * indent);
    String endNode = indent(2, indent) + "</dependency>";
    String dependencyNodeRegExp = ("(?s)" + dependencyNode.replace(endNode, ".*" + endNode));

    projectRepository.replaceRegexp(project, "", POM_XML, dependencyNodeRegExp, "");
  }

  @Override
  public void addPlugin(Project project, Plugin plugin) {
    addPlugin(project, plugin, NEEDLE_PLUGIN);
  }

  @Override
  public void addPluginManagement(Project project, Plugin plugin) {
    addPlugin(project, plugin, NEEDLE_PLUGIN_MANAGEMENT);
  }

  private void addPlugin(Project project, Plugin plugin, String needle) {
    project.addDefaultConfig(PRETTIER_DEFAULT_INDENT);

    int indent = (Integer) project.getConfig(PRETTIER_DEFAULT_INDENT).orElse(DEFAULT_INDENTATION);

    int level = NEEDLE_PLUGIN_MANAGEMENT.equals(needle) ? 4 : 3;

    String pluginNode = Maven.getPluginHeader(plugin, indent).indent(level * indent);

    String pluginRegexp;
    if (NEEDLE_PLUGIN_MANAGEMENT.equals(needle)) {
      // Checking for plugin declaration in <pluginManagement> section
      pluginRegexp =
        (
          FileUtils.REGEXP_PREFIX_DOTALL +
          PLUGIN_MANAGEMENT_BEGIN +
          FileUtils.REGEXP_DOT_STAR +
          pluginNode +
          FileUtils.REGEXP_DOT_STAR +
          PLUGIN_MANAGEMENT_END
        );
    } else {
      // Checking for plugin declaration between <plugin> section and needle (not </plugin> as it can conflict with
      // <plugin> section in <pluginManagement>)
      pluginRegexp =
        FileUtils.REGEXP_PREFIX_DOTALL + PLUGIN_BEGIN + FileUtils.REGEXP_DOT_STAR + pluginNode + FileUtils.REGEXP_DOT_STAR + NEEDLE_PLUGIN;
    }

    if (!projectRepository.containsRegexp(project, "", POM_XML, pluginRegexp)) {
      String newPluginNode = Maven.getPlugin(plugin, indent).indent(level * indent);
      String pluginWithNeedle = (newPluginNode + indent(level, indent) + needle);

      projectRepository.replaceText(project, "", POM_XML, REGEXP_SPACE_STAR + needle, pluginWithNeedle);
    }
  }

  @Override
  public void addProperty(Project project, String key, String value) {
    project.addDefaultConfig(PRETTIER_DEFAULT_INDENT);

    int indent = (Integer) project.getConfig(PRETTIER_DEFAULT_INDENT).orElse(DEFAULT_INDENTATION);

    String pluginRegexp = Maven.getProperty(key, ".*");

    if (!projectRepository.containsRegexp(project, "", POM_XML, pluginRegexp)) {
      String propertyWithNeedle = Maven.getProperty(key, value) + LF + indent(2, indent) + NEEDLE_PROPERTIES;

      projectRepository.replaceText(project, "", POM_XML, NEEDLE_PROPERTIES, propertyWithNeedle);
    }
  }

  @Override
  public void deleteProperty(Project project, String key) {
    project.addDefaultConfig(PRETTIER_DEFAULT_INDENT);

    String propertyNode = Maven.getProperty(key, ".*") + LF;

    projectRepository.replaceText(project, "", POM_XML, propertyNode, "");
  }

  @Override
  public void addRepository(Project project, Repository repository) {
    addRepository(project, repository, NEEDLE_REPOSITORY);
  }

  @Override
  public void addPluginRepository(Project project, Repository repository) {
    addRepository(project, repository, NEEDLE_PLUGIN_REPOSITORY);
  }

  private void addRepository(Project project, Repository repository, String needle) {
    project.addDefaultConfig(PRETTIER_DEFAULT_INDENT);

    int indent = (Integer) project.getConfig(PRETTIER_DEFAULT_INDENT).orElse(DEFAULT_INDENTATION);

    int level = 2;

    String repositoryNode;
    if (NEEDLE_PLUGIN_REPOSITORY.equals(needle)) {
      repositoryNode = Maven.getPluginRepository(repository, indent).indent(2 * indent);
    } else {
      repositoryNode = Maven.getRepositoryHeader(repository, indent).indent(2 * indent);
    }
    String repositoryRegexp = (REGEXP_PREFIX_MULTILINE + repositoryNode);

    if (!projectRepository.containsRegexp(project, "", POM_XML, repositoryRegexp)) {
      String newRepositoryNode;
      if (NEEDLE_PLUGIN_REPOSITORY.equals(needle)) {
        newRepositoryNode = Maven.getPluginRepository(repository, indent).indent(level * indent);
      } else {
        newRepositoryNode = Maven.getRepository(repository, indent).indent(level * indent);
      }

      String repositoryWithNeedle = (newRepositoryNode + indent(level, indent) + needle);
      projectRepository.replaceText(project, "", POM_XML, REGEXP_SPACE_STAR + needle, repositoryWithNeedle);
    }
  }

  @Override
  public void initJava(Project project) {
    addJavaPomXml(project);
    addMavenWrapper(project);
  }

  @Override
  public void addJavaPomXml(Project project) {
    project.addDefaultConfig(PACKAGE_NAME);
    project.addDefaultConfig(PROJECT_NAME);
    project.addDefaultConfig(BASE_NAME);

    String baseName = project.getBaseName().orElse("");
    project.addConfig("dasherizedBaseName", WordUtils.kebabCase(baseName));

    projectRepository.template(ProjectFile.forProject(project).withSource(SOURCE, POM_XML).withSameDestination());
  }

  @Override
  public void addMavenWrapper(Project project) {
    projectRepository.add(ProjectFile.forProject(project).withSource(SOURCE, "mvnw").withSameDestination());
    projectRepository.setExecutable(project, "", "mvnw");

    projectRepository.add(ProjectFile.forProject(project).withSource(SOURCE, "mvnw.cmd").withSameDestination());
    projectRepository.setExecutable(project, "", "mvnw.cmd");

    String sourceWrapper = getPath(SOURCE, ".mvn", "wrapper");
    String destinationWrapper = getPath(".mvn", "wrapper");
    projectRepository.add(
      ProjectFile.forProject(project).withSource(sourceWrapper, "maven-wrapper.jar").withDestinationFolder(destinationWrapper)
    );
    projectRepository.add(
      ProjectFile.forProject(project).withSource(sourceWrapper, "maven-wrapper.properties").withDestinationFolder(destinationWrapper)
    );
  }

  @Override
  public Optional<String> getVersion(String name) {
    Assert.notBlank("name", name);

    String propertyTagIni = new StringBuilder().append("<").append(name).append(".version").append(">").toString();
    String propertyTagEnd = new StringBuilder().append("</").append(name).append(".version").append(">").toString();
    Pattern pattern = Pattern.compile(propertyTagIni + "(.*)" + propertyTagEnd);

    return FileUtils
      .readLineInClasspath(getPath(TEMPLATE_FOLDER, DEPENDENCIES_FOLDER, POM_XML), propertyTagIni)
      .map(readValue -> {
        Matcher matcher = pattern.matcher(readValue);
        if (matcher.find()) {
          return matcher.group(1);
        }
        return null;
      });
  }

  @Override
  public Optional<String> getGroupId(String folder) {
    Assert.notBlank("folder", folder);

    return FileUtils.getValueBetween(getPath(folder, POM_XML), GROUP_ID_BEGIN, GROUP_ID_END);
  }

  @Override
  public Optional<String> getName(String folder) {
    Assert.notBlank("folder", folder);

    return FileUtils.getValueBetween(getPath(folder, POM_XML), NAME_BEGIN, NAME_END);
  }
}
