package tech.jhipster.lite.module.domain.properties;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;

@UnitTest
class JHipsterServerPortTest {

  @Test
  void shouldGetDefaultServerPortFromNullPort() {
    assertThat(new JHipsterServerPort(null).get()).isEqualTo(8080);
  }

  @Test
  void shouldGetServerPortFromPort() {
    assertThat(new JHipsterServerPort(9000).get()).isEqualTo(9000);
  }

  @Test
  void testToString() {
    //Given
    final JHipsterServerPort normal = new JHipsterServerPort(null);
    //When //Then
    assertThat(normal.toString()).isEqualTo("default");

    //Given
    final JHipsterServerPort other = new JHipsterServerPort(9000);
    //When //Then
    assertThat(other.toString()).isEqualTo("9000");
  }
}
