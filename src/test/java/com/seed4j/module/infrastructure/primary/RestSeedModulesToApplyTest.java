package com.seed4j.module.infrastructure.primary;

import static com.seed4j.BeanValidationAssertions.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.seed4j.JsonHelper;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModulesFixture;
import com.seed4j.shared.projectfolder.domain.ProjectFolder;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class RestSeedModulesToApplyTest {

  private static final ProjectFolder projectFolder = mock(ProjectFolder.class);

  @Test
  void shouldDeserializeFromJson() {
    assertThat(JsonHelper.readFromJson(json(), RestSeedModulesToApply.class).toDomain(projectFolder))
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
            "projectName": "Growth Project",
            "baseName": "growth",
            "packageName": "com.seed4j.growth",
            "serverPort": 8080
          }
        }
    }
    """;
  }

  @Test
  void shouldValidateModulesToApplyWithoutAttributes() {
    assertThatBean(new RestSeedModulesToApply(null, null)).hasInvalidProperty("modules").and().hasInvalidProperty("properties");
  }

  @Test
  void shouldValidateModulesToApplyWithEmptyModules() {
    assertThatBean(new RestSeedModulesToApply(List.of(), null)).hasInvalidProperty("modules");
  }
}
