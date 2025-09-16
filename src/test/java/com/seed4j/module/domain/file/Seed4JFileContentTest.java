package com.seed4j.module.domain.file;

import static com.seed4j.module.domain.Seed4JModulesFixture.*;
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
class Seed4JFileContentTest {

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
    Seed4JFileContent content = content("/generator/content/no-template.txt");

    assertThat(content.read(files, context(), templateRenderer)).asString(UTF_8).isEqualTo("This is my content");
  }

  @Test
  void shouldReadTemplatedContent() {
    Seed4JFileContent content = content("/generator/content/template.txt.mustache");

    assertThat(content.read(files, context(), templateRenderer)).asString(UTF_8).isEqualTo("This is com.test.myapp");
  }

  @Test
  void shouldGetRawContentForNotTemplatedFile() throws IOException {
    Seed4JFileContent content = content("/generator/client/vue/webapp/content/images/seed4j_logo-name.png");

    assertThat(content.read(files, context(), templateRenderer)).isEqualTo(
      Files.readAllBytes(Path.of("src/main/resources/generator/client/vue/webapp/content/images/seed4j_logo-name.png"))
    );
  }

  @Test
  void testToStringShowsPath() {
    Seed4JFileContent content = content("path");
    assertThat(content).hasToString("path");
  }

  private static Seed4JFileContent content(String path) {
    return new Seed4JFileContent(new Seed4JSource(Path.of(path)));
  }
}
