package com.seed4j.shared.error.infrastructure.primary;

import static org.mockito.Mockito.mock;

import ch.qos.logback.classic.Level;
import com.seed4j.Logs;
import com.seed4j.LogsSpy;
import com.seed4j.LogsSpyExtension;
import com.seed4j.UnitTest;
import com.seed4j.shared.error.domain.GeneratorException;
import com.seed4j.shared.error.domain.StandardErrorKey;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.MessageSource;

@UnitTest
@ExtendWith(LogsSpyExtension.class)
class GeneratorErrorsHandlerTest {

  private static final GeneratorErrorsHandler handler = new GeneratorErrorsHandler(mock(MessageSource.class));

  @Logs
  private LogsSpy logs;

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
