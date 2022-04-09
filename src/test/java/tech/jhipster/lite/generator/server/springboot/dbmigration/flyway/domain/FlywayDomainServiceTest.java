package tech.jhipster.lite.generator.server.springboot.dbmigration.flyway.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static tech.jhipster.lite.TestUtils.tmpProject;
import static tech.jhipster.lite.generator.server.springboot.dbmigration.flyway.domain.Flyway.DEFAULT_FLYWAY_ENABLED;
import static tech.jhipster.lite.generator.server.springboot.dbmigration.flyway.domain.Flyway.DEFAULT_FLYWAY_LOCATIONS;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.domain.ProjectRepository;

@UnitTest
@ExtendWith(MockitoExtension.class)
class FlywayDomainServiceTest {

  @Mock
  ProjectRepository projectRepository;

  @Mock
  BuildToolService buildToolService;

  @Mock
  SpringBootCommonService springBootCommonService;

  @Spy
  Clock clock = Clock.fixed(Instant.parse("2022-01-22T14:01:54.954396664Z"), ZoneId.of("Europe/Paris"));

  @InjectMocks
  FlywayDomainService flywayDomainService;

  @Test
  void shouldInit() {
    // Given
    Project project = tmpProject();

    when(buildToolService.getVersion(any(Project.class), anyString())).thenReturn(Optional.of("0.0.0"));
    when(springBootCommonService.isSetWithMySQLOrMariaDBDatabase(project)).thenReturn(false);

    // When
    flywayDomainService.init(project);

    // Then
    ArgumentCaptor<Dependency> dependencyArgCaptor = ArgumentCaptor.forClass(Dependency.class);
    verify(buildToolService).addDependency(eq(project), dependencyArgCaptor.capture());
    Dependency expectedDependency = Dependency
      .builder()
      .groupId("org.flywaydb")
      .artifactId("flyway-core")
      .version("\\${flyway.version}")
      .build();
    assertThat(dependencyArgCaptor.getValue()).usingRecursiveComparison().isEqualTo(expectedDependency);

    verify(projectRepository)
      .add(
        project,
        "server/springboot/dbmigration/flyway/resources",
        "V00000000000000__init.sql",
        "src/main/resources/db/migration",
        "V20220122150154__init.sql"
      );

    verify(springBootCommonService, times(2)).addProperties(any(), anyString(), any());
    verify(springBootCommonService).addProperties(project, "spring.flyway.enabled", DEFAULT_FLYWAY_ENABLED);
    verify(springBootCommonService).addProperties(project, "spring.flyway.locations", DEFAULT_FLYWAY_LOCATIONS);
  }

  @Test
  void shouldNotAddFlywayDependency() {
    Project project = tmpProject();

    Assertions.assertThatThrownBy(() -> flywayDomainService.addFlywayDependency(project)).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldInitWithAdditionalFlywayMysqlMariaDBDependency() {
    // Given
    Project project = tmpProject();

    when(buildToolService.getVersion(any(Project.class), anyString())).thenReturn(Optional.of("0.0.0"));
    when(springBootCommonService.isSetWithMySQLOrMariaDBDatabase(project)).thenReturn(true);

    // When
    flywayDomainService.init(project);

    // Then
    ArgumentCaptor<Dependency> dependencyArgCaptor = ArgumentCaptor.forClass(Dependency.class);
    verify(buildToolService, times(2)).addDependency(eq(project), dependencyArgCaptor.capture());
    List<Dependency> dependencies = dependencyArgCaptor.getAllValues();
    Dependency expectedDependency = Dependency
      .builder()
      .groupId("org.flywaydb")
      .artifactId("flyway-core")
      .version("\\${flyway.version}")
      .build();
    assertThat(dependencies.get(0)).usingRecursiveComparison().isEqualTo(expectedDependency);

    Dependency expectedMysqlDependency = Dependency
      .builder()
      .groupId("org.flywaydb")
      .artifactId("flyway-mysql")
      .version("\\${flyway.version}")
      .build();
    assertThat(dependencies.get(1)).usingRecursiveComparison().isEqualTo(expectedMysqlDependency);

    verify(projectRepository)
      .add(
        project,
        "server/springboot/dbmigration/flyway/resources",
        "V00000000000000__init.sql",
        "src/main/resources/db/migration",
        "V20220122150154__init.sql"
      );

    verify(springBootCommonService, times(2)).addProperties(any(), anyString(), any());
    verify(springBootCommonService).addProperties(project, "spring.flyway.enabled", DEFAULT_FLYWAY_ENABLED);
    verify(springBootCommonService).addProperties(project, "spring.flyway.locations", DEFAULT_FLYWAY_LOCATIONS);
  }

  @Test
  void shouldAddUserAuthorityChangelogWithSequence() {
    // Given
    Project project = tmpProject();

    when(springBootCommonService.isDatabaseUseSequences(project)).thenReturn(true);

    // When
    flywayDomainService.addUserAuthorityChangelog(project);

    // Then
    verify(projectRepository)
      .add(
        project,
        "server/springboot/dbmigration/flyway/resources/user",
        "V00000000000000__create_user_authority_tables_postgresql.sql",
        "src/main/resources/db/migration",
        "V20220122150155__create_user_authority_tables.sql"
      );
  }

  @Test
  void shouldAddUserAuthorityChangelogWithoutSequence() {
    // Given
    Project project = tmpProject();

    when(springBootCommonService.isDatabaseUseSequences(project)).thenReturn(false);

    // When
    flywayDomainService.addUserAuthorityChangelog(project);

    // Then
    verify(projectRepository)
      .add(
        project,
        "server/springboot/dbmigration/flyway/resources/user",
        "V00000000000000__create_user_authority_tables.sql",
        "src/main/resources/db/migration",
        "V20220122150155__create_user_authority_tables.sql"
      );
  }
}
