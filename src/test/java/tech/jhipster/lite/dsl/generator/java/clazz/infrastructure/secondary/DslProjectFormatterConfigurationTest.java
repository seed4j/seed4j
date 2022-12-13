package tech.jhipster.lite.dsl.generator.java.clazz.infrastructure.secondary;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import ch.qos.logback.classic.Level;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.LogsSpy;
import tech.jhipster.lite.UnitTest;

@UnitTest
@ExtendWith({ MockitoExtension.class, LogsSpy.class })
class DslProjectFormatterConfigurationTest {

  private static final DslProjectFormatterConfiguration configuration = new DslProjectFormatterConfiguration();

  @Mock
  private DslNpmInstallationReader npmInstallation;

  private final LogsSpy logs;

  public DslProjectFormatterConfigurationTest(LogsSpy logs) {
    this.logs = logs;
  }

  @Test
  void shouldGetTraceFormatterWithoutInstalledNpm() {
    when(npmInstallation.get()).thenReturn(NpmInstallationType.NONE);

    assertThat(configuration.dslProjectFormatter(npmInstallation)).isExactlyInstanceOf(TraceProjectFormatter.class);
    logs.shouldHave(Level.INFO, "Using trace formatter");
  }

  @Test
  void shouldGetUnixFormatterForUnixInstallation() {
    when(npmInstallation.get()).thenReturn(NpmInstallationType.UNIX);

    NpmProjectFormatter formatter = (NpmProjectFormatter) configuration.dslProjectFormatter(npmInstallation);

    assertThat(formatter.command()).isEqualTo("npm");
    logs.shouldHave(Level.INFO, "Using unix formatter");
  }

  @Test
  void shouldGetWindowsFormatterForWindowsInstallation() {
    when(npmInstallation.get()).thenReturn(NpmInstallationType.WINDOWS);

    NpmProjectFormatter formatter = (NpmProjectFormatter) configuration.dslProjectFormatter(npmInstallation);

    assertThat(formatter.command()).isEqualTo("npm.cmd");
    logs.shouldHave(Level.INFO, "Using windows formatter");
  }
}
