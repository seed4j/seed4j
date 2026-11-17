package com.seed4j.module.infrastructure.secondary.javadependency.gradle;

import com.electronwill.nightconfig.core.Config;
import com.electronwill.nightconfig.core.Config.Entry;
import com.electronwill.nightconfig.core.UnmodifiableConfig;
import com.electronwill.nightconfig.core.file.FileConfig;
import com.electronwill.nightconfig.core.io.NewlineStyle;
import com.electronwill.nightconfig.core.io.ParsingException;
import com.electronwill.nightconfig.toml.TomlWriter;
import com.seed4j.module.domain.gradleplugin.GradleCommunityPlugin;
import com.seed4j.module.domain.gradleplugin.GradlePluginSlug;
import com.seed4j.module.domain.javabuild.DependencySlug;
import com.seed4j.module.domain.javabuild.VersionSlug;
import com.seed4j.module.domain.javadependency.DependencyId;
import com.seed4j.module.domain.javadependency.JavaAnnotationProcessor;
import com.seed4j.module.domain.javadependency.JavaDependency;
import com.seed4j.module.domain.javadependency.JavaDependencyVersion;
import com.seed4j.module.domain.properties.Seed4JProjectFolder;
import com.seed4j.shared.error.domain.GeneratorException;
import com.seed4j.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

public class VersionsCatalog {

  private static final String VERSIONS_TOML_KEY = "versions";
  private static final String LIBRARIES_TOML_KEY = "libraries";
  private static final String PLUGINS_TOML_KEY = "plugins";
  private static final String VERSION_REF = "version.ref";
  private static final String VERSION_ATTRIBUTE = "version";
  private static final String REF_ATTRIBUTE = "ref";
  private static final String MODULE_ATTRIBUTE = "module";
  private static final String GROUP_ATTRIBUTE = "group";
  private static final String NAME_ATTRIBUTE = "name";
  private static final String ID_ATTRIBUTE = "id";
  private static final String MODULE_SEPARATOR = ":";
  private static final Pattern INLINE_VERSION_REFERENCE = Pattern.compile("version = \\{ref = (\"[^\"]*\")}");

  private final FileConfig tomlConfigFile;

  public VersionsCatalog(Seed4JProjectFolder projectFolder) {
    this(tomlVersionCatalogPath(projectFolder));
  }

  public VersionsCatalog(Path tomlVersionCatalogFile) {
    tomlConfigFile = FileConfig.builder(tomlVersionCatalogFile).sync().preserveInsertionOrder().build();

    // Missing TOML file will be automatically created, but its parent folder should exist
    if (!Files.exists(tomlVersionCatalogFile.getParent())) {
      tomlVersionCatalogFile.toFile().getParentFile().mkdirs();
    }

    try {
      tomlConfigFile.load();
    } catch (ParsingException exception) {
      throw new InvalidTomlVersionCatalogException(exception);
    }
  }

  private static Path tomlVersionCatalogPath(Seed4JProjectFolder projectFolder) {
    return projectFolder.filePath("gradle").resolve("libs.versions.toml");
  }

  private void save() {
    TomlWriter writer = new TomlWriter();
    writer.setIndent("");
    writer.setNewline(NewlineStyle.UNIX);
    writer.setWriteTableInlinePredicate(table -> !isTopLevelSection(table));
    // night-config writes nested tables as version = {ref = "x"}: the equivalent dotted key is the usual Gradle notation
    String content = INLINE_VERSION_REFERENCE.matcher(writer.writeToString(tomlConfigFile)).replaceAll("version.ref = $1");
    write(TomlInlineTables.withBraceSpaces(content));
  }

  @ExcludeFromGeneratedCodeCoverage(reason = "The error handling is an hard to test implementation detail")
  private void write(String content) {
    try {
      Files.writeString(tomlConfigFile.getNioPath(), content);
    } catch (IOException exception) {
      throw GeneratorException.technicalError(
        "Error writing %s: %s".formatted(tomlConfigFile.getNioPath(), exception.getMessage()),
        exception
      );
    }
  }

  private boolean isTopLevelSection(UnmodifiableConfig table) {
    return tomlConfigFile
      .valueMap()
      .values()
      .stream()
      .anyMatch(section -> section == table);
  }

  private void resetEntry(String section, String alias) {
    Optional.ofNullable(tomlConfigFile.<Config>get(List.of(section, alias))).ifPresent(Config::clear);
  }

  private void setLibrary(String alias, DependencyId id, Optional<VersionSlug> version) {
    resetEntry(LIBRARIES_TOML_KEY, alias);
    tomlConfigFile.set(List.of(LIBRARIES_TOML_KEY, alias, MODULE_ATTRIBUTE), id.groupId().get() + MODULE_SEPARATOR + id.artifactId().get());
    version.ifPresent(versionSlug -> setVersionReference(LIBRARIES_TOML_KEY, alias, versionSlug));
  }

  private void setVersionReference(String section, String alias, VersionSlug versionSlug) {
    tomlConfigFile.set(List.of(section, alias, VERSION_ATTRIBUTE, REF_ATTRIBUTE), versionSlug.slug());
  }

  public static String libraryAlias(JavaDependency dependency) {
    return dependency
      .slug()
      .map(DependencySlug::slug)
      .or(() -> Optional.of(dependency.id().artifactId().get()))
      .map(StringUtils::uncapitalize)
      .orElseThrow();
  }

  public void setVersion(JavaDependencyVersion javaDependencyVersion) {
    tomlConfigFile.set(List.of(VERSIONS_TOML_KEY, javaDependencyVersion.slug().slug()), javaDependencyVersion.version().get());
    save();
  }

  public void addLibrary(JavaDependency dependency) {
    setLibrary(libraryAlias(dependency), dependency.id(), dependency.version());
    save();
  }

  public String libraryAlias(JavaAnnotationProcessor annotationProcessor) {
    return existingLibraryAlias(annotationProcessor.id()).orElseGet(() ->
      StringUtils.uncapitalize(annotationProcessor.id().artifactId().get())
    );
  }

  private Optional<String> existingLibraryAlias(DependencyId dependency) {
    return libraryEntriesMatchingDependency(dependency).stream().map(Entry::getKey).findFirst();
  }

  public void addLibrary(JavaAnnotationProcessor annotationProcessor) {
    Optional<String> existingAlias = existingLibraryAlias(annotationProcessor.id());
    if (existingAlias.isPresent()) {
      annotationProcessor
        .version()
        .ifPresent(versionSlug -> setVersionReference(LIBRARIES_TOML_KEY, existingAlias.orElseThrow(), versionSlug));
    } else {
      setLibrary(libraryAlias(annotationProcessor), annotationProcessor.id(), annotationProcessor.version());
    }
    save();
  }

  public void removeLibrary(DependencySlug dependencySlug) {
    Optional.ofNullable(tomlConfigFile.<Config>get(List.of(LIBRARIES_TOML_KEY, dependencySlug.slug()))).ifPresent(libraryConfig -> {
      tomlConfigFile.remove(List.of(LIBRARIES_TOML_KEY, dependencySlug.slug()));
      VersionSlug.of(libraryConfig.get(VERSION_REF)).ifPresent(versionSlug -> {
        if (versionUnused(tomlConfigFile, versionSlug)) {
          removeVersion(versionSlug);
        }
      });
    });
    save();
  }

  private List<? extends Entry> libraryEntriesMatchingDependency(DependencyId dependency) {
    return tomlConfigFile
      .entrySet()
      .stream()
      .filter(entry -> entry.getKey().equals(LIBRARIES_TOML_KEY))
      .map(Entry::getValue)
      .filter(Config.class::isInstance)
      .map(Config.class::cast)
      .map(Config::entrySet)
      .flatMap(Collection::stream)
      .filter(groupShouldMatch(dependency))
      .filter(nameShouldMatch(dependency))
      .toList();
  }

  private static String versionReference(Entry libraryConfig) {
    return ((Config) libraryConfig.getValue()).get(VERSION_REF);
  }

  private static boolean versionUnused(FileConfig tomlConfigFile, VersionSlug versionSlug) {
    return tomlConfigFile
      .entrySet()
      .stream()
      .filter(entry -> entry.getKey().equals(LIBRARIES_TOML_KEY))
      .map(Entry::getValue)
      .filter(Config.class::isInstance)
      .map(Config.class::cast)
      .map(Config::entrySet)
      .flatMap(Collection::stream)
      .noneMatch(versionShouldMatch(versionSlug));
  }

  private static Predicate<Entry> versionShouldMatch(VersionSlug versionSlug) {
    return libraryConfig -> {
      return versionSlug.slug().equals(versionReference(libraryConfig));
    };
  }

  private void removeVersion(VersionSlug versionSlug) {
    tomlConfigFile.remove(List.of(VERSIONS_TOML_KEY, versionSlug.slug()));
    save();
  }

  private static Predicate<Entry> groupShouldMatch(DependencyId dependency) {
    return libraryConfig ->
      dependency
        .groupId()
        .get()
        .equals(moduleCoordinate((Config) libraryConfig.getValue(), GROUP_ATTRIBUTE, 0));
  }

  private static Predicate<Entry> nameShouldMatch(DependencyId dependency) {
    return libraryConfig ->
      dependency
        .artifactId()
        .get()
        .equals(moduleCoordinate((Config) libraryConfig.getValue(), NAME_ATTRIBUTE, 1));
  }

  // Libraries are written as module = "group:name", but projects generated before declare group and name separately
  private static Object moduleCoordinate(Config libraryConfig, String attribute, int moduleIndex) {
    return Optional.ofNullable(libraryConfig.<String>get(MODULE_ATTRIBUTE))
      .<Object>map(module -> module.split(MODULE_SEPARATOR)[moduleIndex])
      .orElseGet(() -> libraryConfig.get(attribute));
  }

  public Collection<DependencySlug> retrieveDependencySlugsFrom(DependencyId dependency) {
    return libraryEntriesMatchingDependency(dependency).stream().map(UnmodifiableConfig.Entry::getKey).map(DependencySlug::new).toList();
  }

  public Collection<JavaDependencyVersion> retrieveVersions() {
    return tomlConfigFile
      .entrySet()
      .stream()
      .filter(entry -> entry.getKey().equals(VERSIONS_TOML_KEY))
      .map(Entry::getValue)
      .filter(Config.class::isInstance)
      .map(Config.class::cast)
      .map(Config::entrySet)
      .flatMap(Collection::stream)
      .map(entry -> new JavaDependencyVersion(entry.getKey(), entry.getValue()))
      .toList();
  }

  public static String pluginAlias(GradleCommunityPlugin communityPlugin) {
    return communityPlugin.pluginSlug().map(GradlePluginSlug::get).orElse(communityPlugin.id().get());
  }

  public void addPlugin(GradleCommunityPlugin plugin) {
    String pluginAlias = pluginAlias(plugin);
    resetEntry(PLUGINS_TOML_KEY, pluginAlias);
    tomlConfigFile.set(List.of(PLUGINS_TOML_KEY, pluginAlias, ID_ATTRIBUTE), plugin.id().get());
    plugin.versionSlug().ifPresent(versionSlug -> setVersionReference(PLUGINS_TOML_KEY, pluginAlias, versionSlug));
    save();
  }
}
