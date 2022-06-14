package tech.jhipster.lite.generator.client.vue.security.jwt.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static tech.jhipster.lite.TestUtils.tmpProjectWithPackageJson;
import static tech.jhipster.lite.TestUtils.tmpProjectWithPackageJsonPinia;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.error.domain.MissingMandatoryValueException;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectFile;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

@UnitTest
@ExtendWith(MockitoExtension.class)
class VueJwtDomainServiceTest {

  @Mock
  ProjectRepository projectRepository;

  @InjectMocks
  private VueJwtDomainService jwtDomainService;

  @Test
  void shouldAddVueJwt() {
    Project project = tmpProjectWithPackageJsonPinia();

    assertThatCode(() -> jwtDomainService.addJWT(project)).doesNotThrowAnyException();

    verify(projectRepository, times(3)).template(any(ProjectFile.class));
  }

  @Test
  void shouldNotAddVueJwt() {
    Project project = tmpProjectWithPackageJson();

    assertThatThrownBy(() -> jwtDomainService.addJWT(project)).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldNotaddVueJwtWhenNoProject() {
    assertThatThrownBy(() -> jwtDomainService.addJWT(null))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("project");
  }
}
