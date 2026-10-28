package com.seed4j.module.domain.packagejson;

import static com.seed4j.module.domain.nodejs.Seed4JNodePackagesVersionSource.COMMON;
import static org.assertj.core.api.Assertions.assertThat;

import com.seed4j.UnitTest;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

@UnitTest
class PackageJsonDependencyTest {

  @Test
  void shouldRespectEqualsContract() {
    EqualsVerifier.forClass(PackageJsonDependency.class).verify();
  }

  @Test
  void shouldGetPackageAndVersionAsString() {
    PackageJsonDependency dependency = PackageJsonDependency.builder()
      .packageName(new PackageName("@vue/test-utils"))
      .versionSource(COMMON.build())
      .versionPackageName(new PackageName("vue"))
      .build();

    assertThat(dependency.toString()).contains("packageName=@vue/test-utils", "versionSource=", "versionPackageName=vue");
  }
}
