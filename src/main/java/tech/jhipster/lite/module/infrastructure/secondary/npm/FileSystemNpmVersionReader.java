package tech.jhipster.lite.module.infrastructure.secondary.npm;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import tech.jhipster.lite.module.domain.ProjectFiles;
import tech.jhipster.lite.module.domain.npm.*;
import tech.jhipster.lite.module.domain.npm.NpmPackagesVersions.NpmPackagesVersionsBuilder;
import tech.jhipster.lite.shared.error.domain.Assert;

public class FileSystemNpmVersionReader implements NpmVersionsReader {

  private static final Pattern DEV_DEPENDENCIES_PATTERN = Pattern.compile("\"devDependencies\"\\s*:\\s*\\{([^}]*)}", Pattern.DOTALL);
  private static final Pattern DEPENDENCIES_PATTERN = Pattern.compile("\"dependencies\"\\s*:\\s*\\{([^}]*)}", Pattern.DOTALL);
  private static final Pattern PACKAGES_PATTERN = Pattern.compile("\"([^\"]+)\"\\s*:\\s*\"([^\"]+)\"", Pattern.DOTALL);

  private final ProjectFiles projectFiles;
  private final Collection<NpmVersionSource> npmVersionSources;
  private final String parentInputFolder;

  public FileSystemNpmVersionReader(
    ProjectFiles projectFiles,
    Collection<NpmVersionSourceFactory> npmVersionSources,
    String parentInputFolder
  ) {
    Assert.notNull("projectFiles", projectFiles);
    Assert.notNull("npmVersionSources", npmVersionSources);
    Assert.notBlank("parentInputFolder", parentInputFolder);

    this.projectFiles = projectFiles;
    this.npmVersionSources = npmVersionSources.stream().map(NpmVersionSourceFactory::build).toList();
    this.parentInputFolder = parentInputFolder;
  }

  @Override
  public NpmPackagesVersions get() {
    NpmPackagesVersionsBuilder builder = NpmPackagesVersions.builder();

    npmVersionSources.forEach(source -> builder.put(source, sourcePackages(source)));

    return builder.build();
  }

  private Collection<NpmPackage> sourcePackages(NpmVersionSource source) {
    String sourceFile = readVersionsFile(source);

    return Stream.concat(packagesIn(sourceFile, DEV_DEPENDENCIES_PATTERN), packagesIn(sourceFile, DEPENDENCIES_PATTERN)).toList();
  }

  private Stream<NpmPackage> packagesIn(String source, Pattern pattern) {
    Matcher dependenciesMatcher = pattern.matcher(source);

    if (dependenciesMatcher.find()) {
      return readPackages(dependenciesMatcher.group(1));
    }

    return Stream.of();
  }

  private Stream<NpmPackage> readPackages(String content) {
    return PACKAGES_PATTERN.matcher(content).results().map(result -> new NpmPackage(result.group(1), result.group(2)));
  }

  private String readVersionsFile(NpmVersionSource source) {
    return projectFiles.readString(parentInputFolder + sourceFolder(source) + "/package.json");
  }

  private String sourceFolder(NpmVersionSource source) {
    return source.name().toLowerCase();
  }
}
