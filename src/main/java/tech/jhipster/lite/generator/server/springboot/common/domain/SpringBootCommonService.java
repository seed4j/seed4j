package tech.jhipster.lite.generator.server.springboot.common.domain;

import java.util.Optional;
import tech.jhipster.lite.generator.tools.domain.Project;

public interface SpringBootCommonService {
  void addTestLogbackRecorder(Project project);

  void addProperties(Project project, String key, Object value);
  void addPropertiesLocal(Project project, String key, Object value);
  void addPropertiesTest(Project project, String key, Object value);
  void addPropertiesTestLogging(Project project, String key, Level value);
  void addPropertiesNewLine(Project project);
  void addPropertiesLocalNewLine(Project project);
  void addPropertiesTestNewLine(Project project);
  void addPropertiesTestLoggingNewLine(Project project);
  void addPropertiesComment(Project project, String text);
  void addPropertiesLocalComment(Project project, String text);
  void addPropertiesTestComment(Project project, String text);
  void addPropertiesTestLoggingComment(Project project, String text);

  void addLogger(Project project, String packageName, Level level);
  void addLoggerTest(Project project, String packageName, Level level);

  Optional<String> getProperty(Project project, String key);
  boolean isSetWithMySQLOrMariaDBDatabase(Project project);
  boolean isDatabaseUseSequences(Project project);

  void updateIntegrationTestAnnotation(Project project, String className);
}
