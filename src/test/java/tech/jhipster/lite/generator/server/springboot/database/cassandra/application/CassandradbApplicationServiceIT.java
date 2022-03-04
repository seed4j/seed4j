package tech.jhipster.lite.generator.server.springboot.database.cassandra.application;

import static tech.jhipster.lite.TestUtils.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.buildtool.maven.application.MavenApplicationService;
import tech.jhipster.lite.generator.init.application.InitApplicationService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.springboot.core.application.SpringBootApplicationService;

@IntegrationTest
@ExtendWith(MockitoExtension.class)
public class CassandradbApplicationServiceIT {

  public final String DATABASE_PATH = "src/main/docker/";

  @Autowired
  CassandraApplicationService cassandraApplicationService;

  @Autowired
  InitApplicationService initApplicationService;

  @Autowired
  MavenApplicationService mavenApplicationService;

  @Autowired
  SpringBootApplicationService springBootApplicationService;

  @Test
  @DisplayName("Should create Cassandra files")
  void shouldInit() {
    Project project = tmpProject();
    initApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    cassandraApplicationService.init(project);

    //Check if files exists
    assertFileExist(project, DATABASE_PATH + "cassandra.yml");
    assertFileExist(project, DATABASE_PATH + "app.yml");
    assertFileExist(project, DATABASE_PATH + "cassandra-migration.yml");
    assertFileExist(project, DATABASE_PATH + "cassandra/Cassandra-Migration.Dockerfile");
    assertFileExist(project, DATABASE_PATH + "cassandra/scripts/autoMigrate.sh");
    assertFileExist(project, DATABASE_PATH + "cassandra/scripts/execute-cql.sh");

    //Check if files have content
    //assertFileContent(project,DATABASE_PATH+"cassandra.yml",List.of("image: cassandra:3.11.12","dockerfile: cassandra/Cassandra-Migration.Dockerfile"));
    assertFileContent(project, DATABASE_PATH + "app.yml", "- CREATE_KEYSPACE_SCRIPT=create-keyspace-prod.cql");
    assertFileContent(project, DATABASE_PATH + "cassandra-migration.yml", "- USER=docker-cassandra-migration");
    assertFileContent(
      project,
      DATABASE_PATH + "cassandra/Cassandra-Migration.Dockerfile",
      "ADD cassandra/scripts/autoMigrate.sh /usr/local/bin/autoMigrate"
    );
    assertFileContent(
      project,
      DATABASE_PATH + "cassandra/scripts/autoMigrate.sh",
      "log \"Use $CREATE_KEYSPACE_SCRIPT_FOLDER/$CREATE_KEYSPACE_SCRIPT script to create the keyspace if necessary\""
    );
    assertFileContent(
      project,
      DATABASE_PATH + "cassandra/scripts/execute-cql.sh",
      "cqlsh -k $KEYSPACE_NAME -e \"$query\" $CASSANDRA_CONTACT_POINT"
    );
  }
}
