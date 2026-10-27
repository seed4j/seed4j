package com.seed4j.module.infrastructure.secondary.javadependency;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.seed4j.UnitTest;
import com.seed4j.module.domain.ProjectFiles;
import com.seed4j.module.domain.javabuild.VersionSlug;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;

@UnitTest
class FileSystemMavenDependenciesReaderTest {

  private FileSystemMavenDependenciesReader reader;

  @BeforeEach
  void loadReader() {
    ProjectFiles files = mock(ProjectFiles.class);
    when(files.readString(anyString())).thenAnswer(fileContent());

    reader = new FileSystemMavenDependenciesReader(files, "/generator/dependencies/pom.xml");
  }

  private Answer<String> fileContent() {
    return invocation -> Files.readString(Path.of("src/main/resources/" + invocation.getArgument(0, String.class)));
  }

  @Test
  void shouldReadJavaDependencies() {
    assertThat(reader.get().get(new VersionSlug("json-web-token"))).isNotNull();
  }
}
