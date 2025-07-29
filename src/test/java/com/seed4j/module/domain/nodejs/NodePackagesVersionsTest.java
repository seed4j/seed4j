package com.seed4j.module.domain.nodejs;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.seed4j.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@UnitTest
class NodePackagesVersionsTest {

  private static final NodePackagesVersionSource COMMON = JHLiteNodePackagesVersionSource.COMMON.build();
  private static final NodePackagesVersionSource VUE = JHLiteNodePackagesVersionSource.VUE.build();

  @Test
  void shouldNotVersionFromUnknownSource() {
    NodePackagesVersions versions = NodePackagesVersions.builder().build();

    assertThatThrownBy(() -> versions.get(new NodePackageName("unknown"), COMMON))
      .isExactlyInstanceOf(UnknownNodePackageException.class)
      .hasMessageContaining("unknown")
      .hasMessageContaining("common");
  }

  @Test
  void shouldNotGetUnknownPackageVersion() {
    NodePackagesVersions versions = NodePackagesVersions.builder().put(COMMON, List.of(new NodePackage("vue", "1.2.3"))).build();

    assertThatThrownBy(() -> versions.get(new NodePackageName("unknown"), COMMON))
      .isExactlyInstanceOf(UnknownNodePackageException.class)
      .hasMessageContaining("unknown")
      .hasMessageContaining("common");
  }

  @Test
  void shouldGetVersion() {
    NodePackagesVersions versions = NodePackagesVersions.builder()
      .put(COMMON, List.of(new NodePackage("vue", "1.2.3")))
      .put(VUE, List.of(new NodePackage("vue", "1.2.4")))
      .build();

    assertThat(versions.get(new NodePackageName("vue"), VUE)).isEqualTo(new NodePackageVersion("1.2.4"));
    assertThat(versions.get(new NodePackageName("vue"), COMMON)).isEqualTo(new NodePackageVersion("1.2.3"));
  }

  @Nested
  class Merge {

    public static final NodePackageName TYPESCRIPT = new NodePackageName("typescript");
    public static final NodePackageName PRETTIER = new NodePackageName("prettier");

    @Test
    void shouldMergePackagesInSameSource() {
      NodePackagesVersions first = NodePackagesVersions.builder().put(COMMON, List.of(new NodePackage(TYPESCRIPT, "1.0.0"))).build();
      NodePackagesVersions second = NodePackagesVersions.builder().put(COMMON, List.of(new NodePackage(PRETTIER, "2.0.0"))).build();

      assertThat(first.merge(second).get(TYPESCRIPT, COMMON)).isEqualTo(new NodePackageVersion("1.0.0"));
      assertThat(first.merge(second).get(PRETTIER, COMMON)).isEqualTo(new NodePackageVersion("2.0.0"));

      assertThat(second.merge(first).get(TYPESCRIPT, COMMON)).isEqualTo(new NodePackageVersion("1.0.0"));
      assertThat(second.merge(first).get(PRETTIER, COMMON)).isEqualTo(new NodePackageVersion("2.0.0"));
    }

    @Test
    void shouldMergeDifferentVersionsInSameSource() {
      NodePackagesVersions first = NodePackagesVersions.builder().put(COMMON, List.of(new NodePackage(TYPESCRIPT, "1.2.3"))).build();
      NodePackagesVersions second = NodePackagesVersions.builder().put(COMMON, List.of(new NodePackage(TYPESCRIPT, "1.2.6"))).build();

      assertThat(first.merge(second).get(TYPESCRIPT, COMMON)).isEqualTo(new NodePackageVersion("1.2.3"));
      assertThat(second.merge(first).get(TYPESCRIPT, COMMON)).isEqualTo(new NodePackageVersion("1.2.6"));
    }

    @Test
    void shouldNotMuteOperands() {
      NodePackagesVersions versions = NodePackagesVersions.builder().put(COMMON, List.of(new NodePackage(TYPESCRIPT, "1.2.3"))).build();
      NodePackagesVersions other = NodePackagesVersions.builder().put(COMMON, List.of(new NodePackage(TYPESCRIPT, "1.2.6"))).build();

      versions.merge(other);
      assertThat(versions.get(TYPESCRIPT, COMMON)).isEqualTo(new NodePackageVersion("1.2.3"));
      assertThat(other.get(TYPESCRIPT, COMMON)).isEqualTo(new NodePackageVersion("1.2.6"));
    }

    @Test
    void shouldNotMergeWhenSourceIsDistinct() {
      NodePackagesVersions versions = NodePackagesVersions.builder().put(COMMON, List.of(new NodePackage(TYPESCRIPT, "1.2.3"))).build();
      NodePackagesVersions other = NodePackagesVersions.builder().put(VUE, List.of(new NodePackage(TYPESCRIPT, "1.2.6"))).build();

      NodePackagesVersions merged = versions.merge(other);

      assertThat(merged.get(TYPESCRIPT, COMMON)).isEqualTo(new NodePackageVersion("1.2.3"));
      assertThat(merged.get(TYPESCRIPT, VUE)).isEqualTo(new NodePackageVersion("1.2.6"));
    }
  }
}
