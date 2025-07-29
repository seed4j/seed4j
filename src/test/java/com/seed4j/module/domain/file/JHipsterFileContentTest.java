package com.seed4j.module.domain.file;

import static com.seed4j.module.domain.JHipsterModulesFixture.*;
import static java.nio.charset.StandardCharsets.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.seed4j.UnitTest;
import com.seed4j.module.domain.ProjectFiles;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

@UnitTest
@ExtendWith(MockitoExtension.class)
class JHipsterFileContentTest {

  @Mock
  private ProjectFiles files;

  private final TemplateRenderer templateRenderer = (message, context) ->
    message.replace("{{ packageName }}", (CharSequence) context.get("packageName"));

  @BeforeEach
  void loadFiles() {
    lenient()
      .when(files.readBytes(anyString()))
      .thenAnswer(invocation -> Files.readAllBytes(testFilePath(invocation)));
    lenient()
      .when(files.readString(anyString()))
      .thenAnswer(invocation -> Files.readString(testFilePath(invocation)));
  }

  private Path testFilePath(InvocationOnMock invocation) {
    return Path.of("src/test/resources/" + invocation.getArgument(0, String.class));
  }

  @Test
  void shouldReadNotTemplatedContent() {
    JHipsterFileContent content = content("/generator/content/no-template.txt");

    assertThat(content.read(files, context(), templateRenderer)).asString(UTF_8).isEqualTo("This is my content");
  }

  @Test
  void shouldReadTemplatedContent() {
    JHipsterFileContent content = content("/generator/content/template.txt.mustache");

    assertThat(content.read(files, context(), templateRenderer)).asString(UTF_8).isEqualTo("This is com.test.myapp");
  }

  @Test
  void shouldGetRawContentForNotTemplatedFile() throws IOException {
    JHipsterFileContent content = content("/generator/client/vue/webapp/content/images/JHipster-Lite-neon-green.png");

    assertThat(content.read(files, context(), templateRenderer)).isEqualTo(
      Files.readAllBytes(Path.of("src/main/resources/generator/client/vue/webapp/content/images/JHipster-Lite-neon-green.png"))
    );
  }

  @Test
  void testToStringShowsPath() {
    JHipsterFileContent content = content("path");
    assertThat(content).hasToString("path");
  }

  private static JHipsterFileContent content(String path) {
    return new JHipsterFileContent(new JHipsterSource(Path.of(path)));
  }
}
