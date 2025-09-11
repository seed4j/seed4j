package com.seed4j.shared.projectfolder.domain;

import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import java.io.File;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@UnitTest
class ForcedProjectFolderTest {

  private static final String ENDING_BY_UUID_REGEX = ".*\\w{8}(-\\w{4}){3}-\\w{12}$";

  private final ForcedProjectFolder forcedProjectFolder = new ForcedProjectFolder("/tmp/seed4j");

  @Test
  void shouldBeInvalidWithNotMatchingPrefix() {
    assertThat(forcedProjectFolder.isInvalid("/other/folder")).isTrue();
  }

  @Test
  void shouldBeInvalidWithDotDot() {
    assertThat(forcedProjectFolder.isInvalid("/tmp/seed4j/../project")).isTrue();
  }

  @ParameterizedTest
  @ValueSource(strings = { "/tmp/seed4j", "/tmp/seed4j/" })
  void shouldBeInvalidWithRootFolder(String rootDir) {
    assertThat(forcedProjectFolder.isInvalid(rootDir)).isTrue();
  }

  @ParameterizedTest
  @ValueSource(
    strings = {
      "/tmp/seed4j/my-user/21018cf8-9a43-4935-9617-6ea7eab55356", "/tmp/seed4j/sub/folder/directory", "/tmp/seed4j/sub/../directory",
    }
  )
  void shouldBeInvalidWithNestedDirectories(String rootDir) {
    assertThat(forcedProjectFolder.isInvalid(rootDir)).isTrue();
  }

  @Test
  void shouldBeValid() {
    assertThat(forcedProjectFolder.isInvalid("/tmp/seed4j/project")).isFalse();
  }

  @Test
  void shouldGetValidPath() {
    assertThat(forcedProjectFolder.generatePath()).startsWith(Path.of("/tmp/seed4j") + File.separator).matches(ENDING_BY_UUID_REGEX);
  }
}
