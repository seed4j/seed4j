package tech.jhipster.lite.generator.buildtool.maven.domain;

import static tech.jhipster.lite.common.domain.WordUtils.indent;

import java.util.List;
import tech.jhipster.lite.common.domain.WordUtils;
import tech.jhipster.lite.generator.buildtool.generic.domain.*;

public class Maven {

  public static final String NEEDLE_PARENT = "<!-- jhipster-needle-maven-parent -->";
  public static final String NEEDLE_DEPENDENCY = "<!-- jhipster-needle-maven-add-dependency -->";
  public static final String NEEDLE_DEPENDENCY_TEST = "<!-- jhipster-needle-maven-add-dependency-test -->";
  public static final String NEEDLE_PLUGIN = "<!-- jhipster-needle-maven-add-plugin -->";
  public static final String NEEDLE_PLUGIN_MANAGEMENT = "<!-- jhipster-needle-maven-add-plugin-management -->";
  public static final String NEEDLE_PROPERTIES = "<!-- jhipster-needle-maven-property -->";

  public static final String PARENT_BEGIN = "<parent>";
  public static final String PARENT_END = "</parent>";

  public static final String DEPENDENCY_BEGIN = "<dependency>";
  public static final String DEPENDENCY_END = "</dependency>";

  public static final String GROUP_ID_BEGIN = "<groupId>";
  public static final String GROUP_ID_END = "</groupId>";

  public static final String PLUGIN_BEGIN = "<plugin>";
  public static final String PLUGIN_END = "</plugin>";

  public static final String ARTIFACT_ID_BEGIN = "<artifactId>";
  public static final String ARTIFACT_ID_END = "</artifactId>";

  public static final String VERSION_BEGIN = "<version>";
  public static final String VERSION_END = "</version>";

  public static final String SCOPE_BEGIN = "<scope>";
  public static final String SCOPE_END = "</scope>";

  public static final String EXCLUSIONS_BEGIN = "<exclusions>";
  public static final String EXCLUSIONS_END = "</exclusions>";

  public static final String EXCLUSION_BEGIN = "<exclusion>";
  public static final String EXCLUSION_END = "</exclusion>";

  public static final String EXECUTIONS_BEGIN = "<executions>";
  public static final String EXECUTIONS_END = "</executions>";

  public static final String EXECUTION_BEGIN = "<execution>";
  public static final String EXECUTION_END = "</execution>";

  public static final String GOALS_START = "<goals>";
  public static final String GOALS_END = "</goals>";

  public static final String CONFIGURATION_START = "<configuration>";
  public static final String CONFIGURATION_END = "</configuration>";

  private Maven() {}

  public static String getParent(Parent parent) {
    return getParent(parent, WordUtils.DEFAULT_INDENTATION);
  }

  public static String getParentHeader(Parent parent) {
    return getParentHeader(parent, WordUtils.DEFAULT_INDENTATION);
  }

  public static String getParentHeader(Parent parent, int indentation) {
    StringBuilder result = new StringBuilder()
      .append(PARENT_BEGIN)
      .append(System.lineSeparator())
      .append(indent(2, indentation))
      .append(GROUP_ID_BEGIN)
      .append(parent.getGroupId())
      .append(GROUP_ID_END)
      .append(System.lineSeparator())
      .append(indent(2, indentation))
      .append(ARTIFACT_ID_BEGIN)
      .append(parent.getArtifactId())
      .append(ARTIFACT_ID_END)
      .append(System.lineSeparator());
    return result.toString();
  }

  public static String getParent(Parent parent, int indentation) {
    StringBuilder result = new StringBuilder()
      .append(getParentHeader(parent, indentation))
      .append(indent(2, indentation))
      .append(VERSION_BEGIN)
      .append(parent.getVersion())
      .append(VERSION_END)
      .append(System.lineSeparator())
      .append(indent(2, indentation))
      .append("<relativePath/>")
      .append(System.lineSeparator())
      .append(indent(1, indentation))
      .append(PARENT_END);

    return result.toString();
  }

  public static String getDependency(Dependency dependency) {
    return getDependency(dependency, WordUtils.DEFAULT_INDENTATION, List.of());
  }

  public static String getDependency(Dependency dependency, int indentation) {
    return getDependency(dependency, indentation, List.of());
  }

  public static String getDependencyHeader(Dependency dependency) {
    return getDependencyHeader(dependency, WordUtils.DEFAULT_INDENTATION);
  }

  public static String getDependencyHeader(Dependency dependency, int indentation) {
    StringBuilder result = new StringBuilder()
      .append(DEPENDENCY_BEGIN)
      .append(System.lineSeparator())
      .append(indent(3, indentation))
      .append(GROUP_ID_BEGIN)
      .append(dependency.getGroupId())
      .append(GROUP_ID_END)
      .append(System.lineSeparator())
      .append(indent(3, indentation))
      .append(ARTIFACT_ID_BEGIN)
      .append(dependency.getArtifactId())
      .append(ARTIFACT_ID_END)
      .append(System.lineSeparator());
    return result.toString();
  }

  public static String getDependency(Dependency dependency, int indentation, List<Dependency> exclusions) {
    StringBuilder result = new StringBuilder().append(getDependencyHeader(dependency, indentation));

    dependency
      .getVersion()
      .ifPresent(version ->
        result.append(indent(3, indentation)).append(VERSION_BEGIN).append(version).append(VERSION_END).append(System.lineSeparator())
      );

    dependency
      .getScope()
      .ifPresent(scope ->
        result.append(indent(3, indentation)).append(SCOPE_BEGIN).append(scope).append(SCOPE_END).append(System.lineSeparator())
      );

    if (!exclusions.isEmpty()) {
      result.append(indent(3, indentation)).append(getExclusions(exclusions)).append(System.lineSeparator());
    }

    result.append(indent(2, indentation)).append(DEPENDENCY_END);

    return result.toString();
  }

  public static String getExclusion(Dependency exclusion) {
    return getExclusion(exclusion, WordUtils.DEFAULT_INDENTATION);
  }

  public static String getExclusion(Dependency exclusion, int indentation) {
    StringBuilder result = new StringBuilder()
      .append(EXCLUSION_BEGIN)
      .append(System.lineSeparator())
      .append(indent(5, indentation))
      .append(GROUP_ID_BEGIN)
      .append(exclusion.getGroupId())
      .append(GROUP_ID_END)
      .append(System.lineSeparator())
      .append(indent(5, indentation))
      .append(ARTIFACT_ID_BEGIN)
      .append(exclusion.getArtifactId())
      .append(ARTIFACT_ID_END)
      .append(System.lineSeparator())
      .append(indent(4, indentation))
      .append(EXCLUSION_END);
    return result.toString();
  }

  public static String getExclusions(List<Dependency> exclusions) {
    return getExclusions(exclusions, WordUtils.DEFAULT_INDENTATION);
  }

  public static String getExclusions(List<Dependency> exclusions, int indentation) {
    StringBuilder result = new StringBuilder().append(EXCLUSIONS_BEGIN);

    exclusions.forEach(exclusion -> result.append(System.lineSeparator()).append(indent(4, indentation)).append(getExclusion(exclusion)));
    result.append(System.lineSeparator()).append(indent(3, indentation)).append(EXCLUSIONS_END);
    return result.toString();
  }

  public static String getPlugin(Plugin plugin) {
    return getPlugin(plugin, WordUtils.DEFAULT_INDENTATION);
  }

  public static String getPluginHeader(Plugin plugin) {
    return getPluginHeader(plugin, WordUtils.DEFAULT_INDENTATION);
  }

  public static String getPluginHeader(Plugin plugin, int indentation) {
    StringBuilder result = new StringBuilder()
      .append(PLUGIN_BEGIN)
      .append(System.lineSeparator())
      .append(indent(4, indentation))
      .append(GROUP_ID_BEGIN)
      .append(plugin.getGroupId())
      .append(GROUP_ID_END)
      .append(System.lineSeparator())
      .append(indent(4, indentation))
      .append(ARTIFACT_ID_BEGIN)
      .append(plugin.getArtifactId())
      .append(ARTIFACT_ID_END)
      .append(System.lineSeparator());
    return result.toString();
  }

  public static String getPlugin(Plugin plugin, int indentation) {
    StringBuilder result = new StringBuilder().append(getPluginHeader(plugin, indentation));

    plugin
      .getVersion()
      .ifPresent(version ->
        result.append(indent(4, indentation)).append(VERSION_BEGIN).append(version).append(VERSION_END).append(System.lineSeparator())
      );

    List<PluginExecution> pluginExecutions = plugin.getExecutions();
    if (pluginExecutions != null && !pluginExecutions.isEmpty()) {
      result.append(indent(4, indentation)).append(EXECUTIONS_BEGIN).append(System.lineSeparator());
      pluginExecutions.forEach(execution -> result.append(getExecution(execution, indentation)));
      result.append(indent(4, indentation)).append(EXECUTIONS_END).append(System.lineSeparator());
    }

    List<PluginConfiguration> pluginConfigurations = plugin.getConfigurations();

    if (pluginConfigurations != null && !pluginConfigurations.isEmpty()) {
      result.append(getConfiguration(4, indentation, pluginConfigurations));
    }

    result.append(indent(3, indentation)).append(PLUGIN_END);

    return result.toString();
  }

  public static String getConfiguration(int indentTimes, int indentation, List<PluginConfiguration> pluginConfigurations) {
    StringBuilder result = new StringBuilder();

    result.append(indent(indentTimes, indentation)).append(CONFIGURATION_START).append(System.lineSeparator());
    pluginConfigurations.forEach(configuration -> result.append(getConfigurationElement(indentTimes + 1, indentation, configuration)));
    result.append(indent(indentTimes, indentation)).append(CONFIGURATION_END).append(System.lineSeparator());

    return result.toString();
  }

  public static String getConfigurationElement(int indentTimes, int indentation, PluginConfiguration configuration) {
    if (configuration instanceof PluginConfigurationValue) {
      PluginConfigurationValue configurationValue = (PluginConfigurationValue) configuration;
      return new StringBuilder()
        .append(indent(indentTimes, indentation))
        .append(getValueInXmlTag(configurationValue.getName(), configurationValue.getValue()))
        .append(System.lineSeparator())
        .toString();
    } else if (configuration instanceof PluginConfigurationContainer) {
      PluginConfigurationContainer configurationContainer = (PluginConfigurationContainer) configuration;
      StringBuilder containerValue = new StringBuilder();
      configurationContainer
        .getChildren()
        .forEach(childConfig -> containerValue.append(getConfigurationElement(indentTimes + 1, indentation, childConfig)));
      return new StringBuilder()
        .append(indent(indentTimes, indentation))
        .append(getStartTag(configurationContainer.getName()))
        .append(System.lineSeparator())
        .append(containerValue)
        .append(indent(indentTimes, indentation))
        .append(getEndTag(configurationContainer.getName()))
        .append(System.lineSeparator())
        .toString();
    } else {
      throw new UnsupportedOperationException("PluginConfiguration not implemented");
    }
  }

  public static String getExecution(PluginExecution execution, int indentation) {
    StringBuilder result = new StringBuilder().append(indent(5, indentation)).append(EXECUTION_BEGIN).append(System.lineSeparator());
    if (execution.getId() != null) {
      result.append(indent(6, indentation)).append(getValueInXmlTag("id", execution.getId())).append(System.lineSeparator());
    }
    if (execution.getPhase() != null) {
      result.append(indent(6, indentation)).append(getValueInXmlTag("phase", execution.getPhase())).append(System.lineSeparator());
    }
    if (execution.getGoals() != null && !execution.getGoals().isEmpty()) {
      result.append(indent(6, indentation)).append(GOALS_START).append(System.lineSeparator());
      execution
        .getGoals()
        .forEach(goal -> result.append(indent(7, indentation)).append(getValueInXmlTag("goal", goal)).append(System.lineSeparator()));
      result.append(indent(6, indentation)).append(GOALS_END).append(System.lineSeparator());
    }
    if (execution.getConfigurations() != null && !execution.getConfigurations().isEmpty()) {
      result.append(getConfiguration(6, indentation, execution.getConfigurations()));
    }
    return result.append(indent(5, indentation)).append(EXECUTION_END).append(System.lineSeparator()).toString();
  }

  private static String getStartTag(String tag) {
    return new StringBuilder().append("<").append(tag).append(">").toString();
  }

  private static String getEndTag(String tag) {
    return new StringBuilder().append("</").append(tag).append(">").toString();
  }

  private static String getValueInXmlTag(String tag, String value) {
    return new StringBuilder().append("<").append(tag).append(">").append(value).append("</").append(tag).append(">").toString();
  }

  public static String getProperty(String key, String version) {
    return new StringBuilder()
      .append("<")
      .append(key)
      .append(".version>")
      .append(version)
      .append("</")
      .append(key)
      .append(".version>")
      .toString();
  }
}
