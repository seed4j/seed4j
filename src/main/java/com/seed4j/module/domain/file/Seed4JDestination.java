package com.seed4j.module.domain.file;

import com.seed4j.module.domain.properties.Seed4JProjectFolder;
import com.seed4j.shared.error.domain.Assert;
import com.seed4j.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;
import java.nio.file.Path;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Seed4JDestination {

  public static final Seed4JDestination SRC_MAIN_JAVA = new Seed4JDestination("src/main/java");
  public static final Seed4JDestination SRC_TEST_JAVA = new Seed4JDestination("src/test/java");
  public static final Seed4JDestination SRC_MAIN_DOCKER = new Seed4JDestination("src/main/docker");
  public static final Seed4JDestination SRC_MAIN_RESOURCES = new Seed4JDestination("src/main/resources");
  public static final Seed4JDestination SRC_TEST_RESOURCES = new Seed4JDestination("src/test/resources");

  private static final String MUSTACHE_EXTENSION = ".mustache";

  private final String destination;

  public Seed4JDestination(String destination) {
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

  public Seed4JDestination append(String element) {
    return new Seed4JDestination(destination + "/" + element);
  }

  @SuppressWarnings("NullAway")
  public Path folder(Seed4JProjectFolder project) {
    Assert.notNull("project", project);

    return project.filePath(destination).getParent();
  }

  public Path pathInProject(Seed4JProjectFolder project) {
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

    if (!(obj instanceof Seed4JDestination other)) {
      return false;
    }

    return new EqualsBuilder().append(destination, other.destination).isEquals();
  }

  @Override
  public String toString() {
    return destination;
  }
}
