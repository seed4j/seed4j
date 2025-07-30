package com.seed4j.module.infrastructure.secondary;

import static com.seed4j.module.domain.JHipsterModule.*;
import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterFileMatcher;
import com.seed4j.module.domain.JHipsterProjectFilePath;
import com.seed4j.module.domain.properties.JHipsterProjectFolder;
import com.seed4j.shared.error.domain.GeneratorException;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

@UnitTest
class FileSystemGeneratedProjectRepositoryTest {

  private static final String SOURCE = "src/test/resources/projects/files";
  private final FileSystemGeneratedProjectRepository generatedProject = new FileSystemGeneratedProjectRepository();

  @Test
  void shouldGracefullyHandleException() {
    assertThatThrownBy(() -> generatedProject.list(new JHipsterProjectFolder("unknown"), allMatch())).isExactlyInstanceOf(
      GeneratorException.class
    );
  }

  @Test
  void shouldListFiles() {
    Stream<String> files = generatedProject
      .list(new JHipsterProjectFolder(SOURCE), filesWithExtension("java"))
      .stream()
      .map(JHipsterProjectFilePath::get);

    assertThat(files).containsExactlyInAnyOrder("IntegrationTest.java", "MainApp.java");
  }

  private JHipsterFileMatcher allMatch() {
    return path -> true;
  }
}
