package tech.jhipster.lite.generator.history.infrastructure.primary;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static tech.jhipster.lite.generator.history.infrastructure.primary.dto.HistoryDTOTest.values;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.generator.history.application.GeneratorHistoryApplicationService;
import tech.jhipster.lite.generator.history.domain.GeneratorHistoryValue;
import tech.jhipster.lite.generator.history.infrastructure.primary.dto.HistoryDTO;
import tech.jhipster.lite.generator.tools.domain.Project;

@UnitTest
@ExtendWith(SpringExtension.class)
class HistoryResourceTest {

  @Mock
  private GeneratorHistoryApplicationService service;

  @InjectMocks
  private HistoryResource resource;

  @Test
  void shouldGetHistoryFromService() {
    final List<GeneratorHistoryValue> values = values();
    when(service.getValues(any(Project.class))).thenReturn(values);

    ResponseEntity<HistoryDTO> result = resource.get("/tmp/chips");

    assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(result.getBody()).usingRecursiveComparison().isEqualTo(HistoryDTO.from(values));
  }
}
