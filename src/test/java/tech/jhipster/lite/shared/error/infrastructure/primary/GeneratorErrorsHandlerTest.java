package tech.jhipster.lite.shared.error.infrastructure.primary;

import static org.mockito.Mockito.*;

import ch.qos.logback.classic.Level;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.MessageSource;
import tech.jhipster.lite.LogsSpy;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.shared.error.domain.GeneratorException;
import tech.jhipster.lite.shared.error.domain.StandardErrorKey;

@UnitTest
@ExtendWith(LogsSpy.class)
class GeneratorErrorsHandlerTest {

  private static final GeneratorErrorsHandler handler = new GeneratorErrorsHandler(mock(MessageSource.class));

  private final LogsSpy logs;

  public GeneratorErrorsHandlerTest(LogsSpy logs) {
    this.logs = logs;
  }

  @Test
  void shouldLogServerErrorAsError() {
    handler.handleGeneratorException(
      GeneratorException.internalServerError(StandardErrorKey.INTERNAL_SERVER_ERROR).message("Oops").build()
    );

    logs.shouldHave(Level.ERROR, "Oops");
  }

  @Test
  void shouldLogClientErrorAsInfo() {
    handler.handleGeneratorException(GeneratorException.badRequest(StandardErrorKey.BAD_REQUEST).message("Oops").build());

    logs.shouldHave(Level.INFO, "Oops");
  }
}
