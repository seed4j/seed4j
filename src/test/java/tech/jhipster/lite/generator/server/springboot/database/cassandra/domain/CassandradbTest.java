package tech.jhipster.lite.generator.server.springboot.database.cassandra.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;

@UnitTest
class CassandradbTest {

  @Test
  void shouldGetDockerImage() {
    assertThat(Cassandra.getDockerImageName()).isEqualTo("cassandra:3.11.12");
  }

  @Test
  void shouldGetYml() {
    assertThat(Cassandra.getYmlFiles()).isEqualTo(List.of("app", "cassandra-migration"));
  }

  @Test
  void shouldGetScripts() {
    assertThat(Cassandra.getScripts()).isEqualTo(List.of("autoMigrate", "execute-cql"));
  }
}
