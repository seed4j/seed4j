package tech.jhipster.lite.merge.impl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.merge.Merge;
import tech.jhipster.lite.merge.Verify;

@UnitTest
class MergeTest {

  /**
   * Test demonstrates that first 'gen' consists of jpa configuration. 'password='
   * <p>
   * Then the user assigns password=demo
   * <p>
   * And finally jLite adds server port to 'gen' + 'spring.application.name'
   * <p>
   * This must result in {@link BodyBuilder#makeMergedBody()} with all elements correct merge.
   */

  @Test
  void mergeFirstGenNoPwdCustomAddPasswordSecondGenAddLogAndPortThenExpectOk() {
    //Given
    Diamond diamond = Diamond.of(
      """
      #====================================================================
      # Standard Spring Boot properties.
      # Full reference is available at:
      # http://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html
      #====================================================================

      logging.level.com.mycompany.myapp=INFO
      spring.jpa.properties.hibernate.jdbc.batch_size=25
      spring.jpa.properties.hibernate.order_updates=true
      spring.datasource.password=
      spring.jpa.hibernate.ddl-auto=none
      #
      """,
      """
      #====================================================================
      # Standard Spring Boot properties.
      # Full reference is available at:
      # http://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html
      #====================================================================

      spring.application.name=JhipsterSampleApplication

      logging.level.com.mycompany.myapp=INFO
      spring.jpa.properties.hibernate.jdbc.batch_size=25
      spring.jpa.properties.hibernate.order_updates=true
      spring.datasource.password=
      spring.jpa.hibernate.ddl-auto=none
      server.port=8080
      #
      """,
      """
      #====================================================================
      # Standard Spring Boot properties.
      # Full reference is available at:
      # http://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html
      #====================================================================

      logging.level.com.mycompany.myapp=INFO
      spring.jpa.properties.hibernate.jdbc.batch_size=25
      spring.jpa.properties.hibernate.order_updates=true
      spring.datasource.password=demo
      spring.jpa.hibernate.ddl-auto=none
      #
      """
    );
    //When
    final Verify.Situation rs = Verify.by(diamond).verify();
    //Then
    Assertions.assertThat(rs).isEqualTo(Verify.Situation.MUST_MERGE);
    //When
    final Merge.Rs performed = Merge.by(diamond).merge();

    Assertions
      .assertThat(performed.target())
      .isEqualTo(
        Body.of(
          """
          #====================================================================
          # Standard Spring Boot properties.
          # Full reference is available at:
          # http://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html
          #====================================================================

          spring.application.name=JhipsterSampleApplication

          logging.level.com.mycompany.myapp=INFO
          spring.jpa.properties.hibernate.jdbc.batch_size=25
          spring.jpa.properties.hibernate.order_updates=true
          spring.datasource.password=demo
          spring.jpa.hibernate.ddl-auto=none
          server.port=8080
          #
          """
        )
      );
  }
}
