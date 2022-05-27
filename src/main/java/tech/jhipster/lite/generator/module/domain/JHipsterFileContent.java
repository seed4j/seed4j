package tech.jhipster.lite.generator.module.domain;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import org.apache.commons.io.IOUtils;
import tech.jhipster.lite.common.domain.Generated;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.error.domain.GeneratorException;

public class JHipsterFileContent {

  private static final String SLASH = "/";

  private final Path source;

  JHipsterFileContent(Path source) {
    Assert.notNull("source", source);

    this.source = source;
  }

  public String read(JHipsterModuleContext context) {
    Assert.notNull("context", context);

    String rawContent = readFileContent();
    return ArgumentsReplacer.replaceArguments(rawContent, context.get());
  }

  private String readFileContent() {
    InputStream input = getClass().getResourceAsStream(source.toString().replace("\\", SLASH));

    if (input == null) {
      throw new GeneratorException("Can't find file: " + source);
    }

    return readInput(input);
  }

  @Generated
  private String readInput(InputStream input) {
    try {
      return IOUtils.toString(input, StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new GeneratorException("Error reading file: " + e.getMessage(), e);
    }
  }
}
