package com.seed4j.module.infrastructure.secondary.javabuild;

import static com.seed4j.module.domain.Seed4JModule.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import com.seed4j.UnitTest;
import com.seed4j.module.domain.file.Seed4JModuleFiles;
import com.seed4j.module.domain.file.Seed4JModuleFiles.Seed4JModuleFilesBuilder;
import com.seed4j.module.domain.javabuild.JavaBuildTool;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@UnitTest
class FileSystemProjectJavaBuildToolRepositoryTest {

  private final FileSystemProjectJavaBuildToolRepository javaBuildTools = new FileSystemProjectJavaBuildToolRepository();

  @Nested
  class DetectFromSeed4JModuleFiles {

    private final Seed4JModuleFilesBuilder moduleFiles = Seed4JModuleFiles.builder(mock(Seed4JModuleBuilder.class));

    @Test
    void shouldReturnMavenWhenPomXmlIsAdded() {
      moduleFiles.add(from("pom.xml"), to("pom.xml"));

      Optional<JavaBuildTool> javaBuildTool = javaBuildTools.detect(moduleFiles.build());

      assertThat(javaBuildTool).contains(JavaBuildTool.MAVEN);
    }

    @Test
    void shouldReturnMavenWhenPomXmlIsMoved() {
      moduleFiles.move(path("pom.xml.old"), to("pom.xml"));

      Optional<JavaBuildTool> javaBuildTool = javaBuildTools.detect(moduleFiles.build());

      assertThat(javaBuildTool).contains(JavaBuildTool.MAVEN);
    }

    @Test
    void shouldReturnGradleWhenBuildGradleFileIsAdded() {
      moduleFiles.add(from("build.gradle.kts"), to("build.gradle.kts"));

      Optional<JavaBuildTool> javaBuildTool = javaBuildTools.detect(moduleFiles.build());

      assertThat(javaBuildTool).contains(JavaBuildTool.GRADLE);
    }

    @Test
    void shouldReturnGradleWhenBuildGradleFileIsMoved() {
      moduleFiles.move(path("build.gradle"), to("build.gradle.kts"));

      Optional<JavaBuildTool> javaBuildTool = javaBuildTools.detect(moduleFiles.build());

      assertThat(javaBuildTool).contains(JavaBuildTool.GRADLE);
    }

    @Test
    void shouldReturnEmptyWhenNoJavaBuildToolIsAdded() {
      Optional<JavaBuildTool> javaBuildTool = javaBuildTools.detect(moduleFiles.build());

      assertThat(javaBuildTool).isEmpty();
    }
  }
}
