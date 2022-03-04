package tech.jhipster.lite.generator.server.springboot.database.cassandra.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;

@UnitTest
public class CassandradbTest {

  @Test
  public void shouldGetDockerImage() {
    assertThat(Cassandra.getDockerImageName()).isEqualTo("cassandra:3.11.12");
  }

  @Test
  public void shouldGetYml() {
    assertThat(Cassandra.getYmlFiles()).isEqualTo(List.of("app", "cassandra-migration"));
  }

  @Test
  public void shouldGetScripts() {
    assertThat(Cassandra.getScripts()).isEqualTo(List.of("autoMigrate", "execute-cql"));
  }
}
