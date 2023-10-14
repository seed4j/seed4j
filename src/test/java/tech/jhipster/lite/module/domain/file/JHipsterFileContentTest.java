package tech.jhipster.lite.module.domain.file;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static tech.jhipster.lite.module.domain.JHipsterModulesFixture.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.ProjectFiles;

@UnitTest
@ExtendWith(MockitoExtension.class)
class JHipsterFileContentTest {

  @Mock
  private ProjectFiles files;

  @BeforeEach
  void loadFiles() {
    lenient().when(files.readBytes(anyString())).thenAnswer(invocation -> Files.readAllBytes(testFilePath(invocation)));
    lenient().when(files.readString(anyString())).thenAnswer(invocation -> Files.readString(testFilePath(invocation)));
  }

  private Path testFilePath(InvocationOnMock invocation) {
    return Paths.get("src/test/resources/" + invocation.getArgument(0, String.class));
  }

  @Test
  void shouldReadNotTemplatedContent() {
    JHipsterFileContent content = content("/generator/content/no-template.txt");

    assertThat(content.read(files, context())).isEqualTo("This is my content".getBytes(StandardCharsets.UTF_8));
  }

  @Test
  void shouldReadTemplatedContent() {
    JHipsterFileContent content = content("/generator/content/template.txt.mustache");

    assertThat(content.read(files, context())).isEqualTo("This is com.test.myapp".getBytes(StandardCharsets.UTF_8));
  }

  @Test
  void shouldGetRawContentForNotTemplatedFile() throws IOException {
    JHipsterFileContent content = content("/generator/client/vue/webapp/content/images/JHipster-Lite-neon-green.png");

    assertThat(content.read(files, context()))
      .isEqualTo(
        Files.readAllBytes(Paths.get("src/main/resources/generator/client/vue/webapp/content/images/JHipster-Lite-neon-green.png"))
      );
  }

  @Test
  void testToStringShowsPath() {
    //Given
    final JHipsterFileContent content = content("path");
    //When Then
    assertThat(content.toString()).isEqualTo("path");
  }

  private static JHipsterFileContent content(String path) {
    return new JHipsterFileContent(new JHipsterSource(Paths.get(path)));
  }
}
