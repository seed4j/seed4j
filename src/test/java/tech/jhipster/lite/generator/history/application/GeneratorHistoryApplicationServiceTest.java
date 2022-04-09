package tech.jhipster.lite.generator.history.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;
import static tech.jhipster.lite.TestUtils.tmpProject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.error.domain.MissingMandatoryValueException;
import tech.jhipster.lite.generator.history.domain.GeneratorHistoryData;
import tech.jhipster.lite.generator.history.domain.GeneratorHistoryRepository;
import tech.jhipster.lite.generator.tools.domain.Project;

@UnitTest
@ExtendWith(MockitoExtension.class)
class GeneratorHistoryApplicationServiceTest {

  @InjectMocks
  GeneratorHistoryApplicationService generatorHistoryApplicationService;

  @Mock
  GeneratorHistoryRepository generatorHistoryRepository;

  @Test
  void shouldNotGetHistoryWithoutProject() {
    assertThatThrownBy(() -> generatorHistoryApplicationService.getValues(null))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("project");
  }

  @Test
  void shouldGetHistory() {
    final Project project = tmpProject();
    final GeneratorHistoryData historyData = new GeneratorHistoryData();
    when(generatorHistoryRepository.getHistoryData(project)).thenReturn(historyData);

    assertThat(generatorHistoryApplicationService.getValues(project)).isEqualTo(historyData.getValues());
  }
}
