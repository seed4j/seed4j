package tech.jhipster.lite.module.domain.npm;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;

@UnitTest
class NpmPackagesVersionsTest {

  private static final NpmVersionSource COMMON = JHLiteNpmVersionSource.COMMON.build();
  private static final NpmVersionSource VUE = JHLiteNpmVersionSource.VUE.build();

  @Test
  void shouldNotVersionFromUnknownSource() {
    NpmPackagesVersions versions = NpmPackagesVersions.builder().build();

    assertThatThrownBy(() -> versions.get(new NpmPackageName("unknown"), COMMON))
      .isExactlyInstanceOf(UnknownNpmPackageException.class)
      .hasMessageContaining("unknown")
      .hasMessageContaining("common");
  }

  @Test
  void shouldNotGetUnknownPackageVersion() {
    NpmPackagesVersions versions = NpmPackagesVersions.builder().put(COMMON, List.of(new NpmPackage("vue", "1.2.3"))).build();

    assertThatThrownBy(() -> versions.get(new NpmPackageName("unknown"), COMMON))
      .isExactlyInstanceOf(UnknownNpmPackageException.class)
      .hasMessageContaining("unknown")
      .hasMessageContaining("common");
  }

  @Test
  void shouldGetVersion() {
    NpmPackagesVersions versions = NpmPackagesVersions.builder()
      .put(COMMON, List.of(new NpmPackage("vue", "1.2.3")))
      .put(VUE, List.of(new NpmPackage("vue", "1.2.4")))
      .build();

    assertThat(versions.get(new NpmPackageName("vue"), VUE)).isEqualTo(new NpmPackageVersion("1.2.4"));
    assertThat(versions.get(new NpmPackageName("vue"), COMMON)).isEqualTo(new NpmPackageVersion("1.2.3"));
  }

  @Nested
  class Merge {

    public static final NpmPackageName TYPESCRIPT = new NpmPackageName("typescript");
    public static final NpmPackageName PRETTIER = new NpmPackageName("prettier");

    @Test
    void shouldMergePackagesInSameSource() {
      NpmPackagesVersions first = NpmPackagesVersions.builder().put(COMMON, List.of(new NpmPackage(TYPESCRIPT, "1.0.0"))).build();
      NpmPackagesVersions second = NpmPackagesVersions.builder().put(COMMON, List.of(new NpmPackage(PRETTIER, "2.0.0"))).build();

      assertThat(first.merge(second).get(TYPESCRIPT, COMMON)).isEqualTo(new NpmPackageVersion("1.0.0"));
      assertThat(first.merge(second).get(PRETTIER, COMMON)).isEqualTo(new NpmPackageVersion("2.0.0"));

      assertThat(second.merge(first).get(TYPESCRIPT, COMMON)).isEqualTo(new NpmPackageVersion("1.0.0"));
      assertThat(second.merge(first).get(PRETTIER, COMMON)).isEqualTo(new NpmPackageVersion("2.0.0"));
    }

    @Test
    void shouldMergeDifferentVersionsInSameSource() {
      NpmPackagesVersions first = NpmPackagesVersions.builder().put(COMMON, List.of(new NpmPackage(TYPESCRIPT, "1.2.3"))).build();
      NpmPackagesVersions second = NpmPackagesVersions.builder().put(COMMON, List.of(new NpmPackage(TYPESCRIPT, "1.2.6"))).build();

      assertThat(first.merge(second).get(TYPESCRIPT, COMMON)).isEqualTo(new NpmPackageVersion("1.2.3"));
      assertThat(second.merge(first).get(TYPESCRIPT, COMMON)).isEqualTo(new NpmPackageVersion("1.2.6"));
    }

    @Test
    void shouldNotMuteOperands() {
      NpmPackagesVersions versions = NpmPackagesVersions.builder().put(COMMON, List.of(new NpmPackage(TYPESCRIPT, "1.2.3"))).build();
      NpmPackagesVersions other = NpmPackagesVersions.builder().put(COMMON, List.of(new NpmPackage(TYPESCRIPT, "1.2.6"))).build();

      versions.merge(other);
      assertThat(versions.get(TYPESCRIPT, COMMON)).isEqualTo(new NpmPackageVersion("1.2.3"));
      assertThat(other.get(TYPESCRIPT, COMMON)).isEqualTo(new NpmPackageVersion("1.2.6"));
    }

    @Test
    void shouldNotMergeWhenSourceIsDistinct() {
      NpmPackagesVersions versions = NpmPackagesVersions.builder().put(COMMON, List.of(new NpmPackage(TYPESCRIPT, "1.2.3"))).build();
      NpmPackagesVersions other = NpmPackagesVersions.builder().put(VUE, List.of(new NpmPackage(TYPESCRIPT, "1.2.6"))).build();

      NpmPackagesVersions merged = versions.merge(other);

      assertThat(merged.get(TYPESCRIPT, COMMON)).isEqualTo(new NpmPackageVersion("1.2.3"));
      assertThat(merged.get(TYPESCRIPT, VUE)).isEqualTo(new NpmPackageVersion("1.2.6"));
    }
  }
}
