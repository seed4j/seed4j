package tech.jhipster.lite.module.infrastructure.secondary.npm;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.npm.*;

@UnitTest
class JHipsterNpmVersionsTest {

  private static final NpmVersionSource COMMON = JHLiteNpmVersionSource.COMMON.build();
  private static final NpmVersionSource VUE = JHLiteNpmVersionSource.VUE.build();

  @Test
  void shouldNotReadVersionWithoutReaders() {
    JHipsterNpmVersions versions = new JHipsterNpmVersions(List.of());

    assertThatThrownBy(() -> versions.get(new NpmPackageName("unknown"), COMMON))
      .isExactlyInstanceOf(UnknownNpmPackageException.class)
      .hasMessageContaining("unknown");
  }

  @Test
  void shouldNotReadUnknownVersion() {
    JHipsterNpmVersions versions = new JHipsterNpmVersions(List.of(emptyReader()));

    assertThatThrownBy(() -> versions.get(new NpmPackageName("unknown"), COMMON))
      .isExactlyInstanceOf(UnknownNpmPackageException.class)
      .hasMessageContaining("unknown");
  }

  private NpmVersionsReader emptyReader() {
    return () -> NpmPackagesVersions.EMPTY;
  }

  @Test
  void shouldReadFirstReadableVersion() {
    NpmVersionsReader firstReader = () -> NpmPackagesVersions.builder().put(COMMON, packages(new NpmPackage("vue", "1.2.3"))).build();
    NpmVersionsReader secondReader = () ->
      NpmPackagesVersions.builder()
        .put(COMMON, packages(new NpmPackage("vue", "1.2.7")))
        .put(VUE, packages(new NpmPackage("vue", "1.2.7")))
        .build();

    JHipsterNpmVersions versions = new JHipsterNpmVersions(List.of(emptyReader(), firstReader, secondReader));

    assertThat(versions.get("vue", COMMON)).isEqualTo(new NpmPackageVersion("1.2.3"));
    assertThat(versions.get("vue", JHLiteNpmVersionSource.VUE)).isEqualTo(new NpmPackageVersion("1.2.7"));
  }

  private static Collection<NpmPackage> packages(NpmPackage... packages) {
    return List.of(packages);
  }
}
