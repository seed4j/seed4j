package tech.jhipster.lite.dsl.common.infrastructure.primary;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.dsl.common.application.JHipsterDslApplicationService;
import tech.jhipster.lite.projectfolder.domain.FreeProjectFolder;

@UnitTest
class DslResourceTest {

  @Test
  void shouldThrowIOException() {
    JHipsterDslApplicationService jHipsterDslApplicationService = mock(JHipsterDslApplicationService.class);

    DslResource dslResource = new DslResource(jHipsterDslApplicationService, new FreeProjectFolder());

    assertThatThrownBy(() ->
        dslResource.applyDsl(
          "{\"projectFolder\":\"test\"}}",
          new CustomMockMultipartFile("foo", "foo.txt", MediaType.TEXT_PLAIN_VALUE, "hello".getBytes())
        )
      )
      .isExactlyInstanceOf(InvalidDslFileException.class);
  }

  private class CustomMockMultipartFile extends MockMultipartFile {

    public CustomMockMultipartFile(String name, String originalFilename, String contentType, byte[] content) {
      super(name, originalFilename, contentType, content);
    }

    @Override
    public byte[] getBytes() throws IOException {
      throw new IOException();
    }
  }
}
