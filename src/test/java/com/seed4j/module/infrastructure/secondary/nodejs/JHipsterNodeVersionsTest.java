package com.seed4j.module.infrastructure.secondary.nodejs;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.seed4j.UnitTest;
import com.seed4j.module.domain.nodejs.JHLiteNodePackagesVersionSource;
import com.seed4j.module.domain.nodejs.NodePackage;
import com.seed4j.module.domain.nodejs.NodePackageName;
import com.seed4j.module.domain.nodejs.NodePackageVersion;
import com.seed4j.module.domain.nodejs.NodePackagesVersionSource;
import com.seed4j.module.domain.nodejs.NodePackagesVersions;
import com.seed4j.module.domain.nodejs.UnknownNodePackageException;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class JHipsterNodeVersionsTest {

  private static final NodePackagesVersionSource COMMON = JHLiteNodePackagesVersionSource.COMMON.build();
  private static final NodePackagesVersionSource VUE = JHLiteNodePackagesVersionSource.VUE.build();

  @Test
  void shouldNotReadVersionWithoutReaders() {
    JHipsterNodeVersions versions = new JHipsterNodeVersions(List.of());

    assertThatThrownBy(() -> versions.get(new NodePackageName("unknown"), COMMON))
      .isExactlyInstanceOf(UnknownNodePackageException.class)
      .hasMessageContaining("unknown");
  }

  @Test
  void shouldNotReadUnknownVersion() {
    JHipsterNodeVersions versions = new JHipsterNodeVersions(List.of(emptyReader()));

    assertThatThrownBy(() -> versions.get(new NodePackageName("unknown"), COMMON))
      .isExactlyInstanceOf(UnknownNodePackageException.class)
      .hasMessageContaining("unknown");
  }

  private NodePackagesVersionsReader emptyReader() {
    return () -> NodePackagesVersions.EMPTY;
  }

  @Test
  void shouldReadFirstReadableVersion() {
    NodePackagesVersionsReader firstReader = () ->
      NodePackagesVersions.builder().put(COMMON, packages(new NodePackage("vue", "1.2.3"))).build();
    NodePackagesVersionsReader secondReader = () ->
      NodePackagesVersions.builder()
        .put(COMMON, packages(new NodePackage("vue", "1.2.7")))
        .put(VUE, packages(new NodePackage("vue", "1.2.7")))
        .build();

    JHipsterNodeVersions versions = new JHipsterNodeVersions(List.of(emptyReader(), firstReader, secondReader));

    assertThat(versions.get("vue", COMMON)).isEqualTo(new NodePackageVersion("1.2.3"));
    assertThat(versions.get("vue", JHLiteNodePackagesVersionSource.VUE)).isEqualTo(new NodePackageVersion("1.2.7"));
  }

  private static Collection<NodePackage> packages(NodePackage... packages) {
    return List.of(packages);
  }
}
