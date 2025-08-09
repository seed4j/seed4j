package com.seed4j.module.domain.file;

import static org.assertj.core.api.Assertions.assertThat;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.properties.JHipsterProjectFolder;
import java.nio.file.FileSystems;
import org.junit.jupiter.api.Test;

@UnitTest
class SeedDestinationTest {

  private static final String SEPARATOR = FileSystems.getDefault().getSeparator();

  private static final JHipsterProjectFolder PROJECT = new JHipsterProjectFolder(TestFileUtils.tmpDirForTest());

  @Test
  void shouldAddSlashWhenHappeningElementWithoutSlash() {
    assertThat(new SeedDestination("src/main").append("file").pathInProject(PROJECT).toString()).endsWith(path("src", "main", "file"));
  }

  @Test
  void shouldDeduplicateSlashes() {
    assertThat(new SeedDestination("src/main/").append("/file").pathInProject(PROJECT).toString()).endsWith(path("src", "main", "file"));
  }

  @Test
  void testToStringShowsDestination() {
    assertThat(new SeedDestination("src")).hasToString("src");
  }

  private static String path(String... part) {
    return String.join(SEPARATOR, part);
  }
}
