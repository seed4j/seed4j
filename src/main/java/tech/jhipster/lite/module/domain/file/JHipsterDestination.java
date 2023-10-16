package tech.jhipster.lite.module.domain.file;

import java.nio.file.Path;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;
import tech.jhipster.lite.shared.error.domain.Assert;
import tech.jhipster.lite.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;

public class JHipsterDestination {

  public static final JHipsterDestination SRC_MAIN_JAVA = new JHipsterDestination("src/main/java");
  public static final JHipsterDestination SRC_TEST_JAVA = new JHipsterDestination("src/test/java");
  public static final JHipsterDestination SRC_MAIN_DOCKER = new JHipsterDestination("src/main/docker");
  public static final JHipsterDestination SRC_MAIN_RESOURCES = new JHipsterDestination("src/main/resources");

  private static final String MUSTACHE_EXTENSION = ".mustache";

  private final String destination;

  public JHipsterDestination(String destination) {
    this.destination = buildDestination(destination);
  }

  private static String buildDestination(String destination) {
    Assert.notBlank("destination", destination);

    if (destination.endsWith(MUSTACHE_EXTENSION)) {
      return destination.substring(0, destination.length() - MUSTACHE_EXTENSION.length());
    }

    return destination;
  }

  public JHipsterDestination append(String element) {
    return new JHipsterDestination(destination + "/" + element);
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
    return new HashCodeBuilder().append(destination).hashCode();
  }

  @Override
  @ExcludeFromGeneratedCodeCoverage
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    JHipsterDestination other = (JHipsterDestination) obj;
    return new EqualsBuilder().append(destination, other.destination).isEquals();
  }

  @Override
  public String toString() {
    return destination;
  }
}
