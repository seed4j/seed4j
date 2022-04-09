package tech.jhipster.lite.generator.history.domain;

import tech.jhipster.lite.generator.tools.domain.Project;

public interface GeneratorHistoryRepository {
  GeneratorHistoryData getHistoryData(Project project);

  void addHistoryValue(Project project, GeneratorHistoryValue generatorHistoryValue);
}
