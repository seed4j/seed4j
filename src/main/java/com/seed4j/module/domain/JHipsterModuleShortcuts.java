package com.seed4j.module.domain;

import static com.seed4j.module.domain.JHipsterModule.SeedModuleBuilder;
import static com.seed4j.module.domain.JHipsterModule.lineAfterRegex;
import static com.seed4j.module.domain.JHipsterModule.lineAfterText;
import static com.seed4j.module.domain.JHipsterModule.lineBeforeText;
import static com.seed4j.module.domain.JHipsterModule.path;
import static com.seed4j.module.domain.JHipsterModule.regex;
import static com.seed4j.module.domain.JHipsterModule.to;
import static com.seed4j.module.domain.replacement.ReplacementCondition.always;

import com.seed4j.module.domain.file.SeedSource;
import com.seed4j.module.domain.replacement.OptionalReplacer;
import com.seed4j.module.domain.replacement.TextNeedleAfterReplacer;
import com.seed4j.module.domain.replacement.TextNeedleBeforeReplacer;
import com.seed4j.shared.error.domain.Assert;
import java.util.function.Consumer;
import java.util.regex.Pattern;

final class JHipsterModuleShortcuts {

  private static final SeedProjectFilePath README = path("README.md");
  private static final TextNeedleBeforeReplacer JHIPSTER_DOCUMENTATION_NEEDLE = lineBeforeText("\n<!-- seed4j-needle-documentation -->");
  private static final TextNeedleBeforeReplacer JHIPSTER_LOCAL_ENVIRONMENT_NEEDLE = lineBeforeText(
    "\n<!-- seed4j-needle-localEnvironment -->"
  );

  private static final TextNeedleAfterReplacer JHIPSTER_PREREQUISITES = lineAfterText("\n## Prerequisites");

  private static final SeedProjectFilePath SPRING_MAIN_LOG_FILE = path("src/main/resources/logback-spring.xml");
  private static final SeedProjectFilePath SPRING_TEST_LOG_FILE = path("src/test/resources/logback.xml");
  private static final TextNeedleBeforeReplacer JHIPSTER_LOGGER_NEEDLE = lineBeforeText("<!-- seed4j-needle-logback-add-log -->");

  private static final Pattern DEFAULT_LINTSTAGED_CONFIGURATION_ENTRY = Pattern.compile("\\s*'\\*': \\[\\s*].*");

  private JHipsterModuleShortcuts() {}

  static Consumer<SeedModuleBuilder> documentation(DocumentationTitle title, SeedSource source) {
    Assert.notNull("title", title);
    Assert.notNull("source", source);

    return builder -> {
      String target = "documentation/" + title.filename() + source.extension();
      builder.files().add(source, to(target));

      String markdownLink = "- [" + title.get() + "](" + target + ")";
      builder.optionalReplacements().in(README).add(JHIPSTER_DOCUMENTATION_NEEDLE, markdownLink);
    };
  }

  static Consumer<SeedModuleBuilder> localEnvironment(LocalEnvironment localEnvironment) {
    Assert.notNull("localEnvironment", localEnvironment);
    return builder -> builder.optionalReplacements().in(README).add(JHIPSTER_LOCAL_ENVIRONMENT_NEEDLE, localEnvironment.get());
  }

  static Consumer<SeedModuleBuilder> prerequisites(String prerequisites) {
    Assert.notBlank("prerequisites", prerequisites);
    return builder -> builder.optionalReplacements().in(README).add(JHIPSTER_PREREQUISITES, prerequisites);
  }

  static Consumer<SeedModuleBuilder> springTestLogger(String name, LogLevel level) {
    Assert.notBlank("name", name);
    Assert.notNull("level", level);

    return builder ->
      builder.optionalReplacements().in(SPRING_TEST_LOG_FILE).add(logConfigurationEntry(name, level, builder.indentation()));
  }

  static Consumer<SeedModuleBuilder> springMainLogger(String name, LogLevel level) {
    Assert.notBlank("name", name);
    Assert.notNull("level", level);

    return builder ->
      builder.optionalReplacements().in(SPRING_MAIN_LOG_FILE).add(logConfigurationEntry(name, level, builder.indentation()));
  }

  private static OptionalReplacer logConfigurationEntry(String name, LogLevel level, Indentation indentation) {
    return new OptionalReplacer(JHIPSTER_LOGGER_NEEDLE, logger(name, level, indentation));
  }

  private static String logger(String name, LogLevel level, Indentation indentation) {
    return new StringBuilder()
      .append(indentation.spaces())
      .append("<logger name=\"")
      .append(name)
      .append("\" level=\"")
      .append(level.value())
      .append("\" />")
      .toString();
  }

  static Consumer<SeedModuleBuilder> integrationTestExtension(String extensionClass) {
    Assert.notBlank("extensionClass", extensionClass);

    return builder ->
      builder
        .mandatoryReplacements()
        .in(path("src/test/java").append(builder.packagePath()).append("IntegrationTest.java"))
        .add(
          lineBeforeText("import org.springframework.boot.test.context.SpringBootTest;"),
          "import org.junit.jupiter.api.extension.ExtendWith;"
        )
        .add(lineBeforeText("public @interface"), "@ExtendWith(" + extensionClass + ".class)");
  }

  static Consumer<SeedModuleBuilder> preCommitActions(StagedFilesFilter stagedFilesFilter, PreCommitCommands preCommitCommands) {
    Assert.notNull("stagedFilesFilter", stagedFilesFilter);
    Assert.notNull("preCommitCommands", preCommitCommands);

    return builder -> {
      String newLintStagedConfigurationEntry = "%s'%s': %s,".formatted(
        builder.properties().indentation().times(1),
        stagedFilesFilter,
        preCommitCommands
      );

      builder
        .optionalReplacements()
        .in(path(".lintstagedrc.cjs"))
        .add(regex(always(), DEFAULT_LINTSTAGED_CONFIGURATION_ENTRY), "")
        .add(lineAfterRegex("module.exports = \\{"), newLintStagedConfigurationEntry);
    };
  }
}
