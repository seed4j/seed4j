package com.seed4j.module.domain.file;

import com.seed4j.shared.error.domain.Assert;
import java.nio.file.Path;
import org.apache.commons.io.FilenameUtils;

public class Seed4JSource {

  private static final String MUSTACHE_EXTENSION = ".mustache";

  private final Path source;

  public Seed4JSource(Path source) {
    Assert.notNull("source", source);

    this.source = source;
  }

  public Path get() {
    return source;
  }

  public Seed4JSource template(String file) {
    Assert.notBlank("file", file);

    if (isTemplate(file)) {
      return file(file);
    }

    return file(file + MUSTACHE_EXTENSION);
  }

  public Seed4JSource append(String element) {
    return file(element);
  }

  public Seed4JSource file(String file) {
    return new Seed4JSource(source.resolve(file));
  }

  public String extension() {
    String filename = source.getFileName().toString();

    if (isTemplate(filename)) {
      return findExtension(filename.substring(0, filename.length() - MUSTACHE_EXTENSION.length()));
    }

    return findExtension(filename);
  }

  private String findExtension(String filename) {
    return "." + FilenameUtils.getExtension(filename);
  }

  public boolean isNotTemplate() {
    return !isTemplate(source.getFileName().toString());
  }

  private boolean isTemplate(String filename) {
    return filename.endsWith(MUSTACHE_EXTENSION);
  }

  @Override
  public String toString() {
    return String.valueOf(source);
  }
}
