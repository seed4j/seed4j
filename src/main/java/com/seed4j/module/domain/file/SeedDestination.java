package com.seed4j.module.domain.file;

import com.seed4j.module.domain.properties.JHipsterProjectFolder;
import com.seed4j.shared.error.domain.Assert;
import com.seed4j.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;
import java.nio.file.Path;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class SeedDestination {

  public static final SeedDestination SRC_MAIN_JAVA = new SeedDestination("src/main/java");
  public static final SeedDestination SRC_TEST_JAVA = new SeedDestination("src/test/java");
  public static final SeedDestination SRC_MAIN_DOCKER = new SeedDestination("src/main/docker");
  public static final SeedDestination SRC_MAIN_RESOURCES = new SeedDestination("src/main/resources");
  public static final SeedDestination SRC_TEST_RESOURCES = new SeedDestination("src/test/resources");

  private static final String MUSTACHE_EXTENSION = ".mustache";

  private final String destination;

  public SeedDestination(String destination) {
    this.destination = buildDestination(destination);
  }

  public String get() {
    return destination;
  }

  private static String buildDestination(String destination) {
    Assert.notBlank("destination", destination);

    if (destination.endsWith(MUSTACHE_EXTENSION)) {
      return destination.substring(0, destination.length() - MUSTACHE_EXTENSION.length());
    }

    return destination;
  }

  public SeedDestination append(String element) {
    return new SeedDestination(destination + "/" + element);
  }

  public Path folder(JHipsterProjectFolder project) {
    Assert.notNull("project", project);

    return project.filePath(destination).getParent();
  }

  public Path pathInProject(JHipsterProjectFolder project) {
    Assert.notNull("project", project);

    return project.filePath(destination);
  }

  @Override
  @ExcludeFromGeneratedCodeCoverage
  public int hashCode() {
    return new HashCodeBuilder().append(destination).toHashCode();
  }

  @Override
  @ExcludeFromGeneratedCodeCoverage
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (!(obj instanceof SeedDestination other)) {
      return false;
    }

    return new EqualsBuilder().append(destination, other.destination).isEquals();
  }

  @Override
  public String toString() {
    return destination;
  }
}
