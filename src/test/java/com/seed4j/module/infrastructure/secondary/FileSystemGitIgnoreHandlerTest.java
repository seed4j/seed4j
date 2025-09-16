package com.seed4j.module.infrastructure.secondary;

import static com.seed4j.TestFileUtils.projectFrom;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModulesFixture.allProperties;
import static org.assertj.core.api.Assertions.assertThat;

import com.seed4j.UnitTest;
import com.seed4j.module.domain.file.TemplateRenderer;
import com.seed4j.module.domain.gitignore.Seed4JModuleGitIgnore;
import com.seed4j.module.domain.properties.Seed4JProjectFolder;
import org.junit.jupiter.api.Test;

@UnitTest
class FileSystemGitIgnoreHandlerTest {

  private final FileSystemGitIgnoreHandler handler = new FileSystemGitIgnoreHandler(new FileSystemReplacer(TemplateRenderer.NOOP));

  @Test
  void shouldNotCreateGitIgnoreFileIfPatternsAreEmpty() {
    Seed4JProjectFolder projectFolder = projectFrom("src/test/resources/projects/empty");

    handler.handle(projectFolder, Seed4JModuleGitIgnore.builder(moduleBuilder(allProperties())).build());

    assertThat(projectFolder.filePath(".gitignore")).doesNotExist();
  }

  @Test
  void shouldAutomaticallyCreateMissingGitIgnoreFileIfPatternsIsNotEmpty() {
    Seed4JProjectFolder projectFolder = projectFrom("src/test/resources/projects/empty");

    handler.handle(projectFolder, Seed4JModuleGitIgnore.builder(moduleBuilder(allProperties())).pattern("target/").build());

    assertThat(projectFolder.filePath(".gitignore")).exists().content().contains("target/");
  }

  @Test
  void shouldNotAddAgainAnExistingEntry() {
    Seed4JProjectFolder projectFolder = projectFrom("src/test/resources/projects/empty");
    handler.handle(projectFolder, Seed4JModuleGitIgnore.builder(moduleBuilder(allProperties())).pattern("target/").build());

    handler.handle(projectFolder, Seed4JModuleGitIgnore.builder(moduleBuilder(allProperties())).pattern("target/").build());

    assertThat(projectFolder.filePath(".gitignore")).content().containsOnlyOnce("target/");
  }
}
