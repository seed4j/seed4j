package tech.jhipster.lite.generator.module.domain;

import java.nio.file.Path;
import tech.jhipster.lite.error.domain.Assert;

public class JHipsterSource {

  private static final String MUSTACHE_EXTENSION = ".mustache";

  private final Path source;

  JHipsterSource(Path source) {
    Assert.notNull("source", source);

    this.source = source;
  }

  public Path get() {
    return source;
  }

  public JHipsterSource template(String file) {
    Assert.notBlank("file", file);

    if (file.endsWith(MUSTACHE_EXTENSION)) {
      return file(file);
    }

    return file(file + MUSTACHE_EXTENSION);
  }

  public JHipsterSource file(String file) {
    return new JHipsterSource(source.resolve(file));
  }
}
