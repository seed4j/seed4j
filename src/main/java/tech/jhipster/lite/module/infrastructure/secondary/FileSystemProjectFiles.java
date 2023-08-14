package tech.jhipster.lite.module.infrastructure.secondary;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import tech.jhipster.lite.module.domain.ProjectFiles;
import tech.jhipster.lite.shared.error.domain.Assert;
import tech.jhipster.lite.shared.error.domain.GeneratorException;
import tech.jhipster.lite.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;

@Service
class FileSystemProjectFiles implements ProjectFiles {

  private static final String SLASH = "/";

  @Override
  @ExcludeFromGeneratedCodeCoverage(reason = "The error handling is an hard to test implementation detail")
  public String readString(String path) {
    Assert.notBlank("path", path);

    try (InputStream input = getInputStream(path)) {
      assertFileExist(path, input);

      return toString(input);
    } catch (IOException e) {
      throw GeneratorException.technicalError("Error closing " + path + ": " + e.getMessage(), e);
    }
  }

  @ExcludeFromGeneratedCodeCoverage(reason = "The error handling is an hard to test implementation detail")
  private static String toString(InputStream input) {
    try {
      return IOUtils.toString(input, StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw GeneratorException.technicalError("Error reading file: " + e.getMessage(), e);
    }
  }

  @Override
  @ExcludeFromGeneratedCodeCoverage(reason = "The error handling is an hard to test implementation detail")
  public byte[] readBytes(String path) {
    Assert.notBlank("path", path);

    try (InputStream input = getInputStream(path)) {
      assertFileExist(path, input);

      return toByteArray(input);
    } catch (IOException e) {
      throw GeneratorException.technicalError("Error closing " + path + ": " + e.getMessage(), e);
    }
  }

  private void assertFileExist(String path, InputStream input) {
    if (input == null) {
      throw GeneratorException.technicalError("Can't find file: " + path);
    }
  }

  private InputStream getInputStream(String path) {
    return FileSystemProjectFiles.class.getResourceAsStream(path.replace("\\", SLASH));
  }

  @ExcludeFromGeneratedCodeCoverage(reason = "The error handling is an hard to test implementation detail")
  private static byte[] toByteArray(InputStream input) {
    try {
      return IOUtils.toByteArray(input);
    } catch (IOException e) {
      throw GeneratorException.technicalError("Error reading file: " + e.getMessage(), e);
    }
  }
}
