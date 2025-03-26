package tech.jhipster.lite.module.infrastructure.secondary;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterFileMatcher;
import tech.jhipster.lite.module.domain.JHipsterProjectFilePath;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;
import tech.jhipster.lite.shared.error.domain.GeneratorException;

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
