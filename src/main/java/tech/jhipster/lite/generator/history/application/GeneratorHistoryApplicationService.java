package tech.jhipster.lite.generator.history.application;

import java.util.List;
import org.springframework.stereotype.Service;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.generator.history.domain.GeneratorHistoryRepository;
import tech.jhipster.lite.generator.history.domain.GeneratorHistoryValue;
import tech.jhipster.lite.generator.project.domain.Project;

@Service
public record GeneratorHistoryApplicationService(GeneratorHistoryRepository generatorHistoryRepository) {
  public void addHistoryValue(Project project, GeneratorHistoryValue generatorHistoryValue) {
    generatorHistoryRepository.addHistoryValue(project, generatorHistoryValue);
  }

  public List<GeneratorHistoryValue> getValues(Project project) {
    Assert.notNull("project", project);
    return generatorHistoryRepository.getHistoryData(project).getValues();
  }
}
