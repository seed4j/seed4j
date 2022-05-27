package tech.jhipster.lite.generator.module.domain;

import java.nio.file.Path;
import tech.jhipster.lite.error.domain.Assert;

public class JHipsterDestination {

  public static final JHipsterDestination SRC_MAIN_JAVA = new JHipsterDestination("src/main/java");
  public static final JHipsterDestination SRC_TEST_JAVA = new JHipsterDestination("src/test/java");

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
}
