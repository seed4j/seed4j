package com.seed4j.module.infrastructure.secondary.nodejs;

import com.seed4j.module.domain.ProjectFiles;
import com.seed4j.module.domain.nodejs.NodePackage;
import com.seed4j.module.domain.nodejs.NodePackagesVersionSource;
import com.seed4j.module.domain.nodejs.NodePackagesVersionSourceFactory;
import com.seed4j.module.domain.nodejs.NodePackagesVersions;
import com.seed4j.module.domain.nodejs.NodePackagesVersions.NpmPackagesVersionsBuilder;
import com.seed4j.shared.error.domain.Assert;
import java.util.Collection;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class FileSystemNodePackagesVersionReader implements NodePackagesVersionsReader {

  private static final Pattern DEV_DEPENDENCIES_PATTERN = Pattern.compile("\"devDependencies\"\\s*:\\s*\\{([^}]*)}", Pattern.DOTALL);
  private static final Pattern DEPENDENCIES_PATTERN = Pattern.compile("\"dependencies\"\\s*:\\s*\\{([^}]*)}", Pattern.DOTALL);
  private static final Pattern PACKAGES_PATTERN = Pattern.compile("\"([^\"]+)\"\\s*:\\s*\"([^\"]+)\"", Pattern.DOTALL);

  private final ProjectFiles projectFiles;
  private final Collection<NodePackagesVersionSource> nodePackagesVersionSources;
  private final String parentInputFolder;

  public FileSystemNodePackagesVersionReader(
    ProjectFiles projectFiles,
    Collection<NodePackagesVersionSourceFactory> npmVersionSources,
    String parentInputFolder
  ) {
    Assert.notNull("projectFiles", projectFiles);
    Assert.notNull("nodePackagesVersionSources", npmVersionSources);
    Assert.notBlank("parentInputFolder", parentInputFolder);

    this.projectFiles = projectFiles;
    this.nodePackagesVersionSources = npmVersionSources.stream().map(NodePackagesVersionSourceFactory::build).toList();
    this.parentInputFolder = parentInputFolder;
  }

  @Override
  public NodePackagesVersions get() {
    NpmPackagesVersionsBuilder builder = NodePackagesVersions.builder();

    nodePackagesVersionSources.forEach(source -> builder.put(source, sourcePackages(source)));

    return builder.build();
  }

  private Collection<NodePackage> sourcePackages(NodePackagesVersionSource source) {
    String sourceFile = readVersionsFile(source);

    return Stream.concat(packagesIn(sourceFile, DEV_DEPENDENCIES_PATTERN), packagesIn(sourceFile, DEPENDENCIES_PATTERN)).toList();
  }

  private Stream<NodePackage> packagesIn(String source, Pattern pattern) {
    Matcher dependenciesMatcher = pattern.matcher(source);

    if (dependenciesMatcher.find()) {
      return readPackages(dependenciesMatcher.group(1));
    }

    return Stream.of();
  }

  private Stream<NodePackage> readPackages(String content) {
    return PACKAGES_PATTERN.matcher(content)
      .results()
      .map(result -> new NodePackage(result.group(1), result.group(2)));
  }

  private String readVersionsFile(NodePackagesVersionSource source) {
    return projectFiles.readString(parentInputFolder + sourceFolder(source) + "/package.json");
  }

  private String sourceFolder(NodePackagesVersionSource source) {
    return source.name().toLowerCase(Locale.ROOT);
  }
}
