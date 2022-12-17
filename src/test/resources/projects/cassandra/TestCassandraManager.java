package com.jhipster.test;

import com.datastax.oss.driver.api.core.CqlSession;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.testcontainers.containers.CassandraContainer;

class TestCassandraManager implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

  private static final String KEYSPACE = "testKeyspace";
  private CassandraContainer cassandraContainer;
  private static final Integer CONTAINER_STARTUP_TIMEOUT_MINUTES = 10;

  @Override
  public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
    if (cassandraContainer != null) {
      return;
    }
    createCassandraContainer();

    if (cassandraContainer.isRunning()) {
      return;
    }
    cassandraContainer.start();

    CqlSession session = getCqlSession();
    createTestKeyspace(session);
    registerEnvironmentVariables();

    Runtime.getRuntime().addShutdownHook(new Thread(stopContainer()));
  }

  private void registerEnvironmentVariables() {
    System.setProperty("TEST_CASSANDRA_PORT", String.valueOf(cassandraContainer.getMappedPort(CassandraContainer.CQL_PORT)));
    System.setProperty("TEST_CASSANDRA_CONTACT_POINT", cassandraContainer.getHost());
    System.setProperty("TEST_CASSANDRA_DC", cassandraContainer.getLocalDatacenter());
    System.setProperty("TEST_CASSANDRA_KEYSPACE", KEYSPACE);
  }

  private void createTestKeyspace(CqlSession session) {
    String createQuery = "CREATE KEYSPACE " + KEYSPACE + " WITH replication={'class' : 'SimpleStrategy', 'replication_factor':1}";
    session.execute(createQuery);
  }

  private CqlSession getCqlSession() {
    return CqlSession
      .builder()
      .addContactPoint(cassandraContainer.getContactPoint())
      .withLocalDatacenter(cassandraContainer.getLocalDatacenter())
      .build();
  }

  private void createCassandraContainer() {
    cassandraContainer =
      (CassandraContainer) new CassandraContainer("4.0.7")
        .withStartupTimeout(Duration.of(CONTAINER_STARTUP_TIMEOUT_MINUTES, ChronoUnit.MINUTES))
        .withExposedPorts(CassandraContainer.CQL_PORT);
  }

  private Runnable stopContainer() {
    return cassandraContainer::stop;
  }
}
