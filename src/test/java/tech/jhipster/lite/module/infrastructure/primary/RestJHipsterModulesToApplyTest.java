package tech.jhipster.lite.module.infrastructure.primary;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static tech.jhipster.lite.BeanValidationAssertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.JsonHelper;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.shared.projectfolder.domain.ProjectFolder;

@UnitTest
class RestJHipsterModulesToApplyTest {

  private static final ProjectFolder projectFolder = mock(ProjectFolder.class);

  @Test
  void shouldDeserializeFromJson() {
    assertThat(JsonHelper.readFromJson(json(), RestJHipsterModulesToApply.class).toDomain(projectFolder, List.of()))
      .usingRecursiveComparison()
      .isEqualTo(JHipsterModulesFixture.modulesToApply());
  }

  private static String json() {
    return """
      {
      "modules": ["maven-java", "init"],
      "properties":
        {
          "projectFolder": "/dummy",
          "parameters": {
            "projectName": "Chips Project",
            "baseName": "chips",
            "packageName": "tech.jhipster.chips",
            "serverPort": 8080
          }
        }
    }
    """;
  }

  @Test
  void shouldValidateModulesToApplyWithoutAttributes() {
    assertThatBean(new RestJHipsterModulesToApply(null, null)).hasInvalidProperty("modules").and().hasInvalidProperty("properties");
  }

  @Test
  void shouldValidateModulesToApplyWithEmptyModules() {
    assertThatBean(new RestJHipsterModulesToApply(List.of(), null)).hasInvalidProperty("modules");
  }
}
