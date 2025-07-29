package com.seed4j.module.infrastructure.secondary;

import static com.seed4j.module.domain.JHipsterModulesFixture.*;
import static com.seed4j.module.domain.replacement.ReplacementCondition.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import ch.qos.logback.classic.Level;
import com.seed4j.Logs;
import com.seed4j.LogsSpy;
import com.seed4j.LogsSpyExtension;
import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.GeneratedProjectRepository;
import com.seed4j.module.domain.JHipsterProjectFilePath;
import com.seed4j.module.domain.file.TemplateRenderer;
import com.seed4j.module.domain.properties.JHipsterProjectFolder;
import com.seed4j.module.domain.replacement.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@UnitTest
@ExtendWith(LogsSpyExtension.class)
class FileSystemReplacerTest {

  private static final FileSystemReplacer replacer = new FileSystemReplacer(TemplateRenderer.NOOP);

  @Logs
  private LogsSpy logs;

  @Test
  void shouldHandleMandatoryReplacementError() {
    String path = TestFileUtils.tmpDirForTest();

    assertThatThrownBy(() ->
      replacer.handle(
        new JHipsterProjectFolder(path),
        new ContentReplacers(
          JHipsterModuleMandatoryReplacementsFactory.builder(emptyModuleBuilder())
            .in(new JHipsterProjectFilePath("unknown"))
            .add(new TextReplacer(always(), "old"), "new")
            .and()
            .build()
            .replacers()
            .toList()
        ),
        emptyModuleContext()
      )
    ).isExactlyInstanceOf(MandatoryReplacementException.class);
  }

  @Test
  void shouldHandleOptionalReplacementError() {
    String path = TestFileUtils.tmpDirForTest();

    assertThatCode(() ->
      replacer.handle(
        new JHipsterProjectFolder(path),
        new ContentReplacers(
          JHipsterModuleOptionalReplacementsFactory.builder(emptyModuleBuilder())
            .in(new JHipsterProjectFilePath("unknown"))
            .add(new TextReplacer(always(), "old"), "new")
            .and()
            .build()
            .buildReplacers(new JHipsterProjectFolder("dummy"), mock(GeneratedProjectRepository.class))
            .toList()
        ),
        emptyModuleContext()
      )
    ).doesNotThrowAnyException();

    logs.shouldHave(Level.DEBUG, "Can't apply optional replacement");
  }
}
