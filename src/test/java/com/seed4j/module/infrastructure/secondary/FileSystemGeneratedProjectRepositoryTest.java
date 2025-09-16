package com.seed4j.module.infrastructure.secondary;

import static com.seed4j.module.domain.Seed4JModule.*;
import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JFileMatcher;
import com.seed4j.module.domain.Seed4JProjectFilePath;
import com.seed4j.module.domain.properties.Seed4JProjectFolder;
import com.seed4j.shared.error.domain.GeneratorException;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

@UnitTest
class FileSystemGeneratedProjectRepositoryTest {

  private static final String SOURCE = "src/test/resources/projects/files";
  private final FileSystemGeneratedProjectRepository generatedProject = new FileSystemGeneratedProjectRepository();

  @Test
  void shouldGracefullyHandleException() {
    assertThatThrownBy(() -> generatedProject.list(new Seed4JProjectFolder("unknown"), allMatch())).isExactlyInstanceOf(
      GeneratorException.class
    );
  }

  @Test
  void shouldListFiles() {
    Stream<String> files = generatedProject
      .list(new Seed4JProjectFolder(SOURCE), filesWithExtension("java"))
      .stream()
      .map(Seed4JProjectFilePath::get);

    assertThat(files).containsExactlyInAnyOrder("IntegrationTest.java", "MainApp.java");
  }

  private Seed4JFileMatcher allMatch() {
    return path -> true;
  }
}
