package com.seed4j.module.domain.file;

import static com.seed4j.module.domain.Seed4JEngineFixture.*;
import static java.nio.charset.StandardCharsets.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.seed4j.UnitTest;
import com.seed4j.module.domain.ProjectFiles;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
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

  private final TemplateRenderer allKeysRenderer = (message, context) -> {
    String rendered = message;
    for (Map.Entry<String, ?> entry : context.entrySet()) {
      rendered = rendered.replace("{{ " + entry.getKey() + " }}", String.valueOf(entry.getValue()));
    }
    return rendered;
  };

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

    assertThat(content.read(files, context(), templateRenderer))
      .asString(UTF_8)
      .isEqualTo("This is my content");
  }

  @Test
  void shouldReadTemplatedContent() {
    Seed4JFileContent content = content("/generator/content/template.txt.mustache");

    assertThat(content.read(files, context(), templateRenderer))
      .asString(UTF_8)
      .isEqualTo("This is com.test.myapp");
  }

  @Test
  void shouldReadTemplatedContentWithFileContext() {
    Seed4JFileContent content = new Seed4JFileContent(
      new Seed4JSource(Path.of("/generator/content/file-context.txt.mustache")),
      Map.of("javaPackageName", "com.test.myapp.shared")
    );

    assertThat(content.read(files, context(), allKeysRenderer))
      .asString(UTF_8)
      .isEqualTo("com.test.myapp / com.test.myapp.shared");
  }

  @Test
  void shouldOverrideModuleContextWithFileContext() {
    Seed4JFileContent content = new Seed4JFileContent(
      new Seed4JSource(Path.of("/generator/content/file-context.txt.mustache")),
      Map.of("packageName", "overridden", "javaPackageName", "x")
    );

    assertThat(content.read(files, context(), allKeysRenderer))
      .asString(UTF_8)
      .isEqualTo("overridden / x");
    assertThat(context().get()).containsEntry("packageName", "com.test.myapp");
  }

  @Test
  void shouldGetRawContentForNotTemplatedFile() throws IOException {
    Seed4JFileContent content = content("/generator/client/vue/webapp/content/images/seed4j_logo-name.png");

    assertThat(content.read(files, context(), templateRenderer)).isEqualTo(
      Files.readAllBytes(
        Path.of("../seed4j-core-modules/src/main/resources/generator/client/vue/webapp/content/images/seed4j_logo-name.png")
      )
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
